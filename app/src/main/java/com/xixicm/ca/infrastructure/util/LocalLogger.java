package com.xixicm.ca.infrastructure.util;

import android.content.Context;
import android.util.Log;

import com.xixicm.ca.BuildConfig;
import com.xixicm.ca.domain.util.Logger;

import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

/**
 * Created by min.chen on 2017/1/23.
 */

public class LocalLogger implements Logger {
    private boolean LOGGABLE;
    public static final String LOG_FOLDER = "log";

    public LocalLogger(Context context) {
        initLogging(context.getApplicationContext());
        LOGGABLE = BuildConfig.DEBUG;
    }

    public LocalLogger(Context context, boolean loggable) {
        initLogging(context.getApplicationContext());
        LOGGABLE = loggable;
    }

    private void initLogging(Context applicationContext) {
        final File logDir = applicationContext.getDir(LOG_FOLDER, Context.MODE_PRIVATE);
        String logName = applicationContext.getPackageName().substring(applicationContext.getPackageName().lastIndexOf("."));
        final File logFile = new File(logDir, logName + ".log");

        final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        final PatternLayoutEncoder filePattern = new PatternLayoutEncoder();
        filePattern.setContext(context);
        filePattern.setPattern("%d{MM-dd HH:mm:ss} [%thread] %logger{0} - %msg%n");
        filePattern.start();

        final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(context);
        fileAppender.setFile(logFile.getAbsolutePath());

        final TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.setFileNamePattern(logDir.getAbsolutePath() + "/" + logName + ".%d{yyyy-MM-dd,UTC}.log.gz");
        rollingPolicy.setMaxHistory(7);
        rollingPolicy.start();

        fileAppender.setEncoder(filePattern);
        fileAppender.setRollingPolicy(rollingPolicy);
        fileAppender.start();

        final PatternLayoutEncoder logcatTagPattern = new PatternLayoutEncoder();
        logcatTagPattern.setContext(context);
        logcatTagPattern.setPattern("%logger{0}");
        logcatTagPattern.start();

        final PatternLayoutEncoder logcatPattern = new PatternLayoutEncoder();
        logcatPattern.setContext(context);
        logcatPattern.setPattern("[%thread] %msg%n");
        logcatPattern.start();

        final LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(context);
        logcatAppender.setTagEncoder(logcatTagPattern);
        logcatAppender.setEncoder(logcatPattern);
        logcatAppender.start();

        final ch.qos.logback.classic.Logger log = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        log.addAppender(fileAppender);
        log.addAppender(logcatAppender);
        log.setLevel(Level.TRACE);
    }

    @Override
    public void v(String tag, String msg) {
        if (LOGGABLE || Log.isLoggable(tag, Log.VERBOSE)) {
            LoggerFactory.getLogger(tag).trace(msg);
        }
    }

    @Override
    public void v(String tag, String msg, Throwable tr) {
        if (LOGGABLE || Log.isLoggable(tag, Log.VERBOSE)) {
            LoggerFactory.getLogger(tag).trace(msg, tr);
        }
    }

    @Override
    public void d(String tag, String msg) {
        if (LOGGABLE || Log.isLoggable(tag, Log.DEBUG)) {
            LoggerFactory.getLogger(tag).debug(msg);
        }
    }

    @Override
    public void d(String tag, String msg, Throwable tr) {
        if (LOGGABLE || Log.isLoggable(tag, Log.DEBUG)) {
            LoggerFactory.getLogger(tag).debug(msg, tr);
        }
    }

    @Override
    public void i(String tag, String msg) {
        if (LOGGABLE || Log.isLoggable(tag, Log.INFO)) {
            LoggerFactory.getLogger(tag).info(msg);
        }
    }

    @Override
    public void i(String tag, String msg, Throwable tr) {
        if (LOGGABLE || Log.isLoggable(tag, Log.INFO)) {
            LoggerFactory.getLogger(tag).info(msg, tr);
        }
    }

    @Override
    public void w(String tag, String msg) {
        if (LOGGABLE || Log.isLoggable(tag, Log.WARN)) {
            LoggerFactory.getLogger(tag).warn(msg);
        }
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        if (LOGGABLE || Log.isLoggable(tag, Log.WARN)) {
            LoggerFactory.getLogger(tag).warn(msg, tr);
        }
    }

    @Override
    public void e(String tag, String msg) {
        if (LOGGABLE || Log.isLoggable(tag, Log.ERROR)) {
            LoggerFactory.getLogger(tag).error(msg);
        }
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        if (LOGGABLE || Log.isLoggable(tag, Log.ERROR)) {
            LoggerFactory.getLogger(tag).error(msg, tr);
        }
    }
}
