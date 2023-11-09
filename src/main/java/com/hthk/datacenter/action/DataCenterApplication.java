package com.hthk.datacenter.action;

import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.config.FintechStaticData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/8 17:08
 */
@SpringBootApplication(scanBasePackages = "com.hthk")
public class DataCenterApplication {

    private final static Logger logger = LoggerFactory.getLogger(DataCenterApplication.class);

    private void start(String[] args) {

        String configPath =
                new StringBuilder("spring.config.location=")
                        .append("classpath:/")
                        .append(FintechStaticData.DEFAULT_CLASS_PATH)
                        .append("/")
                        .toString();

        String appName = null;
        try {
            SpringApplicationBuilder builder = new SpringApplicationBuilder(DataCenterApplication.class)
                    .properties(configPath);
            builder.run(args);
            logger.info("{} kick-off", builder.context().getBean(AppConfig.class).getAppName());
        } catch (Throwable e) {
            logger.error("{} exit with Exception:\r\n{}", appName, e.getMessage(), e);
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        new DataCenterApplication().start(args);
    }

}
