# Noby Java clientとは何ですか？ (What is Noby Java client?)

Noby Java clientは、NOBY APIへのアクセスを簡単にするためのJavaライブラリです。(The Noby Java client is a Java library that provides easy access to the NOBY API.)

<img src="https://raw.githubusercontent.com/cotogoto/noby-java-client/master/noby_image/noby_neutral_a.jpg" width='200'>

* CotoGoto
    * https://www.cotogoto.ai/

* Noby API
    * https://webapi.cotogoto.ai/

## 更新日 (Updates)

* 2023/07/12 0.7.0 最新化
* 2021/12/23 0.6.0 最新化
* 2021/06/18 0.5.1 APIトークンの項目追加
* 2021/06/18 0.5.0 released
    
## インストール方法 (Installation)

ライブラリのインストールは以下の手順で行います：

1. GitHubからソースコードをチェックアウトします。(Check out the source code from GitHub.)
2. Eclipseの「ファイルメニュー > インポート」から既存のMavenプロジェクトとしてソースコードをインポートします。(Import the source code as an existing Maven project from the 'File Menu > Import' in Eclipse.)
3. プロジェクトエクスプローラーから「Noby Java client」プロジェクトを選択し、右クリックメニューを表示します。(Select the 'Noby Java client' project from the Project Explorer and display the right-click menu.)
4. 「実行 > Maven install」を選択してプロジェクトをコンパイルします。(Select 'Run > Maven install' to compile the project.)
5. プロジェクト内のtargetフォルダにJarファイルが生成されます。(A Jar file will be generated in the target folder within the project.)

また、自分のプロジェクトのpom.xmlファイルに「Noby Java client」のMavenを追加することもできます。(You can also add the Noby Java client Maven to your project's pom.xml file.)

```
    <dependency>
        <groupId>noby-java-client</groupId>
        <artifactId>noby-java-client</artifactId>
        <version>0.7.0</version>
    </dependency>
```

## 使い方 (Usage)

基本的な使い方は以下の3ステップです：

1. Noby APIに従ったパラメータを設定します。(Set parameters according to the Noby API.)
2. NobyClientオブジェクトを生成します。(Generate a NobyClient object.)
3. 会話を設定して実行します。(Set up the conversation and execute it.)

以下にサンプルコードを示します：

```java
    var appKey = "APP_KEY";
    //　こちらから各種設定を行います。
    var setting = new Setting();
    setting.setMail(null);
    setting.setPass(null);
    setting.setToken(null);
    setting.setLat(null);
    setting.setLng(null);
    setting.setStudy(null);
    setting.setPersona(null);
    setting.setEnding(null);
    
    var client = new NobyClient(appKey, setting);
    var result = client.exec("こんにちは");
```

# ライセンス (License)

ソースコードはMITライセンスの下でライセンスされており、ウェブサイトのコンテンツはCC BY 4.0でライセンスされています。(The source code is licensed under the MIT license, and the website content is licensed under CC BY 4.0.)
