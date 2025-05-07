package de.voidstack_overload.cardgame.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardLogger{
    private final Logger logger;
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public StandardLogger() {
        Class<?> caller = WALKER.getCallerClass();
        this.logger = LoggerFactory.getLogger(caller.getSimpleName());
    }

    public StandardLogger(String name) {
        this.logger = LoggerFactory.getLogger(name);
    }

    public void log(String log) {
        this.logger.info(log);
    }

    public void log(String log, Object... args) {
        this.logger.info(log, args);
    }

    public void error(String error) {
        this.logger.error(error);
    }

    public void error(String error, Object... args) {
        this.logger.error(error, args);
    }

    public void error(String error, Throwable ex) {
        this.logger.error(error, ex);
    }

    public void error(String error, Throwable ex, Object... args) {
        this.logger.error(error, ex, args);
    }

    public void debug(String log) {
        this.logger.debug(log);
    }

    public void debug(String log, Object... args) {
        this.logger.debug(log, args);
    }
}
