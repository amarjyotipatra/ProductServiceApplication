# Product Service

This is a Spring Boot-based microservice designed to manage products. It utilizes a MySQL database for data persistence and includes basic RESTful API endpoints.

## Features

*   **RESTful API:** Provides endpoints for managing product data.
*   **MySQL Database:** Uses MySQL for persistent data storage.
*   **Spring Boot:** Built using the Spring Boot framework for ease of development and deployment.
*   **JPA:** Uses Spring Data JPA for database access.
*   **Gradle:** Uses Gradle as a build tool.
* **Flyway:** Flyway is used for database migrations.
* **Actuator:** Uses actuator for application monitoring.
* **Devtools:** Uses devtools for faster development.

## Prerequisites

*   **Java JDK 21:**  Make sure you have Java Development Kit (JDK) version 21 installed.
*   **MySQL Database:** A running MySQL database instance.
*   **Gradle:** Gradle is included as a wrapper (`gradlew`), but you can use a globally installed version if you prefer.

## Getting Started

1.  **Clone the Repository:**

    ```bash
    git clone <repository-url>
    cd ProductService
    ```

2.  **Configure the Database:**

    *   **Environment Variables (Recommended for Production):**
        *   Set the following environment variables with your database credentials:
            *   `DATABASE_URL`: The JDBC URL for your database (e.g., `jdbc:mysql://<your-db-host>:<port>/<db-name>`).
            *   `DATABASE_USER`: Your database username.
            *   `DATABASE_PASSWORD`: Your database password.
    *   **Local Development (Optional):**
        *   Create a file named `application-local.properties` in `src/main/resources`.
        *   Add the following content, replacing the placeholders with your local database credentials:

        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/productservice
        spring.datasource.username=user
        spring.datasource.password=password
        ```
        *   **Important:** Do not commit `application-local.properties` to the repository. It is already in the `.gitignore` file.
    * **application.properties:**
        * This file is used to specify non-sensitive configurations, and contains fallback values for local testing.
        * It is safe to commit this file to your repository.

3.  **Build the Project:**

    ```bash
    ./gradlew clean build
    ```

4.  **Run the Application:**

    *   **With Environment Variables:**

        ```bash
        ./gradlew bootRun
        ```

    *   **With Local Profile (for local development):**

        ```bash
        ./gradlew bootRun -Dspring.profiles.active=local
        ```

5.  **Database migrations:** Make sure that you have run database migrations using Flyway before starting your application.

6. **Access the Application:** Once running, the application will be accessible at `http://localhost:5000`.

## API Endpoints

_(Add details about your specific API endpoints here, such as:)_

*   `GET /products`: Retrieve all products.
*   `GET /products/{id}`: Retrieve a specific product by ID.
*   `POST /products`: Create a new product.
*   `PUT /products/{id}`: Update an existing product.
*   `DELETE /products/{id}`: Delete a product.

_(Include examples of requests and responses if possible.)_

## Project Structure

ProductService/ ├── .gradle/ # Gradle wrapper files ├── build/ # Build output directory ├── gradle/ # Gradle wrapper distribution ├── gradlew # Gradle wrapper executable (for Linux/macOS) ├── gradlew.bat # Gradle wrapper executable (for Windows) ├── src/ │ ├── main/ │ │ ├── java/ # Main Java source code │ │ │ └── com/ │ │ │ └── example/ │ │ │ └── productservice/ #your application classes │ │ │ └── ... │ │ └── resources/ # Main resources │ │ ├── application.properties # General configuration │ │ ├── application-local.properties # Local configuration (not committed) │ │ └── db/ │ │ └── migration/ #Flyway migration files │ └── test/ # Test source code ├── .gitignore # Git ignore rules ├── build.gradle # Gradle build file └── README.md # This file

## Contributing

_(Add guidelines for contributing if you plan to accept external contributions.)_

## Further Documentation

*   [Official Gradle documentation](https://docs.gradle.org)
*   [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.2/gradle-plugin)
*   [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
*   [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

## License

_(Add license information if applicable.)_