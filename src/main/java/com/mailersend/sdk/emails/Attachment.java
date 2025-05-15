/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/

package com.mailersend.sdk.emails;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * <p>Attachment class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Attachment {

    @SerializedName("content")
    public String content;

    @SerializedName("filename")
    public String filename;

    @SerializedName("id")
    public String id;

    @SerializedName("disposition")
    public String disposition;

    /**
     * Reads a file, encodes it to base64 and sets it as an attachment
     *
     * @param path a {@link java.lang.String} object.
     * @throws java.io.IOException
     */
    public void AddAttachmentFromFile(String path) throws IOException {
        File file = new File(path);
        this.content = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        this.filename = file.getName();
        setDisposition(false);
    }

    /**
     * Sets an attachments contents and filename
     *
     * @param content  The base64 contents of the attachment
     * @param filename a {@link java.lang.String} object.
     */
    public void setAttachment(String content, String filename) {
        this.content = content;
        this.filename = filename;
        setDisposition(false);
    }

    /**
     * Reads a file, encodes it to base64 and sets it as an attachment
     *
     * @param contentId The contentId of the image. If set and not empty, disposition will be set to inline, else to attachment
     * @param path      a {@link java.lang.String} object.
     * @throws java.io.IOException
     */
    public void AddImageFromFile(String contentId, String path) throws IOException {
        File file = new File(path);
        this.id = contentId;
        this.content = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        this.filename = file.getName();
        setDisposition(contentId != null && contentId.trim().length() > 1);
    }

    /**
     * Sets an attachments contents and filename
     *
     * @param contentId The contentId of the image. If set and not empty, disposition will be set to inline, else to attachment
     * @param content   The base64 contents of the attachment
     * @param filename  a {@link java.lang.String} object.
     */
    public void setImage(String contentId, String content, String filename) {
        this.id = contentId;
        this.content = content;
        this.filename = filename;
        setDisposition(contentId != null && contentId.trim().length() > 1);
    }

    /**
     * Sets the disposition of this attachment.
     *
     * @param inline If set to true, disposition will be set to "inline", if false to "attachment"
     */
    public void setDisposition(boolean inline) {
        this.disposition = inline ? "inline" : "attachment";
    }
}