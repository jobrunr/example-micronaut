# JobRunr Micronaut example

This repository shows an example how you can integrate [JobRunr](https://www.jobrunr.io) and [Micronaut](https://micronaut.io/).

## About this project
### Micronaut and JobRunr integration
To integrate JobRunr into Micronaut:
- just add the dependency: `org.jobrunr:jobrunr-micronaut-feature:LATEST`
- make sure that you configure JobRunr in the micronaut `application.yml`
```yaml
jobrunr:
  job-scheduler:
    enabled: true
  background-job-server:
    enabled: true
  dashboard:
    enabled: true
```
- JobRunr will automatically find the correct [StorageProvider](https://www.jobrunr.io/en/documentation/installation/storage/).

### Package structure
This project has the following packages:
- **org.jobrunr.examples**: this package contains the application class that bootstraps the Micronaut app.
- **org.jobrunr.examples.services**: this package contains [MyService](src/main/java/org/jobrunr/examples/services/MyService.java), a simple `@Singleton` service with some example methods which you can run in the background.
- **org.jobrunr.examples.webap.api**: this package contains the following http resource:
    - `JobController`: this resource contains a couple of REST api's which allows you to enqueue new Background Jobs

## How to run this project:
- clone the project and open it in your favorite IDE that supports Maven
- Run the Maven plugin `./gradlew run` and wait for Micronaut to be up & running
- Open your favorite browser:
    - Navigate to the JobRunr dashboard located at http://localhost:8000/dashboard.
    - Navigate to the JobController at [http://localhost:8080/jobs/] to enqueue jobs
    - Visit the dashboard again and see the jobs being processed!

> Note: Running micronaut apps with JobRunr using GraalVM native mode is not yet supported.