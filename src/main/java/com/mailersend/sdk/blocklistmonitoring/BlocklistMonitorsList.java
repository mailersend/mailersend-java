/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.blocklistmonitoring;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * Represents a paginated list of blocklist monitors.
 */
public class BlocklistMonitorsList extends PaginatedResponse {

    @SerializedName("data")
    public BlocklistMonitor[] monitors;

    /**
     * Called after deserialization to post-process each monitor.
     */
    public void postDeserialize() {
        if (monitors != null) {
            for (BlocklistMonitor monitor : monitors) {
                monitor.postDeserialize();
            }
        }
    }
}
