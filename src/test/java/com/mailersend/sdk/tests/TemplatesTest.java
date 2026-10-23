package com.mailersend.sdk.tests;

import static com.mailersend.sdk.tests.CapturingHttpClient.json;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
     * Tests retrieving a list of templates with the default page and limit
     */
    @Test
    public void testGetTemplates() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        TemplatesList list = ms.templates().getTemplates();

        assertEquals(1, list.templates.length);
        assertEquals("3z0vklo631xl7qrx", list.templates[0].id);
        assertEquals("Template", list.templates[0].name);
        assertEquals(Date.from(Instant.parse("2022-08-19T13:10:49Z")), list.templates[0].createdAt);
    }


    /**
     * Tests that getTemplates() sends the page, the limit and the domain id in the query
     */
    @Test
    public void testGetTemplatesSendsThePageLimitAndDomainId() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, "{\"data\":[],\"links\":{},\"meta\":{}}");

        ms.templates().page(2).limit(50).domainId(TestHelper.domainId).getTemplates();

        assertEquals("GET", client.lastRequest().method());
        assertEquals(TEMPLATES_URL + "?page=2&limit=50&domain_id=" + TestHelper.domainId, client.lastUrl());
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

        assertEquals("5genxmqlgozlyvk7", item.category.id);
        assertEquals("aut", item.category.name);
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

        assertNull(list.templates[1].category);
        assertEquals(0, list.templates[1].categories.length);
        assertEquals(0, list.templates[1].translations.length);
    }


    /**
     * Tests the retrieval of a single template
     */
    @Test
    public void testGetSingleTemplate() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        TemplatesList list = ms.templates().getTemplates();

        Template template = ms.templates().getTemplate(list.templates[0].id);

        assertEquals("3z0vklo631xl7qrx", template.id);
        assertEquals("Template", template.name);
        assertEquals("dd", template.type);
        assertEquals(0, template.stats.total);
        assertNull(template.stats.lastEmailSentAt);
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
     * Tests that getTemplate() parses the domain totals when the API returns them as an object
     */
    @Test
    public void testGetTemplateWithDomainTotals() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        Template template = ms.templates().getTemplate(TEMPLATE_ID);

        assertEquals(25, template.domain.totals.hardBounced);
        assertEquals(40, template.domain.totals.softBounced);
        assertEquals(231, template.domain.totals.sent);
        assertEquals(109, template.domain.totals.delivered);
    }


    /**
     * Tests deleting a template
     */
    @Test
    public void testDeleteTemplate() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        TemplatesList list = ms.templates().getTemplates();

        MailerSendResponse response = ms.templates().deleteTemplate(list.templates[0].id);

        assertEquals(200, response.responseStatusCode);
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
     * Tests that updateTemplate() puts the documented fields, auto_generate included and the
     * subject left out, to the template's endpoint
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testUpdateTemplateSendsTheDocumentedFields() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, TEMPLATE_RESPONSE);

        Template template = ms.templates().builder()
            .name("My Template")
            .html("<p>Hello {{name}}</p>")
            .text("Hello {{name}}")
            .subject("Ignored subject")
            .domainId(TestHelper.domainId)
            .categories(new String[] {})
            .tags(new String[] { "welcome" })
            .autoGenerate(true)
            .updateTemplate(TEMPLATE_ID);

        assertEquals("PUT", client.lastRequest().method());
        assertEquals(TEMPLATES_URL + "/" + TEMPLATE_ID, client.lastUrl());
        assertEquals(json("{"
                + "'name':'My Template',"
                + "'html':'<p>Hello {{name}}</p>',"
                + "'text':'Hello {{name}}',"
                + "'domain_id':'" + TestHelper.domainId + "',"
                + "'categories':[],"
                + "'tags':['welcome'],"
                + "'auto_generate':true"
                + "}"), client.lastBody());
        assertEquals(TEMPLATE_ID, template.id);
    }


    /**
     * Tests that createTemplate() requires the html and the text
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("missingTemplateContent")
    public void testCreateTemplateRequiresHtmlAndText(String label, String html, String text, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder()
                .name("My Template")
                .html(html)
                .text(text)
                .createTemplate();
        });

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> missingTemplateContent() {
        return Stream.of(
            Arguments.of("html not set", null, "Hello", "Template html cannot be empty"),
            Arguments.of("html blank", "  ", "Hello", "Template html cannot be empty"),
            Arguments.of("text not set", "<h1>Hello</h1>", null, "Template text cannot be empty"),
            Arguments.of("text blank", "<h1>Hello</h1>", "  ", "Template text cannot be empty")
        );
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
     * Tests that tags() rejects more than 5 tags and tags longer than 191 characters
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidTemplateTags")
    public void testTemplateTagsAreValidated(String label, String[] tags, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder().tags(tags);
        });

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidTemplateTags() {
        return Stream.of(
            Arguments.of("more than 5 tags", new String[]{"a", "b", "c", "d", "e", "f"}, "Template tags cannot have more than 5 items"),
            Arguments.of("tag longer than 191 characters", new String[]{"a".repeat(192)}, "Each template tag cannot be longer than 191 characters")
        );
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
     * Tests that getTemplate() throws when templateId is missing
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("missingTemplateIds")
    public void testGetTemplateWithEmptyId(String label, String templateId) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().getTemplate(templateId);
        });

        assertEquals("Template ID cannot be empty", e.getMessage());
    }


    /**
     * Tests that updateTemplate() throws when templateId is missing
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("missingTemplateIds")
    public void testUpdateTemplateWithEmptyId(String label, String templateId) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().builder().updateTemplate(templateId);
        });

        assertEquals("Template ID cannot be empty", e.getMessage());
    }


    /**
     * Tests that deleteTemplate() throws when templateId is missing
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("missingTemplateIds")
    public void testDeleteTemplateWithEmptyId(String label, String templateId) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.templates().deleteTemplate(templateId);
        });

        assertEquals("Template ID cannot be empty", e.getMessage());
    }

    static Stream<Arguments> missingTemplateIds() {
        return Stream.of(
            Arguments.of("empty template id", ""),
            Arguments.of("blank template id", "  "),
            Arguments.of("null template id", null)
        );
    }
}
