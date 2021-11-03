/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/

package com.mailersend.sdk.emails;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

import com.google.gson.annotations.SerializedName;

public class Attachment {

    @SerializedName("content")
    public String content;

    @SerializedName("filename")
    public String filename;

    @SerializedName("id")
    public String id;
    
    
    /**
     * Reads a file, encodes it to base64 and sets it as an attachment
     * @param path
     * @throws IOException
     */
    public void AddAttachmentFromFile(String path) throws IOException {
        File file = new File(path);
        this.content = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        this.filename = file.getName();
    }
    
    
    /**
     * Sets an attachments contents and filename
     * @param content The base64 contents of the attachment
     * @param filename
     */
    public void setAttachment(String content, String filename) {
        
        this.content = content;
        this.filename = filename;
    }
}
