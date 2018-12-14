Feature: Server returns the right responses

  Scenario: Server returns Hello, world!
    Given the server is running
    When I send a simple GET request
    Then I get a response of "Hello, world!"