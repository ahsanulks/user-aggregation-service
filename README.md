# money-forward-test

## Architecture
I'm using hexagonal architecture here, as it's better to separate the business logic from other concerns like data retrieval, frameworks, etc.

## Run Application
- Use Docker: `docker-compose up`
- Development: Utilize devcontainer for an easy setup of the development environment
- Test: run with `mvn test`

### Available Endpoints
- Swagger-UI: `{baseUrl}/swagger-ui/index.html`
- OpenApi: `{baseUrl}/v3/api-docs`
- ApiUser: `{baseUrl}/api/v1/users/{userId}`

## Problem
One of the challenges in this project was the need to use three APIs to gather all the required data (users and accounts). Two APIs, namely `accounts/{accountId}` and `users/{userId}/accounts`, served the same purpose by providing account data. However, using the `accounts/{accountId}` endpoint introduced an N+1 problem, as each account data was retrieved individually, resulting in unnecessary calls for the same data.

## Solution
To address the identified problems, the decision was made to prefer using the `users/{userId}/accounts` endpoint, as it provides all the necessary account data in one call. To further optimize the solution and eliminate the N+1 problem, a decorator pattern was implemented. However, considering scenarios where unnecessary calls to the account API are undesired, wen can easily modify the configuration by setting `user.service.decorator.enabled` to `false` in `application.properties`. This action removes the decorator, ensuring that the application only calls the `get user` (`users/{userId}`) and `get accounts` (`users/{userId}/accounts`) endpoints.
