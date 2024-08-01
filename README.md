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
        - [Send email with attachment](#send-email-with-attachment)
        - [Schedule an email](#schedule-an-email)
        - [Send bulk emails](#send-bulk-emails)
        - [Get bulk request status](#get-bulk-request-status)
    - [Inbound routes](#inbound-routes)
        - [Get a list of inbound routes](#get-a-list-of-inbound-routes)
        - [Get an inbound route](#get-an-inbound-route)
        - [Create an inbound route](#create-an-inbound-route)
        - [Update an inbound route](#update-an-inbound-route)
        - [Delete an inbound route](#delete-an-inbound-route)
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
    - [Scheduled messages](#scheduled-messages)
        - [Get a list of scheduled messages](#get-a-list-of-scheduled-messages)
        - [Get a scheduled message](#get-a-scheduled-message)
        - [Delete a scheduled message](#delete-a-scheduled-message)
    - [Tokens](#tokens)
        - [Create a token](#create-a-token)
        - [Update token](#update-token)
        - [Delete token](#delete-token)
    - [Recipients](#recipients)
        - [Get a list of recipients](#get-a-list-of-recipients)
        - [Get a single recipient](#get-a-single-recipient)
        - [Delete a recipient](#delete-a-recipient)
        - [Get recipients from a suppression list](#get-recipients-from-a-suppression-list)
        - [Add recipients to a suppression list](#add-recipients-to-a-suppression-list)
        - [Delete recipients from a suppression list](#delete-recipients-from-a-suppression-list)
    - [Webhooks](#webhooks)
        - [Get a list of webhooks](#get-a-list-of-webhooks)
        - [Get a single webhook](#get-a-single-webhook)
        - [Create a webhook](#create-a-webhook)
        - [Update a webhook](#update-a-webhook)
        - [Delete a webhook](#delete-a-webhook)
    - [Templates](#templates)
        - [Get a list of templates](#get-a-list-of-templates)
        - [Get a single template](#get-a-single-template)
        - [Delete a template](#delete-a-template)
    - [Email verification](#email-verification)
        - [Get all email verification lists](#get-all-email-verification-lists)
        - [Get an email verification list](#get-an-email-verification-list)
        - [Create an email verification list](#create-an-email-verification-list)
        - [Verify an email list](#verify-an-email-list)
        - [Get email verification list results](#get-email-verification-list-results)
    - [SMS activity](#sms-activity)
        - [Get a list of SMS activities](#get-a-list-of-sms-activities)
        - [Get activity of a single message](#get-activity-of-a-single-message)
    - [SMS messages](#sms-messages)
        - [Get a list of SMS messages](#get-a-list-of-sms-messages)
        - [Get info on a SMS message](#get-info-on-a-sms-message)
    - [SMS Phone numbers](#sms-phone-numbers)
        - [Get a list of SMS phone numbers](#get-a-list-of-sms-phone-numbers)
        - [Get an SMS phone number](#get-an-sms-phone-number)
        - [Update a single SMS phone number](#update-a-single-sms-phone-number)
        - [Delete an SMS phone number](#delete-an-sms-phone-number)
    - [SMS recipients](#sms-recipients)
        - [Get a list of SMS recipients](#get-a-list-of-sms-recipients)
        - [Get an SMS recipient](#get-an-sms-recipient)
        - [Update a single SMS recipient](#update-a-single-sms-recipient)
    - [SMS inbounds](#sms-inbounds)
        - [Get a list of SMS inbound routes](#get-a-list-of-sms-inbound-routes)
        - [Get a single SMS inbound route](#get-a-single-sms-inbound-route)
        - [Add an SMS inbound route](#add-an-sms-inbound-route)
        - [Update an inbound route](#update-an-inbound-route)
        - [Delete an SMS inbound route](#delete-an-sms-inbound-route)
    - [SMS webhooks](#sms-webhooks)
        - [Get a list of SMS webhooks](#get-a-list-of-sms-webhooks)
        - [Get an SMS webhook](#get-an-sms-webhook)
        - [Create an SMS webhook](#create-an-sms-webhook)
        - [Update an SMS webhook](#update-an-sms-webhook)
        - [Delete an SMS webhook](#delete-an-sms-webhook)
    - [SMS](#sms)
        - [Send an SMS with personalization](#send-an-sms-with-personalization)

- [Testing](#testing)
- [Support and Feedback](#support-and-feedback)
- [License](#license)

<a name="installation"></a>

# Installation

Using Maven:

    <dependency>
      <groupId>com.mailersend</groupId>
      <artifactId>java-sdk</artifactId>
      <version>1.0.1</version>
    </dependency>

Using Gradle:

    implementation 'com.mailersend:java-sdk:1.0.0'
    
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
import com.mailersend.sdk.emails.Email;
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
    
        MailerSendResponse response = ms.emails().send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Send a template-based email

```java
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");

    Recipient recipient = new Recipient("name", "your@recipient.com");
    
    email.addRecipient(recipient);

    email.setTemplateId("Your MailerSend template ID");

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.emails().send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Advanced personalization

```java
import com.mailersend.sdk.emails.Email;
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
    
        MailerSendResponse response = ms.emails().send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Send email with attachment

```java
import com.mailersend.sdk.emails.Email;
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
    
        MailerSendResponse response = ms.emails().send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Schedule an email

```java
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import java.util.Calendar;
import java.util.Date;

public void scheduleEmail() {

    Email email = new Email();

    email.setFrom("name", "your email");
    email.addRecipient("name", "your@recipient.com");
   
    email.setSubject("Email subject");

    email.setPlain("This is the text content");
    email.setHtml("<p>This is the HTML content</p>");

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DATE, 1);

    email.setSendAt(calendar.getTime());

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        MailerSendResponse response = ms.emails().send(email);
        System.out.println(response.messageId);
    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Send bulk emails

```java
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendBulkEmails() {

    Email email1 = new Email();

    email1.setFrom("name", "your email");
    email1.addRecipient("name", "your@first-recipient.com");
   
    email1.setSubject("Email subject 1");

    email1.setPlain("This is the text content for the first email");
    email1.setHtml("<p>This is the HTML content for the first email</p>");

    Email email2 = new Email();

    email2.setFrom("name", "your email");
    email2.addRecipient("name", "your@second-recipient.com");
   
    email2.setSubject("Email subject 2");

    email2.setPlain("This is the text content for the second email");
    email2.setHtml("<p>This is the HTML content for the second email</p>");

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        String bulkSendId = ms.emails().bulkSend(new Email[] { email1, email2 });

        // you can use the bulkSendId to get the status of the emails
        System.out.println(bulkSendId);

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```


### Get bulk request status

```java
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.emails.BulkSendStatus;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void getBulkEmailsStatus() {

    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        BulkSendStatus status = ms.emails().bulkSendStatus("bulk send id");

        System.out.println(status.state);

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

## Inbound routes

### Get a list of inbound routes

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.inboundroutes.InboundRoute;
import com.mailersend.sdk.inboundroutes.InboundRoutesList;

public void getInboundRoutes() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        InboundRoutesList routes = ms.inboundRoutes().getRoutes();

        for (InboundRoute route : routes.routes) {
            System.out.println(route.id);
        }

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}

```

### Get an inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.inboundroutes.InboundRoute;

public void getInboundRoute() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        InboundRoute route = ms.inboundRoutes().getRoute("inbound route id");

        System.out.println(route.id);
        System.out.println(route.name);

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}

```

### Create an inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.inboundroutes.Forward;

public void createInboundRoute() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {

        Forward forward = new Forward();
        forward.type = "webhook";
        forward.value = "https://example-domain.com";
        forward.secret = "asdfgh";
    
        ms.inboundRoutes().builder()
            .domainId("domain id")
            .name("Test inbound name")
            .domainEnabled(false)
            .matchFilter("match_all")
            .forwards(new Forward[] { forward })
            .addRoute();

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}

```

### Update an inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.inboundroutes.Forward;
import com.mailersend.sdk.inboundroutes.InboundRoute;

public void updateInboundRoute() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {

        Forward forward = new Forward();
        forward.type = "webhook";
        forward.value = "https://example-domain.com";
        forward.secret = "asdfgh";
    
        InboundRoute route = ms.inboundRoutes().builder()
            .domainId("domain id")
            .name("Updated route name")
            .domainEnabled(false)
            .matchFilter("match_all")
            .forwards(new Forward[] { forward })
            .updateRoute("inbound route id");

        System.out.println(route.id);
        System.out.println(route.name);

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}

```

### Delete an inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;

public void deleteInboundRoute() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {

        boolean result = ms.inboundRoutes().deleteRoute("inbound route id");

        System.out.println(result);

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

## Scheduled messages

### Get a list of scheduled messages

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.scheduledmessages.ScheduledMessagesList;
import com.mailersend.sdk.scheduledmessages.ScheduledMessage;

public void getScheduledMessages() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        ScheduledMessagesList messages = ms.scheduledmessages().getScheduledMessages();

        for (ScheduledMessage message : messages.scheduledMessages) {
            System.out.println(message.id);
            System.out.println(message.subject);
        }

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Get a scheduled message

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.scheduledmessages.ScheduledMessage;

public void getScheduledMessage() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        ScheduledMessage message = ms.scheduledmessages().getScheduledMessage("message id");

        System.out.println(message.id);
        System.out.println(message.subject);

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

### Delete a scheduled message

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.scheduledmessages.ScheduledMessage;

public void deleteScheduledMessage() {
    
    MailerSend ms = new MailerSend();

    ms.setToken("Your API token");

    try {
    
        boolean result = ms.scheduledmessages().deleteScheduledMessage("message id");

        System.out.println(result);

    } catch (MailerSendException e) {

        e.printStackTrace();
    }
}
```

## Tokens

### Create a token

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.tokens.Token;
import com.mailersend.sdk.tokens.TokenAdd;
import com.mailersend.sdk.tokens.TokenScopes;

public void CreateToken() {
    
    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {
        
        TokenAdd token = ms.tokens().addBuilder()
            .name("Test token")
            .domainId("domain id")
            .addScope(TokenScopes.activityFull)
            .addScope(TokenScopes.analyticsFull)
            .addToken();
        
        System.out.println(token.id);
        System.out.println(token.name);
        System.out.println(token.accessToken);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Update token

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.tokens.Token;

public void CreateToken() {
    
    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {
        
    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {

        // true to pause it, false to unpause it
        Token token = ms.tokens().updateToken(T"token id", true);
        
        System.out.println(token.name);
        System.out.println(token.status);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
        fail();
    }
}
```

### Delete token

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.tokens.Token;

public void CreateToken() {
    
    MailerSend ms = new MailerSend();
    ms.setToken(TestHelper.validToken);
    
    try {
        MailerSendResponse response = ms.tokens().deleteToken("token to delete");
        
        System.out.println(response.responseStatusCode);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Recipients

### Get a list of recipients

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.ApiRecipient;
import com.mailersend.sdk.util.ApiRecipientsList;

public void GetRecipients() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        ApiRecipientsList list = ms.recipients().getRecipients();
        
        for (ApiRecipient recipient : list.recipients) {
            
            System.out.println(recipient.id);
            System.out.println(recipient.email);
        }

    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```


### Get a single recipient

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.recipients.Recipient;

public void GetSingleRecipient() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        Recipient recipient = ms.recipients().getRecipient("recipient id");
        
        System.out.println(recipient.email);

    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete a recipient

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.MailerSendResponse;

public void DeleteRecipient() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        MailerSendResponse response = ms.recipients().deleteRecipient("recipient id");
        
        System.out.println(response.responseStatusCode);

    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get recpients from a suppression list

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.recipients.BlocklistItem;
import com.mailersend.sdk.recipients.BlocklistListResponse;
import com.mailersend.sdk.recipients.SuppressionItem;
import com.mailersend.sdk.recipients.SuppressionList;

public void GetRecipientsFromSuppressionList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        BlocklistListResponse blocklist = ms.recipients().suppressions().getBlocklist();
        
        for (BlocklistItem item : blocklist.items) {
            
            System.out.println(item.id);
            System.out.println(item.pattern);
            System.out.println(item.type);
        }
        
        SuppressionList hardBounces = ms.recipients().suppressions().getHardBounces();
        
        for (SuppressionItem item : hardBounces.items) {
            
            System.out.println(item.id);
            System.out.println(item.recipient.email);
        }
        
        SuppressionList spamComplaints = ms.recipients().suppressions().getSpamComplaints();
        
        for (SuppressionItem item : spamComplaints.items) {
            
            System.out.println(item.id);
            System.out.println(item.recipient.email);
        }
        
        SuppressionList unsubscribes = ms.recipients().suppressions().getUnsubscribes();
        
        for (SuppressionItem item : unsubscribes.items) {
            
            System.out.println(item.id);
            System.out.println(item.recipient.email);
        }

    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Add recpients to a suppression list

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.recipients.BlocklistItem;
import com.mailersend.sdk.recipients.BlocklistListResponse;
import com.mailersend.sdk.recipients.SuppressionItem;
import com.mailersend.sdk.recipients.SuppressionList;

public void AddRecipientsToSuppressionList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        // blocklist
        ms.recipients().suppressions().addBuilder().pattern(".*@example.com");
        ms.recipients().suppressions().addBuilder().recipient("test@example.com");
        BlocklistItem[] items = ms.recipients().suppressions().addBuilder().addToBlocklist();
        
        for (BlocklistItem item : items) {
            
            System.out.println(item.id);
        }
        
        
        // hard bounces
        ms.recipients().suppressions().addBuilder().recipient("test@example.com");
        ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
        SuppressionList list = ms.recipients().suppressions().addBuilder().addRecipientsToHardBounces();
        
        for (SuppressionItem item : list.items) {
            
            System.out.println(item.id);
        }
        
        
        // spam complaints
        ms.recipients().suppressions().addBuilder().recipient("test@example.com");
        ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
        list = ms.recipients().suppressions().addBuilder().addRecipientsToSpamComplaints();
        
        for (SuppressionItem item : list.items) {
            
            System.out.println(item.id);
        }
        
        
        // unsubscribes
        ms.recipients().suppressions().addBuilder().recipient("test@example.com");
        ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
        list = ms.recipients().suppressions().addBuilder().addRecipientsToUnsubscribes();
        
        for (SuppressionItem item : list.items) {
            
            System.out.println(item.id);
        }

    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete recipients from a suppression list

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.recipients.BlocklistItem;
import com.mailersend.sdk.recipients.BlocklistListResponse;
import com.mailersend.sdk.recipients.SuppressionItem;
import com.mailersend.sdk.recipients.SuppressionList;

public void DeleteRecipientsFromSuppressionList () {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        // delete from blocklist
        BlocklistListResponse blocklist = ms.recipients().suppressions().getBlocklist();
        
        if (blocklist.items.length == 0) {
            
            fail();
        }
        
        String itemId = blocklist.items[0].id;
        
        MailerSendResponse response = ms.recipients().suppressions().deleteBlocklistItems(new String[] { itemId });
        
        System.out.println(response.responseStatusCode);
        
        
        // delete from hard bounces
        SuppressionList hardBounces = ms.recipients().suppressions().getHardBounces();
        
        if (hardBounces.items.length == 0) {
            
            fail();
        }
        
        itemId = hardBounces.items[0].id;
        
        response = ms.recipients().suppressions().deleteHardBouncesItems(new String[] { itemId });
        
        System.out.println(response.responseStatusCode);
        
        
        // delete from spam complaints
        SuppressionList spamComplaints = ms.recipients().suppressions().getSpamComplaints();
        
        if (spamComplaints.items.length == 0) {
            
            fail();
        }
        
        itemId = spamComplaints.items[0].id;
        
        response = ms.recipients().suppressions().deleteSpamComplaintsItems(new String[] { itemId });
        
        System.out.println(response.responseStatusCode);
        

        // delete from unsubscribes
        SuppressionList unsubscribes = ms.recipients().suppressions().getUnsubscribes();
        
        if (unsubscribes.items.length == 0) {
            
            fail();
        }
        
        itemId = unsubscribes.items[0].id;
        
        response = ms.recipients().suppressions().deleteUnsubscribesItems(new String[] { itemId });
        
        System.out.println(response.responseStatusCode);

    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Webhooks

### Get a list of webhooks

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.webhooks.Webhook;
import com.mailersend.sdk.webhooks.WebhooksList;

public void GetWebhooks() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        WebhooksList list = ms.webhooks().getWebhooks("domain id");
        
        for (Webhook webhook : list.webhooks) {
            
            System.out.println(webhook.name);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get a single webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.webhooks.Webhook;

public void GetSingleWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        Webhook webhook = ms.webhooks().getWebhook("webhook id");
        
        System.out.println(webhook.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Create a webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.webhooks.Webhook;

public void CreateWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        Webhook webhook = ms.webhooks().builder()
            .name("Webhook name")
            .url("Webhook url")
            .addEvent(WebhookEvents.ACTIVITY_OPENED)
            .addEvent(WebhookEvents.ACTIVITY_CLICKED)
            .createWebhook("domain id");
        
        System.out.println(webhook.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Update a webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.webhooks.Webhook;

public void UpdateWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        Webhook webhook = ms.webhooks()
                .builder()
                .name("Updated webhook name")
                .updateWebhook("webhook id");
                    
        System.out.println(webhook.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete a webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public void DeleteWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        MailerSendResponse response = ms.webhooks().deleteWebhook("webhook id");
            
        System.out.println(response.responseStatusCode);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Templates

### Get a list of templates

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.templates.TemplateItem;
import com.mailersend.sdk.templates.TemplatesList;

public void getTemplatesList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
            TemplatesList list = ms.templates().getTemplates();
            
            for (TemplateItem item : list.templates) {
                
                System.out.println(item.id);
                System.out.println(item.name);
            }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get a single template

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.templates.Template;
import com.mailersend.sdk.templates.TemplateItem;
import com.mailersend.sdk.templates.TemplatesList;

public void getTemplate() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
            Template template = ms.templates().getTemplate("template id");
            
            System.out.println(template.id);
            System.out.println(template.name);
            System.out.println(template.imagePath);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete a template

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.templates.Template;
import com.mailersend.sdk.templates.TemplateItem;
import com.mailersend.sdk.templates.TemplatesList;

public void deleteTemplate() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
            MailerSendResponse response = ms.templates().deleteTemplate("template id");
            
            System.out.println(response.responseStatusCode);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## Email verification

### Get all email verification lists

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.emailverification.EmailVerificationList;
import com.mailsend.sdk.emailverification.EmailVerificationLists;

public void getLists() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        EmailVerificationLists lists = ms.emailVerification().getLists();
            
        for (EmailVerificationList list : lists.lists) {
            System.out.println(list.id);
            System.out.println(list.name);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get an email verification list

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.emailverification.EmailVerificationList;

public void getList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        EmailVerificationList list = ms.emailVerification().getList("list id");

        System.out.println(list.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Create an email verification list

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.emailverification.EmailVerificationList;

public void createList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        EmailVerificationList list = ms.emailVerification().builder()
			.name("Test email verification")
			.addEmail("info@example.com")
			.addEmail("info1@example.com")
			.addEmail("info2@example.com")
			.create();

        System.out.println(list.id);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Verify an email list

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.emailverification.EmailVerificationList;

public void verifyList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        EmailVerificationList list = ms.emailVerification().verifyList("list id");

        System.out.println(list.status.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get email verification list results

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.emailverification.ListVerificationResults;
import com.mailsend.sdk.emailverification.VerificationResult;

public void verifyList() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        ListVerificationResults results = ms.emailVerification().verificationResults("list id");

        for (VerificationResult result : results.results) {
            System.out.println(result.address);
            System.out.println(result.result);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## SMS activity

### Get a list of SMS activities

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.sms.activities.SmsActivityList;
import com.mailsend.sdk.sms.activities.SmsActivity;

public void getActivities() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsActivityList list = ms.sms().activities().getActivities();
            
        for (SmsActivity activity : lists.smsActivities) {
            System.out.println(activity.id);
            System.out.println(activity.content);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get activity of a single message

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.sms.activities.SmsActivity;

public void getActivity() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsActivity activity = ms.sms().activities().getMessageActivity("message id");
            
        System.out.println(activity.id);
        System.out.println(activity.content);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## SMS messages

### Get a list of SMS messages

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.sms.messages.SmsMessageList;
import com.mailsend.sdk.sms.messages.SmsMessage;

public void getSmsMessages() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsMessageList list = ms.sms().messages().getMessages();
            
        for (SmsMessage message : list.messages) {
            System.out.println(message.id);
            System.out.println(message.text);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get info on a SMS message

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailsend.sdk.sms.messages.SmsMessage;

public void getSmsMessage() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsMessage message = ms.sms().messages().getMessage("message id");

        System.out.println(message.id);
        System.out.println(message.text);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## SMS Phone numbers

### Get a list of SMS phone numbers

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumber;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumberList;

public void getSmsPhoneNumbers() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        PhoneNumberList numbers = ms.sms().phoneNumbers().getPhoneNumbers();
        
        for (PhoneNumber number : numbers.phoneNumbers) {
            
            System.out.println(number.id);
        }
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get an SMS phone number

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumber;

public void getSmsPhoneNumber() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
                
        PhoneNumber number = ms.sms().phoneNumbers().getPhoneNumber("phone number id");

        System.out.println(number.id);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Update a single SMS phone number

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.phonenumbers.PhoneNumber;

public void updateSmsPhoneNumber() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        boolean pausePhoneNumber = false;
        PhoneNumber number = ms.sms().phoneNumbers().updatePhoneNumber("phone number id", pausePhoneNumber);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete an SMS phone number

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;

public void deleteSmsPhoneNumber() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        boolean result = ms.sms().phoneNumbers().deletePhoneNumber("phone number id");

        System.out.println(result);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## SMS recipients

### Get a list of SMS recipients

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.recipients.SmsRecipient;
import com.mailersend.sdk.sms.recipients.SmsRecipientList;

public void getSmsRecipients() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsRecipientList list = ms.sms().recipients().getRecipients();
        
        for (SmsRecipient recipient : list.recipients) {
            
            System.out.println(recipient.id);
        }
        
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get an SMS recipient

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.recipients.SmsRecipient;

public void getSmsRecipient() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsRecipient recipient = ms.sms().recipients().getRecipient("recipient id");
        
        System.out.println(recipient.id);
        
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Update a single SMS recipient

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.recipients.SmsRecipient;

public void updateSmsRecipient() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        String status = "opt_out";

        SmsRecipient recipient = ms.sms().recipients().updateRecipient("recipient id", status);
        
        System.out.println(recipient.status);
        
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## SMS inbounds

### Get a list of SMS inbound routes

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteList;

public void getSmsInboundRoutes() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();
        
        for (SmsInboundRoute route : routes.routes) {
            
            System.out.println(route.id);
        }
        
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get a single SMS inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute;

public void getSmsInboundRoute() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsInboundRoute route = ms.sms().inboundRoutes().getSmsInboundRoute("route id");
        
        System.out.println(route.id);
        
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Add an SMS inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute;

public void addSmsInboundRoute() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsInboundRoute route = ms.sms().inboundRoutes().builder()
                .smsNumberId("sms number id")
                .name("Test inbound route")
                .enabled(false)
                .forwardUrl("https://example.com")
                .filter("equal", "START")
                .addSmsInboundRoute();
        
        System.out.println(route.id);
        
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Update an inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute;

public void updateSmsInboundRoute() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsInboundRoute route = ms.sms().inboundRoutes().builder()
                .smsNumberId("sms number id")
                .name("Test inbound route updated")
                .enabled(false)
                .forwardUrl("https://example.com")
                .filter("equal", "START")
                .updateSmsInboundRoute("route id");
        
        System.out.println(route.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete an SMS inbound route

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute;

public void deleteSmsInboundRoute() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        boolean result = ms.sms().inboundRoutes().deleteSmsInboundRoute("route id");
        
        System.out.println(result);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

## SMS webhooks

### Get a list of SMS webhooks

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sms.webhooks.SmsWebhook;
import com.mailersend.sms.webhooks.SmsWebhookList;

public void getSmsWebhooks() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsWebhookList list = ms.sms().webhooks().getWebhooks("phone number id");
        
        for (SmsWebhook webhook : list.webhooks) {
            
            System.out.println(webhook.id);
        }
        
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Get an SMS webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sms.webhooks.SmsWebhook;

public void getSmsWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsWebhook webhook = ms.sms().webhooks().getWebhook("webhook id");
        
        System.out.println(webhook.id);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Create an SMS webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sms.webhooks.SmsWebhook;

public void createSmsWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsWebhook webhook = ms.sms().webhooks().builder()
            .addEvent("sms.sent")
            .name("sms webhook")
            .url("https://example.com")
            .createWebhook("sms phone number id");
        
        System.out.print(webhook.id);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Update an SMS webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sms.webhooks.SmsWebhook;

public void updateSmsWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        SmsWebhook webhook = ms.sms().webhooks().builder()
        	.name("sms updated webhook")
        	.updateWebhook("webhook id");
        	
        System.out.print(webhook.name);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```

### Delete an SMS webhook

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;

public void deleteSmsWebhook() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        boolean result = ms.sms().webhooks().deleteWebhook("webhook id");
        	
        System.out.print(result);
        
    } catch (MailerSendException e) {
        
        e.printStackTrace();
    }
}
```
## SMS

### Send an SMS with personalization

```java
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;

public void sendSms() {
    
    MailerSend ms = new MailerSend();
    ms.setToken("mailersend token");
    
    try {
        
        String messageId = ms.sms().builder().from("from phone number")
        .addRecipient("to phone number")
        .text("test sms {{name}}")
        .addPersonalization("to phone number", "name", "name personalization")
        .send();
        
        System.out.println(messageId);
        
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
