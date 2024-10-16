package me.bhop.lanbroadcaster.common.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JavaLogger implements AbstractLogger {
    private final Logger logger;

    public JavaLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String string) {
        logger.info(string);
    }

    @Override
    public void info(String string, Throwable t) {
        logger.log(Level.INFO, string, t);
    }

    @Override
    public void warn(String string, Throwable t) {
        logger.log(Level.WARNING, string, t);
    }

    @Override
    public void warn(String string) {
        logger.log(Level.WARNING, string);
    }

    @Override
    public void error(String string) {
        logger.log(Level.SEVERE, string);
    }

    @Override
    public void error(String string, Throwable t) {
        logger.log(Level.SEVERE, string, t);
    }
    
}
