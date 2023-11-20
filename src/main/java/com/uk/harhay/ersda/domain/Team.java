package com.uk.harhay.ersda.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Team {

    private String name;
    private boolean fakeTeam;

    public static Team fakeTeam() {
        return Team.builder()
                .fakeTeam(true)
                .name("free slot")
                .build();
    }
}
