package no.fintlabs.consumer.model.virksomhet;

import no.fint.model.resource.felles.VirksomhetResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class VirksomhetConfig extends ConsumerConfig<VirksomhetResource> {

    public VirksomhetConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "virksomhet";
    }
}
