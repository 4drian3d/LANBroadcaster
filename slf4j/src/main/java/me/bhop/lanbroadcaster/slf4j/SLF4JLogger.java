package me.bhop.lanbroadcaster.slf4j;

import org.slf4j.Logger;

import me.bhop.lanbroadcaster.common.logger.AbstractLogger;

public class SLF4JLogger implements AbstractLogger {
    private final Logger logger;

    public SLF4JLogger(Logger logger) {
        this.logger = logger;
    }
    @Override
    public void info(String string) {
        logger.info(string);
    }

    @Override
    public void info(String string, Throwable t) {
        logger.info(string, t);
    }

    @Override
    public void warn(String string, Throwable t) {
        logger.warn(string, t);
    }

    @Override
    public void warn(String string) {
        logger.warn(string);
    }

    @Override
    public void error(String string) {
        logger.error(string);
    }

    @Override
    public void error(String string, Throwable t) {
        logger.error(string, t);
    }
      
}
