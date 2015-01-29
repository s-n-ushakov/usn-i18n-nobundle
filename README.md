<!--
This document is authored using GitHub Flavored Markdown:
https://help.github.com/articles/github-flavored-markdown/
-->

# usn-i18n-nobundle

Java programmer's i18n framework, compile time verifiable, free of
<code>[ResourceBundle](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html)</code>.

## Background

Internationalization (i18n) of Java programs typically involves separation data
from code, with message texts being moved to special text resource collections
formatted as
<code>[Properties](http://docs.oracle.com/javase/8/docs/api/java/util/Properties.html)</code>
files and called
[resource bundles](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html).
Messages in different languages get into different properties files, and
individual messages are identified by their property keys regardless of
language.

This approach, with messages stored in "almost plain text" format, and messages
in different languages stored in different files, has certain advantages. In
particular, it makes it easy to distribute translation work between translators,
and there is no need for every particular translator to deal with excessive text
data.

Meanwhile, having message texts coupled with code via plain text keys also makes
the whole construction fragile and prone to accumulation of occasional errors.
It is easy to get any particular message "orphaned" due to key being
renamed occasionally, and it is equally easy to obtain code that attempts to use
non-existing messages, with that only being discovered at run time.

Continuous integration and support of internationalized software might benefit
from technology that would allow code and message texts being coupled stronger,
with links being verified at compile time. Programmers might also benefit from
autocompletion aids being available when dealing with text messages.

## Approach

This project offers data structures and control facilities that allow bringing
all message texts back into Java code and handled as ordinary Java variables to
allow compile time verification. All text strings related to the same message in
different languages are organized into one collection-based data structure with
a single identifier. And the programmer never needs to care about language for
any current user. The programmer just needs to make necessary arrangements for
the policy that would govern user-to-language mappings, and the framework will
do the rest.

All this is achieved with the price of text messages being put back into Java
code and looking maybe less like "plain text". And they are all collected
together side-by-side in all languages. There may be different opinions on
whether this is an advantage or not. But for cases when the programmer and the
translator happen to be the same person, the advantage is evident :)

## Prerequisites

The framework currently requires Java 1.7+, as it makes use of
<code>[Locale#toLanguageTag()](https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html#toLanguageTag--)</code>.
This may be changed if really necessary.

## Building

Both [pom.xml](pom.xml) for Maven and [build.xml](build.xml) for Ant are
available. Ant buildfile automatically labels every build with current date.
Maven builds are labeled with project version from the .pom file, but a warning
is issued if this version does not match current date. Artifact/build versions
are currently formatted like "YYYYMMDD" rather than "v1.0", as no distinct
versioning policy has evolved so far.

## Usage – HOW-TO

- download or build the latest 'usn-i18n-nobundle-YYYYMMDD.jar' file and add it
  to your application class path;
- make a decision on the `I18nHandler` subclass you need: whether it should
  depend on some context for obtaining user's locale preferences or should it
  get these preferences from the application directly; hence select an existing
  `I18nHandler` subclass or make a subclass of your own;
- create an instance of the chosen `I18nHandler` subclass in your application;
  no need to care about its further fate; feel free to mark it with
  `@SuppressWarnings ("unused")`;
- create as many of `I18nItem` subclasses' instances as you need for all the
  messages that require internationalization;
- use your messages via corresponding `s(TArg1 arg1)` and similar methods;
- refer to the modest example on the package summary javadocs page and to API
  docs in general when necessary;
- enjoy :)

## Hello application

The 'Hello user' style application below demonstrates several basic concepts:

- shorthand notation for localized messages, taking advantage of static import
  of the `LocalizedMessage#lm()` method;
- instantiation of an `I18nHandler` for the application;
- using an internationalized logger;
- formatting messages with arguments;
- using implicit `toString()` calls instead of explicit `s()` when possible.

```java
package test;

import usn.i18n.nobundle.I15dLogger;
import usn.i18n.nobundle.I15dLoggerFactory;
import usn.i18n.nobundle.I18nHandler;
import usn.i18n.nobundle.I18nHandlerForSingleUser;
import usn.i18n.nobundle.I18nItem0;
import usn.i18n.nobundle.I18nItem1;
import usn.i18n.nobundle.I18nItemAny;

import static usn.i18n.nobundle.LocalizedMessage.lm;

public class HelloUser
  {
    // only I18nItem0 and I18nItemAny message items are currently supported
    // by I15dLogger without confusion...
    public static I18nItem0 LOG_MSG_APP_START = new I18nItem0
        (lm ("en", "Application started."),
         lm ("fr", "Le démarrage de l'application."),
         lm ("ru", "Приложение запущено."));
    public static I18nItemAny LOG_MSG_USER_LOGON = new I18nItemAny
        (lm ("en", "User {0} logged on."),
         lm ("fr", "{0} utilisateur connecté."),
         lm ("ru", "Пользователь {0} вошёл в систему."));
    public static I18nItem1<String> USER_MSG_GREETING = new I18nItem1<String>
      (lm ("en", "Hi {0}!"),
       lm ("fr", "Bonjour {0}!"),
       lm ("ru", "Привет, {0}!"));
    public static I18nItem0 USER_MSG_BYE = new I18nItem0
    (lm ("en", "Nothing left to do, bye!"),
     lm ("fr", "Rien à faire, au revoir!"),
     lm ("ru", "Мы всё сделали, до свидания!"));

    public static void main (String [] args)
      {
        // the 'i18nHandler' variable itself is not needed,
        // we just need to istantiate some 'I18nHandler' subclass,
        // and we do it here by using an instance of 'I18nHandlerForSingleUser'
        // that will use system default locale if no other arrangements are made
        @SuppressWarnings ("unused")
        I18nHandler i18nHandler = new I18nHandlerForSingleUser ();

        // arrange for an i18n-aware logger for our application
        I15dLogger logger = I15dLoggerFactory.getLogger (HelloUser.class);

        // log a simple message without arguments
        logger.info (LOG_MSG_APP_START);

        // learn something about the user to say hello
        String userName = System.getProperty ("user.name");

        // log a message with an argument
        logger.info (LOG_MSG_USER_LOGON, userName);

        // issue a message with an argument to a user
        System.out.println (USER_MSG_GREETING.s (userName));

        // for messages without arguments we can use implicit .toString()
        // instead of explicit .s()
        System.out.println (USER_MSG_BYE);
      } // main
  } // class HelloUser
```

## Licenses

The project is issued and distributed under the following licenses:

- [The BSD 2-Clause License](LICENSE.BSD) - for those who care about legal
  issues seriously;
- [The License in Three Lines](LICENSE.LITL) (LITL) - for those who may like it
  :)

## Distributions

The following distribution types are available:

- plain JAR:
  - usn-i18n-nobundle-YYYYMMDD.jar
- Maven style javadoc and sources JARs:
  - usn-i18n-nobundle-YYYYMMDD-javadoc.jar
  - usn-i18n-nobundle-YYYYMMDD-sources.jar
- full distribution that contains everything:
  - usn-i18n-nobundle-YYYYMMDD-full.jar

<!-- NOTE '../../' below compensate 'blob/master/' that is otherwise added by
          GitHub
          -->
The latest release can be found [here](../../releases/latest).

## TODO

- allow convenient handling of plurals;
- make the Maven artifact for the project available from some public repository.

## See also

- [Look Ma, no ResourceBundle :) Or yet another approach to i18n in Java](http://s-n-ushakov.blogspot.com/2013/02/look-ma-no-resourcebundle-or-yet.html) - initial post at blogger.com
- [Look Ma, no ResourceBundle :) Step 2: Dealing with message arguments](http://s-n-ushakov.blogspot.com/2013/06/look-ma-no-resourcebundle-step-2.html) - the second post at blogger.com
