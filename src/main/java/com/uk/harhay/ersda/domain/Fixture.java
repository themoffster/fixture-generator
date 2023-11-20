package com.uk.harhay.ersda.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Fixture {

    private final Team homeTeam;
    private final Team awayTeam;
}
