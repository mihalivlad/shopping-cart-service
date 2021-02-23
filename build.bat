IF "%1"=="start" (
    call docker-compose up --build
) ELSE IF "%1"=="build" (
    call mvn clean install
    call docker build -t siminastefan/microservices .
) ELSE IF "%1"=="stop" (
    call docker-compose down
)