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
                            EnumMetrics.USER__USERS.getMetric(),
                            EnumMetrics.USER__NEW_USERS.getMetric(),
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGEVIEWS.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGES_PER_SESSION.getMetric(),
                            EnumMetrics.SESSION__AVG_SESSION_DURATION.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric()
                    )
            )
    );

    public static final List<AnalyticsReport> BEHAVIOR_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "Behavior -> Overview",
                    "behavior-overview",
                    Arrays.asList(),
                    Arrays.asList(
                            EnumMetrics.PAGE_TRACKING__PAGEVIEWS.getMetric(),
                            EnumMetrics.PAGE_TRACKING__UNIQUE_PAGEVIEWS.getMetric(),
                            EnumMetrics.PAGE_TRACKING__AVG_TIME_ON_PAGE.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PERCENT_EXIT.getMetric()
                    )
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
