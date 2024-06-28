package com.gigware.goldmine;

import com.gigware.goldmine.pojo.AnalyticsReport;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

final class Reports {

    public static final List<AnalyticsReport> PROCESS_REPORTS = Lists.newArrayList();

    public static final List<AnalyticsReport> AUDIENCE_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Overview",
                    "audience-overview",
                    Arrays.asList(),
                    Arrays.asList(
                            AnalMetrics.USERS__USERS,
                            AnalMetrics.USERS__NEW_USERS,
                            AnalMetrics.SESSIONS__SESSIONS,
                            AnalMetrics.PAGE_TRACKING__PAGE_VIEWS,
                            AnalMetrics.PAGE_TRACKING__PAGE_VIEWS_PER_SESSION,
                            AnalMetrics.SESSIONS__AVG_SESSION_DURATION,
                            AnalMetrics.SESSIONS__BOUNCE_RATE
                    )
            )
    );

    public static final List<AnalyticsReport> BEHAVIOR_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "Behavior -> Overview",
                    "behavior-overview",
                    Arrays.asList(),
                    Arrays.asList()
            )
    );

    public static final List<AnalyticsReport> ACQUISITION_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "Acquisition -> Overview",
                    "acquisition-overview",
                    Arrays.asList(),
                    Arrays.asList()
            )
    );

    public static final List<AnalyticsReport> CONVERSIONS_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "",
                    "",
                    Arrays.asList(),
                    Arrays.asList()
            )
    );

    static {
        PROCESS_REPORTS.addAll(BEHAVIOR_REPORTS);
        PROCESS_REPORTS.addAll(AUDIENCE_REPORTS);
        PROCESS_REPORTS.addAll(ACQUISITION_REPORTS);
        PROCESS_REPORTS.addAll(CONVERSIONS_REPORTS);
    }

}
