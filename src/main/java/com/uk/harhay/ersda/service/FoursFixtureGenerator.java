package com.uk.harhay.ersda.service;

import static com.uk.harhay.ersda.domain.Pitch.PITCH_1;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_2;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_3;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_4;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_5;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_6;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_7;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_8;
import static com.uk.harhay.ersda.domain.PitchType.EVEN;
import static com.uk.harhay.ersda.domain.PitchType.ODD;
import static com.uk.harhay.ersda.domain.Round.HALF_PAST;
import static com.uk.harhay.ersda.domain.Round.ON_THE_HOUR;
import static com.uk.harhay.ersda.domain.Round.QUARTER_PAST;
import static com.uk.harhay.ersda.domain.Round.QUARTER_TO;
import static com.uk.harhay.ersda.domain.Team.fakeTeam;

import com.google.common.collect.Lists;
import com.uk.harhay.ersda.domain.Fixture;
import com.uk.harhay.ersda.domain.GeneratedFixtures;
import com.uk.harhay.ersda.domain.Pitch;
import com.uk.harhay.ersda.domain.PitchType;
import com.uk.harhay.ersda.domain.Round;
import com.uk.harhay.ersda.domain.Team;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

@Service
public class FoursFixtureGenerator extends FixturesGenerator {

    public List<GeneratedFixtures> generateFixtures(List<Team> teams) {
        var partitionSize = (int) Math.ceil((double) teams.size() / 2);
        var partitions = Lists.partition(teams, partitionSize % 2 == 0 ? partitionSize : partitionSize + 1);
        var list = new ArrayList<GeneratedFixtures>();
        for (int i = 0; i < partitions.size(); i++) {
            list.add(doGenerateGroups(partitions.get(i), i == 0 ? ODD : EVEN));
        }
        return list;
    }

    @Override
    GeneratedFixtures doGenerateGroups(List<Team> teams, PitchType pitchType) {
        var homeTeams = teams.subList(0, (int) Math.ceil((double) teams.size() / 2));
        var awayTeams = new LinkedList<>(teams.stream()
                .filter(Predicate.not(homeTeams::contains))
                .toList());
        for (int i = 0; i < homeTeams.size() - awayTeams.size(); i++) {
            awayTeams.add(fakeTeam());
        }
        var firstFixtures = buildFixtureMap(homeTeams, awayTeams, ON_THE_HOUR, pitchType);
        var secondFixtures = buildFixtureMap(homeTeams, awayTeams, QUARTER_PAST, pitchType);
        var thirdFixtures = buildFixtureMap(homeTeams, awayTeams, HALF_PAST, pitchType);
        var fourthFixtures = buildFixtureMap(homeTeams, awayTeams, QUARTER_TO, pitchType);

        return GeneratedFixtures.builder()
                .matches(Map.of(
                        ON_THE_HOUR, firstFixtures,
                        QUARTER_PAST, secondFixtures,
                        HALF_PAST, thirdFixtures,
                        QUARTER_TO, fourthFixtures
                ))
                .build();
    }

    private Map<Pitch, Fixture> buildFixtureMap(List<Team> homeTeams, List<Team> awayTeams, Round roundOfFixtures, PitchType pitchType) {
        if (roundOfFixtures != ON_THE_HOUR) {
            var removedTeam = awayTeams.remove(0);
            awayTeams.add(awayTeams.size(), removedTeam);
        }
        return buildFixtureMap(homeTeams, awayTeams, pitchType);
    }

    private Map<Pitch, Fixture> buildFixtureMap(List<Team> homeTeams, List<Team> awayTeams, PitchType pitchType) {
        return Map.of(
                pitchType == ODD ? PITCH_1 : PITCH_2, new Fixture(getTeam(homeTeams, 0), getTeam(awayTeams, 0)),
                pitchType == ODD ? PITCH_3 : PITCH_4, new Fixture(getTeam(homeTeams, 1), getTeam(awayTeams, 1)),
                pitchType == ODD ? PITCH_5 : PITCH_6, new Fixture(getTeam(homeTeams, 2), getTeam(awayTeams, 2)),
                pitchType == ODD ? PITCH_7 : PITCH_8, new Fixture(getTeam(homeTeams, 3), getTeam(awayTeams, 3))
        );
    }

    private Team getTeam(List<Team> teams, int i) {
        try {
            return teams.get(i);
        } catch (IndexOutOfBoundsException e) {
            return fakeTeam();
        }
    }
}
