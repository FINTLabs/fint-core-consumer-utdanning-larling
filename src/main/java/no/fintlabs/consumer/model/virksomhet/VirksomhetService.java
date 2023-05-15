package no.fintlabs.consumer.model.virksomhet;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.felles.VirksomhetResource;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class VirksomhetService extends CacheService<VirksomhetResource> {

    private final VirksomhetKafkaConsumer virksomhetKafkaConsumer;

    private final VirksomhetLinker linker;

    public VirksomhetService(
            VirksomhetConfig virksomhetConfig,
            CacheManager cacheManager,
            VirksomhetKafkaConsumer virksomhetKafkaConsumer,
            VirksomhetLinker linker) {
        super(virksomhetConfig, cacheManager, virksomhetKafkaConsumer);
        this.virksomhetKafkaConsumer = virksomhetKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<VirksomhetResource> initializeCache(CacheManager cacheManager, ConsumerConfig<VirksomhetResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retension = virksomhetKafkaConsumer.registerListener(VirksomhetResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retension);
    }

    private void addResourceToCache(ConsumerRecord<String, VirksomhetResource> consumerRecord) {
        this.eventLogger.logDataRecieved();
        if (consumerRecord.value() == null) {
            getCache().remove(consumerRecord.key());
        } else {
            VirksomhetResource virksomhetResource = consumerRecord.value();
            linker.mapLinks(virksomhetResource);
            getCache().put(consumerRecord.key(), virksomhetResource, linker.hashCodes(virksomhetResource));
        }
    }

    @Override
    public Optional<VirksomhetResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                resource -> Optional
                        .ofNullable(resource)
                        .map(VirksomhetResource::getVirksomhetsId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false));
    }
}