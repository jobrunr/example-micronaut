package example.micronaut;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jobrunr.jobs.states.StateName.SCHEDULED;
import static org.jobrunr.jobs.states.StateName.SUCCEEDED;
import static org.jobrunr.utils.StringUtils.substringAfter;
import static org.awaitility.Awaitility.await;

@MicronautTest
public class JobControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    StorageProvider storageProvider;

    @Test
    public void testEnqueueSimpleJob() {
        HttpRequest<String> request = HttpRequest.GET("/jobs/simple-job");
        String responseBody = client.toBlocking().retrieve(request);
        assertThat(responseBody).startsWith("Job Enqueued: ");

        final UUID enqueuedJobId = UUID.fromString(substringAfter(responseBody, ": "));

        await()
                .atMost(30, TimeUnit.SECONDS)
                .until(() -> storageProvider.getJobById(enqueuedJobId).hasState(SUCCEEDED));
    }

    @Test
    public void testScheduleSimpleJob() {
        HttpRequest<String> request = HttpRequest.GET("/jobs/schedule-simple-job?when=" + Duration.of(3, ChronoUnit.HOURS));
        String responseBody = client.toBlocking().retrieve(request);
        assertThat(responseBody).startsWith("Job Scheduled: ");

        final UUID scheduledJobId = UUID.fromString(substringAfter(responseBody, ": "));
        await()
                .atMost(30, TimeUnit.SECONDS)
                .until(() -> storageProvider.getJobById(scheduledJobId).hasState(SCHEDULED));
    }
}