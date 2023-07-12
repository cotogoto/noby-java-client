package ai.cotogoto.noby;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import ai.cotogoto.noby.model.Result;
import ai.cotogoto.noby.model.Setting;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.arnx.jsonic.JSON;

/**
 * NobyClient is a class for communication with a specific web API.
 * This class requires the appKey and message parameters.
 */
public class NobyClient {

    private final String appKey;

    private final Client client;

    private String       mail;

    private String       pass;

    private String       token;

    private Double       lat;

    private Double       lng;

    private Integer      study;

    private Integer      persona;

    private String       ending;

    /**
     * Creates a new instance of NobyClient.
     *
     * @param appKey the application key (required)
     * @param setting a Setting object containing various configuration properties (optional)
     */
    public NobyClient(final String appKey, final Setting setting) {

        this.appKey = appKey;
        this.client = ClientBuilder.newClient();

        if (setting != null) {
            this.mail = setting.getMail();
            this.pass = setting.getPass();
            this.token = setting.getToken();
            this.lat = setting.getLat();
            this.lng = setting.getLng();
            this.study = setting.getStudy();
            this.persona = setting.getPersona();
            this.ending = setting.getEnding();
        }
    }


    /**
     * Sends a GET request to the web API and returns a Result object.
     *
     * @param message the conversation text (required)
     * @return a Result object representing the response
     * @throws Exception if there is an error during the request
     */
    public Result exec(final String message) throws Exception {

        this.validateParameters();

        Result result = null;
        final var parameters = new Parameters();
        parameters.addParameter("app_key", this.appKey);
        parameters.addParameter("text", URLEncoder.encode(message, "UTF-8"));

        this.addOptionalParameters(parameters);

        final var response = this.client.target("https://app.cotogoto.ai/webapi/noby.json?" + parameters.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new Exception("HTTP error: " + response.getStatus());
        }

        final var json = response.readEntity(String.class);

        if (json.contains("errors")) {

            final var errors = (Map <?, ?>) JSON.decode(json);
            final var list = (List <?>) errors.get("errors");
            for (final Object obj : list) {
                final var error = (Map <?, ?>) obj;
                throw new Exception((String) error.get("message"));
            }
        } else {
            result = JSON.decode(json, Result.class);
        }

        return result;
    }


    /**
     * Validates the required parameters.
     *
     * @throws Exception if required parameters are missing
     */
    private void validateParameters() throws Exception {

        if (this.appKey == null) {
            throw new NullPointerException("AppKey parameters are required.");
        }
    }


    /**
     * Adds optional parameters to the request if they are present.
     *
     * @param parameters the Parameters object to add the optional parameters to
     */
    private void addOptionalParameters(final Parameters parameters) {

        if (this.mail != null) {
            parameters.addParameter("mail", this.mail);
        }
        if (this.pass != null) {
            parameters.addParameter("pass", this.pass);
        }
        if (this.token != null) {
            parameters.addParameter("token", this.token);
        }
        if (this.lat != null) {
            parameters.addParameter("lat", this.lat.toString());
        }
        if (this.lng != null) {
            parameters.addParameter("lng", this.lng.toString());
        }
        if (this.study != null) {
            parameters.addParameter("study", this.study.toString());
        }
        if (this.persona != null) {
            parameters.addParameter("persona", this.persona.toString());
        }
        if (this.ending != null) {
            parameters.addParameter("ending", this.ending.toString());
        }
    }
}
