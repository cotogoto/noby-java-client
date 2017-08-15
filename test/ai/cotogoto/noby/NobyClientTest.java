package ai.cotogoto.noby;

import ai.cotogoto.noby.model.Result;

public class NobyClientTest {

    public static void main(final String[] args) throws Exception {


        final String appKey = "APP_KEY";
        final String mail = null;
        final String pass = null;
        final Double lat = null;
        final Double lng = null;
        final Integer study = null;
        final Integer persona = null;
        final String ending = null;

        final NobyClient client=    new NobyClient(appKey,mail,pass,lat,lng,study,persona,ending);
        final Result result = client.exec("こんにちは");

        System.out.println(result);
    }

}
