package no.fintlabs.consumer.model.virksomhet;

import no.fint.model.resource.felles.VirksomhetResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class VirksomhetKafkaConsumer extends EntityKafkaConsumer<VirksomhetResource> {
    public VirksomhetKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            VirksomhetConfig virksomhetConfig) {
        super(entityConsumerFactoryService,
                listenerBeanRegistrationService,
                entityTopicService,
                virksomhetConfig);
    }
}
