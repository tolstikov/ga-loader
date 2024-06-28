package com.gigware.goldmine;

import com.google.api.services.analyticsreporting.v4.model.Dimension;

final class AnalDimensions {
    public static final Dimension USERS__USER_TYPE = new Dimension().setName("ga:userType");
    public static final Dimension USERS__COUNT_OF_SESSIONS = new Dimension().setName("ga:sessionCount");
    public static final Dimension USERS__DAYS_SINCE_LAST_SESSIONS = new Dimension().setName("ga:daysSinceLastSession");

    public static final Dimension SYSTEM__LANGUAGE = new Dimension().setName("ga:language");

    public static final Dimension SESSION__SESSION_DURATION = new Dimension().setName("ga:sessionDurationBucket");

    public static final Dimension PAGE_TRACKING__HOSTNAME = new Dimension().setName("ga:hostname");
    public static final Dimension PAGE_TRACKING__PAGE_PATH = new Dimension().setName("ga:pagePath");
    public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_1 = new Dimension().setName("ga:pagePathLevel1");
    public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_2 = new Dimension().setName("ga:pagePathLevel2");
    public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_3 = new Dimension().setName("ga:pagePathLevel3");
    public static final Dimension PAGE_TRACKING__PAGE_PATH_LVL_4 = new Dimension().setName("ga:pagePathLevel4");
    public static final Dimension PAGE_TRACKING__PAGE_TITLE = new Dimension().setName("ga:pageTitle");
    public static final Dimension PAGE_TRACKING__LANDING_PATE = new Dimension().setName("ga:landingPagePath");
    public static final Dimension PAGE_TRACKING__SECOND_PAGE = new Dimension().setName("ga:secondPagePath");

}
