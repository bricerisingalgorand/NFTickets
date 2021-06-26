# quotes

How to start the quotes application
---

1. Run `mvn clean install` to build your application
2. Get a postgres instance running at localhost:5432 `docker run -p 5432:5432 -d postgres`
3. Start application with `java -jar target/quotes.jar server config/local.yml`
4. To check that your application is running enter url `http://localhost:8080`

docker-compose setup
---

1. Run `mvn clean install` to build your application
2. Run `docker-compose up -d` to start the app
3. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
