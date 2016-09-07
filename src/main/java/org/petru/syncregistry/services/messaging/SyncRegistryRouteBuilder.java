package org.petru.syncregistry.services.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lucian Tuca
 * @date 06/09/16.
 */
@Component public class SyncRegistryRouteBuilder extends RouteBuilder
{
    @Autowired
    SyncRegistryDefinitionProcessor syncRegistryDefinitionProcessor;

    @Autowired
    MessagingConfiguration messagingConfiguration;

    @Override public void configure() throws Exception
    {
        from(messagingConfiguration.START_ENDPOINT)
            .process(syncRegistryDefinitionProcessor)
            .to(messagingConfiguration.QUEUE_ENDOINT);
    }
}
