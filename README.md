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
    - [Activities](#activities)
        - [Get a list of Activities](#get-a-list-of-activities)
        - [Activities filters](#activities-filters)
        - [Activities pagination](#activities-pagination)
        - [Get email for resend](#activity-email-for-resend)
    - [Analytics](#analytics)
        - [Activity data by date](#activity-data-by-date)
        - [Opens by country](#opens-by-country)
        - [Opens by user-agent name](#opens-by-user-agent-name)
        - [Opens by reading environment](#opens-by-reading-environment)
    - [Domains](#domains)
        - [Get a list of domains](#get-a-list-of-domains)
        - [Get a single domain](#get-a-single-domain)
        - [Delete a domain](#delete-a-domain)
        - [Add a Domain](#add-a-domain)
        - [Get DNS Records](#get-dns-records)
        - [Verify a Domain](#verify-a-domain)
        - [Get a list of recipients per domain](#get-a-list-of-recipients-per-domain)
        - [Update domain settings](#update-domain-settings)
    - [Messages](#messages)
        - [Get a list of messages](#get-a-list-of-messages)
        - [Get a single message](#get-a-single-message)
- [Testing](#testing)
- [Support and Feedback](#support-and-feedback)
- [License](#license)

<a name="installation"></a>

# Installation
For now please download the latest release from GitHub or download the source and compile it yourself. We plan to release the SDK on Maven Central once it reaches a mature state.

# Usage

## Email 

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
## Activities 

The SDK provides a simple interface to retrieve a list of activities for a domain.

The SDK returns an `Activities` object on successful send or throws a `MailerSendException` on a failed one.

Through the `Activities` object you can get the list of activities, get the next page of results, convert an activity into an email for resend, etc.

### Get a list of activities

```java
import com.mailersend.sdk.ActivitiesList;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void getActivities() {

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        ActivitiesList activities = ms.activities().getActivities("domain id");

        for (Activity activity : activities.activities) {

            System.out.println(activity.id);
            System.out.println(activity.createdAt.toString());

            System.out.println(activity.email.from);
            System.out.println(activity.email.subject);
            System.out.println(activitiy.email.recipient.email);
        }

        
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Activities filters

```java
import com.mailersend.sdk.ActivitiesList;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.EventTypes;

public void getActivities() {

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        int page = 1;
        int limit = 25; // also the default limit is 25
        Date dateFrom = DateUtils.addDays(new Date(), -30); // you'll need apache-commons for this
        Date dateTo = new Date();

        String events[] = {EventTypes.OPENED, EventTypes.SENT}; // check com.mailsersend.sdk.util.EventTypes for a full list of events

        ActivitiesList activities = ms.activities().getActivities("domain id", page, limit, dateFrom, dateTo, events);

        for (Activity activity : activities.activities) {

            System.out.println(activity.id);
        }
        
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Activities pagination

```java
import com.mailersend.sdk.ActivitiesList;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void getActivities() {

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        // without any filters, the default limit is 25
        ActivitiesList activities = ms.activities().getActivities("domain id");

        System.out.println(activities.getCurrentPage());

        for (Activity activity : activities.activities) {

            System.out.println(activity.id);
        }


        // get the next page
        ActivitiesList nextPage = activities.nextPage();

        System.out.println(nextPage.getCurrentPage());

        for (Activity activity : nextPage.activities) {

            System.out.println(activity.id);
        }


        // you can also get the previous page
        ActivitiesList previousPage = nextPage.previousPage();

        System.out.println(previousPage.getCurrentPage());

        for (Activity activity : previousPage.activities) {

            System.out.println(activity.id);
        }

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Activity email for resend

```java
import com.mailersend.sdk.ActivitiesList;
import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void getActivities() {

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        // without any filters, the default limit is 25
        ActivitiesList activities = ms.activities().getActivities("domain id");

        Activity activity = ms.activities().activities[0];

        Email email = activity.email.toEmail();

        // you can change the email contents or add a template id and send it with `ms.send(email)`

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

# Analytics

Analytics retrieval follows the builder pattern and is accessible from the `MailerSend.analytics` object.

## Activity data by date

```java
import java.util.Date;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.analytics.AnalyticsByDate;
import com.mailersend.sdk.analytics.AnalyticsByDateList;
import com.mailersend.sdk.analytics.AnalyticsList;
import com.mailersend.sdk.analytics.AnalyticsStatistic;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.EventTypes;

public void getAnalyticsByDate() {

    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {
        
        Date dateFrom = new Date(); // set your from date normally
        
        AnalyticsByDateList list = ms.analytics()
                .dateFrom(dateFrom)
                .dateTo(new Date())
                .domainId(TestHelper.domainId)
                .getByDate(new String[] {EventTypes.DELIVERED, EventTypes.OPENED, EventTypes.CLICKED});
        
        System.out.println("\n\nAnalytics by date for domain:");
        for (AnalyticsByDate dayStat : list.statistics) {
            
            System.out.println(dayStat.statDate.toString());
            System.out.println(dayStat.delivered);
            System.out.println(dayStat.opened);
            System.out.println(dayStat.clicked);
                            
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Opens by country

```java
import java.util.Date;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.analytics.AnalyticsList;
import com.mailersend.sdk.analytics.AnalyticsStatistic;
import com.mailersend.sdk.exceptions.MailerSendException;

public void getOpensByCountry() {

    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {
        
        Date dateFrom = new Date(); // set your from date normally
        
        AnalyticsList list = ms.analytics()
                .dateFrom(dateFrom)
                .dateTo(new Date())
                .domainId(TestHelper.domainId)
                .getOpensByCountry();
        
        System.out.println("\n\nOpens by country:");
        
        for (AnalyticsStatistic stat : list.statistics) {
            
            System.out.println(stat.name + " - " + stat.count);
        }
                
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Opens by user agent name

```java
import java.util.Date;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.analytics.AnalyticsList;
import com.mailersend.sdk.analytics.AnalyticsStatistic;
import com.mailersend.sdk.exceptions.MailerSendException;

public void getOpensByUserAgentName() {

    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {
        
        Date dateFrom = new Date(); // set your from date normally
        
        AnalyticsList list = ms.analytics()
                .dateFrom(dateFrom)
                .dateTo(new Date())
                .getOpensByUserAgent();
        
        System.out.println("\n\nOpens by user agent:");
        
        for (AnalyticsStatistic stat : list.statistics) {
            
            System.out.println(stat.name + " - " + stat.count);
        }
                
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Opens by reading environment

```java
import java.util.Date;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.analytics.AnalyticsList;
import com.mailersend.sdk.analytics.AnalyticsStatistic;
import com.mailersend.sdk.exceptions.MailerSendException;

public void getOpensByUserAgentType() {

    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {
        
        Date dateFrom = new Date(); // set your from date normally
        
        AnalyticsList list = ms.analytics()
                .dateFrom(dateFrom)
                .dateTo(new Date())
                .getOpensByUserAgenType();
        
        System.out.println("\n\nOpens by user agent type:");
        
        for (AnalyticsStatistic stat : list.statistics) {
            
            System.out.println(stat.name + " - " + stat.count);
        }
                
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Domains

### Get a list of domains

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.domains.DomainsList;
import com.mailersend.sdk.exceptions.MailerSendException;

public void DomainsList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("token");
    
    try {
        
        DomainsList list = ms.domains().getDomains();
        
        for (Domain domain : list.domains) {
            
            System.out.println(domain.id);
            System.out.println(domain.name);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get a single domain

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.exceptions.MailerSendException;

public void SingleDomain() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("api token");
    
    try {
        
        Domain domain = ms.domains().getDomain("domain id");
        
        System.out.println(domain.id);
        System.out.println(domain.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete a domain

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;

public void DeleteDomain() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("api token");
    
    try {
        
        boolean domainDeleted = ms.domains().deleteDomain("domain id");
        
        System.out.println("Domain deleted: ".contains(String.valueOf(domainDeleted)));
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Add a domain

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.exceptions.MailerSendException;

public void AddDomain() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("api token");
    
    try {
        
        Domain domain = ms.domains().addDomainBuilder().addDomain("domain to add");
        
        System.out.println(domain.id);
        System.out.println(domain.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get DNS Records

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.domains.DomainDnsAttribute;
import com.mailersend.sdk.domains.DomainDnsRecords;
import com.mailersend.sdk.exceptions.MailerSendException;

public void DomainDnsRecords() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("api token");
    
    try {
        
        DomainDnsRecords records = ms.domains().getDomainDnsRecords("domain id");
        
        printDomainDnsAttribute(records.spf);
        printDomainDnsAttribute(records.dkim);
        printDomainDnsAttribute(records.customTracking);
        printDomainDnsAttribute(records.returnPath);
        printDomainDnsAttribute(records.inboundRouting);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}

private void printDomainDnsAttribute(DomainDnsAttribute attribute) {
    
    System.out.println(attribute.hostname);
    System.out.println(attribute.type);
    System.out.println(attribute.value);
}
```

### Verify a Domain

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.DomainVerificationStatus;
import com.mailersend.sdk.exceptions.MailerSendException;

public void VerifyDomain() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("api token");
    
    try {
        
        DomainVerificationStatus status = ms.domains().verifyDomain("domain id");
        
        System.out.println(status.message);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get a list of recipients per domain

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.DomainRecipientsList;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.ApiRecipient;

public void ReceipientsPerDomain() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("api token");
    
    try {
        
        DomainRecipientsList list = ms.domains().getDomainRecipients("domaion id");
        
        for (ApiRecipient recipient : list.recipients) {
            
            System.out.println(recipient.email);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Update domain settings

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.exceptions.MailerSendException;

public void UpdateDomainSettings() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("api token");
    
    try {
        
        Domain domain = ms.domains().updateDomainSettingsBuilder()
            .customnTrackingEnabled(true)
            .sendPaused(false)
            .updateDomain("domain id");
        
        System.out.println(domain.domainSettings.customTrackingEnabled);
        System.out.println(domain.domainSettings.sendPaused);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```
## Messages

### Get a list of messages

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.messages.Message;
import com.mailersend.sdk.messages.MessagesList;

public void MessagesList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("token");
    
    try {
        
        MessagesList list = ms.messages().getMessages();
        
        for (MessageListItem message : list.messages) {
            
            System.out.println(message.id);
            System.out.println(message.createdAt.toString());
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get a single message

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.messages.Message;
import com.mailersend.sdk.messages.MessagesListItem;

public void SingleMessage() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("token");
    
    try {
        
        Message message = ms.messages().getMessage("message id");
        
        System.out.println(message.id);
        System.out.println(message.createdAt.toString());
        System.out.println(message.domain.name);
        
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
