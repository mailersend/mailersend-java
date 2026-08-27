/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

/**
 * The possible recipient interactions, used by the interaction filter of the emails list endpoint
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailInteraction {

    /** Constant <code>OPENED="opened"</code> */
    public static final String OPENED = "opened";

    /** Constant <code>CLICKED="clicked"</code> */
    public static final String CLICKED = "clicked";

    /** Constant <code>UNSUBSCRIBED="unsubscribed"</code> */
    public static final String UNSUBSCRIBED = "unsubscribed";

    /** Constant <code>COMPLAINED="complained"</code> */
    public static final String COMPLAINED = "complained";

    /**
     * Matches emails with none of the other interactions recorded.
     * Filter value only, it is never returned in a response
     */
    public static final String NO_INTERACTION = "no_interaction";
}
