/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * The response of the emails list endpoint
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailsList extends PaginatedResponse {

    @SerializedName("data")
    public EmailListItem[] emails;

    protected transient MailerSend mailersendObj;

    /** The query parameters of the request that returned this list, without the page */
    protected transient String baseQuery;


    /**
     * Returns the current results page
     *
     * @return a int.
     */
    public int getCurrentPage() {

        if (meta != null) {

            return meta.currentPage;
        }

        return 0;
    }


    /**
     * Whether there is a next results page.
     * The endpoint returns no total and no last page, so the links are the only way to tell
     *
     * @return a boolean.
     */
    public boolean hasNext() {

        return links != null && links.next != null;
    }


    /**
     * Whether there is a previous results page
     *
     * @return a boolean.
     */
    public boolean hasPrevious() {

        return links != null && links.prev != null;
    }


    /**
     * Gets the next results page using the original filters
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.emails.EmailsList} object or null if there are no more results
     */
    public EmailsList next() throws MailerSendException {

        if (mailersendObj == null || !hasNext()) {

            return null;
        }

        return mailersendObj.emails().requestEmails(baseQuery, getCurrentPage() + 1);
    }


    /**
     * Gets the previous results page using the original filters
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link com.mailersend.sdk.emails.EmailsList} object or null if this is the first page
     */
    public EmailsList previous() throws MailerSendException {

        if (mailersendObj == null || !hasPrevious()) {

            return null;
        }

        return mailersendObj.emails().requestEmails(baseQuery, getCurrentPage() - 1);
    }


    /**
     * Is called to perform any actions after the deserialization of the response
     * to the /emails endpoint
     * Do not call directly
     */
    public void postDeserialize() {

        if (emails == null) {

            emails = new EmailListItem[0];

            return;
        }

        for (EmailListItem email : emails) {

            email.parseDates();
        }
    }
}
