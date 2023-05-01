                                # Spring Boot and Angular Template

This is a template project for a full-stack application using Spring Boot and Angular.
Note that this project is still a work in progress and security has not been implemented.
The angular part is not yet implemented.
If you need a template project for a full-stack application using Spring Boot and Angular, you can use this project as a
starting point.
If you want a version with open-api generator, you can use the branch `main-open_api`.

## Project Structure

The project is organized as a Maven multi-module project, with the following modules:

- `server`: the backend module, which includes the Spring Boot application.
    - `server/project-template-persistence`: module containing the persistence layer of the backend.
    - `server/project-template-service`: module containing the business logic of the backend.
    - `server/project-template-rest`: module containing the REST endpoints of the backend.
    - `server/project-template-image`: module containing Dockerfiles for building and running the project.
- `web`: the frontend module, which will contain the Angular application.

## Backend

The backend is a Spring Boot application, which provides RESTful web services to the frontend. The `server` module
includes the following features:

- Querydsl: used to generate type-safe JPA queries.
- MapStruct: used to generate mapping code between DTOs and entities.
- jjwt: used to generate JSON Web Tokens (JWTs) for authentication and authorization.
- Jakarta Bean Validation: used to validate the data received from the client.
- Using H2 as the database for now.

## Getting Started

To run the project, you will need to have Java 17 and Maven 3 installed. Then, you can run the following command from
the root directory of the project:

    mvn clean install

This will build the project and run all the tests. To run the backend, you can navigate to the `server` directory and
run the following command:

    mvn spring-boot:run

This will start the Spring Boot application and expose the REST API on http://localhost:8080. You can use Swagger UI to
test the API by opening http://localhost:8080/swagger-ui.html in your web browser.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more information.

## Contributing

Contributions to this project are welcome. To contribute, please create a pull request on GitHub.