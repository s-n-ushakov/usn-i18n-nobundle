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

import java.text.MessageFormat;

import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * <p>The intermediate internationalized message storage and formatting class in
 * the hierarchy to implement an approach when user preferences may be retrieved
 * from an arbitrary generic {@code TContext} instance; not intended to be used
 * or subclassed by applications directly.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>See {@linkplain usn.i18n.nobundle package info} for a usage example.</p>
 * 
 * @param <TContext> the same class as {@code TContext} for
 *                   {@link I18nHandlerInContext} subclass being used
 */
public class I18nItemInContext<TContext>
    extends I18nItem
  {
    /**
     * The public constructor.
     * 
     * @param data a varargs array of translations for a message to various
     *             locales
     */
    public I18nItemInContext (LocalizedMessage... data)
      {
        super (data);
      } // I18nItemInContext

    /**
     * Find the best match, exact or approximate, among locales available for
     * this item, for current user. Tries to find exact match for user
     * preferences first, then approximate match for language part of user
     * preferences only, then for application default locale, then for any
     * English flavor, and finally tries to return just anything available. In
     * case of several locales available for one language in this item, and only
     * language being considered, the last locale supplied upon item
     * construction wins. The method is exposed as public for cases when an
     * application needs just information about best appropriate locale.
     * 
     * @param contextData an instance of context-specific data to retrieve user
     *                    preferences from
     * @return the best match among available locales, represented by its
     *         {@link Locale#toLanguageTag() locale language tag}
     * @throws NoSuchElementException should this instance happen to be empty
     */
    public String getBestLocaleTag (TContext contextData)
        throws NoSuchElementException // not required, just to be documented...
      {
        @SuppressWarnings ("unchecked")   // explicit type cast
        I18nHandlerInContext<TContext> handler =
            (I18nHandlerInContext<TContext>) I18nHandler.getHandler ();
        return handler.findBestLocaleTag (contextData, this);
      } // getBestLocaleTag

    /**
     * Similar to {@link #getBestLocaleTag(Object) #getBestLocaleTag(TContext)},
     * but returns just the language field for the locale found. The method is
     * not used internally and is exposed as public for cases when an
     * application needs just information about best appropriate language.
     * 
     * @param contextData an instance of context-specific data to retrieve user
     *                    preferences from
     * @return the language field for the best match among available locales
     * @throws NoSuchElementException should this instance happen to be empty
     */
    public String getBestLanguage (TContext contextData)
        throws NoSuchElementException // not required, just to be documented...
      {
        String bestLocaleTag = this.getBestLocaleTag (contextData);
        // use I18nHandler locale cache to obtain an appropriate Locale instance
        Locale locale = I18nHandler.localeForLanguageTag (bestLocaleTag);
        return locale.getLanguage ();
      } // getBestLanguage

    /**
     * A utility method to find both the best locale suitable for given user for
     * given {@code I18nItemInContext} instance, and the appropriate message,
     * all represented as a {@link LocalizedMessage} instance.
     * 
     * @param contextData an instance of context-specific data to retrieve user
     *                    preferences from
     * @return a new {@link LocalizedMessage} instance for the best locale guess
     */
    protected LocalizedMessage getBestLocaleAndMessage (TContext contextData)
      {
        String bestLocaleTag = this.getBestLocaleTag (contextData);
        return new LocalizedMessage (bestLocaleTag,
                                     this.messages.get (bestLocaleTag));
      } // getBestLocaleAndMessage

    /**
     * The method to obtain a localized {@link MessageFormat} instance to be
     * used by subclasses that take message formatting arguments.
     * 
     * @param contextData an instance of context-specific data to retrieve user
     *                    preferences from
     * @return a localized {@link MessageFormat} instance
     */
    protected MessageFormat obtainMessageFormat (TContext contextData)
      {
        LocalizedMessage localizedMessage =
            this.getBestLocaleAndMessage (contextData);
        String pattern = localizedMessage.message;
        Locale locale =
            I18nHandler.localeForLanguageTag (localizedMessage.localeTag);
        return new MessageFormat (pattern, locale);
      } // obtainMessageFormat

  } // class I18nItemInContext
