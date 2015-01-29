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

/**
 * <p>This package implements an alternative to traditional approach to Java
 * software internationalization and allows localized resources be referenced
 * via individual class member names rather than by arbitrary strings. This
 * allows localized resource references be checked at compile time and also
 * facilitates taking advantage of autocompletion aids provided by IDE's.</p>
 * 
 * @author Sergey Ushakov, s-n-ushakov@yandex.ru
 * @version 2013-06-18
 * 
 * <p>An application using this package would normally:</p>
 * <ul>
 * <li>select one of the two approaches to be used for user locale preferences
 * handling:
 *   <ul>
 *     <li>with user preferences handled and optionally specified by a
 *         {@link usn.i18n.nobundle.I18nHandler I18nHandler} subclass
 *         singleton linked to user preferences in an arbitrary application
 *         specific manner, possibly queried with the application or specified
 *         as part of persisted user preferences in
 *         single-user-per-application-instance cases, etc;
 *     <li>with user preferences handled by a
 *         {@link usn.i18n.nobundle.I18nHandlerInContext
 *         I18nHandlerInContext} subclass singleton capable of detecting user
 *         preferences dynamically from an arbitrary application specific
 *         context; such contexts may be constituted, without limitation, by
 *         {@link javax.servlet.ServletRequest ServletRequest},
 *         {@link javax.servlet.http.HttpSession HttpSession}, etc;
 *     </ul>
 *     in fact there is no tremendous difference between these two approaches
 *     when you start following them... :)</li>
 * <li>subclass either
 *     {@link usn.i18n.nobundle.I18nHandler I18nHandler} or
 *     {@link usn.i18n.nobundle.I18nHandlerInContext I18nHandlerInContext},
 *     providing an application-specific
 *     {@link usn.i18n.nobundle.I18nHandler#getUserLocaleTags()
 *     getUserLocaleTags()} method and optionally an application-specific
 *     {@link usn.i18n.nobundle.I18nHandler#getDefaultLocaleTag()
 *     getDefaultLocaleTag()} method;</li>
 * <li>declare a necessary set of static {@code I18nItem} subclass instances,
 *     one for every message that requires being internationalized; feel free to
 *     have them all in one place or scatter them across your package tree to
 *     your best convenience;</li>
 * <li>use them where necessary via their static member names with
 *     {@link usn.i18n.nobundle.I18nItem0#s() .s()} method being called.</li>
 * </ul>
 * 
 * <p>The following classes are intended for direct use by applications:</p>
 * <ul>
 * <li>handlers:
 *   <ul>
 *     <li>{@link usn.i18n.nobundle.I18nHandler I18nHandler}
 *         &ndash; to be subclassed by multi-user applications in cases when no
 *         explicit user context is available, but it is possible to retrieve
 *         user preferences from the application itself;</li>
 *     <li>{@link usn.i18n.nobundle.I18nHandlerInContext
 *         I18nHandlerInContext} &ndash; to be subclassed by multi-user
 *         applications in cases when some sort of user context is available to
 *         retrieve user preferences from, like
 *         {@link javax.servlet.ServletRequest ServletRequest},
 *         {@link javax.servlet.http.HttpSession HttpSession}, etc;</li>
 *     <li>{@link usn.i18n.nobundle.I18nHandlerForSingleUser
 *         I18nHandlerForSingleUser} &ndash; a ready-to-use handler class
 *         suitable for single-user (e.g. desktop) applications in cases when
 *         it is possible to retrieve user preferences once on application
 *         initialization;</li>
 *     <li>{@link usn.i18n.nobundle.I18nHandlerForServletRequest
 *         I18nHandlerForServletRequest} &ndash; a ready-to-use handler class
 *         suitable for HTTP server applications;</li>
 *     </ul>
 *     you need just to instantiate a handler of your choice, and it will
 *     establish itself as an application-wide singleton by itself;</li>
 * <li>message collection classes to be used as static storage for
 *     multi-locale messages or their formatting patterns; these classes
 *     comprise a two-dimensional matrix according to their properties:
 *   <ul>
 *     <li>depending on the approach to user context handling:
 *       <ul>
 *         <li>classes to be used together with
 *             {@link usn.i18n.nobundle.I18nHandlerInContext
 *             I18nHandlerInContext} subclasses; they have 'InContext' suffix in
 *             their names;</li>
 *         <li>classes to be used together with basic
 *             {@link usn.i18n.nobundle.I18nHandler I18nHandler}
 *             subclasses,
 *             {@link usn.i18n.nobundle.I18nHandlerForSingleUser
 *             I18nHandlerSingleUser} included; they do not have any
 *             context-related suffix in their names;</li>
 *         </ul>
 *       </li>
 *     <li>depending on the number of the arguments they take for formatting:
 *         '0', '1', '2', '3' or 'Any', the latter being the last resort for
 *         large number of arguments, lacking the ability to check argument
 *         types at compile time.</li>
 *     </ul>
 *     </li>
 * <li>internationalized (i15d) wrappers for main <a
 *     href="http://www.slf4j.org/">SLF4J</a> classes for logging:
 *     {@link usn.i18n.nobundle.I15dLogger I15dLogger} and appropriate
 *     factory
 *     {@link usn.i18n.nobundle.I15dLoggerFactory I15dLoggerFactory}.</li>
 * </ul>
 * 
 * <p>An example of "hello world" application:</p>
 * <pre>
 * import usn.i18n.nobundle.I15dLogger;
 * import usn.i18n.nobundle.I15dLoggerFactory;
 * import usn.i18n.nobundle.I18nHandler;
 * import usn.i18n.nobundle.I18nItem0;
 * import usn.i18n.nobundle.I18nItemAny;
 * 
 * import static usn.i18n.nobundle.LocalizedMessage.lm;
 * 
 * public class MyApplication
 *   {
 *     // only 'I18nItemAny' message items are currently supported for logger
 *     static I18nItemAny LOG_MSG_HELLO = new I18nItemAny
 *       (lm ("en", "Hello World!"),
 *        lm ("fr_CA", "Bonjour Monde!"));
 *     public static I18nItem0 USER_MESSAGE_1 = new I18nItem0
 *         (lm ("en", "That's cool!"),
 *          lm ("fr_CA", "C'est le pied!"));
 * 
 *     public static void main (String [] args)
 *       {
 *         // the 'i18nHandler' variable itself is not needed,
 *         // we just need to istantiate some 'I18nHandler' subclass,
 *         // and we do it here subclassing 'I18nHandler' anonymously
 *         {@literal @SuppressWarnings} ("unused")
 *         I18nHandler i18nHandler = new I18nHandler ()
 *           {
 *             {@literal @Override}
 *             protected String getDefaultLocaleTag ()
 *               {
 *                 return "fr_CA";
 *               } // getDefaultLocaleTag
 *             {@literal @Override}
 *             protected String [] getUserLocaleTags ()
 *               {
 *                 // do something application-specific here...
 *               } // getUserLocaleTags
 *           };
 * 
 *         I15dLogger logger =
 *           I15dLoggerFactory.getLogger (MyApplication.class);
 * 
 *         // for messages without arguments we can use .toString() implicitly
 *         // instead of explicit .s()
 *         logger.info (LOG_MSG_HELLO);
 * 
 *         System.out.println (USER_MESSAGE_1.s ());
 *       } // main
 *   } // class MyApplication
 * </pre>
 */
package usn.i18n.nobundle;
