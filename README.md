# JCurl

Native and JDK based HTTP client.

## Build

Build as JAR:
```bash
gradlew build
```

Build as native executable:
```bash
./gradlew nativeCompile
```

## Run
1. As Java
  ```
  java -jar build/libs/http-client-java-cli-19.jar --url http://example.com --method GET
  ```

2. As CLI app
  ```bash 
    jcurl ...args
  ```

## Docs

``` jcurl [arguments] ```

### Arguments
- ``` --url ``` - request URI
- ``` --method ``` - GET / POST / PUT / DELETE / HEAD
- ``` --auth ``` or ``` -A ``` - authorization as 'USER:PASSWORD'. e.g. ``` --auth admin:admin ```
- ``` --headers ``` - request Headers in format of: ``` key:value, key2:value2 ```
- ``` --query ``` - query parameters for the request.  will be passed as ``` ?query= ```
