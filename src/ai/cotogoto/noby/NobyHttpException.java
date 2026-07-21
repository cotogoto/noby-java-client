package ai.cotogoto.noby;

/**
 * Exception thrown when the NOBY API returns an unsuccessful HTTP status.
 *
 * <p>The response body is deliberately excluded from the exception message so
 * that logging the exception does not implicitly disclose response data.</p>
 */
public class NobyHttpException extends Exception {

    private static final long serialVersionUID = 1L;

    /** Maximum number of response-body characters retained for diagnostics. */
    public static final int MAX_RESPONSE_BODY_LENGTH = 8192;

    private final int     status;

    private final String  responseBody;

    private final boolean responseBodyTruncated;

    /**
     * Creates an exception for an unsuccessful HTTP response.
     *
     * @param status HTTP status code
     * @param responseBody response body; may be {@code null}
     */
    public NobyHttpException(final int status, final String responseBody) {

        this(status, responseBody, null);
    }


    /**
     * Creates an exception for an unsuccessful HTTP response whose body could
     * not be read.
     *
     * @param status HTTP status code
     * @param responseBody response body; may be {@code null}
     * @param cause body-reading failure
     */
    public NobyHttpException(final int status, final String responseBody, final Throwable cause) {

        super("HTTP error: " + status, cause);
        this.status = status;

        final var body = responseBody == null ? "" : responseBody;
        this.responseBodyTruncated = body.length() > MAX_RESPONSE_BODY_LENGTH;
        this.responseBody = this.responseBodyTruncated
                ? body.substring(0, MAX_RESPONSE_BODY_LENGTH)
                : body;
    }


    /**
     * Returns the HTTP status code.
     *
     * @return HTTP status code
     */
    public final int getStatus() {

        return this.status;
    }


    /**
     * Returns the retained response body. Callers must treat this value as
     * potentially sensitive and must not log it without appropriate handling.
     *
     * @return response body, or an empty string when unavailable
     */
    public final String getResponseBody() {

        return this.responseBody;
    }


    /**
     * Indicates whether the response body exceeded the retention limit.
     *
     * @return {@code true} when the retained body was truncated
     */
    public final boolean isResponseBodyTruncated() {

        return this.responseBodyTruncated;
    }
}
