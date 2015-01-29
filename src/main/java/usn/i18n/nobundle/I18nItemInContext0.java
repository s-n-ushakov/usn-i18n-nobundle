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

/**
 * <p>A class that implements multi-locale resource without formatting
 * arguments, to be used together with {@link I18nHandlerInContext}
 * subclasses.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>See {@linkplain usn.i18n.nobundle package info} for a usage example.</p>
 * 
 * @param <TContext> the same class as {@code TContext} for
 *                   {@link I18nHandlerInContext} subclass being used
 */
public class I18nItemInContext0<TContext>
    extends I18nItemInContext<TContext>
  {
    /**
     * The public constructor.
     * 
     * @param data a varargs array of translations for a message to various
     *             locales
     */
    public I18nItemInContext0 (LocalizedMessage... data)
      {
        super (data);
      } // I18nItemInContext0

    /**
     * The method to obtain a message in a locale that is best preferred for
     * the current user.
     * 
     * @param contextData an instance of context-specific data to retrieve user
     *                    preferences from
     * @return a formatted user locale specific message
     */
    public String s (TContext contextData)
      {
        return this.getBestLocaleAndMessage (contextData).message;
      } // s

  } // class I18nItemInContext0
