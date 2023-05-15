package no.fintlabs.consumer.model.virksomhet;

import no.fint.model.resource.okonomi.regnskap.VirksomhetResource;
import no.fint.model.resource.okonomi.regnskap.VirksomhetResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class VirksomhetLinker extends FintLinker<VirksomhetResource> {

    public VirksomhetLinker() {
        super(VirksomhetResource.class);
    }

    public void mapLinks(VirksomhetResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public VirksomhetResources toResources(Collection<VirksomhetResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public VirksomhetResources toResources(Stream<VirksomhetResource> stream, int offset, int size, int totalItems) {
        VirksomhetResources resources = new VirksomhetResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(VirksomhetResource virksomhet) {
        return getAllSelfHrefs(virksomhet).findFirst().orElse(null);
    }


    @Override
    public Stream<String> getAllSelfHrefs(VirksomhetResource virksomhet) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(virksomhet.getSystemId()) && !StringUtils.isEmpty(virksomhet.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(virksomhet.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(VirksomhetResource virksomhet) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(virksomhet.getSystemId()) && !StringUtils.isEmpty(virksomhet.getSystemId().getIdentifikatorverdi())) {
            builder.add(virksomhet.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}