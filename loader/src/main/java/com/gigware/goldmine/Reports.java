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

    private static final List<AnalyticsReport> AUDIENCE_ACTIVE_USER_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> User explorer",
                    "audience-userExplorer",
                    Arrays.asList(
                            EnumDimensions.CLIENT_ID.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.SESSION__AVG_SESSION_DURATION.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric(),
                            EnumMetrics.ECOMMERCE__REVENUE.getMetric(),
                            EnumMetrics.ECOMMERCE__TRANSACTIONS.getMetric(),
                            EnumMetrics.GOAL_CONVERSIONS__GOAL_CONVERSION_RATE.getMetric()
                    )
            )
    );

    private static final List<AnalyticsReport> AUDIENCE_GEO_LANGUAGE_REPORTS = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Geo Language",
                    "audience-geoLanguage",
                    Arrays.asList(
                            EnumDimensions.SYSTEM__LANGUAGE.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.USER__USERS.getMetric(),
                            EnumMetrics.USER__NEW_USERS.getMetric(),
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGES_PER_SESSION.getMetric(),
                            EnumMetrics.SESSION__AVG_SESSION_DURATION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_CONVERSION_RATE.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_COMPLETION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_VALUE.getMetric()
                    )
            )
    );

    private static final List<AnalyticsReport> AUDIENCE_GEO_LANGUAGE_REPORT = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Geo Location",
                    "audience-geoLocation",
                    Arrays.asList(
                            EnumDimensions.GEO_NETWORK__COUNTRY.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.USER__USERS.getMetric(),
                            EnumMetrics.USER__NEW_USERS.getMetric(),
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGES_PER_SESSION.getMetric(),
                            EnumMetrics.SESSION__AVG_SESSION_DURATION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_CONVERSION_RATE.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_COMPLETION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_VALUE.getMetric()
                    )
            )
    );

    private static final List<AnalyticsReport> AUDIENCE_BEHAVIOR_NEW_VS_RETURNING = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Behavior New vs Returning",
                    "audience-behavior-new-vs-returning",
                    Arrays.asList(
                            EnumDimensions.USER__USER_TYPE.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.USER__USERS.getMetric(),
                            EnumMetrics.USER__NEW_USERS.getMetric(),
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGES_PER_SESSION.getMetric(),
                            EnumMetrics.SESSION__AVG_SESSION_DURATION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_CONVERSION_RATE.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_COMPLETION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_VALUE.getMetric()
                    )
            )
    );

    private static final List<AnalyticsReport> AUDIENCE_BEHAVIOR_FREQUENCY_AND_RECENCY= Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Behavior Frequency & Recency",
                    "audience-behavior-frequency-and-recency",
                    Arrays.asList(
                            EnumDimensions.USER__COUNT_OF_SESSIONS.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGEVIEWS.getMetric()
                    )
            )
    );

    public static final List<AnalyticsReport> AUDIENCE_BEHAVIOR_ENGAGEMENT = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Behavior Engagement",
                    "audience-behavior-engagement",
                    Arrays.asList(
                            EnumDimensions.SESSION__SESSION_DURATION.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGEVIEWS.getMetric()
                    )
            )
    );

    static {
        PROCESS_REPORTS.addAll(BEHAVIOR_REPORTS);
        PROCESS_REPORTS.addAll(AUDIENCE_REPORTS);
        PROCESS_REPORTS.addAll(ACQUISITION_REPORTS);
        PROCESS_REPORTS.addAll(CONVERSIONS_REPORTS);
    }

}
