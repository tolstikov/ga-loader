package com.gigware.goldmine;

import com.google.api.services.analyticsreporting.v4.model.Metric;

final class AnalMetrics {
    public static final Metric USERS__USERS = new Metric().setExpression("ga:users").setAlias("users");
    public static final Metric USERS__NEW_USERS = new Metric().setExpression("ga:newUsers").setAlias("newUsers");

    public static final Metric SESSIONS__SESSIONS = new Metric().setExpression("ga:sessions").setAlias("sessions");
    public static final Metric SESSIONS__BOUNCES = new Metric().setExpression("ga:bounces").setAlias("bounces");
    public static final Metric SESSIONS__BOUNCE_RATE = new Metric().setExpression("ga:bounceRate").setAlias("bounceRate");
    public static final Metric SESSIONS__SESSION_DURATION = new Metric().setExpression("ga:sessionDuration").setAlias("sessionDuration");
    public static final Metric SESSIONS__AVG_SESSION_DURATION = new Metric().setExpression("ga:avgSessionDuration").setAlias("avgSessionDuration");

    public static final Metric PAGE_TRACKING__PAGE_VALUE = new Metric().setExpression("ga:pageValue").setAlias("pageValue");
    public static final Metric PAGE_TRACKING__ENTRANCES = new Metric().setExpression("ga:entrances").setAlias("entrances");
    public static final Metric PAGE_TRACKING__ENTRANCES_PAGEVIEWS = new Metric().setExpression("ga:entranceRate").setAlias("entranceRate");
    public static final Metric PAGE_TRACKING__PAGE_VIEWS = new Metric().setExpression("ga:pageviews").setAlias("pageViews");
    public static final Metric PAGE_TRACKING__PAGE_VIEWS_PER_SESSION = new Metric().setExpression("ga:pageviewsPerSession").setAlias("pageViewsPerSession");
    public static final Metric PAGE_TRACKING__UNIQUE_PAGE_VIEWS = new Metric().setExpression("ga:uniquePageviews").setAlias("uniquePageViews");
    public static final Metric PAGE_TRACKING__TIME_ON_PAGE = new Metric().setExpression("ga:timeOnPage").setAlias("timeOnPage");
    public static final Metric PAGE_TRACKING__AVG_TIME_ON_PAGE = new Metric().setExpression("ga:avgTimeOnPage").setAlias("avgTimeOnPage");
    public static final Metric PAGE_TRACKING__EXISTS = new Metric().setExpression("ga:exits").setAlias("exits");
    public static final Metric PAGE_TRACKING__EXIT_RATE = new Metric().setExpression("ga:exitRate").setAlias("exitRate");
}
