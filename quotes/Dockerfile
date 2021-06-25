FROM openjdk:11

RUN useradd -ms /bin/bash quotes
WORKDIR /home/quotes

ENV LIFECYCLE='dev'

# Copy jar and config files from builder stage
COPY quotes-service/target/quotes-service.jar quotes-service.jar
COPY quotes-service/configs configs

RUN chown -R quotes /home/quotes
USER quotes

CMD exec java -jar quotes-service.jar server configs/${LIFECYCLE}.yml
