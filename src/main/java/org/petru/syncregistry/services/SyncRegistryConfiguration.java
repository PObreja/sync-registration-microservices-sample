package org.petru.syncregistry.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SyncRegistryConfiguration
{
	private static final Log logger = LogFactory
			.getLog(SyncRegistryConfiguration.class);

	public SyncRegistryConfiguration() {
	}
}
