package stroom.statistics.impl.sql;

import stroom.job.api.ScheduledJobsModule;
import stroom.job.api.RunnableWrapper;

import javax.inject.Inject;

import static stroom.job.api.Schedule.ScheduleType.CRON;
import static stroom.job.api.Schedule.ScheduleType.PERIODIC;

public class SQLStatisticsJobsModule extends ScheduledJobsModule {
    @Override
    protected void configure() {
        super.configure();
        bindJob()
                .name("Evict from object pool")
                .description("Evict from SQL Statistics event store object pool")
                .managed(false)
                .schedule(PERIODIC, "1m")
                .to(EvictFromObjectPool.class);
        bindJob()
                .name("SQL Stats In Memory Flush")
                .description("SQL Stats In Memory Flush (Cache to DB)")
                .schedule(CRON, "0,10,20,30,40,50 * *")
                .to(SQLStatsFlush.class);
        bindJob()
                .name("SQL Stats Database Aggregation")
                .description("Run SQL stats database aggregation")
                .schedule(CRON, "5,15,25,35,45,55 * *")
                .to(SQLStatsAggregation.class);
    }

    private static class EvictFromObjectPool extends RunnableWrapper {
        @Inject
        EvictFromObjectPool(final SQLStatisticEventStore sqlStatisticEventStore) {
            super(sqlStatisticEventStore::evict);
        }
    }

    private static class SQLStatsFlush extends RunnableWrapper {
        @Inject
        SQLStatsFlush(final SQLStatisticCache sqlStatisticCache) {
            super(sqlStatisticCache::execute);
        }
    }

    private static class SQLStatsAggregation extends RunnableWrapper {
        @Inject
        SQLStatsAggregation(final SQLStatisticAggregationManager sqlStatisticAggregationManager) {
            super(sqlStatisticAggregationManager::aggregate);
        }
    }
}
