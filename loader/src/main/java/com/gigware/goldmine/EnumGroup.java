package com.gigware.goldmine;

import java.util.Arrays;
import java.util.List;

enum EnumGroup {
    ADWORDS(Arrays.asList(
            EnumDimensions.ADWORDS__TRUEVIEW_VIDEO_AD,
            EnumDimensions.ADWORDS__GOOGLE_ADS_AD_GROUP_ID,
            EnumDimensions.ADWORDS__AD_DISTRIBUTION_NETWORK,
            EnumDimensions.ADWORDS__DESTINATION_URL,
            EnumDimensions.ADWORDS__PLACEMENT_TYPE,
            EnumDimensions.ADWORDS__PLACEMENT_DOMAIN,
            EnumDimensions.ADWORDS__GOOGLE_ADS_CAMPAIGN_ID,
            EnumDimensions.ADWORDS__DISPLAY_URL,
            EnumDimensions.ADWORDS__QUERY_WORD_COUNT,
            EnumDimensions.ADWORDS__GOOGLE_ADS_CREATIVE_ID,
            EnumDimensions.ADWORDS__AD_FORMAT,
            EnumDimensions.ADWORDS__QUERY_MATCH_TYPE,
            EnumDimensions.ADWORDS__GOOGLE_ADS_CUSTOMER_ID,
            EnumDimensions.ADWORDS__KEYWORD_MATCH_TYPE,
            EnumDimensions.ADWORDS__GOOGLE_ADS_CRITERIA_ID,
            EnumDimensions.ADWORDS__GOOGLE_ADS_AD_GROUP,
            EnumDimensions.ADWORDS__GOOGLE_ADS_AD_SLOT,
            EnumDimensions.ADWORDS__TARGETING_TYPE,
            EnumDimensions.ADWORDS__PLACEMENT_URL,
            EnumDimensions.ADWORDS__SEARCH_QUERY
    ), Arrays.asList(
            EnumMetrics.ADWORDS__CPM,
            EnumMetrics.ADWORDS__CPC,
            EnumMetrics.ADWORDS__RPC,
            EnumMetrics.ADWORDS__ROAS,
            EnumMetrics.ADWORDS__COST_PER_TRANSACTION,
            EnumMetrics.ADWORDS__CTR,
            EnumMetrics.ADWORDS__IMPRESSIONS,
            EnumMetrics.ADWORDS__CLICKS,
            EnumMetrics.ADWORDS__COST_PER_GOAL_CONVERSION,
            EnumMetrics.ADWORDS__COST_PER_CONVERSION,
            EnumMetrics.ADWORDS__COST
    )),
    APP_TRACKING(Arrays.asList(
            EnumDimensions.APP_TRACKING__APP_NAME,
            EnumDimensions.APP_TRACKING__SCREEN_NAME,
            EnumDimensions.APP_TRACKING__APP_ID,
            EnumDimensions.APP_TRACKING__LANDING_SCREEN,
            EnumDimensions.APP_TRACKING__SCREEN_DEPTH,
            EnumDimensions.APP_TRACKING__EXIT_SCREEN,
            EnumDimensions.APP_TRACKING__APP_INSTALLER_ID,
            EnumDimensions.APP_TRACKING__APP_VERSION
    ), Arrays.asList(
            EnumMetrics.APP_TRACKING__SCREEN_VIEWS,
            EnumMetrics.APP_TRACKING__SCREENS_PER_SESSION,
            EnumMetrics.APP_TRACKING__TIME_ON_SCREEN,
            EnumMetrics.APP_TRACKING__UNIQUE_SCREEN_VIEWS,
            EnumMetrics.APP_TRACKING__AVG_TIME_ON_SCREEN
    )),
    AUDIENCE(Arrays.asList(
            EnumDimensions.AUDIENCE__AGE,
            EnumDimensions.AUDIENCE__AFFINITY_CATEGORY_REACH,
            EnumDimensions.AUDIENCE__INMARKET_SEGMENT,
            EnumDimensions.AUDIENCE__GENDER,
            EnumDimensions.AUDIENCE__OTHER_CATEGORY
    ), Arrays.asList(
    )),
    CHANNEL_GROUPING(Arrays.asList(
            EnumDimensions.CHANNEL_GROUPING__DEFAULT_CHANNEL_GROUPING
    ), Arrays.asList(
    )),
    CONTENT_GROUPING(Arrays.asList(
            EnumDimensions.CONTENT_GROUPING__PAGE_GROUP_XX,
            EnumDimensions.CONTENT_GROUPING__PREVIOUS_PAGE_GROUP_XX,
            EnumDimensions.CONTENT_GROUPING__LANDING_PAGE_GROUP_XX
    ), Arrays.asList(
            EnumMetrics.CONTENT_GROUPING__UNIQUE_VIEWS_XX
    )),
    CUSTOM_VARIABLES_OR_COLUMNS(Arrays.asList(
            EnumDimensions.CUSTOM_VARIABLES_OR_COLUMNS__CUSTOM_DIMENSION_XX,
            EnumDimensions.CUSTOM_VARIABLES_OR_COLUMNS__CUSTOM_VARIABLE_VALUE_XX,
            EnumDimensions.CUSTOM_VARIABLES_OR_COLUMNS__CUSTOM_VARIABLE_KEY_XX
    ), Arrays.asList(
            EnumMetrics.CUSTOM_VARIABLES_OR_COLUMNS__CALCULATED_METRIC,
            EnumMetrics.CUSTOM_VARIABLES_OR_COLUMNS__CUSTOM_METRIC_XX_VALUE
    )),
    EVENT_TRACKING(Arrays.asList(
            EnumDimensions.EVENT_TRACKING__EVENT_CATEGORY,
            EnumDimensions.EVENT_TRACKING__EVENT_ACTION,
            EnumDimensions.EVENT_TRACKING__EVENT_LABEL
    ), Arrays.asList(
            EnumMetrics.EVENT_TRACKING__SESSIONS_WITH_EVENT,
            EnumMetrics.EVENT_TRACKING__UNIQUE_EVENTS,
            EnumMetrics.EVENT_TRACKING__AVG_VALUE,
            EnumMetrics.EVENT_TRACKING__EVENT_VALUE,
            EnumMetrics.EVENT_TRACKING__TOTAL_EVENTS,
            EnumMetrics.EVENT_TRACKING__EVENTS_PER_SESSION_WITH_EVENT
    )),
    EXCEPTIONS(Arrays.asList(
            EnumDimensions.EXCEPTIONS__EXCEPTION_DESCRIPTION
    ), Arrays.asList(
            EnumMetrics.EXCEPTIONS__EXCEPTIONS,
            EnumMetrics.EXCEPTIONS__CRASHES,
            EnumMetrics.EXCEPTIONS__CRASHES_PER_SCREEN,
            EnumMetrics.EXCEPTIONS__EXCEPTIONS_PER_SCREEN
    )),
    GEO_NETWORK(Arrays.asList(
            EnumDimensions.GEO_NETWORK__SERVICE_PROVIDER,
            EnumDimensions.GEO_NETWORK__CITY,
            EnumDimensions.GEO_NETWORK__LATITUDE,
            EnumDimensions.GEO_NETWORK__LONGITUDE,
            EnumDimensions.GEO_NETWORK__CONTINENT_ID,
            EnumDimensions.GEO_NETWORK__REGION,
            EnumDimensions.GEO_NETWORK__COUNTRY_ISO_CODE,
            EnumDimensions.GEO_NETWORK__METRO,
            EnumDimensions.GEO_NETWORK__NETWORK_DOMAIN,
            EnumDimensions.GEO_NETWORK__REGION_ID,
            EnumDimensions.GEO_NETWORK__CITY_ID,
            EnumDimensions.GEO_NETWORK__CONTINENT,
            EnumDimensions.GEO_NETWORK__SUB_CONTINENT_CODE,
            EnumDimensions.GEO_NETWORK__SUB_CONTINENT,
            EnumDimensions.GEO_NETWORK__COUNTRY,
            EnumDimensions.GEO_NETWORK__REGION_ISO_CODE,
            EnumDimensions.GEO_NETWORK__METRO_ID
    ), Arrays.asList(
    )),
    GOAL_CONVERSIONS(Arrays.asList(
            EnumDimensions.GOAL_CONVERSIONS__GOAL_COMPLETION_LOCATION,
            EnumDimensions.GOAL_CONVERSIONS__GOAL_PREVIOUS_STEP_1,
            EnumDimensions.GOAL_CONVERSIONS__GOAL_PREVIOUS_STEP_3,
            EnumDimensions.GOAL_CONVERSIONS__GOAL_PREVIOUS_STEP_2
    ), Arrays.asList(
            EnumMetrics.GOAL_CONVERSIONS__GOAL_STARTS,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_XX_VALUE,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_XX_ABANDONED_FUNNELS,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_XX_STARTS,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_XX_COMPLETIONS,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_XX_ABANDONMENT_RATE,
            EnumMetrics.GOAL_CONVERSIONS__PER_SESSION_GOAL_VALUE,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_COMPLETIONS,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_CONVERSION_RATE,
            EnumMetrics.GOAL_CONVERSIONS__ABANDONED_FUNNELS,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_XX_CONVERSION_RATE,
            EnumMetrics.GOAL_CONVERSIONS__TOTAL_ABANDONMENT_RATE,
            EnumMetrics.GOAL_CONVERSIONS__GOAL_VALUE
    )),
    INTERNAL_SEARCH(Arrays.asList(
            EnumDimensions.INTERNAL_SEARCH__SITE_SEARCH_STATUS,
            EnumDimensions.INTERNAL_SEARCH__START_PAGE,
            EnumDimensions.INTERNAL_SEARCH__SEARCH_DESTINATION_PAGE,
            EnumDimensions.INTERNAL_SEARCH__SITE_SEARCH_CATEGORY,
            EnumDimensions.INTERNAL_SEARCH__DESTINATION_PAGE,
            EnumDimensions.INTERNAL_SEARCH__REFINED_KEYWORD,
            EnumDimensions.INTERNAL_SEARCH__SEARCH_TERM
    ), Arrays.asList(
            EnumMetrics.INTERNAL_SEARCH__PERCENT_SEARCH_EXITS,
            EnumMetrics.INTERNAL_SEARCH__RESULTS_PAGEVIEWS,
            EnumMetrics.INTERNAL_SEARCH__PERCENT_SESSIONS_WITH_SEARCH,
            EnumMetrics.INTERNAL_SEARCH__SITE_SEARCH_GOAL_XX_CONVERSION_RATE,
            EnumMetrics.INTERNAL_SEARCH__RESULTS_PAGEVIEWS_PER_SEARCH,
            EnumMetrics.INTERNAL_SEARCH__PER_SEARCH_GOAL_VALUE,
            EnumMetrics.INTERNAL_SEARCH__SEARCH_REFINEMENTS,
            EnumMetrics.INTERNAL_SEARCH__SITE_SEARCH_GOAL_CONVERSION_RATE,
            EnumMetrics.INTERNAL_SEARCH__PERCENT_SEARCH_REFINEMENTS,
            EnumMetrics.INTERNAL_SEARCH__SESSIONS_WITH_SEARCH,
            EnumMetrics.INTERNAL_SEARCH__TIME_AFTER_SEARCH,
            EnumMetrics.INTERNAL_SEARCH__TOTAL_UNIQUE_SEARCHES,
            EnumMetrics.INTERNAL_SEARCH__AVG_SEARCH_DEPTH,
            EnumMetrics.INTERNAL_SEARCH__SEARCH_EXITS,
            EnumMetrics.INTERNAL_SEARCH__SEARCH_DEPTH
    )),
    LIFETIME_VALUE_AND_COHORTS(Arrays.asList(
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__ACQUISITION_CHANNEL,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__ACQUISITION_MEDIUM,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__WEEK,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__ACQUISITION_SOURCE,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__ACQUISITION_SOURCE_PER_MEDIUM,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__COHORT,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__DAY,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__ACQUISITION_CAMPAIGN,
            EnumDimensions.LIFETIME_VALUE_AND_COHORTS__MONTH
    ), Arrays.asList(
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__SESSION_DURATION_PER_USER,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__USER_RETENTION,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__APPVIEWS_PER_USER_LTV,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__PAGEVIEWS_PER_USER,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__REVENUE_PER_USER,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__PAGEVIEWS_PER_USER_LTV,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__SESSIONS_PER_USER_LTV,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__SESSION_DURATION_PER_USER_LTV,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__SESSIONS_PER_USER,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__REVENUE_PER_USER_LTV,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__TOTAL_USERS,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__USERS,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__GOAL_COMPLETIONS_PER_USER,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__GOAL_COMPLETIONS_PER_USER_LTV,
            EnumMetrics.LIFETIME_VALUE_AND_COHORTS__APPVIEWS_PER_USER
    )),
    PAGE_TRACKING(Arrays.asList(
            EnumDimensions.PAGE_TRACKING__PAGE,
            EnumDimensions.PAGE_TRACKING__HOSTNAME,
            EnumDimensions.PAGE_TRACKING__PAGE_DEPTH,
            EnumDimensions.PAGE_TRACKING__PAGE_TITLE,
            EnumDimensions.PAGE_TRACKING__EXIT_PAGE,
            EnumDimensions.PAGE_TRACKING__PREVIOUS_PAGE_PATH,
            EnumDimensions.PAGE_TRACKING__LANDING_PAGE,
            EnumDimensions.PAGE_TRACKING__PAGE_PATH_LEVEL_1,
            EnumDimensions.PAGE_TRACKING__PAGE_PATH_LEVEL_2,
            EnumDimensions.PAGE_TRACKING__PAGE_PATH_LEVEL_3,
            EnumDimensions.PAGE_TRACKING__PAGE_PATH_LEVEL_4,
            EnumDimensions.PAGE_TRACKING__SECOND_PAGE
    ), Arrays.asList(
            EnumMetrics.PAGE_TRACKING__PERCENT_EXIT,
            EnumMetrics.PAGE_TRACKING__TIME_ON_PAGE,
            EnumMetrics.PAGE_TRACKING__PAGE_VALUE,
            EnumMetrics.PAGE_TRACKING__ENTRANCES,
            EnumMetrics.PAGE_TRACKING__UNIQUE_PAGEVIEWS,
            EnumMetrics.PAGE_TRACKING__ENTRANCES_PER_PAGEVIEWS,
            EnumMetrics.PAGE_TRACKING__EXITS,
            EnumMetrics.PAGE_TRACKING__PAGEVIEWS,
            EnumMetrics.PAGE_TRACKING__AVG_TIME_ON_PAGE,
            EnumMetrics.PAGE_TRACKING__PAGES_PER_SESSION
    )),
    PLATFORM_OR_DEVICE(Arrays.asList(
            EnumDimensions.PLATFORM_OR_DEVICE__BROWSER_VERSION,
            EnumDimensions.PLATFORM_OR_DEVICE__OPERATING_SYSTEM_VERSION,
            EnumDimensions.PLATFORM_OR_DEVICE__BROWSER_SIZE,
            EnumDimensions.PLATFORM_OR_DEVICE__MOBILE_INPUT_SELECTOR,
            EnumDimensions.PLATFORM_OR_DEVICE__DATA_SOURCE,
            EnumDimensions.PLATFORM_OR_DEVICE__OPERATING_SYSTEM,
            EnumDimensions.PLATFORM_OR_DEVICE__MOBILE_DEVICE_MODEL,
            EnumDimensions.PLATFORM_OR_DEVICE__MOBILE_DEVICE_MARKETING_NAME,
            EnumDimensions.PLATFORM_OR_DEVICE__BROWSER,
            EnumDimensions.PLATFORM_OR_DEVICE__MOBILE_DEVICE_INFO,
            EnumDimensions.PLATFORM_OR_DEVICE__DEVICE_CATEGORY,
            EnumDimensions.PLATFORM_OR_DEVICE__MOBILE_DEVICE_BRANDING
    ), Arrays.asList(
    )),
    SESSION(Arrays.asList(
            EnumDimensions.SESSION__SESSION_DURATION
    ), Arrays.asList(
            EnumMetrics.SESSION__BOUNCE_RATE,
            EnumMetrics.SESSION__BOUNCES,
            EnumMetrics.SESSION__SESSION_DURATION,
            EnumMetrics.SESSION__SESSIONS,
            EnumMetrics.SESSION__AVG_SESSION_DURATION,
            EnumMetrics.SESSION__UNIQUE_DIMENSION_COMBINATIONS,
            EnumMetrics.SESSION__HITS
    )),
    SITE_SPEED(Arrays.asList(
    ), Arrays.asList(
            EnumMetrics.SITE_SPEED__AVG_REDIRECTION_TIME_SEC,
            EnumMetrics.SITE_SPEED__SERVER_CONNECTION_TIME_MS,
            EnumMetrics.SITE_SPEED__AVG_SERVER_RESPONSE_TIME_SEC,
            EnumMetrics.SITE_SPEED__PAGE_LOAD_SAMPLE,
            EnumMetrics.SITE_SPEED__PAGE_LOAD_TIME_MS,
            EnumMetrics.SITE_SPEED__AVG_PAGE_LOAD_TIME_SEC,
            EnumMetrics.SITE_SPEED__PAGE_DOWNLOAD_TIME_MS,
            EnumMetrics.SITE_SPEED__REDIRECTION_TIME_MS,
            EnumMetrics.SITE_SPEED__DOMAIN_LOOKUP_TIME_MS,
            EnumMetrics.SITE_SPEED__DOCUMENT_INTERACTIVE_TIME_MS,
            EnumMetrics.SITE_SPEED__DOCUMENT_CONTENT_LOADED_TIME_MS,
            EnumMetrics.SITE_SPEED__SERVER_RESPONSE_TIME_MS,
            EnumMetrics.SITE_SPEED__AVG_DOMAIN_LOOKUP_TIME_SEC,
            EnumMetrics.SITE_SPEED__AVG_DOCUMENT_CONTENT_LOADED_TIME_SEC,
            EnumMetrics.SITE_SPEED__AVG_DOCUMENT_INTERACTIVE_TIME_SEC,
            EnumMetrics.SITE_SPEED__DOM_LATENCY_METRICS_SAMPLE,
            EnumMetrics.SITE_SPEED__AVG_SERVER_CONNECTION_TIME_SEC,
            EnumMetrics.SITE_SPEED__SPEED_METRICS_SAMPLE,
            EnumMetrics.SITE_SPEED__AVG_PAGE_DOWNLOAD_TIME_SEC
    )),
    SOCIAL_INTERACTIONS(Arrays.asList(
            EnumDimensions.SOCIAL_INTERACTIONS__SOCIAL_TYPE,
            EnumDimensions.SOCIAL_INTERACTIONS__SOCIAL_ACTION,
            EnumDimensions.SOCIAL_INTERACTIONS__SOCIAL_NETWORK_AND_ACTION_HIT,
            EnumDimensions.SOCIAL_INTERACTIONS__SOCIAL_NETWORK,
            EnumDimensions.SOCIAL_INTERACTIONS__SOCIAL_ENTITY
    ), Arrays.asList(
            EnumMetrics.SOCIAL_INTERACTIONS__UNIQUE_SOCIAL_ACTIONS,
            EnumMetrics.SOCIAL_INTERACTIONS__ACTIONS_PER_SOCIAL_SESSION,
            EnumMetrics.SOCIAL_INTERACTIONS__SOCIAL_ACTIONS
    )),
    SYSTEM(Arrays.asList(
            EnumDimensions.SYSTEM__FLASH_VERSION,
            EnumDimensions.SYSTEM__JAVA_SUPPORT,
            EnumDimensions.SYSTEM__LANGUAGE,
            EnumDimensions.SYSTEM__SCREEN_COLORS,
            EnumDimensions.SYSTEM__SOURCE_PROPERTY_TRACKING_ID,
            EnumDimensions.SYSTEM__SCREEN_RESOLUTION,
            EnumDimensions.SYSTEM__SOURCE_PROPERTY_DISPLAY_NAME
    ), Arrays.asList(
    )),
//    TIME(Arrays.asList(
//            EnumDimensions.TIME__HOUR_INDEX,
//            EnumDimensions.TIME__MONTH_OF_THE_YEAR,
//            EnumDimensions.TIME__DAY_INDEX,
//            EnumDimensions.TIME__MINUTE,
//            EnumDimensions.TIME__MONTH_INDEX,
//            EnumDimensions.TIME__MINUTE_INDEX,
//            EnumDimensions.TIME__ISO_YEAR,
//            EnumDimensions.TIME__DAY_OF_THE_MONTH,
//            EnumDimensions.TIME__WEEK_INDEX,
//            EnumDimensions.TIME__WEEK_OF_YEAR,
//            EnumDimensions.TIME__YEAR,
//            EnumDimensions.TIME__HOUR,
//            EnumDimensions.TIME__MONTH_OF_YEAR,
//            EnumDimensions.TIME__DATE_HOUR_AND_MINUTE,
//            EnumDimensions.TIME__DAY_OF_WEEK,
//            EnumDimensions.TIME__DATE,
//            EnumDimensions.TIME__ISO_WEEK_OF_ISO_YEAR,
//            EnumDimensions.TIME__HOUR_OF_DAY,
//            EnumDimensions.TIME__WEEK_OF_THE_YEAR,
//            EnumDimensions.TIME__DAY_OF_WEEK_NAME,
//            EnumDimensions.TIME__ISO_WEEK_OF_THE_YEAR
//    ), Arrays.asList(
//    )),
    TRAFFIC_SOURCES(Arrays.asList(
            EnumDimensions.TRAFFIC_SOURCES__SOCIAL_NETWORK,
            EnumDimensions.TRAFFIC_SOURCES__AD_CONTENT,
            EnumDimensions.TRAFFIC_SOURCES__FULL_REFERRER,
            EnumDimensions.TRAFFIC_SOURCES__KEYWORD,
            EnumDimensions.TRAFFIC_SOURCES__CAMPAIGN,
            EnumDimensions.TRAFFIC_SOURCES__SOURCE,
            EnumDimensions.TRAFFIC_SOURCES__SOCIAL_SOURCE_REFERRAL,
            EnumDimensions.TRAFFIC_SOURCES__CAMPAIGN_CODE,
            EnumDimensions.TRAFFIC_SOURCES__MEDIUM,
            EnumDimensions.TRAFFIC_SOURCES__SOURCE_PER_MEDIUM,
            EnumDimensions.TRAFFIC_SOURCES__REFERRAL_PATH
    ), Arrays.asList(
            EnumMetrics.TRAFFIC_SOURCES__ORGANIC_SEARCHES
    )),
    USER(Arrays.asList(
            EnumDimensions.USER__USER_DEFINED_VALUE,
            EnumDimensions.USER_TIMINGS__TIMING_VARIABLE,
            EnumDimensions.USER_TIMINGS__TIMING_CATEGORY,
            EnumDimensions.USER__COUNT_OF_SESSIONS,
            EnumDimensions.USER__DAYS_SINCE_LAST_SESSION,
            EnumDimensions.USER__USER_BUCKET,
            EnumDimensions.USER__USER_TYPE,
            EnumDimensions.USER_TIMINGS__TIMING_LABEL
    ), Arrays.asList(
            EnumMetrics.USER__NUMBER_OF_SESSIONS_PER_USER,
            EnumMetrics.USER__14_DAY_ACTIVE_USERS,
            EnumMetrics.USER__28_DAY_ACTIVE_USERS,
            EnumMetrics.USER__30_DAY_ACTIVE_USERS,
            EnumMetrics.USER__PERCENT_NEW_SESSIONS,
            EnumMetrics.USER__NEW_USERS,
            EnumMetrics.USER__7_DAY_ACTIVE_USERS,
            EnumMetrics.USER_TIMINGS__AVG_USER_TIMING_SEC,
            EnumMetrics.USER__USERS,
            EnumMetrics.USER__1_DAY_ACTIVE_USERS,
            EnumMetrics.USER_TIMINGS__USER_TIMING_MS,
            EnumMetrics.USER_TIMINGS__USER_TIMING_SAMPLE
    )),
    USER_TIMINGS(Arrays.asList(
            EnumDimensions.USER_TIMINGS__TIMING_VARIABLE,
            EnumDimensions.USER_TIMINGS__TIMING_CATEGORY,
            EnumDimensions.USER_TIMINGS__TIMING_LABEL
    ), Arrays.asList(
            EnumMetrics.USER_TIMINGS__AVG_USER_TIMING_SEC,
            EnumMetrics.USER_TIMINGS__USER_TIMING_MS,
            EnumMetrics.USER_TIMINGS__USER_TIMING_SAMPLE
    ));

    private final List<EnumDimensions> dimensions;
    private final List<EnumMetrics> metrics;

    EnumGroup(final List<EnumDimensions> dimensions, List<EnumMetrics> metrics) {
        this.dimensions = dimensions;
        this.metrics = metrics;
    }

    public List<EnumDimensions> getDimensions() {
        return dimensions;
    }

    public List<EnumMetrics> getMetrics() {
        return metrics;
    }
}
