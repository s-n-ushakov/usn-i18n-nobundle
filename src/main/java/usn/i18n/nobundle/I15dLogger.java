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

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import org.slf4j.ext.LoggerWrapper;

/**
 * <p>An internationalized (i15d) logger class for <a
 * href="http://www.slf4j.org/">SLF4J</a>.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2015-01-17
 * 
 * <p>Rewritten mainly after {@link org.slf4j.cal10n.LocLogger}, with <a
 * href="http://permalink.gmane.org/gmane.comp.java.logback.user/3218"
 * >discussion on {@code Marker}</a> taken into consideration. Takes care of
 * necessary early checks for logging being enabled at required level, performs
 * i18n-specific actions and then delegates to {@link LoggerWrapper} as
 * superclass.</p>
 * 
 * <p>{@link Throwable} instances may be added to arguments of the logging
 * methods at the last position as the general rule, as per the <a
 * href="http://www.slf4j.org/faq.html#paramException">approach</a> taken by
 * {@link org.slf4j.cal10n.LocLogger}.</p>
 * 
 * <p>See {@linkplain usn.i18n.nobundle package info} for a usage example.</p>
 */
public class I15dLogger
    extends LoggerWrapper
  {
    /**
     * The marker for every localized message logged by logger instances. It
     * allows marker-aware implementations to perform additional processing on
     * localized messages. Implemented after {@link org.slf4j.cal10n.LocLogger}.
     */
    protected static Marker LOCALIZED = MarkerFactory.getMarker ("LOCALIZED");
    
    /**
     * The class to hold classified logging arguments, to implement the approach
     * to throwables.
     */
    protected static class ClassifiedArgs
      {
        Throwable throwable;
        Object [] args;
      } // class ClassifiedArgs

    /**
     * The constructor to be used by logger factories.
     * 
     * @param logger an underlying {@link Logger} instance
     */
    I15dLogger (Logger logger)
      {
        super (logger, I15dLogger.class.getName ());
      } // I15dLogger

    /**
     * Sort the logging arguments to extract a {@link Throwable} instance at the
     * last position, if present.
     * 
     * @param args logging arguments to sort
     * @return a {@link ClassifiedArgs} instance with a {@link Throwable}
     *         argument separated, if any
     */
    protected static ClassifiedArgs classifyArgs (Object... args)
      {
        ClassifiedArgs classifiedArgs = new ClassifiedArgs ();
        if (args.length == 0)
            {
              classifiedArgs.throwable = null;
              classifiedArgs.args = args;
            }
          else
            {
              Object lastArg = args [args.length - 1];
              if (lastArg instanceof Throwable)
                  {
                    classifiedArgs.throwable = (Throwable) lastArg;
                    classifiedArgs.args = Arrays.copyOf (args, args.length - 1);
                  }
                else
                  {
                    classifiedArgs.throwable = null;
                    classifiedArgs.args = args;
                  }
            }
        return classifiedArgs;
      } // classifyArgs

    /**
     * Log a localized message at the TRACE level without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     */
    public void trace (I18nItem0 item)
      {
        if (!this.logger.isTraceEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.trace (LOCALIZED, translatedMsg);
      } // trace

    /**
     * Log a localized message at the TRACE level with a {@link Throwable} and
     * without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     * @param t a {@link Throwable} instance to log
     */
    public void trace (I18nItem0 item, Throwable t)
      {
        if (!this.logger.isTraceEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.trace (LOCALIZED, translatedMsg, t);
      } // trace

    /**
     * Log a localized message at the TRACE level with arbitrary number of
     * message formatting arguments. Allows the last argument to be a
     * {@link Throwable} and handled appropriately as per <a
     * href="http://www.slf4j.org/faq.html#paramException">SLF4J approach</a>.
     * 
     * @param item an {@link I18nItemAny} instance to be used as message
     *        template
     * @param args optional arguments
     */
    public void trace (I18nItemAny item, Object... args)
      {
        if (!this.logger.isTraceEnabled (LOCALIZED))
          {
            return;
          }
        ClassifiedArgs sortedArgs = classifyArgs (args);
        String translatedMsg = item.s (sortedArgs.args);
        super.trace (LOCALIZED, translatedMsg, sortedArgs.throwable);
      } // trace

    /**
     * Log a localized message at the DEBUG level without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     */
    public void debug (I18nItem0 item)
      {
        if (!this.logger.isDebugEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.debug (LOCALIZED, translatedMsg);
      } // debug

    /**
     * Log a localized message at the DEBUG level with a {@link Throwable} and
     * without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     * @param t a {@link Throwable} instance to log
     */
    public void debug (I18nItem0 item, Throwable t)
      {
        if (!this.logger.isDebugEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.debug (LOCALIZED, translatedMsg, t);
      } // debug

    /**
     * Log a localized message at the DEBUG level with arbitrary number of
     * message formatting arguments. Allows the last argument to be a
     * {@link Throwable} and handled appropriately as per <a
     * href="http://www.slf4j.org/faq.html#paramException">SLF4J approach</a>.
     * 
     * @param item an {@link I18nItemAny} instance to be used as message
     *        template
     * @param args optional arguments
     */
    public void debug (I18nItemAny item, Object... args)
      {
        if (!this.logger.isDebugEnabled (LOCALIZED))
          {
            return;
          }
        ClassifiedArgs sortedArgs = classifyArgs (args);
        String translatedMsg = item.s (sortedArgs.args);
        super.debug (LOCALIZED, translatedMsg, sortedArgs.throwable);
      } // debug

    /**
     * Log a localized message at the INFO level without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     */
    public void info (I18nItem0 item)
      {
        if (!this.logger.isInfoEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.info (LOCALIZED, translatedMsg);
      } // info

    /**
     * Log a localized message at the INFO level with a {@link Throwable} and
     * without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     * @param t a {@link Throwable} instance to log
     */
    public void info (I18nItem0 item, Throwable t)
      {
        if (!this.logger.isInfoEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.info (LOCALIZED, translatedMsg, t);
      } // info

    /**
     * Log a localized message at the INFO level with arbitrary number of
     * message formatting arguments. Allows the last argument to be a
     * {@link Throwable} and handled appropriately as per <a
     * href="http://www.slf4j.org/faq.html#paramException">SLF4J approach</a>.
     * 
     * @param item an {@link I18nItemAny} instance to be used as message
     *        template
     * @param args optional arguments
     */
    public void info (I18nItemAny item, Object... args)
      {
        if (!this.logger.isInfoEnabled (LOCALIZED))
          {
            return;
          }
        ClassifiedArgs sortedArgs = classifyArgs (args);
        String translatedMsg = item.s (sortedArgs.args);
        super.info (LOCALIZED, translatedMsg, sortedArgs.throwable);
      } // info

    /**
     * Log a localized message at the WARN level without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     */
    public void warn (I18nItem0 item)
      {
        if (!this.logger.isWarnEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.warn (LOCALIZED, translatedMsg);
      } // warn

    /**
     * Log a localized message at the WARN level with a {@link Throwable} and
     * without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     * @param t a {@link Throwable} instance to log
     */
    public void warn (I18nItem0 item, Throwable t)
      {
        if (!this.logger.isWarnEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.warn (LOCALIZED, translatedMsg, t);
      } // warn

    /**
     * Log a localized message at the WARN level with arbitrary number of
     * message formatting arguments. Allows the last argument to be a
     * {@link Throwable} and handled appropriately as per <a
     * href="http://www.slf4j.org/faq.html#paramException">SLF4J approach</a>.
     * 
     * @param item an {@link I18nItemAny} instance to be used as message
     *        template
     * @param args optional arguments
     */
    public void warn (I18nItemAny item, Object... args)
      {
        if (!this.logger.isWarnEnabled (LOCALIZED))
          {
            return;
          }
        ClassifiedArgs sortedArgs = classifyArgs (args);
        String translatedMsg = item.s (sortedArgs.args);
        super.warn (LOCALIZED, translatedMsg, sortedArgs.throwable);
      } // warn

    /**
     * Log a localized message at the ERROR level without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     */
    public void error (I18nItem0 item)
      {
        if (!this.logger.isErrorEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.error (LOCALIZED, translatedMsg);
      } // error

    /**
     * Log a localized message at the ERROR level with a {@link Throwable} and
     * without formatting arguments.
     * 
     * @param item an {@link I18nItem0} instance to be used as message
     * @param t a {@link Throwable} instance to log
     */
    public void error (I18nItem0 item, Throwable t)
      {
        if (!this.logger.isErrorEnabled (LOCALIZED))
          {
            return;
          }
        String translatedMsg = item.s ();
        super.error (LOCALIZED, translatedMsg, t);
      } // error

    /**
     * Log a localized message at the ERROR level with arbitrary number of
     * message formatting arguments. Allows the last argument to be a
     * {@link Throwable} and handled appropriately as per <a
     * href="http://www.slf4j.org/faq.html#paramException">SLF4J approach</a>.
     * 
     * @param item an {@link I18nItemAny} instance to be used as message
     *        template
     * @param args optional arguments
     */
    public void error (I18nItemAny item, Object... args)
      {
        if (!this.logger.isErrorEnabled (LOCALIZED))
          {
            return;
          }
        ClassifiedArgs sortedArgs = classifyArgs (args);
        String translatedMsg = item.s (sortedArgs.args);
        super.error (LOCALIZED, translatedMsg, sortedArgs.throwable);
      } // error

  } // class I15dLogger
