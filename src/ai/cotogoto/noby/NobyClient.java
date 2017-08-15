package ai.cotogoto.noby;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ai.cotogoto.noby.model.Result;
import net.arnx.jsonic.JSON;

/**
 * Created by hidekazu.aoshima on 04/23/16.
 */
public class NobyClient {

    /** appKey. */
    private final String  appKey;

    /** mail. */
    private final String  mail;

    /** pass. */
    private final String  pass;

    /** lat. */
    private final Double  lat;

    /** lng. */
    private final Double  lng;

    /** study. */
    private final Integer study;

    /** persona. */
    private final Integer persona;

    /** ending. */
    private final String  ending;

    /**
     * constructor.
     * @param pAppKey Set the Consumer Key(App Key) that was issued at the time of registration.(Required)
     * @param pMail Set the e-mail address at the time of login of Cotogoto.(Any)
     * @param pPass Set the password at the time of login of Cotogoto.(Any)
     * @param pLat latitude. To use the check-in function of Cotogoto.(Any)
     * @param pLng longitude. To use the check-in function of Cotogoto.(Any)
     * @param pStudy Select the presence or absence of learning at the time of the conversation.(Any)
     *              "0: No", "1: Yes"
     * @param pPersona Select the personality of artificial intelligence.(Any)
     *                "0: Normal", "1: Tsundere (woman)", "2: Tsundere (man)."
     */
    public NobyClient(
            final String pAppKey,
            final String pMail,
            final String pPass,
            final Double pLat,
            final Double pLng,
            final Integer pStudy,
            final Integer pPersona,
            final String pEnding) {

        this.appKey = pAppKey;
        this.mail = pMail;
        this.pass = pPass;
        this.lat = pLat;
        this.lng = pLng;
        this.study = pStudy;
        this.persona = pPersona;
        this.ending = pEnding;
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
            final Response response = client.target("https://www.cotogoto.ai/webapi/noby.json?" + parameters.toString())
                    .request(MediaType.TEXT_PLAIN_TYPE)
                    .get();


            final String json = response.readEntity(String.class);

            if (json.indexOf("errors") > -1) {
                @SuppressWarnings ("rawtypes")
                final
                Map errors = JSON.decode(json);
                @SuppressWarnings ("rawtypes")
                final
                List list = (List) errors.get("errors");
                for (final Object obj : list) {
                    @SuppressWarnings ("rawtypes")
                    final
                    Map error = (Map) obj;
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
