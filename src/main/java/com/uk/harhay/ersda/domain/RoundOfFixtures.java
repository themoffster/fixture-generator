package com.uk.harhay.ersda.domain;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoundOfFixtures {

    private Map<Pitch, Fixture> fixtureMap;
}
