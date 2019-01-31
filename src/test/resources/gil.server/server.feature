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

  Scenario: Server can access a file inside a public folder
    When I send a simple GET request to "/file%20with%20space.txt"
    Then I get an HTTP response with a message body containing "The title of this text file has spaces."

  Scenario: Server can respond to OPTIONS request to a file inside a public folder
    When I send an OPTIONS request to "/file%20with%20space.txt"
    Then I get an HTTP response with an Allow header field of "OPTIONS, GET"

  Scenario: Server can handle a POST request to an endpoint
    When I send a POST request to "/api/people"
    Then I get an HTTP response with status code 201

  Scenario: Server can handle a GET request to a dynamic endpoint
    When I send a simple GET request to "/api/people/0"
    Then I get an HTTP response with status code 200

  Scenario: Server can respond to OPTIONS request to a static route
    When I send an OPTIONS request to "/api/people"
    Then I get an HTTP response with an Allow header field of "OPTIONS, POST"

  Scenario: Server can respond to OPTIONS request to a dynamic route
    When I send an OPTIONS request to "/api/people/0"
    Then I get an HTTP response with an Allow header field of "OPTIONS, GET"

  Scenario: Server can respond to GET request to an image file
     When I send a simple GET request to "/img/kitty-300x300.jpg"
     Then I get an HTTP response with status code 200 and content-type field of "image/jpeg"

  Scenario: Server can respond to GET request to a public directory
     When I send a simple GET request to "/public"
     Then I get an HTTP response with status code 200 and anchor tag to an "img" directory
