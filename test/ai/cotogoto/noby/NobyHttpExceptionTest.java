package ai.cotogoto.noby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NobyHttpExceptionTest {

    @Test
    public void retainsStatusAndBodyWithoutAddingBodyToMessage() {

        final var exception = new NobyHttpException(400, "diagnostic response");

        assertEquals(400, exception.getStatus());
        assertEquals("diagnostic response", exception.getResponseBody());
        assertFalse(exception.isResponseBodyTruncated());
        assertEquals("HTTP error: 400", exception.getMessage());
        assertFalse(exception.toString().contains("diagnostic response"));
        assertTrue(exception instanceof Exception);
    }


    @Test
    public void convertsNullBodyToEmptyString() {

        final var exception = new NobyHttpException(500, null);

        assertEquals("", exception.getResponseBody());
        assertFalse(exception.isResponseBodyTruncated());
    }


    @Test
    public void retainsDifferentHttpStatuses() {

        final int[] statuses = { 401, 403, 404, 429, 500, 503 };

        for (final int status : statuses) {
            assertEquals(status, new NobyHttpException(status, "").getStatus());
        }
    }


    @Test
    public void retainsInvalidJsonAsPlainText() {

        final var exception = new NobyHttpException(400, "{not-json");

        assertEquals("{not-json", exception.getResponseBody());
    }


    @Test
    public void truncatesOversizedBody() {

        final var body = repeat("x", NobyHttpException.MAX_RESPONSE_BODY_LENGTH + 1);
        final var exception = new NobyHttpException(400, body);

        assertEquals(NobyHttpException.MAX_RESPONSE_BODY_LENGTH, exception.getResponseBody().length());
        assertTrue(exception.isResponseBodyTruncated());
    }


    @Test
    public void preservesBodyReadingFailureAsCause() {

        final var cause = new IllegalStateException("read failed");
        final var exception = new NobyHttpException(502, "", cause);

        assertEquals(cause, exception.getCause());
        assertEquals(502, exception.getStatus());
    }


    private static String repeat(final String value, final int count) {

        final var builder = new StringBuilder(count * value.length());
        for (var i = 0; i < count; i++) {
            builder.append(value);
        }
        return builder.toString();
    }
}
