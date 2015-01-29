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
 * <p>A utility class to facilitate construction and identification of localized
 * messages, with messages being allowed to take
 * {@link java.text.MessageFormat MessageFormat}-style arguments upon being
 * used.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>See {@linkplain usn.i18n.nobundle package info} for a usage example.</p>
 */
public class LocalizedMessage
  {
    /**
     * Locale identifier, as per {@link Locale#toLanguageTag() locale language
     * tag}
     */
    String localeTag;
    /**
     * A localized message, optionally containing formatting argument
     * placeholders, as per {@link java.text.MessageFormat MessageFormat}
     */
    String message;

    /**
     * The standard constructor.
     * 
     * @param localeTag the {@link Locale#toLanguageTag() locale language tag}
     * @param message the localized message, optionally containing formatting
     *                argument placeholders, as per
     *                {@link java.text.MessageFormat MessageFormat}
     */
    LocalizedMessage (String localeTag, String message)
      {
        this.localeTag = localeTag;
        this.message = message;
      } // LocalizedMessage

    /**
     * A "syntactic sugar" shorthand method to create a {@link LocalizedMessage}
     * instance.
     * 
     * @param localeTag the {@link Locale#toLanguageTag() locale language tag}
     * @param message the localized message, optionally containing formatting
     *                argument placeholders, as per
     *                {@link java.text.MessageFormat MessageFormat}
     * @return a new {@link LocalizedMessage} instance
     */
    public static LocalizedMessage lm (String localeTag, String message)
      {
        return new LocalizedMessage (localeTag, message);
      } // lm

  } // class LocalizedMessage
