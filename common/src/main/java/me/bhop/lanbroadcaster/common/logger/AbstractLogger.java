package me.bhop.lanbroadcaster.common.logger;

public interface AbstractLogger {
    void info(String string);

    void info(String string, Throwable t);

    void warn(String string, Throwable t);

    void warn(String string);

    void error(String string);

    void error(String string, Throwable t);
}
