package ai.cotogoto.noby;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

}
