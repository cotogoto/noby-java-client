package ai.cotogoto.noby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import ai.cotogoto.noby.model.Setting;
import jakarta.ws.rs.client.ClientBuilder;

public class NobyClientTest {

    @Test
    public void decodeResponseReturnsResultForHttpOk() throws Exception {

        final var result = NobyClient.decodeResponse(200, "{\"text\":\"hello\",\"mood\":0.5}");

        assertEquals("hello", result.getText());
        assertEquals(0.5, result.getMood(), 0.0);
    }


    @Test
    public void decodeResponsePreservesApiErrorMessage() {

        try {
            NobyClient.decodeResponse(200, "{\"errors\":[{\"message\":\"invalid request\"}]}");
        } catch (final Exception e) {
            assertEquals("invalid request", e.getMessage());
            return;
        }

        throw new AssertionError("Expected an API error");
    }


    @Test
    public void decodeResponseReturnsHttpErrorDetails() throws Exception {

        try {
            NobyClient.decodeResponse(400, "bad request details");
        } catch (final NobyHttpException e) {
            assertEquals(400, e.getStatus());
            assertEquals("bad request details", e.getResponseBody());
            return;
        }

        throw new AssertionError("Expected an HTTP error");
    }


    @Test
    public void createParametersEncodesJapaneseEnding() throws Exception {

        final var query = createQuery("hello", "のじゃ");

        assertEquals("app_key=APP_KEY&text=hello&ending=%E3%81%AE%E3%81%98%E3%82%83", query);
    }


    @Test
    public void createParametersPreservesAsciiEnding() throws Exception {

        final var query = createQuery("hello", "noja");

        assertEquals("app_key=APP_KEY&text=hello&ending=noja", query);
    }


    @Test
    public void createParametersEncodesReservedCharactersInEnding() throws Exception {

        final var query = createQuery("hello", "a b&c=d%");

        assertEquals("app_key=APP_KEY&text=hello&ending=a+b%26c%3Dd%25", query);
    }


    @Test
    public void createParametersOmitsNullEnding() throws Exception {

        final var query = createQuery("hello", null);

        assertEquals("app_key=APP_KEY&text=hello", query);
        assertFalse(query.contains("ending="));
    }


    @Test
    public void createParametersEncodesTextAndEndingExactlyOnce() throws Exception {

        final var query = createQuery("あ%", "のじゃ%");
        final var expected = "app_key=APP_KEY&text=%E3%81%82%25&ending=%E3%81%AE%E3%81%98%E3%82%83%25";

        assertEquals(expected, query);

        try (var client = ClientBuilder.newClient()) {
            final var rawQuery = client.target("https://app.cotogoto.ai/webapi/noby.json?" + query)
                    .getUri()
                    .getRawQuery();
            assertEquals(expected, rawQuery);
        }
    }


    private static String createQuery(final String message, final String ending) throws Exception {

        final var setting = new Setting();
        setting.setEnding(ending);

        return new NobyClient("APP_KEY", setting).createParameters(message).toString();
    }

}
