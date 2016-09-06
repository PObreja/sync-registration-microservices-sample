package org.petru.syncregistry.services.messaging;

import java.io.IOException;

import org.apache.camel.ProducerTemplate;
import org.gytheio.messaging.MessageProducer;
import org.gytheio.messaging.camel.CamelMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lucian Tuca
 * @date 06/09/16.
 */
@Configuration public class MessagingConfiguration
{
    public static final String FROM = "direct:start";
    public static final String TO = "seda:integrations";

    @Autowired
    private ProducerTemplate producerTemplate;

    public MessagingConfiguration()
    {
    }

    @Bean public MessageProducer messageProducer() throws IOException
    {
        CamelMessageProducer messageProducer = new CamelMessageProducer();
        messageProducer.setProducer(producerTemplate);
        messageProducer.setEndpoint(FROM);
        return messageProducer;
    }
}
