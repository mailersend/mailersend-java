/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.inboundmessages;

import com.google.gson.annotations.SerializedName;

/**
 * <p>The media file of an <code>image</code>, <code>audio</code>, <code>video</code>, <code>document</code> or <code>sticker</code> inbound message.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessageAttachment {

    /**
     * A temporary signed URL to the file. null when the file is not stored
     */
    @SerializedName("url")
    public String url;

    /**
     * stored, pending, failed or expired
     */
    @SerializedName("status")
    public String status;

    @SerializedName("mime_type")
    public String mimeType;

    /**
     * The file size in bytes
     */
    @SerializedName("size")
    public Long size;

    @SerializedName("caption")
    public String caption;

    @SerializedName("filename")
    public String filename;

    /**
     * Present for stickers only
     */
    @SerializedName("animated")
    public Boolean animated;
}
