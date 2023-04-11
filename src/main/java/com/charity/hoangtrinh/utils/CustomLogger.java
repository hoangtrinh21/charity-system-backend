package com.charity.hoangtrinh.utils;

import org.apache.log4j.*;

import java.io.IOException;

public class CustomLogger {
    private Logger logger;
    private String pattern = "%d{HH:mm:ss.SSS} - %msg%n";
    public CustomLogger(Logger logger, Level level, boolean stdOut, boolean fileOut) {
        this.logger = logger;
        if (stdOut) {
            ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout(pattern), ConsoleAppender.SYSTEM_OUT);
            consoleAppender.setThreshold(level);
            consoleAppender.activateOptions();
            this.logger.addAppender(consoleAppender);
        }
        if (fileOut) {
            String[] strs = this.logger.getName().split("\\.");
            String fileName = strs[strs.length - 1];
            RollingFileAppender rollingFileAppender = null;
            try {
                rollingFileAppender = new RollingFileAppender(new PatternLayout(pattern), fileName, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            rollingFileAppender.setThreshold(level);
            rollingFileAppender.setMaxFileSize("10MB");
            this.logger.addAppender(rollingFileAppender);
        }
    }

    public void info(String msg) {
        this.logger.info(msg);
    }
    public void debug(String msg) {
        this.logger.debug(msg);
    }
    public void error(String msg) {
        this.logger.error(msg);
    }
    public void trace(String msg) {
        this.logger.trace(msg);
    }
    public void warm(String msg) {
        this.logger.warn(msg);
    }
}
