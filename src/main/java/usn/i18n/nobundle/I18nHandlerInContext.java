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

import java.util.Locale;

/**
 * <p>The intermediate handler class in the hierarchy to implement an approach
 * when user preferences may be retrieved from an arbitrary generic
 * {@code TContext} instance; to be subclassed by an application.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>An application using this approach would normally subclass this class
 * and:</p>
 * <ul>
 * <li>override the {@link #getUserLocaleTags(Object)
 * getUserLocaleTags(TContext)} method;</li>
 * <li>optionally override the {@link #getDefaultLocaleTag()} method;</li>
 * </ul>
 * 
 * <p>See {@linkplain usn.i18n.nobundle package info} for a usage example.</p>
 *
 * @param <TContext> an arbitrary class that might be used to retrieve user
 *                   locale preferences from
 */
public class I18nHandlerInContext<TContext>
    extends I18nHandler
  {
    /**
     * A utility method to be used by {@link I18nItemInContext} subclasses to
     * find the best locale suitable for given user for given
     * {@link I18nItemInContext} instance.
     * 
     * @param contextData an instance of context-specific data to retrieve user
     *                    preferences from
     * @param item an {@link I18nItemInContext} instance to be served
     * @return {@link Locale#toLanguageTag() language tag} for the best suitable
     *         locale found
     */
    protected String findBestLocaleTag (TContext contextData,
                                        I18nItemInContext<TContext> item)
      {
        return this.findBestLocaleTag (item, getUserLocaleTags (contextData));
      } // findBestLocaleTag

    // ---- methods to be overridden by subclasses -----------------------------

    /**
     * The method to be used internally to obtain an array of user's preferred
     * locales, identified via their
     * {@link Locale#toLanguageTag() language tags}, best preferred coming
     * first. The default implementation just calls the corresponding
     * superclass' argumentless method. To be overridden to implement an
     * application-specific approach to user locale preferences. A subclass is
     * expected to retrieve user locale preferences via the {@code contextData}
     * argument.
     * 
     * @param contextData an instance of context-specific data to retrieve user
     *                    preferences from
     * @return an array of {@link Locale#toLanguageTag() locale language tags}
     *         for user's preferred locales, best preferred coming first
     */
    protected String [] getUserLocaleTags (TContext contextData)
      {
        return super.getUserLocaleTags ();
      } // getUserLocaleTags

  } // class I18nHandlerInContext
