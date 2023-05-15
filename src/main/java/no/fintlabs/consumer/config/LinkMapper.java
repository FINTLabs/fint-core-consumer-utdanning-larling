package no.fintlabs.consumer.config;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class LinkMapper {

    public static Map<String, String> linkMapper() {
        return ImmutableMap.<String, String>builder()
                .put("no.fint.model.okonomi.regnskap.Virksomhet", "/okonomi/regnskap/virksomhet")
                .put("no.fint.model.okonomi.regnskap.Person", "/okonomi/regnskap/person")
                .put("no.fint.model.utdanning.larling.Larling", "/utdanning/larling/larling")
                .build();
    }

}
