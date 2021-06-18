# Noby Java client とは

Noby Java client は NOBY API に簡単にアクセスするためのJavaクライアントライブラリです。

<img src="https://raw.githubusercontent.com/cotogoto/noby-java-client/master/noby_image/noby_neutral_a.jpg" width='200'>

* CotoGoto
    * https://www.cotogoto.ai/

* Noby API
    * https://webapi.cotogoto.ai/

## Updates
* 2021/06/18 0.5.0 released

## インストール

#### Eclipseから

* GitHubからソースコードをチェックアウトします。
* 「ファイルメニュー > インポート」から「既存 Maven プロジェクト」でソースコードをインポートします。
* プロジェクト・エクスプローラーから「Noby Java client」のプロジェクトを選択して、右クリックメニューを表示します。
* 「実行 > Maven install」を選択して、プロジェクトのコンパイルを行います。
* プロジェクト内のtargetフォルダにJarファイルが生成されます。

※自プロジェクト内のpom.xmlファイルに「Noby Java client」のMavenを追加するこもできます。

    <dependency>
        <groupId>noby-java-client</groupId>
        <artifactId>noby-java-client</artifactId>
        <version>0.5.0-SNAPSHOT</version>
    </dependency>

## 使い方

基本的な使い方は以下の 3 ステップとなります。

* Noby API に従ったパラメータ
* NobyClient オブジェクトを生成
* 会話を設定して実行

それぞれのパラメータを設定します。

    String appKey = "APP_KEY";
    Setting setting = new Setting();
    setting.setMail(null);
    setting.setPass(null);
    setting.setToken(null);
    setting.setLat(null);
    setting.setLng(null);
    setting.setStudy(null);
    setting.setPersona(null);
    setting.setEnding(appKey);

続いて NobyClient を生成します。

    NobyClient client = new NobyClient(appKey, setting);

会話内容を設定して、実行すると会話の結果が取得できます。

    Result result = client.exec("こんにちは");

# License
The source code is licensed MIT. The website content is licensed CC BY 4.0,see LICENSE.