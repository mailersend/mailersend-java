<a href="https://www.mailersend.com"><img src="https://www.mailersend.com/images/logo.svg" width="200px"/></a>

MailerSend Java SDK

[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

# Table of Contents
- [Installation](#installation)
- [Usage](#usage)
    - [Email](#email)
        - [Send an email](#send-an-email)
        - [Add CC, BCC recipients](#add-cc-bcc-recipients)
        - [Send a template-based email](#send-a-template-based-email)
        - [Advanced personalization](#advanced-personalization)
        - [Simple personalization](#simple-personalization)
        - [Send email with attachment](#send-email-with-attachment)
- [Testing](#testing)
- [Support and Feedback](#support-and-feedback)
- [License](#license)

<a name="installation"></a>

# Installation
For now please download the latest release from GitHub or download the source and compile it yourself. We plan to release the SDK on Maven Central once it reaches a mature state.

# Usage

## Email 

### General

The SDK provides a simple interface to send an email through MailerSend. Check the examples below for various use cases.

The SDK returns a `MailerSendResponse` object on successful send or throws a `MailerSendException` on a failed one.

Through the `MailerSendResponse` object you can get the ID of the sent message, while the `MailerSendException` contains the response code and all errors that occured. Check the respective `code` and `errors` properties.

### Send an email

```java
import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");
    email.addRecipient("name", "your@recipient.com");

    // you can also add multiple recipients by calling addRecipient again
    email.addRecipient("name 2", "your@recipient2.com");

    // there's also a recipient object you can use
    Recipient recipient = new Recipient("name", "your@recipient3.com");
    email.addRecipient(recipient);
    
    email.setSubject("Email subject");

    email.setPlain("This is the text content");
    email.setHtml("<p>This is the HTML content</p>");

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Add CC, BCC recipients

```java
import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");
    email.addRecipient("name", "your@recipient.com");

    email.addCc("name", "yourCc@recipient.com");

    // add a second cc recipient
    email.addCc("name 2", "yourCc2@recipient.com");

    // same for a bcc recipient
    email.addBcc("bcc name", "yourBcc@recipient.com");
    
    email.setSubject("Email subject");

    email.setPlain("This is the text content");
    email.setHtml("<p>This is the HTML content</p>");

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Send a template-based email

```java
import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");

    Recipient recipient = new Recipient("name", "your@recipient.com");
    
    email.addRecipient(recipient);

    email.setTemplateId("Your MailerSend template ID");

    // you can add a variable for a specific recipient
    email.addVariable(recipient, "variable name", "variable value");

    // you can use the addVariable overload to add a variable to all recipients
    email.addVariable("all recipients variable name", "variable value");

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Advanced personalization

```java
import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");

    Recipient recipient = new Recipient("name", "your@recipient.com");
    
    email.addRecipient(recipient);

    email.setSubject("Subject {{ var }}");
    email.setPlain("This is the text version with a {{ var }}.");
    email.setHtml("<p>This is the HTML version with a {{ var }}.</p>");

    // you can add personalization for all recipients
    email.addPersonalization("var name", "personalization value");

    // you can add personalization for each recipient separately
    email.addPersonalization(recipient, "var2 name", "personalization value");

    // you can also add POJOs as advanced personalization provided they can be serialized to JSON via Gson and do not have any object properties
    MyPojo obj = new MyPojo();
    obj.property1 = "property 1 value";
    obj.array1 = {1, 2, 3, 4};

    email.addPersonalization("pojo", obj);

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Simple personalization

```java
import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");

    email.setSubject("Subject {$var1} {$var2}");

    email.setPlain("This is the text version with a {$var1} and a {$var2}.")
    email.setHtml("<p>This is the HTML version with a {$var1} and a {$var2}.</p>");

    Recipient recipient = new Recipient("name", "your@recipient.com");
    
    email.addRecipient(recipient);

    email.setTemplateId("Your MailerSend template ID");

    // you can add a variable for a specific recipient
    email.addVariable(recipient, "var1", "variable");

    // you can use the addVariable overload to add a variable to all recipients
    email.addVariable("var2", "second variable");

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Send email with attachment

```java
import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");
    email.addRecipient("name", "your@recipient.com");
   
    email.setSubject("Email subject");

    email.setPlain("This is the text content");
    email.setHtml("<p>This is the HTML content</p>");

    // attach a file to the email
    email.attachFile("LICENSE");

    // if you already have a file object, you can attach that to the email
    File file = new File("LICENSE");
    email.attachFile(file);

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

# Testing

Change the properties in the `TestHelper` class of the `com.mailersend.sdk.tests` package to correspond to your account details, then simply run
```
mvn test
```

<a name="support-and-feedback"></a>
# Support and Feedback

In case you find any bugs, submit an issue directly here in GitHub.

You are welcome to create an SDK for any other programming language.

If you have any troubles using our API or SDK free to contact our support by email [info@mailersend.com](mailto:info@mailersend.com)

The official documentation is at [https://developers.mailersend.com](https://developers.mailersend.com)

<a name="license"></a>
# License

[The MIT License (MIT)](LICENSE)
