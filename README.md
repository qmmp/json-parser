# Overview
A simple JSON parser.
It does not support JSON Schema or object mapping; it only supports conversion between JSON strings and Java data.

# Change Log
## 1.2.1
- Added a README.
- Made it possible to explicitly retrieve numeric values from a `JsonObject`.
- Fixed a bug so that accessing an undefined key on a `JsonObject` now explicitly returns `null`.
## 1.2.1
- Fixed minor typos detected by SonarCloud