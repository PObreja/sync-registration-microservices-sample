package org.petru.syncregitry.services;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SyncRegitryConfiguration {
    private static final Log logger = LogFactory.getLog(SyncRegitryConfiguration.class);

    public SyncRegitryConfiguration()
    {
    }
}
