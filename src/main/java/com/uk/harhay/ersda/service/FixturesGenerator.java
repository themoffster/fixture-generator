package com.uk.harhay.ersda.service;

import com.uk.harhay.ersda.domain.GeneratedFixtures;
import com.uk.harhay.ersda.domain.PitchType;
import com.uk.harhay.ersda.domain.Team;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
abstract class FixturesGenerator {

    abstract GeneratedFixtures doGenerateGroups(List<Team> teams, PitchType pitchType);
}
