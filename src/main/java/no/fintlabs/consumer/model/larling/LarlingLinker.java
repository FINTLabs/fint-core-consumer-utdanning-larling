package no.fintlabs.consumer.model.larling;


import no.fint.model.resource.utdanning.vurdering.ElevfravarResource;
import no.fint.model.resource.utdanning.vurdering.ElevfravarResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class ElevfravarLinker extends FintLinker<ElevfravarResource> {

    public ElevfravarLinker() {
        super(ElevfravarResource.class);
    }

    public void mapLinks(ElevfravarResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public ElevfravarResources toResources(Collection<ElevfravarResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public ElevfravarResources toResources(Stream<ElevfravarResource> stream, int offset, int size, int totalItems) {
        ElevfravarResources resources = new ElevfravarResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(ElevfravarResource fravar) {
        return getAllSelfHrefs(fravar).findFirst().orElse(null);
    }


    @Override
    public Stream<String> getAllSelfHrefs(ElevfravarResource fravar) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(fravar.getSystemId()) && !StringUtils.isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(fravar.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(ElevfravarResource fravar) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(fravar.getSystemId()) && !StringUtils.isEmpty(fravar.getSystemId().getIdentifikatorverdi())) {
            builder.add(fravar.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}