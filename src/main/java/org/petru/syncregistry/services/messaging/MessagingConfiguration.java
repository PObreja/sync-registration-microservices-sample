package org.petru.syncregistry.services.messaging;

import java.io.IOException;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.amqp.AMQPConnectionDetails;
import org.gytheio.messaging.MessageProducer;
import org.gytheio.messaging.camel.CamelMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lucian Tuca
 * @date 06/09/16.
 */
@Configuration
public class MessagingConfiguration
{
    @Value("${messaging.events.start.endpoint}")
    public String START_ENDPOINT;

    @Value("${messaging.events.queue.endpoint}")
    public String QUEUE_ENDOINT;

    @Value("${messaging.broker.url}")
    private String messagingBrokerUrl;

    @Autowired
    private ProducerTemplate producerTemplate;

    public MessagingConfiguration()
    {
    }

    @Bean public MessageProducer messageProducer() throws IOException
    {
        CamelMessageProducer messageProducer = new CamelMessageProducer();
        messageProducer.setProducer(producerTemplate);
        messageProducer.setEndpoint(START_ENDPOINT);
        return messageProducer;
    }

    @Bean public AMQPConnectionDetails amqpConnection()
    {
        return new AMQPConnectionDetails(messagingBrokerUrl);
    }
}
