package no.fintlabs.consumer.model.larling;

import no.fint.model.resource.utdanning.larling.LarlingResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class LarlingKafkaConsumer extends EntityKafkaConsumer<LarlingResource> {
    public LarlingKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            LarlingConfig config) {
        super(entityConsumerFactoryService,
                listenerBeanRegistrationService,
                entityTopicService,
                config);
    }
}
