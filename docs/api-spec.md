# API仕様（現行実装ベース）

## 1. 対象範囲

このリポジトリは受信HTTP APIを公開しない。本書では、利用側へ公開するJava APIと、内部から呼び出す外部NOBY HTTP APIを分けて記載する。外部APIの記載はクライアント実装から確認できる送受信だけを対象とし、公式仕様を代替しない。

## 2. 公開Java API一覧

| API | 概要 | 入力 | 出力／例外 |
|---|---|---|---|
| `new NobyClient(String appKey, Setting setting)` | クライアント生成 | `appKey`、任意の `Setting` | `NobyClient`。この時点では必須値検証・通信なし |
| `Result exec(String message)` | 会話要求を実行 | 会話文 | `Result`。宣言上 `throws Exception` |
| `NobyHttpException` | 非200 HTTP応答を表す例外 | HTTPステータス、応答本文 | `Exception` の派生型 |
| `Setting` getter/setter | 任意設定の保持 | 下表参照 | Lombok生成アクセサー |
| 結果モデル getter/setter | 応答値の保持 | 下表参照 | Lombok生成アクセサー |

`Parameters` は `public` だが、主に内部クエリ構築用である。公開互換性への期待範囲は未確認。

## 3. 外部HTTP API

### 3.1 会話実行

| 項目 | 内容 |
|---|---|
| Method | `GET` |
| Path | `https://app.cotogoto.ai/webapi/noby.json` |
| 概要 | 会話文と任意設定を送り、会話・解析結果を取得 |
| Request body | なし |
| Accept | `application/json` |
| Controller / Service | 該当なし。`NobyClient.exec` が直接実行 |

### 3.2 Request parameters

| 名前 | Java上の型 | 必須 | 実装上の処理／検証 |
|---|---:|---:|---|
| `app_key` | `String` | 必須 | `null` のみ拒否。エンコードなし |
| `text` | `String` | 必須とJavaDocに記載 | UTF-8で `URLEncoder.encode`。null・空・長さ検証なし |
| `mail` | `String` | 任意 | 非null時のみ。エンコードなし |
| `pass` | `String` | 任意 | 非null時のみ。エンコードなし |
| `token` | `String` | 任意 | 非null時のみ。エンコードなし |
| `lat` | `Double` | 任意 | `toString()`。範囲検証なし |
| `lng` | `Double` | 任意 | `toString()`。範囲検証なし |
| `study` | `Integer` | 任意 | コメント上 `0`: No、`1`: Yes。値域検証なし |
| `persona` | `Integer` | 任意 | コメント上 `0`: Normal、`1`: Tsundere (woman)、`2`: Tsundere (man)。値域検証なし |
| `ending` | `String` | 任意 | 非null時のみ。UTF-8で `URLEncoder.encode` |

全パラメータはURLクエリへ連結される。秘密情報を含むためURLの記録は禁止する。

### 3.3 Response

HTTP 200かつエラー判定されないJSONはJSONICにより次の `Result` へ変換される。

| Javaプロパティ | 型 | 意味（ソースコメント） |
|---|---|---|
| `commandId` | `String` | コマンドID |
| `commandName` | `String` | コマンド名 |
| `text` | `String` | 会話 |
| `type` | `String` | 返信タイプ |
| `mood` | `double` | ムード |
| `negaposi` | `double` | ネガポジ |
| `negaposiList` | `List<Negaposi>` | ネガポジ結果（`word`, `score`） |
| `emotion` | `Emotion` | 感情（`word`, `likeDislike`, `joySad`, `angerFear`） |
| `emotionList` | `List<Emotion>` | 感情結果 |
| `wordList` | `List<Word>` | 解析結果（`feature`, `start`, `surface`） |
| `art` | `String` | 人工物名 |
| `org` | `String` | 組織名 |
| `psn` | `String` | 人名 |
| `loc` | `String` | 地名 |
| `dat` | `String` | 日付表現 |
| `tim` | `String` | 時刻表現 |

実際のJSONキー、各値の列挙・範囲・null許容性は公式仕様または実応答で要確認。

## 4. Status codeとエラー仕様

| 条件 | 現行のライブラリ動作 |
|---|---|
| HTTP 200、本文に文字列 `errors` なし | `Result` へデコードして返却 |
| HTTP 200、本文に文字列 `errors` あり | `errors` 配列をMap/Listとして読み、最初の `message` を汎用 `Exception` で送出 |
| HTTP 200以外 | 本文を読み `NobyHttpException` を送出 |
| `appKey == null` | `NullPointerException("AppKey parameters are required.")` |
| 通信・デコード等の失敗 | 下位例外が伝播 |

`NobyHttpException` の公開情報は次のとおりである。

| API | 内容 |
|---|---|
| `getStatus()` | HTTPステータスコード |
| `getResponseBody()` | 最大8,192文字の応答本文。本文なし／読取不能時は空文字 |
| `isResponseBodyTruncated()` | 本文が8,192文字を超えて切り詰められたか |
| `getMessage()` | 従来互換の `HTTP error: <status>`。本文を含まない |
| `getCause()` | 本文読取に失敗した場合の原因。それ以外は通常 `null` |

本文は秘密情報・個人情報を含む可能性があるため、利用側は無加工でログ出力してはならない。ライブラリ自身は本文や完全な要求URLをログ出力しない。

HTTPステータスごとの公式な意味、エラー本文スキーマ、リトライ可否は未確認。400、401、403、404、429、5xx等は `getStatus()` で機械的に区別できるが、業務上の扱いは利用側で公式仕様を確認して決定する。

## 5. 認証・権限

- `app_key` は必須として送る。
- `mail`、`pass`、`token` は任意設定として送れる。
- ライブラリ内にユーザー認証、権限判定、トークン更新はない。
- どの組み合わせが必要か、API機能ごとの権限は未確認。

## 6. Validation

実装されている検証は `appKey != null` のみである。会話文、空文字、値域、文字数、認証項目の整合性は外部APIへ委ねられている。

## 7. 要確認事項

- APIの現行公式ホスト、バージョン、HTTPメソッド
- 全パラメータのエンコード規則と長さ・値域
- JSONキーの命名規則と必須性
- ステータス別のエラー本文、429/5xxの再試行条件
- 認証方式、秘密情報をクエリへ含める方式の継続可否
- 接続・読取タイムアウトおよびレート制限
