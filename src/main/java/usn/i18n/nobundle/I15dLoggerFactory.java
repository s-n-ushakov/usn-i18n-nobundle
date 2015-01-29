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

import org.slf4j.LoggerFactory;

/**
 * <p>An internationalized (i15d) logger factory class for <a
 * href="http://www.slf4j.org/">SLF4J</a>.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>This is essentially a wrapper around a
 * {@link org.slf4j.LoggerFactory LoggerFactory} producing {@link I15dLogger}
 * instances. Rewritten mainly after
 * {@link org.slf4j.cal10n.LocLoggerFactory}.</p>
 * 
 * <p>See {@linkplain usn.i18n.nobundle package info} for a usage example.</p>
 */
public class I15dLoggerFactory
  {
    /**
     * Get an I15dLogger instance by name.
     * 
     * @param name logger instance name
     * @return I15dLogger instance for name provided
     */
    public static I15dLogger getLogger (String name)
      {
        return new I15dLogger (LoggerFactory.getLogger (name));
      } // getLogger

    /**
     * Get an I15dLogger instance by class. The returned logger will be named
     * after the class.
     * 
     * @param clazz the class to get a logger for
     * @return I15dLogger instance for class provided
     */
    public static I15dLogger getLogger (Class<?> clazz)
      {
        return getLogger (clazz.getName ());
      } // getLogger

  } // class I15dLoggerFactory
