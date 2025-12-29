# SwitchPay Backend

This is the backend for the SwitchPay platform. It is a RESTful API written in Java using Spring Boot.

## Getting Started

To get started with the project, you will need to clone the repository and install the dependencies using Maven.

```bash
git clone https://github.com/yourusername/SwitchPay.git
cd SwitchPay
mvn install
```

## Building the Project

To build the project, run the following command in the root directory of the project:

```bash
mvn clean package
```

This will build the project and create a JAR file in the target directory.

## Database Configurations

The project uses a postgresdatabase to store data. To configure the database, you will need to provide the following configuration:

* spring.datasource.url: The URL of the database
* spring.datasource.username: The username to use when connecting to the database
* spring.datasource.password: The password to use when connecting to the database
* spring.datasource.driver-class-name: The JDBC driver class to use when connecting to the database

For example, if you are using a PostgreSQL database, you might use the following configuration:

spring.datasource.url=jdbc:postgresql://localhost:5432/switchpay
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver


## Running the Project

To run the project, run the following command in the root directory of the project:

java -jar target/SwitchPay-0.0.1-SNAPSHOT.jar

This will start the project on port 4040.

## Endpoints

The following endpoints are available:

### Authentication

* POST /login - Login to the system and get a JWT token
* POST /register - Register a new user

### Accounts

* GET /accounts - Get a list of all accounts
* POST /accounts - Create a new account
* GET /accounts/{accountId} - Get a specific account by id
* PUT /accounts/{accountId} - Update a specific account by id
* DELETE /accounts/{accountId} - Delete a specific account by id


## Usage

To use the API, you will need to send a JWT token in the Authorization header of your requests. The token can be obtained by logging in to the system using the /login endpoint.

curl -X GET \
  http://localhost:4040/api/v1/account/my-accounts \
  -H 'Authorization: Bearer <JWT_TOKEN>'
Example using curl:

```bash
curl -X GET \
  http://localhost:4040/api/v1/account/my-accounts \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.