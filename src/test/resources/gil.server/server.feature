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