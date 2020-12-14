# FPLWrapper

A user-friendly API for the Fantasy Premier League online game.

## Usage
The API is running at http://kelanyll.com/fpl-wrapper and is documented at http://kelanyll.com/fpl-wrapper/swagger.

## Quickstart

1. Run `mvn package` in the root directory to build the `jar`.
2. Run the following command to run the server with the config:
    ```sh
    java -jar target/fpl-wrapper.jar server server-config.yml
    ```
    The server should be running at `localhost:8080`.
