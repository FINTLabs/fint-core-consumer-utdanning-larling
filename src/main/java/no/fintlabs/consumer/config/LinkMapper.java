package no.fintlabs.consumer.config;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class LinkMapper {

    public static Map<String, String> linkMapper() {
        return ImmutableMap.<String, String>builder()
                .put("no.fint.model.utdanning.larling.Virksomhet", "/utdanning/larling/virksomhet")
                .put("no.fint.model.utdanning.larling.Person", "/utdanning/larling/person")
                .put("no.fint.model.utdanning.larling.Larling", "/utdanning/larling/larling")
                .build();
    }

}
