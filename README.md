## A Simple HTTP Server
### Implemented in Java

An object oriented style implementation of an HTTP server programmed in Java.

#### Setup

* If you don't have Gradle installed, you can download / install from here: https://gradle.org/install/
* Clone the http-server repository
* Change directories into the `http-server` directory

#### Running the server

* Run the server with the `./gradlew run --args "port 4040"` command.
  Note: server can be run on any port between 1025 and 48999 but the acceptance tests are programmed to connect to the server on port 4040.

#### Testing

* For unit tests run the `./gradlew test` command.
* For acceptance tests run the `./gradlew cucumber` command.
  Note: Acceptance tests are programmed to run on port 4040, so make sure that the server is started on port 4040 before running the cucumber tests.