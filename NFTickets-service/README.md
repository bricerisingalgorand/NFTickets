# quotes

How to start the quotes application
---

1. Run `mvn clean install` to build your application
2. Get a postgres instance running at localhost:5432 `docker run -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres`
3. Set up schemas `java -jar quotes-service/target/quotes-service.jar db migrate quotes-service/configs/local.yml`
4. Start application with `java -jar target/quotes.jar server config/local.yml`
5. To check that your application is running enter url `http://localhost:8080`

docker-compose setup
---

1. Run `mvn clean install` to build your application
2. Run `docker-compose up -d` to start the app
3. To check that your application is running enter url `http://localhost:8080`
4. See swagger ui on `http://localhost:8082/`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
