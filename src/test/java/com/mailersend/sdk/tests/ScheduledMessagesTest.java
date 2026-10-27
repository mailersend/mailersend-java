/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.scheduledmessages.ScheduledMessage;
import com.mailersend.sdk.scheduledmessages.ScheduledMessages;
import com.mailersend.sdk.scheduledmessages.ScheduledMessagesList;
import com.mailersend.sdk.vcr.VcrRecorder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ScheduledMessagesTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("ScheduledMessagesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }


    /**
     * GET /v1/message-schedules returns a list with pagination
     */
    @Test
    public void testCanGetScheduledMessages() throws MailerSendException {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        ScheduledMessagesList list = ms.scheduledMessages().getScheduledMessages();

        assertNotNull(list);
        assertNotNull(list.scheduledMessages);
        assertNotNull(list.meta);
    }


    /**
     * GET /v1/message-schedules with domainId, page, limit, and status filters applied
     */
    @Test
    public void testCanGetScheduledMessagesWithFilters() throws MailerSendException {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        ScheduledMessagesList list = ms.scheduledMessages()
                .domainId(TestHelper.domainId)
                .page(2)
                .limit(10)
                .status(ScheduledMessages.STATUS_SCHEDULED)
                .getScheduledMessages();

        assertNotNull(list);
        assertNotNull(list.scheduledMessages);
        assertNotNull(list.meta);
    }


    /**
     * GET /v1/message-schedules/{id} returns a single scheduled message with parsed dates
     */
    @Test
    public void testCanGetSingleScheduledMessage() throws MailerSendException {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        ScheduledMessage message = ms.scheduledMessages().getScheduledMessage("test-schedule-id-123");

        assertNotNull(message);
        assertNotNull(message.id);
        assertFalse(message.id.isEmpty(), "id should not be empty");
        assertNotNull(message.sendAt);
    }


    /**
     * DELETE /v1/message-schedules/{id} returns true on success
     */
    @Test
    public void testCanDeleteScheduledMessage() throws MailerSendException {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        boolean result = ms.scheduledMessages().deleteScheduledMessage("test-schedule-id-123");

        assertTrue(result);
    }


    /**
     * GET /v1/message-schedules/{id} with invalid ID returns 404
     */
    @Test
    public void testGetScheduledMessageWithInvalidIdFails() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.scheduledMessages().getScheduledMessage("invalid-schedule-id");
        });
        assertEquals(404, e.code);
    }


    /**
     * DELETE /v1/message-schedules/{id} with invalid ID returns 404
     */
    @Test
    public void testDeleteScheduledMessageWithInvalidIdFails() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.scheduledMessages().deleteScheduledMessage("invalid-schedule-id");
        });
        assertEquals(404, e.code);
    }


    /**
     * GET /v1/message-schedules with an invalid token returns 401
     */
    @Test
    public void testInvalidTokenFailsWith401OnGetScheduledMessages() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.invalidToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.scheduledMessages().getScheduledMessages();
        });
        assertEquals(401, e.code);
    }


}
