# FPLWrapper

A user-friendly API for the Fantasy Premier League online game.

### Built with
- [Dropwizard](https://www.dropwizard.io/en/latest/index.html)
- [PostgreSQL](https://www.postgresql.org/)

Deployed via [AWS](https://aws.amazon.com/).

*Note: I've disabled this API as I'm unable to maintain it.*

## Usage
See https://kelanyll.com/posts/fpl-wrapper for an introduction into the API and `/examples` for example usage 
in Python.

### Development
The project can be imported as a Maven project using IntelliJ IDEA. To run the server using IntelliJ IDEA, create a 
Run configuration that runs the class `FPLWrapperApplication` with CLI argument `server server-config.yml`.

### Setup
The quickest way to get this running locally will be to install [Docker](https://www.docker.com/) on your machine 
and run the server and database as containers. You will also need [Java 11](https://openjdk.java.net/projects/jdk/11/) and [Maven](https://maven.apache.org/) to build the server. 
1. Navigate to `db/` and run `docker build -t fpl-wrapper-db .` to build the database image.
2. Set a password for the database under an environment variable (such as `FPL_DB_PASS`).
2. Run `docker run --name fpl-wrapper-db -d -p 5432:5432 -e POSTGRES_PASSWORD=$FPL_DB_PASS fpl-wrapper-db` to run 
   the 
   container.
3. In `/server-config.yml`, insert the correct IP address in the JDBC url; the database 
   container should be running at `172.17.0.2` in the default bridge network but you can check this with `docker 
   network inspect bridge`. See here for more information about Docker networking: https://docs.docker.com/network/network-tutorial-standalone/.
4. In the root of the project, run `mvn package` to build the server JAR.
5. Run `docker build -t fpl-wrapper-server .`.
6. Run `docker run --name fpl-wrapper-server -d -p 8080:8080 -p 8081:8081 -e FPL_DB_PASS=$FPL_DB_PASS fpl-wrapper-server`.
7. The server should be up and running now: http://localhost:8080/fpl-wrapper/swagger.
