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

import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.ServletRequest;

/**
 * <p>This {@link I18nHandlerInContext} subclass is ready-to-use to implement
 * the approach with user locale preferences being retrieved from
 * {@link javax.servlet.ServletRequest ServletRequest} {@code Accept-Language}
 * headers.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>You may optionally wish to further subclass this class to override the
 * {@link #getDefaultLocaleTag()} method. You may also wish to subclass this
 * class for retrieving locale preferences for authenticated users via some
 * {@link javax.servlet.http.HttpSession HttpSession} attribute.</p>
 */
public class I18nHandlerForServletRequest
    extends I18nHandlerInContext<ServletRequest>
  {
    /**
     * An implementation of the method to get an array of user preferred
     * locales from a {@link javax.servlet.ServletRequest ServletRequest}
     * instance via {@code Accept-Language} headers.
     * 
     * @param contextData a {@link javax.servlet.ServletRequest ServletRequest}
     *                    instance to retrieve user preferences from
     * @return an array of {@link Locale#toLanguageTag() locale language tags}
     *         as specified by the {@code ServletRequest}, falling back to the
     *         application default locale if empty
     */
    @Override
    protected String [] getUserLocaleTags (ServletRequest contextData)
      {
        Vector<String> localeTags = new Vector<String> ();
        Enumeration<Locale> locales = contextData.getLocales ();
        while (locales.hasMoreElements ())
          {
            Locale locale = locales.nextElement ();
            String localeTag = locale.toLanguageTag ();
            if (!localeTag.isEmpty ())
              {
                localeTags.add (localeTag);
              }
          }
        return (localeTags.size () != 0) ?
            localeTags.toArray (new String [0]) :
            super.getUserLocaleTags (contextData);
      } // getUserLocaleTags

  } // class I18nHandlerForServletRequest
