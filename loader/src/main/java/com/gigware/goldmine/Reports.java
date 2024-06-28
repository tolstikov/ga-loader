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

    private static final List<AnalyticsReport> AUDIENCE_BEHAVIOR_FREQUENCY_AND_RECENCY = Arrays.asList(
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

    public static final List<AnalyticsReport> AUDIENCE_TECHNOLOGY_BROWSER_AND_OS = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Technology Browsers and OS",
                    "audience-technology-browsers-and-os",
                    Arrays.asList(
                            EnumDimensions.PLATFORM_OR_DEVICE__BROWSER.getDimension()
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

    public static final List<AnalyticsReport> AUDIENCE_TECHNOLOGY_NETWORK = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Technology Network",
                    "audience-technology-network",
                    Arrays.asList(
                            EnumDimensions.GEO_NETWORK__SERVICE_PROVIDER.getDimension()
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

    public static final List<AnalyticsReport> AUDIENCE_MOBILE_OVERVIEW = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Mobile Overview",
                    "audience-mobile-overview",
                    Arrays.asList(
                            EnumDimensions.PLATFORM_OR_DEVICE__DEVICE_CATEGORY.getDimension()
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

    public static final List<AnalyticsReport> AUDIENCE_MOBILE_DEVICES = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Mobile Devices",
                    "audience-mobile-devices",
                    Arrays.asList(
                            EnumDimensions.PLATFORM_OR_DEVICE__MOBILE_DEVICE_INFO.getDimension()
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

    public static final List<AnalyticsReport> AUDIENCE_CUSTOM_CUSTOM_VARIABLES = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Custom Custom Variables",
                    "audience-custom-custom-variables",
                    Arrays.asList(
                            EnumDimensions.CUSTOM_VAR_NAME_1.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.USER__PERCENT_NEW_SESSIONS.getMetric(),
                            EnumMetrics.USER__NEW_USERS.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGES_PER_SESSION.getMetric(),
                            EnumMetrics.SESSION__AVG_SESSION_DURATION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_CONVERSION_RATE.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_COMPLETION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_VALUE.getMetric()
                    )
            )
    );

    public static final List<AnalyticsReport> AUDIENCE_CUSTOM_USER_DEFINED = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Custom User Defined",
                    "audience-custom-user-defined",
                    Arrays.asList(
                            EnumDimensions.USER__USER_DEFINED_VALUE.getDimension()
                    ),
                    Arrays.asList(
                            EnumMetrics.SESSION__SESSIONS.getMetric(),
                            EnumMetrics.USER__PERCENT_NEW_SESSIONS.getMetric(),
                            EnumMetrics.USER__NEW_USERS.getMetric(),
                            EnumMetrics.SESSION__BOUNCE_RATE.getMetric(),
                            EnumMetrics.PAGE_TRACKING__PAGES_PER_SESSION.getMetric(),
                            EnumMetrics.SESSION__AVG_SESSION_DURATION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_CONVERSION_RATE.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_COMPLETION.getMetric(),
                            EnumMetrics.UNKNOWN__GOAL_1_VALUE.getMetric()
                    )
            )
    );

    // todo метрики вроде как есть, но экстерал неймов нету
    public static final List<AnalyticsReport> AUDIENCE_BENCHMARKING_CHANNELS = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Benchmarking Channels",
                    "audience-benchmarking-channels",
                    Arrays.asList(
                            EnumDimensions.CHANNEL_GROUPING__DEFAULT_CHANNEL_GROUPING.getDimension()
                    ),
                    Arrays.asList(
                    )
            )
    );

    // todo метрики вроде как есть, но экстерал неймов нету
    public static final List<AnalyticsReport> AUDIENCE_BENCHMARKING_LOCATION = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Benchmarking Location",
                    "audience-benchmarking-location",
                    Arrays.asList(
                            EnumDimensions.GEO_NETWORK__COUNTRY.getDimension()
                    ),
                    Arrays.asList(
                    )
            )
    );

    // todo метрики вроде как есть, но экстерал неймов нету
    public static final List<AnalyticsReport> AUDIENCE_BENCHMARKING_DEVICES = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Benchmarking Devices",
                    "audience-benchmarking-devices",
                    Arrays.asList(
                            EnumDimensions.PLATFORM_OR_DEVICE__DEVICE_CATEGORY.getDimension()
                    ),
                    Arrays.asList(
                    )
            )
    );

    // todo нету ничего с префиксом ga:
    public static final List<AnalyticsReport> AUDIENCE_USERS_FLOW = Arrays.asList(
            new AnalyticsReport(
                    "Audience -> Users Flow",
                    "audience-users-flow",
                    Arrays.asList(
                    ),
                    Arrays.asList(
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
