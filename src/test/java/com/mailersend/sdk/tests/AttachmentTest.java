package com.mailersend.sdk.tests;

import com.google.gson.Gson;
import com.mailersend.sdk.emails.Attachment;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttachmentTest {
    @TempDir
    Path directory;

    @ParameterizedTest
    @CsvSource({"x, inline", "xx, inline", "' x ', inline", "NULL, attachment", "'', attachment", "'   ', attachment"})
    void setImageUsesNonEmptyContentId(String contentId, String disposition) {
        Attachment attachment = new Attachment();
        attachment.setImage(contentId.equals("NULL") ? null : contentId, "AQID", "image.png");
        assertImage(attachment, contentId.equals("NULL") ? null : contentId, disposition);
    }

    @ParameterizedTest
    @CsvSource({"x, inline", "xx, inline", "' x ', inline", "NULL, attachment", "'', attachment", "'   ', attachment"})
    void addImageFromFileUsesNonEmptyContentId(String contentId, String disposition) throws IOException {
        Path image = directory.resolve("image.png");
        Files.write(image, new byte[]{1, 2, 3});
        Attachment attachment = new Attachment();
        attachment.AddImageFromFile(contentId.equals("NULL") ? null : contentId, image.toString());
        assertImage(attachment, contentId.equals("NULL") ? null : contentId, disposition);
    }

    private void assertImage(Attachment attachment, String contentId, String disposition) {
        assertEquals(contentId, attachment.id);
        assertEquals("AQID", attachment.content);
        assertEquals("image.png", attachment.filename);
        assertEquals(disposition, new Gson().toJsonTree(attachment).getAsJsonObject().get("disposition").getAsString());
    }
}
