package ai.cotogoto.noby;

import ai.cotogoto.noby.model.Setting;

public class NobyClientTest {

    public static void main(final String[] args) throws Exception {

        final var appKey = "<APP_KEY>";
        final var setting = new Setting();
        setting.setMail(null);
        setting.setPass(null);
        setting.setToken(null);
        setting.setLat(null);
        setting.setLng(null);
        setting.setStudy(null);
        setting.setPersona(null);
        setting.setEnding(null);

        final var client = new NobyClient(appKey, setting);
        final var result = client.exec("こんにちは");

        System.out.println(result);
    }

}
