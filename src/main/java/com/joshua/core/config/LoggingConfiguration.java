package com.joshua.core.config;

import com.joshua.core.config.builder.Builder;
import org.apache.commons.logging.LogConfigurationException;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlType;
import java.util.TimeZone;

/**
 * Created by joshua on 2017/7/14.
 */

@XmlType
public class LoggingConfiguration {
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    @XmlType
    public static class ConsoleConfiguration{

    }

    @XmlType
    public static class FileConfiguration{

    }

    public static class LoggingConfigurationBuilder implements Builder<LogConfigurationException>{

        @Override
        public LogConfigurationException build() {
            return null;
        }
    }
}
