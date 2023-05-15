package no.fintlabs.consumer.model.larling;


import no.fint.model.resource.utdanning.larling.LarlingResource;
import no.fint.model.resource.utdanning.larling.LarlingResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class LarlingLinker extends FintLinker<LarlingResource> {

    public LarlingLinker() {
        super(LarlingResource.class);
    }

    public void mapLinks(LarlingResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public LarlingResources toResources(Collection<LarlingResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public LarlingResources toResources(Stream<LarlingResource> stream, int offset, int size, int totalItems) {
        LarlingResources resources = new LarlingResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(LarlingResource resource) {
        return getAllSelfHrefs(resource).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(LarlingResource resource) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(resource.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(LarlingResource resource) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(resource.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}