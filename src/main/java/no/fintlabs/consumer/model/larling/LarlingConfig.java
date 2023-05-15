package no.fintlabs.consumer.model.larling;

import no.fint.model.resource.utdanning.larling.LarlingResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class ElevfravarConfig extends ConsumerConfig<LarlingResource> {

    public ElevfravarConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "larling";
    }
}
