/*
 * Copyright (c) 2013, Sergey Ushakov, <s-n-ushakov@yandex.ru>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * See http://opensource.org/licenses/BSD-2-Clause for reference.
 */

package usn.i18n.nobundle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <p>The basic handler class in the hierarchy that implements an approach when
 * user preferences are either retrieved from the application or stored locally
 * in single-user-per-application-instance cases; to be subclassed by an
 * application.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2015-01-19
 * 
 * <p>This class specifies the most basic handler methods. It also declares and
 * sustains an {@code I18nHandler} singleton to be used by {@link I18nItem}
 * subclasses.</p>
 * 
 * <p>An application would normally subclass this class and:</p>
 * <ul>
 * <li>override the {@link #getUserLocaleTags()} method;</li>
 * <li>optionally override the {@link #getDefaultLocaleTag()} method;</li>
 * </ul>
 * 
 * <p>See {@linkplain usn.i18n.nobundle package info} for a usage example.</p>
 */
public class I18nHandler
  {
    /**
     * The application-specific {@code I18nHandler} subclass instance to be
     * used. Established by the constructor.
     */
    private static I18nHandler handler = null;

    /**
     * The default locale to be used by an application, represented by its
     * {@link Locale#toLanguageTag() language tag}. To be accessed and possibly
     * overridden via the {@link #getDefaultLocaleTag()} method.
     */
    private static final String defaultLocaleTag =
        Locale.getDefault ().toLanguageTag ();

    /**
     * The local locale cache, arranged to avoid repeated calls for
     * {@link Locale#forLanguageTag(String)}.
     */
    private static final Map<String, Locale> localeCache =
        new HashMap<String, Locale> ();

    /**
     * The no-argument constructor to be used by subclasses. Checks and assigns
     * the {@code I18nHandler} singleton.
     */
    protected I18nHandler ()
      {
        if (handler != null)
          {
            throw new IllegalStateException
              ("An I18nHandler instance is already in place.");
          }
        handler = this;
      } // I18nHandler

    /**
     * The method to be used by {@link I18nItem} subclasses for obtaining a
     * reference to the {@code I18nHandler} singleton.
     * 
     * @return reference to the 'handler' singleton
     */
    static I18nHandler getHandler ()
      {
          return handler;
      } // getHandler

    /**
     * The procedure to query and populate the local locale cache, indexed by
     * language tags; made public to allow {@link I18nItem} subclassing.
     * 
     * @param localeTag the {@link Locale#toLanguageTag() locale language tag}
     *                  to query for
     * @return a {@link Locale} instance, either from cache or new
     */
    public static Locale localeForLanguageTag (String localeTag)
      {
        if (localeCache.containsKey (localeTag))
            {
              return localeCache.get (localeTag);
            }
          else
            {
              Locale locale = Locale.forLanguageTag (localeTag);
              localeCache.put (localeTag, locale);
              return locale;
            }
      } // localeForLanguageTag

    /**
     * The core method to be used by {@code I18nHandler} subclasses to find
     * the best locale suitable for given user for given {@link I18nItem}
     * instance, with user locale preferences already having been identified.
     * Tries to find a match for user preferences first, either exact match for
     * user preferred locale or approximate match for language part thereof,
     * then for application default locale, then for any English flavor, and
     * finally tries to return just anything available. In case of several
     * locales available for one language in the {@link I18nItem} instance, and
     * only language being matched, the last locale supplied upon {@code item}
     * construction wins.
     * 
     * @param item an internationalized message instance
     * @param userLocaleTags an array of user preferred locales represented by
     *                       their {@link Locale#toLanguageTag() language tags},
     *                       best preferred coming first
     * @return {@link Locale#toLanguageTag() language tag} for the best suitable
     *         locale found
     * @throws NoSuchElementException should the {@code item} happen to be empty
     */
    protected String findBestLocaleTag
        (I18nItem item, String [] userLocaleTags)
        throws NoSuchElementException // not required, just to be documented...
      {
        // construct the language-to-locale mapping lazily for the item
        if (item.localeTagsForLanguages == null)
          {
            item.localeTagsForLanguages = new HashMap<String, String> ();
            for (String localeTag : item.messages.keySet ())
              {
                // and it's the last locale tag for a given language to win...
                item.localeTagsForLanguages.put
                  (localeForLanguageTag (localeTag).getLanguage (),
                   localeTag);
              }
          }
        // construct the overall collection of locales to try for this item;
        // use LinkedHashSet as a collection that preserves addition order and
        // avoids duplicates
        LinkedHashSet<String> localeTagsToTry =
            new LinkedHashSet<String> (Arrays.asList (userLocaleTags));
        localeTagsToTry.add (this.getDefaultLocaleTag ());
        localeTagsToTry.add (Locale.ENGLISH.toLanguageTag ());
        // declare a variable for the result
        String bestLocaleTag = null;
        // try every locale in the collection in the order of preference
        for (String localeTag : localeTagsToTry)
          {
            // first try every locale exactly
            if (item.messages.containsKey (localeTag))
              {
                bestLocaleTag = localeTag;
                break;
              }
            // and if no luck, try approximate match via language only
            String language = localeForLanguageTag (localeTag).getLanguage ();
            if (item.localeTagsForLanguages.containsKey (language))
              {
                bestLocaleTag = item.localeTagsForLanguages.get (language);
                break;
              }
          }
        // if no luck then try just any locale available for this item
        if (bestLocaleTag == null)
          {
            // this may occasionally throw java.util.NoSuchElementException ...
            bestLocaleTag =  item.messages.keySet ().iterator ().next ();
          }
        return bestLocaleTag;
      } // findBestLocaleTag

    /**
     * A utility method to be used by {@link I18nItem} subclasses to find the
     * best locale suitable for given user for given {@link I18nItem}
     * instance.
     * 
     * @param item an {@link I18nItem} instance to be served
     * @return {@link Locale#toLanguageTag() language tag} for the best suitable
     *         locale found
     * @throws NoSuchElementException should the {@code item} happen to be empty
     */
    protected String findBestLocaleTag (I18nItem item)
        throws NoSuchElementException // not required, just to be documented...
      {
        return this.findBestLocaleTag (item, this.getUserLocaleTags ());
      } // findBestLocaleTag

    // ---- methods to be overridden by subclasses -----------------------------

    /**
     * The method to be used internally to obtain the application-wide default
     * locale; to be overridden, should the application-wide default locale
     * be different from the system locale.
     * 
     * @return the {@link Locale#toLanguageTag() locale language tag} for
     *         default locale of an application
     */
    protected String getDefaultLocaleTag ()
      {
        return defaultLocaleTag;
      } // getDefaultLocaleTag

    /**
     * The method to be used internally to obtain an array of user's preferred
     * locales, identified via their
     * {@link Locale#toLanguageTag() language tags}, best preferred coming
     * first; to be overridden to implement an application-specific approach to
     * user locale preferences. A subclass may retrieve user locale preferences
     * either by querying the application or simply storing user preferences
     * locally like {@link I18nHandlerForSingleUser}.
     * 
     * @return an array of {@link Locale#toLanguageTag() locale language tags}
     *         for user's preferred locales, best preferred coming first
     */
    protected String [] getUserLocaleTags ()
      {
        return new String [] { this.getDefaultLocaleTag () };
      } // getUserLocaleTags

  } // class I18nHandler
