package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.templates.Template;
import com.mailersend.sdk.templates.TemplateItem;
import com.mailersend.sdk.templates.TemplatesList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class TemplatesTest {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("TemplatesTest_" + info.getDisplayName());
	}

	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}

    /**
     * Tests retrieving a list of templates
     */
    @Test
    public void testGetTemplates() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            TemplatesList list = ms.templates().getTemplates();

            for (TemplateItem item : list.templates) {

                System.out.println(item.id);
                System.out.println(item.name);
            }

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }


    }


    /**
     * Tests the retrieval of a single template
     */
    @Test
    public void testGetSingleTemplate() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            TemplatesList list = ms.templates().getTemplates();

            if (list.templates.length == 0) {

                fail();
            }

            Template template = ms.templates().getTemplate(list.templates[0].id);

            System.out.println(template.id);
            System.out.println(template.name);
            System.out.println(template.imagePath);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests deleting a template
     */
    @Test
    public void testDeleteTemplate() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            TemplatesList list = ms.templates().getTemplates();

            if (list.templates.length == 0) {

                fail();
            }

            MailerSendResponse response = ms.templates().deleteTemplate(list.templates[0].id);

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that createTemplate() throws when html is not set
     */
    @Test
    public void testCreateTemplateWithoutHtml() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder()
                .name("My Template")
                .createTemplate();
        });

        assertEquals("Template html cannot be empty", e.getMessage());
    }


    /**
     * Tests that createTemplate() throws when text is not set (but html is set)
     */
    @Test
    public void testCreateTemplateWithoutText() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder()
                .name("My Template")
                .html("<h1>Hello</h1>")
                .createTemplate();
        });

        assertEquals("Template text cannot be empty", e.getMessage());
    }


    /**
     * Tests that name() throws when name exceeds 50 characters
     */
    @Test
    public void testTemplateNameTooLong() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder().name("a".repeat(51));
        });

        assertEquals("Template name cannot be longer than 50 characters", e.getMessage());
    }


    /**
     * Tests that tags() throws when more than 5 tags are provided
     */
    @Test
    public void testTemplateTooManyTags() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder().tags(new String[]{"a", "b", "c", "d", "e", "f"});
        });

        assertEquals("Template tags cannot have more than 5 items", e.getMessage());
    }


    /**
     * Tests that tags() throws when a tag exceeds 191 characters
     */
    @Test
    public void testTemplateTagTooLong() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder().tags(new String[]{"a".repeat(192)});
        });

        assertEquals("Each template tag cannot be longer than 191 characters", e.getMessage());
    }


    /**
     * Tests that limit() and page() reject invalid values (parameterized)
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidPaginationParams")
    public void testGetTemplatesInvalidPagination(String label, int limit, int page, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            if (label.contains("limit")) {
                ms.templates().limit(limit);
            } else {
                ms.templates().page(page);
            }
        });

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidPaginationParams() {
        return Stream.of(
            Arguments.of("limit too low", 5, 1, "Limit must be between 10 and 100"),
            Arguments.of("limit too high", 101, 1, "Limit must be between 10 and 100"),
            Arguments.of("page too low", 10, 0, "Page must be at least 1")
        );
    }


    /**
     * Tests that getTemplate() throws when templateId is empty
     */
    @Test
    public void testGetTemplateWithEmptyId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().getTemplate("");
        });

        assertEquals("Template ID cannot be empty", e.getMessage());
    }


    /**
     * Tests that updateTemplate() throws when templateId is empty
     */
    @Test
    public void testUpdateTemplateWithEmptyId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder().updateTemplate("");
        });

        assertEquals("Template ID cannot be empty", e.getMessage());
    }


    /**
     * Tests that deleteTemplate() throws when templateId is empty
     */
    @Test
    public void testDeleteTemplateWithEmptyId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().deleteTemplate("");
        });

        assertEquals("Template ID cannot be empty", e.getMessage());
    }

}
