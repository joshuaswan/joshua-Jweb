package com.joshua.core.core.internal.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.jul.LevelChangePropagator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.joshua.core.config.LoggingConfiguration;
import com.joshua.core.config.util.LogLevel;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by joshua on 2017/7/24.
 */
public class Logging {

    public static void configure(LoggingConfiguration config){
        Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        LoggerContext context = root.getLoggerContext();

        context.reset();

        setupLoggerForJUL(context);

        configureLevels(root,config);

        for (LoggingConfiguration.ConsoleConfiguration consoleConfiguration : config.getConsole().asSet())
            root.addAppender(appender(consoleConfiguration,context));

        for (LoggingConfiguration.FileConfiguration fileConfiguration : config.getFile().asSet())
            root.addAppender(appender(fileConfiguration,context));


    }

    private static void configureLevels(Logger root,LoggingConfiguration configuration){
        root.setLevel(configuration.getLevel().value());
        for (Map.Entry<String,LogLevel> entry : configuration.getLoggers().entrySet())
            ((Logger) LoggerFactory.getLogger(entry.getKey())).setLevel(entry.getValue().value());
    }

    private static Appender<ILoggingEvent> appender(LoggingConfiguration.ConsoleConfiguration consoleConfiguration,LoggerContext context) {
        return configureAppender(new ConsoleAppender<ILoggingEvent>(),consoleConfiguration.getLevel(),context,consoleConfiguration.getFormat(),consoleConfiguration.getTimeZone());
    }

    private static Appender<ILoggingEvent> appender(final LoggingConfiguration.FileConfiguration fileConfiguration,final LoggerContext context) {
        return configureAppender(fileAppender(fileConfiguration,context),fileConfiguration.getLevel(),context,fileConfiguration.getFormat(),fileConfiguration.getTimeZone());
    }

    private static FileAppender<ILoggingEvent> fileAppender(LoggingConfiguration.FileConfiguration fileConfiguration, LoggerContext context){
        return rollingFileAppender(fileConfiguration,context);
    }

    private static FileAppender<ILoggingEvent> rollingFileAppender(LoggingConfiguration.FileConfiguration fileConfiguration,LoggerContext context){
        RollingFileAppender appender = new RollingFileAppender<ILoggingEvent>();
        SizeAndTimeBasedFNATP<ILoggingEvent> triggeringPolicy = new SizeAndTimeBasedFNATP<>();
        if (fileConfiguration.getArchive().isPresent()){
            triggeringPolicy.setMaxFileSize(fileConfiguration.getArchive().get().getMaxFileSize().toString());
        }
        triggeringPolicy.setContext(context);

        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        if (fileConfiguration.getArchive().isPresent()){
            rollingPolicy.setFileNamePattern(fileConfiguration.getArchive().get().getNamePattern());
        }else {
            rollingPolicy.setFileNamePattern(fileConfiguration.getFilename());
        }
        rollingPolicy.setTimeBasedFileNamingAndTriggeringPolicy(triggeringPolicy);
        triggeringPolicy.setTimeBasedRollingPolicy(rollingPolicy);
        if (fileConfiguration.getArchive().isPresent()){
            rollingPolicy.setMaxHistory(fileConfiguration.getArchive().get().getMaxHistory());
            rollingPolicy.setCleanHistoryOnStart(fileConfiguration.getArchive().get().getCleanHistoryOnStart());
        }
        appender.setRollingPolicy(rollingPolicy);
        appender.setTriggeringPolicy(triggeringPolicy);

        rollingPolicy.setParent(appender);
        rollingPolicy.start();
        return appender;
    }

    private static Function<LoggingConfiguration.FileConfiguration.ArchiveConfiguration,FileAppender<ILoggingEvent>> rollingFileAppender(final LoggerContext context){
        return new  Function< LoggingConfiguration.FileConfiguration.ArchiveConfiguration,FileAppender<ILoggingEvent>>(){

            @Nullable
            @Override
            public FileAppender<ILoggingEvent> apply(@Nullable LoggingConfiguration.FileConfiguration.ArchiveConfiguration input) {
                RollingFileAppender appender = new RollingFileAppender<ILoggingEvent>();
                SizeAndTimeBasedFNATP<ILoggingEvent> triggeringPolicy = new SizeAndTimeBasedFNATP<>();
                triggeringPolicy.setMaxFileSize(input.getMaxFileSize().toString());
                triggeringPolicy.setContext(context);

                TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
                rollingPolicy.setContext(context);
                rollingPolicy.setFileNamePattern(input.getNamePattern());
                rollingPolicy.setTimeBasedFileNamingAndTriggeringPolicy(triggeringPolicy);
                triggeringPolicy.setTimeBasedRollingPolicy(rollingPolicy);
                rollingPolicy.setMaxHistory(input.getMaxHistory());

                appender.setRollingPolicy(rollingPolicy);
                appender.setTriggeringPolicy(triggeringPolicy);

                rollingPolicy.setParent(appender);
                rollingPolicy.start();
                return appender;
            }
        };
    }

    private static <T extends OutputStreamAppender<ILoggingEvent>> T configureAppender(T appender,LogLevel level,LoggerContext context,Optional<String> format,TimeZone timeZone){
        appender.setContext(context);
        appender.setEncoder(encoder(context,format,timeZone));
        appender.addFilter(levelFilter(level));
        appender.start();
        return appender;
    }

    private static ThresholdFilter levelFilter(LogLevel level){
        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel(level.toString());
        filter.start();
        return filter;
    }

    private static PatternLayoutEncoder encoder(LoggerContext context,Optional<String> format,TimeZone timeZone){
        PatternLayoutEncoder layoutEncoder = new PatternLayoutEncoder();
        layoutEncoder.setContext(context);
        layoutEncoder.setPattern(format
                .or("%-5p [%d{ISO8601," + timeZone.getID() + "}][%thread] %c: %m\n%rEx"));
        layoutEncoder.start();
        return layoutEncoder;
    }

    private static void setupLoggerForJUL(LoggerContext context){
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        LevelChangePropagator propagator = new LevelChangePropagator();
        propagator.setContext(context);
        propagator.setResetJUL(true);
        propagator.start();
        context.addListener(propagator);
    }
}
