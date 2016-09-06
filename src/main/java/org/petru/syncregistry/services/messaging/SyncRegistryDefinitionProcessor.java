package org.petru.syncregistry.services.messaging;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.petru.syncregistry.services.SyncRegistryDefinition;
import org.springframework.stereotype.Component;

/**
 * @author Lucian Tuca
 * @date 06/09/16.
 */
@Component
public class SyncRegistryDefinitionProcessor implements Processor
{

    private static final String PROCESSED = "processed";

    @Override public void process(Exchange exchange) throws Exception
    {
        SyncRegistryDefinition syncRegistryDefinition = exchange.getIn()
            .getBody(SyncRegistryDefinition.class);

        syncRegistryDefinition
            .setFirstSystemItem(syncRegistryDefinition.getFirstSystemItem() + PROCESSED);
        syncRegistryDefinition
            .setSecondSystemItem(syncRegistryDefinition.getSecondSystemItem() + PROCESSED);

        System.out.println("Done processing!");

        exchange.getOut().setBody(syncRegistryDefinition, SyncRegistryDefinition.class);
    }
}
