package no.fintlabs.consumer.model.larling;

import jakarta.annotation.PostConstruct;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.larling.LarlingResource;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LarlingService extends CacheService<LarlingResource> {

    private final LarlingKafkaConsumer elevfravarKafkaConsumer;
    private final LarlingLinker linker;

    public LarlingService(
            LarlingConfig config,
            CacheManager cacheManager,
            LarlingKafkaConsumer kafkaConsumer,
            LarlingLinker linker) {
        super(config, cacheManager, kafkaConsumer);
        this.elevfravarKafkaConsumer = kafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<LarlingResource> initializeCache(CacheManager cacheManager, ConsumerConfig<LarlingResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        elevfravarKafkaConsumer.registerListener(LarlingResource.class, this::addResourceToCache);
    }

    private void addResourceToCache(ConsumerRecord<String, LarlingResource> consumerRecord) {
        updateRetensionTime(consumerRecord.headers().lastHeader("topic-retension-time"));
        this.eventLogger.logDataRecieved();
        if (consumerRecord.value() == null) {
            getCache().remove(consumerRecord.key());
        } else {
            LarlingResource LarlingResource = consumerRecord.value();
            linker.mapLinks(LarlingResource);
            getCache().put(consumerRecord.key(), LarlingResource, linker.hashCodes(LarlingResource));
        }
    }

    @Override
    public Optional<LarlingResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                resource -> Optional
                        .ofNullable(resource)
                        .map(LarlingResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false));
    }
}