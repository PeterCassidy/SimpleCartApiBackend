# Simple Cart API

A Simple Shopping cart API with Product and Offer repositories, and a simple cart endpoint which produces a
receipt and itemized breakdown of offers.

## Prerequisites

Java 21

## Installation & Running

1. Clone the repo
2. Install dependencies:
   ```bash
   ./mvnw clean install
   ```
3. Start the server:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Access Swagger ui to test the API - http://localhost:8080/swagger-ui/index.html

## Running Tests

```bash
./mvnw test
```

## Notes

- **Package Structure:** As the system is relatively simple I opted for packages based on layers (controller/service/repo). For more complex microservices this wouldn't be ideal, separating by domain can improve maintenance.
- **Design Choices:** As this is a relatively simple kata, i opted for a simple single method cart service, which just takes the list of product ids and generates the itemized receipt response. In a real world scenario
it would be more appropriate to have various types of offer (multi-buy, mixed product offers, percentage discounts). Perhaps using a strategy pattern to apply various offers to the same cart, 
- **Trade-offs:** I implemented quite simple error responses, error handling, validation, and testing. Given a production API these would be much more robust, and of course would include some form of authorization/authentication. I wanted to include a front end implementation, but ran out of time. I included swagger-ui to allow for easy testing in lieu of this

