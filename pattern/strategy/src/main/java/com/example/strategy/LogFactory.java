package com.example.strategy;

enum LogType {
    FILE("file"), DB("db"), CONSOLE("console");


    private String logType;

    LogType(String logType) {
        this.logType = logType;
    }

    public static LogType getByLogType(String logType) {
        for (LogType e : LogType.values()) {
            if (e.logType.equals(logType)) {
                return e;
            }
        }
        return null;
    }

}

public class LogFactory {

    private static final ILog fileLog;
    private static final ILog consoleLog;
    private static final ILog dbLog;
    private static final LogFactory logFactory = new LogFactory();

    static {
        fileLog = new FileLog();
        consoleLog = new ConsoleLog();
        dbLog = new DBLog();
    }

    private LogFactory() {

    }

    public static LogFactory getInstance() {
        return logFactory;
    }

    public ILog getLogger(String logType) {
        LogType logTypeEnum = LogType.getByLogType(logType);
        if (logTypeEnum == null) {
            return null;
        }
        switch (logTypeEnum) {
            case DB:
                return dbLog;
            case FILE:
                return fileLog;
            case CONSOLE:
                return consoleLog;
            default:
                return null;
        }
    }
}
