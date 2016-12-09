package ai.cotogoto.noby;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;

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

        InputStream is = null;
        Reader r = null;

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

            // GAE/Jの場合はHttpsURLConnectionが利用できないので、HttpURLConnectionに変更して使ってください。
            // http://www.cotogoto.aiでも接続はできます。
            final URL url = new URL("https://www.cotogoto.ai/webapi/noby.json?" + parameters.toString());
            final HttpsURLConnection secureConn = (HttpsURLConnection) url.openConnection();

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,
                    new X509TrustManager[] { new LooseTrustManager() },
                    new SecureRandom());
            secureConn.setSSLSocketFactory(sslContext.getSocketFactory());
            secureConn.setHostnameVerifier(new LooseHostnameVerifier());

            is = secureConn.getInputStream();
            r = new InputStreamReader(is, "UTF-8");
            final String json = IOUtils.toString(r);

            if (json.indexOf("errors") > -1) {
                @SuppressWarnings ("rawtypes")
                Map errors = JSON.decode(json);
                @SuppressWarnings ("rawtypes")
                List list = (List) errors.get("errors");
                for (Object obj : list) {
                    @SuppressWarnings ("rawtypes")
                    Map error = (Map) obj;
                    throw new Exception((String) error.get("message"));
                }
            } else {
                result = JSON.decode(json, Result.class);
            }

        } catch (final Exception e) {
            throw e;
        } finally {
            if (r != null) {
                r.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return result;
    }

    /**
    * Created by hidekazu.aoshima on 04/23/16.
    */
    public class LooseTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {

        }


        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {

        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {

            return null;
        }
    }

    /**
    * Created by hidekazu.aoshima on 04/23/16.
    */
    public class LooseHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(final String hostname, final SSLSession session) {

            return true;
        }
    }
}
