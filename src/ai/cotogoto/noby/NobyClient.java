package ai.cotogoto.noby;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ai.cotogoto.noby.model.Result;
import ai.cotogoto.noby.model.Setting;
import net.arnx.jsonic.JSON;

/**
 * Created by hidekazu.aoshima on 04/23/16.
 */
public class NobyClient {

    /** appKey. */
    private final String appKey;

    /** mail. */
    private String       mail;

    /** pass. */
    private String       pass;

    /** token. */
    private String       token;

    /** lat. */
    private Double       lat;

    /** lng. */
    private Double       lng;

    /** study. */
    private Integer      study;

    /** persona. */
    private Integer      persona;

    /** ending. */
    private String       ending;

    /**
     * constructor.
     * @param pAppKey Set the Consumer Key(App Key) that was issued at the time of registration.(Required)
     * @param setting Set the setting data.(Any)
     */
    public NobyClient(String pAppKey, Setting setting) {

        this.appKey = pAppKey;
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
     * exec.
     * @param message Set the text of the conversation.
     * @return result
     * @throws Exception exception.
     */
    public Result exec(final String message) throws Exception {

        Result result = null;

        try {
            final Parameters parameters = new Parameters();
            if (this.appKey == null) {
                throw new NullPointerException("parameter is required.");
            }
            parameters.addParameter("app_key", this.appKey);
            if (this.mail != null) {
                parameters.addParameter("mail", this.mail);
            }
            if (this.pass != null) {
                parameters.addParameter("pass", this.pass);
            }
            if (this.token != null) {
                parameters.addParameter("token", this.token);
            }
            if (message == null) {
                throw new NullPointerException("parameter is required.");
            }

            parameters.addParameter("text", URLEncoder.encode(message, "UTF-8"));

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

            final Client client = ClientBuilder.newClient();
            final Response response = client.target("https://app.cotogoto.ai/webapi/noby.json?" + parameters.toString())
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            final String json = response.readEntity(String.class);

            if (json.indexOf("errors") > -1) {
                @SuppressWarnings ("rawtypes")
                final Map errors = JSON.decode(json);
                @SuppressWarnings ("rawtypes")
                final List list = (List) errors.get("errors");
                for (final Object obj : list) {
                    @SuppressWarnings ("rawtypes")
                    final Map error = (Map) obj;
                    throw new Exception((String) error.get("message"));
                }
            } else {
                result = JSON.decode(json, Result.class);
            }

        } catch (final Exception e) {
            throw e;
        }
        return result;
    }
}
