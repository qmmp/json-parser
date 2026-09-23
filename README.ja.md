# 概要
シンプルなjsonパーサ。
JSONスキーマはサポートせず、Objectマッピングもサポートせず、JSON文字列<->Javaデータの相互変換のみをサポートする。

# 変更履歴
## 1.2.1
- READMEを追加
- JsonObjectから数値を明示的に取得できるようにした。
- JsonObjectに対し未定義キーでアクセスした場合、明示的にnullを返すように修正。
## 1.2.2
- sonarcloudで検出された軽微なタイプミスの修正
## 1.3.0
- JsonObject、JsonArrayから値を取り出すとき、型指定を可能にした。
- JsonObject、JsonArrayでgetJsonString で文字列、数値をとりだすgetメソッドを追加