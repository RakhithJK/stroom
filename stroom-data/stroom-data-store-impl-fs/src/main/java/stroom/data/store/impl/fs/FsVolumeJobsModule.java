package stroom.data.store.impl.fs;

import stroom.job.api.ScheduledJobsModule;
import stroom.job.api.RunnableWrapper;

import javax.inject.Inject;

import static stroom.job.api.Schedule.ScheduleType.PERIODIC;

public class FsVolumeJobsModule extends ScheduledJobsModule {
    @Override
    protected void configure() {
        super.configure();
        bindJob()
                .name("File System Volume Status")
                .description("Update the usage status of file system volumes")
                .schedule(PERIODIC, "5m")
                .to(FileVolumeStatus.class);
    }

    private static class FileVolumeStatus extends RunnableWrapper {
        @Inject
        FileVolumeStatus(final FsVolumeService volumeService) {
            super(volumeService::updateStatus);
        }
    }
}
