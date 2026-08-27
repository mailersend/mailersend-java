/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.MailerSendStringResponse;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>Emails class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Emails {

    private MailerSend apiObjectReference;

    private Recipient defaultFrom = null;

    private String domainIdFilter = null;
    private Long dateFromFilter = null;
    private Long dateToFilter = null;
    private int limitFilter = -1;
    private int pageFilter = -1;
    private String[] statusFilter = null;
    private String[] interactionFilter = null;
    private String recipientEmailFilter = null;
    private String messageIdFilter = null;
    private String templateIdFilter = null;
    private String subjectFilter = null;
    private String tagFilter = null;

    /**
     * <p>Constructor for Emails.</p>
     *
     * @param objectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Emails(MailerSend objectRef) {
        
        apiObjectReference = objectRef;
    }
    
    
    /**
     * Sets the default from
     *
     * @param from a {@link com.mailersend.sdk.Recipient} object.
     */
    public void setDefaultFrom(Recipient from) {
        
        this.defaultFrom = from;
    }
    
    
    /**
     * Creates a new email
     *
     * @return a {@link com.mailersend.sdk.emails.Email} object.
     */
    public Email createEmail() {
        
        Email newEmail = new Email();
        newEmail.from = this.defaultFrom;
        
        return newEmail;
    }
    
    
    /**
     * Sends the given email
     *
     * @param email a {@link com.mailersend.sdk.emails.Email} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.MailerSendResponse} object.
     */
    public MailerSendResponse send(Email email) throws MailerSendException {
        
        String json = email.serializeForSending();
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
       
        MailerSendResponse response = api.postRequest("/email", json, MailerSendResponse.class);
        
        return response;
    }
    
    
    /**
     * Sends the given emails in one batch call
     *
     * @param emails an array of {@link com.mailersend.sdk.emails.Email} objects.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link java.lang.String} object.
     */
    public String bulkSend(Email[] emails) throws MailerSendException {
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
        
        for (Email email : emails) {
            
            email.preparePersonalizationForAllRecipients();
            email.prepareSubstitutionsForAllRecipients();
        }
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        String json = gson.toJson(emails);
        
        SendBulkResponse response = api.postRequest("/bulk-email", json, SendBulkResponse.class);
        
        return response.bulkSendId;
    }
    
    
    /**
     * Get the status of a bulk email send
     *
     * @param bulkSendId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.emails.BulkSendStatus} object.
     */
    public BulkSendStatus bulkSendStatus(String bulkSendId) throws MailerSendException {
        
        String endpoint = "/bulk-email/".concat(bulkSendId);
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());
    
        
        MailerSendStringResponse response = api.getRequest(endpoint, MailerSendStringResponse.class);
        
        // because the response might include null fields, we'll use a custom deserializer to get the BulkSendStatus object
   
        JsonDeserializer<BulkSendStatus> deserializer = new JsonDeserializer<BulkSendStatus>() {  

            @Override
            public BulkSendStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                
                JsonObject jsonObject = json.getAsJsonObject();
                
                JsonObject data = jsonObject.get("data").getAsJsonObject();
                
                BulkSendStatus newStatus = new BulkSendStatus();
                
                newStatus.id = data.get("id").getAsString();
                
                newStatus.state = data.get("state").getAsString();
                
                newStatus.totalRecipientsCount = data.get("total_recipients_count").getAsInt();
                
                newStatus.suppressedRecipientsCount = data.get("suppressed_recipients_count").getAsInt();
                
                newStatus.validationErrorsCount = data.get("validation_errors_count").getAsInt();
                
                ArrayList<String> messagesIdsList = new ArrayList<String>();
                
                if (!data.get("messages_id").isJsonNull()) {
                	
	                JsonArray messagesIds = data.get("messages_id").getAsJsonArray();
	             	                
	                for (JsonElement messageId : messagesIds) {
	                    
	                    messagesIdsList.add(messageId.getAsString());
	                }
                }
                
                newStatus.messagesId = messagesIdsList.toArray(new String[0]);
                
                newStatus.createdAtString = data.get("created_at").getAsString();
                
                newStatus.updatedAtString = data.get("updated_at").getAsString();
                
                JsonElement validationErrorsEl = data.get("validation_errors");
                
                if (validationErrorsEl != null) {
                
                    newStatus.validationErrors = validationErrorsEl.getAsJsonObject();
                }
                
                JsonElement suppressedRecipientsEl = data.get("suppressed_recipients");
                
                if (suppressedRecipientsEl != null && !suppressedRecipientsEl.isJsonNull()) {
                    
                    newStatus.suppressedRecipients = suppressedRecipientsEl.getAsJsonObject();
                }
                
                return newStatus;
            }
        };
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        
        gsonBuilder.registerTypeAdapter(BulkSendStatus.class, deserializer);
        
        Gson customGson = gsonBuilder.create();  
        
        BulkSendStatus status = customGson.fromJson(response.responseString, BulkSendStatus.class);
        
        status.parseDates();

        return status;

    }


    /**
     * Sets the domain id to retrieve the emails for. Required by getEmails()
     *
     * @param domainId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails domainId(String domainId) {

        domainIdFilter = domainId;

        return this;
    }


    /**
     * Sets the from date as a unix timestamp. Required by getEmails()
     *
     * @param dateFrom a long, the date as a unix timestamp in seconds.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails dateFrom(long dateFrom) {

        dateFromFilter = dateFrom;

        return this;
    }


    /**
     * Sets the from date. Required by getEmails()
     *
     * @param dateFrom a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails dateFrom(Date dateFrom) {

        dateFromFilter = dateFrom == null ? null : dateFrom.getTime() / 1000;

        return this;
    }


    /**
     * Sets the to date as a unix timestamp. Required by getEmails()
     *
     * @param dateTo a long, the date as a unix timestamp in seconds.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails dateTo(long dateTo) {

        dateToFilter = dateTo;

        return this;
    }


    /**
     * Sets the to date. Required by getEmails()
     *
     * @param dateTo a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails dateTo(Date dateTo) {

        dateToFilter = dateTo == null ? null : dateTo.getTime() / 1000;

        return this;
    }


    /**
     * Sets the results limit (10 - 100, default 25)
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails limit(int limit) {

        limitFilter = limit;

        return this;
    }


    /**
     * Sets the results page to retrieve (1 - 1000, default 1)
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails page(int page) {

        pageFilter = page;

        return this;
    }


    /**
     * Filters the emails by status. Multiple values are combined with OR
     *
     * @param status one or more of the constants in {@link com.mailersend.sdk.emails.EmailStatus}.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails status(String... status) {

        statusFilter = status;

        return this;
    }


    /**
     * Filters the emails by recipient interaction. Multiple values are combined with OR
     *
     * @param interaction one or more of the constants in {@link com.mailersend.sdk.emails.EmailInteraction}.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails interaction(String... interaction) {

        interactionFilter = interaction;

        return this;
    }


    /**
     * Filters the emails by the recipient's email address
     *
     * @param recipientEmail a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails recipientEmail(String recipientEmail) {

        recipientEmailFilter = recipientEmail;

        return this;
    }


    /**
     * Filters the emails by the id of the message that created them
     *
     * @param messageId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails messageId(String messageId) {

        messageIdFilter = messageId;

        return this;
    }


    /**
     * Filters the emails by template id
     *
     * @param templateId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails templateId(String templateId) {

        templateIdFilter = templateId;

        return this;
    }


    /**
     * Filters the emails by subject. Partial, case insensitive match, minimum 3 characters
     *
     * @param subject a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails subject(String subject) {

        subjectFilter = subject;

        return this;
    }


    /**
     * Filters the emails by tag. Exact match against a value of the email's tags
     *
     * @param tag a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.emails.Emails} object.
     */
    public Emails tag(String tag) {

        tagFilter = tag;

        return this;
    }


    /**
     * Gets a list of emails using the set filters. The domain id, from date and to date are required.
     * Use EmailsList.next() to get the following results page
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.emails.EmailsList} object.
     */
    public EmailsList getEmails() throws MailerSendException {

        if (domainIdFilter == null || domainIdFilter.isBlank()) {

            throw new MailerSendException("A domain id is required.");
        }

        if (dateFromFilter == null || dateToFilter == null) {

            throw new MailerSendException("Date from and Date to dates are required.");
        }

        if (dateToFilter <= dateFromFilter) {

            throw new MailerSendException("From date cannot be after to date.");
        }

        return requestEmails(prepareParamsUrl(), pageFilter);
    }


    /**
     * Gets a single email with its activity events
     *
     * @param emailId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.emails.EmailInfo} object.
     */
    public EmailInfo getEmail(String emailId) throws MailerSendException {

        String endpoint = "/email/".concat(emailId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleEmailResponse response = api.getRequest(endpoint, SingleEmailResponse.class);

        if (response.email != null) {

            response.email.postDeserialize();
        }

        return response.email;
    }


    /**
     * Does the request to the emails endpoint with the given query parameters and page
     * @param query The query part of the request url, without the page
     * @param page The results page to retrieve, pass -1 to let the API default to the first page
     * @return the found list of emails
     * @throws MailerSendException
     */
    EmailsList requestEmails(String query, int page) throws MailerSendException {

        String endpoint = "/emails".concat(query);

        if (page > -1) {

            endpoint = endpoint.concat(query.isEmpty() ? "?" : "&").concat("page=").concat(String.valueOf(page));
        }

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        EmailsList response = api.getRequest(endpoint, EmailsList.class);

        response.postDeserialize();

        // we pass these to the EmailsList object so that it can retrieve the next and previous pages
        response.mailersendObj = apiObjectReference;
        response.baseQuery = query;

        return response;
    }


    /**
     * Prepares the query part of the emails request url, without the page
     * @return
     */
    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("domain_id=".concat(urlEncode(domainIdFilter)));

        params.add("date_from=".concat(String.valueOf(dateFromFilter)));

        params.add("date_to=".concat(String.valueOf(dateToFilter)));

        if (limitFilter > -1) {

            params.add("limit=".concat(String.valueOf(limitFilter)));
        }

        if (statusFilter != null) {

            for (String status : statusFilter) {

                params.add("status[]=".concat(urlEncode(status)));
            }
        }

        if (interactionFilter != null) {

            for (String interaction : interactionFilter) {

                params.add("interaction[]=".concat(urlEncode(interaction)));
            }
        }

        if (recipientEmailFilter != null) {

            params.add("recipient_email=".concat(urlEncode(recipientEmailFilter)));
        }

        if (messageIdFilter != null) {

            params.add("message_id=".concat(urlEncode(messageIdFilter)));
        }

        if (templateIdFilter != null) {

            params.add("template_id=".concat(urlEncode(templateIdFilter)));
        }

        if (subjectFilter != null) {

            params.add("subject=".concat(urlEncode(subjectFilter)));
        }

        if (tagFilter != null) {

            params.add("tag=".concat(urlEncode(tagFilter)));
        }

        String requestParams = "";

        for (int i = 0; i < params.size(); i++) {

            String attrSep = "&";

            if (i == 0) {

                attrSep = "?";
            }

            requestParams = requestParams.concat(attrSep).concat(params.get(i));
        }

        return requestParams;
    }


    /**
     * Url encodes a query parameter value
     * @param value
     * @return
     */
    private String urlEncode(String value) {

        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
