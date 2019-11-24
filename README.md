# FPLWrapper
***
A user-friendly API for the Fantasy Premier League online game.
## Quickstart
***
1. Run `mvn package` in the root directory to build the `jar`.
2. Add a Fantasy Premier League (FPL) account email and password to `server-config.yml` - necessary in making initial calls to the FPL API to bootstrap the server with FPL data.
3. Run the following command to run the server with the config:
    ```sh
    java -jar target/fpl-wrapper-0.0.1-SNAPSHOT.jar server server-config.yml
    ```
    The server should be running at `localhost:8080`.
## Documentation
***
Visit `localhost:8080/swagger` once you have run the server for API Documentation.