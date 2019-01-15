Feature: Server returns the right responses

  Scenario: Server returns Hello, world!
    When I send a simple GET request
    Then I get a response of "Hello, world!"

  Scenario: Server responds to GET request to valid endpoint with status code 200
    When I send a simple GET request to "/"
    Then I get an HTTP response with status code 200

  Scenario: Server responds to GET request to invalid endpoint with status code 404
    When I send a simple GET request to "/fake_endpoint"
    Then I get an HTTP response with status code 404

  Scenario: Server can process a GET request with a single query parameter
    When I send a GET request with a single query to "/api/parameters?name=lula"
    Then I get an HTTP response with a message body containing "name=lula"

  Scenario: Server can process a GET request with a multiple query parameter
    When I send a GET request with multiple query parameters to "/api/parameters?name=lula&species=cat"
    Then I get an HTTP response with a message body containing "name=lula" and "species=cat"