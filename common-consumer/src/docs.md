FORMAT: 1D
HOST: https://api.cc.newco.com

# Common Consumer API
A **generic** description of that goals of this project.

## Subtitle
Also Markdown *formatted*. This also includes automatic "smartypants" formatting -- hooray!

> "A quote from another time and place"

Another paragraph. Code sample:

```http
Authorization: bearer 5262d64b892e8d4341000001
```

And some code with no highlighting:

## HTTP Status Code Summary
+ **200** OK - Everything worked as expected.

+ **201** Created - Everything worked and a new resource was created.

+ **400** Bad Request - Often missing a required parameter.

+ **401** Unauthorized - No valid API key provided.

+ **402** Request Failed - Parameters were valid but request failed.

+ **404** Not Found - The requested item doesn't exist.

+ **500, 502, 503, 504** Server errors - something went wrong on the server.

```no-highlight
hey
```

# Group Consumers
The totality of consumers contained within our data stores.

## Consumer List [/consumers]
Note list description

+ Even
+ More
+ Markdown

### Add Consumer [POST]
Add Consumer

+ Request

    + Headers

            Content-Type: application/json

    + Body

            {            	
            	"firstName": "John",
            	"lastName": "Doe",
            	"address": {
            		"line1": "123 E Main St",
            		"line2": "#503",
            		"city": "Portland",
            		"state": "OR",
            		"postalCode": 97201
            	},
            	"ssn": 123456789,
            	"dob": "1980-11-22",
            	"email": "john.doe@test.com"
            }

+ Response 201
    
        + Headers
                
                Content-Type: application/json
                
        + Body
        
                {
                	"id": 12321,
                	"firstName": "John",
                	"lastName": "Doe",
                	"address": {
                		"line1": "123 E Main St",
                		"line2": "#503",
                		"city": "Portland",
                		"state": "OR",
                		"postalCode": 97201
                	},
                	"ssn": 123456789,
                	"dob": "1980-11-22",
                	"email": "john.doe@test.com"
                }

+ Response 400

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Could not create consumer"
            }


## Consumer [/consumers/{consumerId}]
Note list description

+ Even
+ More
+ Markdown


### Get Consumer [GET]
Get a consumer.
    
+ Request

    + Headers

            Content-Type: application/json

    + Body
    {
    }


+ Response 200
    
    + Headers
    
            Content-Type: application/json
            
    + Body
            
            {
                "id": 12321,
                "firstName": "John",
                "lastName": "Doe",
                "address": {
                    "line1": "123 E Main St",
                    "line2": "#503",
                    "city": "Portland",
                    "state": "OR",
                    "postalCode": 97201
                },
                "ssn": 123456789,
                "dob": "1980-11-22",
                "email": "john.doe@test.com"
            }

+ Response 400

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Invalid consumer id"
            }
            
+ Response 404

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Consumer not found"
            }


### Update Consumer [PUT]
Update a consumer

+ Request

    + Headers

            Content-Type: application/json

    + Body

            {
                "id": 12321,
                "firstName": "John",
                "lastName": "Doe",
                "address": {
                    "line1": "123 E Main St",
                    "line2": "#503",
                    "city": "Portland",
                    "state": "OR",
                    "postalCode": 97201
                },
                "ssn": 123456789,
                "dob": "1980-11-22",
                "email": "john.doe@test.com"
            }

+ Response 200

    + Headers
            
            Content-Type: application/json
            
    + Body
    
        {
            "id": 12321,
            "firstName": "John",
            "lastName": "Doe",
            "address": {
                "line1": "123 E Main St",
                "line2": "#503",
                "city": "Portland",
                "state": "OR",
                "postalCode": 97201
            },
            "ssn": 123456789,
            "dob": "1980-11-22",
            "email": "john.doe@test.com"
        }

+ Response 400

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Invalid consumer id"
            }
            
+ Response 404

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Consumer not found"
            }

### Delete Consumer [DELETE]
Delete a consumer

+ Request

    + Headers

            Content-Type: application/json

    + Body


+ Response 200
    
    + Headers
    
            Content-Type: application/json
            
    + Body
            
            {
                "id": 12321,
                "firstName": "John",
                "lastName": "Doe",
                "address": {
                    "line1": "123 E Main St",
                    "line2": "#503",
                    "city": "Portland",
                    "state": "OR",
                    "postalCode": 97201
                },
                "ssn": 123456789,
                "dob": "1980-11-22",
                "email": "john.doe@test.com"
            }

+ Response 400

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Invalid consumer id"
            }
    
+ Response 404

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Consumer not found"
            }

## Consumer Matching [/consumers/match]
Match a consumer

### Get Consumer Match [POST]
Get a consumer match

+ Request

    + Headers

            Content-Type: application/json

    + Body

            {
                "firstName": "John",
                "lastName": "Doe",
                "address": {
                    "line1": "123 E Main St",
                    "line2": "#503",
                    "city": "Portland",
                    "state": "OR",
                    "postalCode": 97201
                },
                "ssn": 123456789,
                "dob": "1980-11-22",
                "email": "john.doe@test.com"
            }

+ Response 200

    + Headers
    
            Content-Type: application/json
    
    + Body
            [
                {
                    "firstName": "Jon",
                    "lastName": "Doe",
                    "address": {
                        "line1": "123 E Main St",
                        "line2": "#503",
                        "city": "Portland",
                        "state": "OR",
                        "postalCode": 97201
                    },
                    "ssn": 123456789,
                    "dob": "1980-11-22",
                    "email": "john.doe@test.com"
                },
                {
                    "firstName": "John",
                    "lastName": "DoeY",
                    "address": {
                        "line1": "123 E Maintenance St",
                        "line2": "#503a",
                        "city": "Portlandia",
                        "state": "OR",
                        "postalCode": 97201
                    },
                    "ssn": 123456789,
                    "dob": "1980-11-22",
                    "email": "john.doe@test.com"
                },
                ...
            ]

+ Response 404

    + Headers

            Content-Type: application/json

    + Body

            {
                "error": "Consumer not found"
            }