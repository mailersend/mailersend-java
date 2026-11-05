package com.mailersend.sdk.tests;

import static com.mailersend.sdk.tests.CapturingHttpClient.json;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
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
import com.mailersend.sdk.templates.TemplateTranslation;
import com.mailersend.sdk.templates.TemplatesList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class TemplatesTest {

    private static final String TEMPLATES_URL = "https://api.mailersend.com/v1/templates";

    private static final String TEMPLATE_ID = "x8emy5o5world01w";

    /** The create and update response of the docs */
    private static final String TEMPLATE_RESPONSE = "{\"data\":{\"id\":\"x8emy5o5world01w\",\"name\":\"My Template\","
            + "\"description\":null,\"type\":\"html\","
            + "\"image_path\":\"http://mailersend.com/images/templates/placeholder.png\","
            + "\"created_at\":\"2021-06-22T15:38:51.000000Z\",\"updated_at\":\"2021-06-22T15:38:51.000000Z\"}}";

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


    /**
     * Tests that createTemplate() posts the documented fields, without the subject and the
     * auto_generate that the create endpoint does not accept, and parses the created template
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testCreateTemplateSendsTheDocumentedFields() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(201, TEMPLATE_RESPONSE);

        Template template = ms.templates().builder()
            .name("My Template")
            .html("<p>Hello {{name}}</p>")
            .text("Hello {{name}}")
            .subject("Ignored subject")
            .domainId(TestHelper.domainId)
            .categories(new String[] { "5genxmqlgozlyvk7" })
            .tags(new String[] { "welcome" })
            .autoGenerate(true)
            .createTemplate();

        assertEquals("POST", client.lastRequest().method());
        assertEquals(TEMPLATES_URL, client.lastUrl());
        assertEquals(json("{"
                + "'name':'My Template',"
                + "'html':'<p>Hello {{name}}</p>',"
                + "'text':'Hello {{name}}',"
                + "'domain_id':'" + TestHelper.domainId + "',"
                + "'categories':['5genxmqlgozlyvk7'],"
                + "'tags':['welcome']"
                + "}"), client.lastBody());
        assertEquals(TEMPLATE_ID, template.id);
        assertEquals(Date.from(Instant.parse("2021-06-22T15:38:51Z")), template.createdAt);
    }


    /**
     * Tests that getTemplate() parses the content, the categories, the translations and the stats
     * of the documented response
     */
    @Test
    public void testGetTemplateParsesContentTranslationsAndStats() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        Template template = ms.templates().getTemplate(TEMPLATE_ID);

        assertEquals("<p>Hello {{name}}</p>", template.html);
        assertEquals("Hello {{name}}", template.text);
        assertEquals("5genxmqlgozlyvk7", template.categories[0].id);
        assertEquals("de", template.translations[0].language);
        assertEquals(Date.from(Instant.parse("2021-06-23T09:30:00Z")), template.translations[0].lastEmailSentAt);

        assertEquals(1200, template.stats.total);
        assertEquals(0, template.stats.queued);
        assertEquals(4, template.stats.sent);
        assertEquals(16, template.stats.rejected);
        assertEquals(1180, template.stats.delivered);
        assertEquals("98", template.stats.deliveryRate);
        assertEquals(590, template.stats.opened);
        assertEquals("50", template.stats.openRate);
        assertEquals(118, template.stats.clicked);
        assertEquals("10", template.stats.clickRate);
        assertEquals(2, template.stats.unsubscribed);
        assertEquals("0", template.stats.unsubscribeRate);
        assertEquals(0, template.stats.complained);
        assertEquals("0", template.stats.complainRate);
        assertEquals(Date.from(Instant.parse("2026-10-13T09:30:00Z")), template.stats.lastEmailSentAt);
    }


    /**
     * Tests that getTemplate() leaves the domain totals null when the API returns them as an empty
     * list, which it does for the domain of a template
     */
    @Test
    public void testGetTemplateWithEmptyDomainTotals() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        Template template = ms.templates().getTemplate(TEMPLATE_ID);

        assertEquals("1jreeo", template.domain.id);
        assertNull(template.domain.totals);
    }


    /**
     * Tests that the list items carry the categories and the translations of the documented response
     */
    @Test
    public void testGetTemplatesParsesCategoriesAndTranslations() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        TemplatesList list = ms.templates().getTemplates();

        TemplateItem item = list.templates[0];

        assertEquals(1, item.categories.length);
        assertEquals("5genxmqlgozlyvk7", item.categories[0].id);

        TemplateTranslation translation = item.translations[0];

        assertEquals("de", translation.language);
        assertEquals("German", translation.languageName);
        assertEquals("published", translation.status);
        assertEquals(120, translation.totalEmailSent);
        assertEquals(Date.from(Instant.parse("2020-06-12T08:15:00Z")), translation.lastEmailSentAt);
        assertEquals(Date.from(Instant.parse("2020-06-10T10:20:00Z")), translation.translatedAt);
        assertEquals(Date.from(Instant.parse("2020-06-10T10:15:00Z")), translation.createdAt);
        assertEquals(Date.from(Instant.parse("2020-06-10T10:20:00Z")), translation.updatedAt);

        assertEquals(0, list.templates[1].categories.length);
        assertEquals(0, list.templates[1].translations.length);
    }
}
