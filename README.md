# JCurl

Native and JDK based HTTP client.

## Build

Build as JAR:
```
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
