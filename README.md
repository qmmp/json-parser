# Overview
A simple JSON parser.
It does not support JSON Schema or object mapping; it only supports conversion between JSON strings and Java data.

# Change Log
## 1.2.1
- Added a README.
- Made it possible to explicitly retrieve numeric values from a `JsonObject`.
- Fixed a bug so that accessing an undefined key on a `JsonObject` now explicitly returns `null`.
## 1.2.2
- Fixed minor typos detected by SonarCloud
## 1.3.0 SNAPSHOT
- Added the ability to specify types when extracting values from `JsonObject` and `JsonArray`.
- Added `getJsonString` and `get` methods to `JsonObject` and `JsonArray` to extract strings and numbers.