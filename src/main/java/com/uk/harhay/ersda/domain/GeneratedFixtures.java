package com.uk.harhay.ersda.domain;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratedFixtures {

    private Map<Round, Map<Pitch, Fixture>> matches;
}
