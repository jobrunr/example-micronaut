package org.jobrunr.examples.webapp.api;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import org.jobrunr.examples.services.MyService;
import org.jobrunr.examples.services.MyServiceInterface;
import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.scheduling.JobScheduler;

import jakarta.inject.Inject;

@Controller("/jobs")
public class JobController {

    @Inject
    private JobScheduler jobScheduler;

    @Inject
    private MyServiceInterface myService;

    @Get
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        return "Hello World from JobController!<br />" +
                "You can:<br />" +
                "- <a href=\"/jobs/simple-job\">Enqueue a simple job</a><br />" +
                "- <a href=\"/jobs/simple-job-instance\">Enqueue a simple job using a service instance</a><br />" +
                "- <a href=\"/jobs/long-running-job\">Enqueue a long-running job</a><br />" +
                "- <a href=\"/jobs/long-running-job-with-job-context\">Enqueue a long-running job using a JobContext to log progress</a><br />" +
                "- Learn more on <a href=\"https://www.jobrunr.io/\">www.jobrunr.io</a><br />"
                ;
    }

    @Get("/simple-job")
    @Produces(MediaType.TEXT_PLAIN)
    public String simpleJob(@QueryValue(value = "name", defaultValue = "World") String name) {
        final JobId enqueuedJobId = jobScheduler.<MyService>enqueue(myService -> myService.doSimpleJob("Hello " + name));
        return "Job Enqueued: " + enqueuedJobId;
    }

    @Get("/simple-job-instance")
    @Produces(MediaType.TEXT_PLAIN)
    public String simpleJobUsingInstance(@QueryValue(value = "name", defaultValue = "World") String name) {
        final JobId enqueuedJobId = jobScheduler.enqueue(() -> myService.doSimpleJob("Hello " + name));
        return "Job Enqueued: " + enqueuedJobId;
    }

    @Get("/long-running-job")
    @Produces(MediaType.TEXT_PLAIN)
    public String longRunningJob(@QueryValue(value = "name", defaultValue = "World") String name) {
        final JobId enqueuedJobId = jobScheduler.<MyService>enqueue(myService -> myService.doLongRunningJob("Hello " + name));
        return "Job Enqueued: " + enqueuedJobId;
    }

    @Get("/long-running-job-with-job-context")
    @Produces(MediaType.TEXT_PLAIN)
    public String longRunningJobWithJobContext(@QueryValue(value = "name", defaultValue = "World") String name) {
        final JobId enqueuedJobId = jobScheduler.<MyService>enqueue(myService -> myService.doLongRunningJobWithJobContext("Hello " + name, JobContext.Null));
        return "Job Enqueued: " + enqueuedJobId;
    }
}