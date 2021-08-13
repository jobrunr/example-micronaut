package org.jobrunr.examples.services;

import org.jobrunr.jobs.annotations.Job;

public interface MyServiceInterface {

    @Job(name = "Doing some simple job")
    void doSimpleJob(String anArgument);

    void doLongRunningJob(String anArgument);
}
