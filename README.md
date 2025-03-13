# Product Service

This is a Spring Boot-based microservice designed to manage products. It utilizes an Azure SQL Database for data persistence and includes RESTful API endpoints. The application now features caching for improved performance and pagination for efficient product retrieval.

## Features

*   **RESTful API:** Provides endpoints for managing product data.
*   **Azure SQL Database:** Uses Azure SQL Database for persistent data storage.
*   **Spring Boot:** Built using the Spring Boot framework for ease of development and deployment.
*   **JPA:** Uses Spring Data JPA for database access and object-relational mapping.
*   **Gradle:** Uses Gradle as a build tool.
*   **Caching:** Implements caching to reduce database load and improve response times.
*   **Pagination:** Supports paginated retrieval of products for efficient handling of large product sets.
*   **Flyway:** Flyway is used for database migrations.
*   **Actuator:** Uses Actuator for application monitoring and management.
*   **Devtools:** Uses Devtools for faster development.
* **HQL Queries**: Uses HQL queries for complex queries.

## Prerequisites

*   **Java JDK 21:** Ensure you have Java Development Kit (JDK) version 21 installed.
*   **Azure SQL Database:** A running Azure SQL Database instance.
*   **Gradle:** Gradle is included as a wrapper (`gradlew`), but you can use a globally installed version if you prefer.

## Getting Started

1.  **Clone the Repository:**

    ```bash
    git clone <repository-url>
    cd ProductService
    ```

2.  **Configure the Database:**

    *   **Azure SQL Database:** Configure your `application.properties` file to connect to your Azure SQL Database.
    *   **`application.properties`:** Update `src/main/resources/application.properties` with the following:

        ```properties
        spring.application.name=ProductService
        server.port=5000

        # Azure SQL Database Connection
        spring.jpa.hibernate.ddl-auto=update
        spring.datasource.url=jdbc:sqlserver://<your-server-name>.database.windows.net:1433;database=<your-database-name>;user=<your-username>@<your-server-name>;password=<your-password>;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;

        spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
        ```

        *   **Placeholders:** Replace the following placeholders with your actual values from Azure:
            *   `<your-server-name>`
            *   `<your-database-name>`
            *   `<your-username>`
            *   `<your-password>`
    *   **Firewall:** Configure your Azure SQL Database firewall to allow connections from your local machine (for development) or your application server's IP address (for production).
    *   **Security:** Use environment variables to store sensitive information like database credentials for production environments.
    *   **Local Development (Optional):** If you prefer, you can create an `application-local.properties` file for local testing. Do *not* commit this file to the repository.

        ```properties
        #local configuration properties
        ```

3.  **Build the Project:**

    ```bash
    ./gradlew clean build
    ```

4.  **Run the Application:**

    ```bash
    ./gradlew bootRun
    ```

5.  **Database Migrations:** Ensure you have run database migrations using Flyway *before* starting your application, if you are using Flyway.

6.  **Access the Application:** Once running, the application will be accessible at `http://localhost:5000`.

## API Endpoints

*   `GET /products`: Retrieve all products.
    *   **Caching:** This endpoint now caches the list of all products for faster subsequent requests.
    *   **Pagination:** This endpoint supports pagination. You can use the following query parameters:
        *   `pageNo`: The page number (starting from 1).
        *   `pageSize`: The number of products per page.
        * Example: `GET /products?pageNo=2&pageSize=10` will retrieve the second page, with 10 products per page.

*   `GET /products/{id}`: Retrieve a specific product by ID.
    *   **Caching:** This endpoint caches individual products by their ID for improved performance.
*   `POST /products`: Create a new product.
    * The new product will be added to the cache.
*   `PUT /products/{id}`: Update an existing product.
    * The updated product will replace the old product in the cache.
*   `DELETE /products/{id}`: Delete a product.
    * The deleted product will be removed from the cache.

## Caching

*   **Technology:** This application uses Spring's caching abstraction with in-memory caching to cache products.
*   **Cache Regions:**
    *   `products`: Caches lists of all products (`getAllProducts`) and individual products by ID (`getProductById`).
*   **Cache Management:**
    *   `@Cacheable`: Used to cache the results of the `getProductById` and `getAllProducts` methods.
    *   `@CachePut`: Used to update the cache when a product is created or updated.
* **Cache Eviction**: The deleted product will be automatically removed from the cache.

## Pagination

*   **Implementation:** The `SelfProductService` uses Spring Data's `Page` and `Pageable` to implement pagination for the `getAllProducts` endpoint.
*   **Parameters:** The `pageNo` and `pageSize` query parameters control the pagination behavior.
* **Sorted**: The list of products is sorted by id.
