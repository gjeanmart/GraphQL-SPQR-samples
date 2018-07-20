# GraphQL SPQR sample

GraphQL SPQR samples

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

- Java 8


### Build

1. After checking out the code, navigate to the root directory
```
$ cd /path/to/graphql-sqpr-test/
```

2. Compile, test and package the project
```
$ mvn clean package
```

3. Run the project

**Executable JAR:**

```
$ java -jar target/graphql-sqpr-test-0.0.1-SNAPSHOT-exec.jar
```


## Query (Context)

### Request

```

{
  "query": "query article($id: String) { article(id: $id) { id, content, userid, user { username } comment(limit: 5) {id comment user {email}} } }",
  "variables": {
    "id": "1"
  },
  "operationName": "article"
}
```

### Response

```
{
    "data": {
        "article": {
            "id": "article1",
            "content": "blabla",
            "userid": "user1",
            "user": {
                "username": "1"
            },
            "comment": [
                {
                    "id": 0,
                    "comment": "comment0",
                    "user": {
                        "email": "greg@mail.com"
                    }
                },
                {
                    "id": 1,
                    "comment": "comment1",
                    "user": {
                        "email": "greg@mail.com"
                    }
                },
                {
                    "id": 2,
                    "comment": "comment2",
                    "user": {
                        "email": "greg@mail.com"
                    }
                },
                {
                    "id": 3,
                    "comment": "comment3",
                    "user": {
                        "email": "greg@mail.com"
                    }
                },
                {
                    "id": 4,
                    "comment": "comment4",
                    "user": {
                        "email": "greg@mail.com"
                    }
                }
            ]
        }
    },
    "errors": [],
    "dataPresent": true,
    "extensions": null
}
```




## Query (interface / inheritance)

### Request

```

{
  "query":"query resources { resources { type id ... on Resource1 {name fieldA fieldB } ... on Resource2 {name field2 field1 } } }",
  "variables": {},
  "operationName": "resources"
}
```

### Response

```
{
    "data": {
        "resources": [
            {
                "type": "TYPE1",
                "id": "1",
                "name": "resource1",
                "fieldA": "hello",
                "fieldB": "world"
            },
            {
                "type": "TYPE2",
                "id": "2",
                "name": "resource1",
                "field2": "worl2d",
                "field1": "hello2"
            }
        ]
    },
    "errors": [],
    "dataPresent": true,
    "extensions": null
}
```

