/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.whatsapp.templates.WhatsAppTemplate;
import com.mailersend.sdk.whatsapp.templates.WhatsAppTemplateList;

public class WhatsAppTemplatesTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("WhatsAppTemplatesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    @Test
    public void TestGetWhatsAppTemplates() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppTemplateList list = ms.whatsapp().templates()
                .whatsappAccountId("3enl6x27wmrxrl2v")
                .status("approved")
                .category("UTILITY")
                .language("en_US")
                .page(1)
                .limit(25)
                .getTemplates();

            assertEquals(2, list.templates.length);

            WhatsAppTemplate template = list.templates[0];
            assertEquals("23zxk54v6gjy6v7m", template.id);
            assertEquals("3enl6x27wmrxrl2v", template.whatsappAccountId);
            assertEquals("order_confirmation", template.name);
            assertEquals("Order confirmation", template.readableName);
            assertEquals("UTILITY", template.category);
            assertEquals("en_US", template.language);
            assertEquals("approved", template.status);
            assertEquals("{{1}}", template.components.header.text);
            assertNotNull(template.createdAt);
            assertNotNull(template.updatedAt);

            WhatsAppTemplate noHeader = list.templates[1];
            assertNull(noHeader.components.header);
            assertEquals("QUICK_REPLY", noHeader.components.buttons[0].type);
            assertNull(noHeader.components.buttons[0].url);
            assertNull(list.links.next);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestGetSingleWhatsAppTemplate() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppTemplate template = ms.whatsapp().templates().getTemplate("23zxk54v6gjy6v7m");

            assertEquals("23zxk54v6gjy6v7m", template.id);
            assertEquals("Hi {{1}}, your order is confirmed and will arrive on {{2}}.", template.components.body.text);
            assertEquals(1, template.components.buttons.length);
            assertEquals("URL", template.components.buttons[0].type);
            assertEquals("View order", template.components.buttons[0].text);
            assertEquals("https://shop.example.com/{{1}}", template.components.buttons[0].url);

        } catch (MailerSendException e) {
            fail();
        }
    }
}
