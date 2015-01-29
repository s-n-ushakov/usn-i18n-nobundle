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
 * <p>This {@link I18nHandler} subclass is almost ready-to-use to implement the
 * approach with user locale preferences being stored locally, allowing single
 * user per application instance.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>You may optionally wish to further subclass this class to override the
 * {@link #getDefaultLocaleTag()} method.</p>
 */
public class I18nHandlerForSingleUser
    extends I18nHandler
  {
    /**
     * An array of {@link Locale#toLanguageTag() locale language tags} for
     * preferred locales for this {@link I18nHandler} subclass instance,
     * typically for a user. To be set via the
     * {@link #setUserLocaleTags(String[])} method. Defaults to an empty array,
     * so to yield falling back to the application default locale.
     */
    private String [] userLocaleTags = new String [0];

    /**
     * The method to be used for setting arrays of preferred
     * {@link Locale#toLanguageTag() locale language tags}, typically on
     * per-user basis.
     * 
     * @param userLocaleTags an array of user preferred locales represented by
     *                       their {@link Locale#toLanguageTag() language tags}
     */
    public void setUserLocaleTags (String [] userLocaleTags)
      {
        this.userLocaleTags = userLocaleTags;
      } // setUserLocaleTags

    /**
     * An implementation of the method to get an array of user preferred
     * {@link Locale#toLanguageTag() locale language tags} as a copy of locally
     * stored per-user setting.
     * 
     * @return a copy of the locally assigned array of
     *         {@link Locale#toLanguageTag() language tags}, falling back to the
     *         application default locale if empty
     */
    @Override
    protected String [] getUserLocaleTags ()
      {
        return (this.userLocaleTags.length != 0) ?
                 this.userLocaleTags.clone () : super.getUserLocaleTags ();
      } // getUserLocaleTags

  } // class I18nHandlerForSingleUser
