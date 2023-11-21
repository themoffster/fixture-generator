package com.uk.harhay.ersda.service;

import static com.uk.harhay.ersda.domain.Pitch.PITCH_1;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_2;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_3;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_4;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_5;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_6;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_7;
import static com.uk.harhay.ersda.domain.Pitch.PITCH_8;
import static com.uk.harhay.ersda.domain.Team.fakeTeam;
import static com.uk.harhay.ersda.utils.GeneratedFixtureUtils.getAwayTeams;
import static com.uk.harhay.ersda.utils.GeneratedFixtureUtils.getHomeTeams;
import static com.uk.harhay.ersda.utils.TeamUtils.buildTeams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.uk.harhay.ersda.domain.Fixture;
import com.uk.harhay.ersda.domain.GeneratedFixtures;
import com.uk.harhay.ersda.domain.Team;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FoursFixtureGeneratorTest {

    private final FoursFixtureGenerator generator = new FoursFixtureGenerator();

    @Test
    void shouldGenerateFixturesFor8Teams() {
        var teams = buildTeams(8);
        var generatedFixtures = generator.generateFixtures(teams);
        assertNotNull(generatedFixtures);
        assertEquals(1, generatedFixtures.size());
        assertAllMatchesOnOddPitches(generatedFixtures.get(0));
        assertNumberOfFixturesForEachTeam(generatedFixtures, teams, 4);
        assertSameTeamsDontPlayEachOtherMoreThanOnce(getAllFixtures(generatedFixtures));
        assertHomeTeamsAreAlwaysHomeTeams(generatedFixtures);
    }

    @Test
    void shouldGenerateFixturesFor13Teams() {
        var teams = buildTeams(13);
//        var expectedThreeGames = teams.stream()
//                .filter(e -> List.of("Team 8", "Team 9").contains(e.getName()))
//                .toList();
//        var expectedFourGames = teams.stream()
//                .filter(e -> !List.of("Team 8", "Team 9").contains(e.getName()))
//                .filter(Team::isFakeTeam)
//                .toList();

        var generatedFixtures = generator.generateFixtures(teams);

        assertNotNull(generatedFixtures);
        assertEquals(2, generatedFixtures.size());
        assertAllMatchesOnOddPitches(generatedFixtures.get(0));
        assertAllMatchesOnEvenPitches(generatedFixtures.get(1));
//        assertNumberOfFixturesForEachTeam(generatedFixtures, expectedThreeGames, 3);
//        assertNumberOfFixturesForEachTeam(generatedFixtures, expectedFourGames, 4);
        assertSameTeamsDontPlayEachOtherMoreThanOnce(getAllFixtures(generatedFixtures));
        assertHomeTeamsAreAlwaysHomeTeams(generatedFixtures);
        assertNoFakeTeams(generatedFixtures.get(0));
        assertFakeTeamsAreAwayTeams(generatedFixtures.get(1));
    }

    @Test
    void shouldGenerateFixturesFor14Teams() {
        var teams = buildTeams(14);
        var generatedFixtures = generator.generateFixtures(teams);
        assertNotNull(generatedFixtures);
        assertEquals(2, generatedFixtures.size());
        assertAllMatchesOnOddPitches(generatedFixtures.get(0));
        assertAllMatchesOnEvenPitches(generatedFixtures.get(1));
        assertNumberOfFixturesForEachTeam(generatedFixtures, teams, 4);
    }

    @Test
    void shouldGenerateFixturesFor15Teams() {
        var teams = buildTeams(15);
        var expectedThreeGames = teams.stream()
                .filter(e -> List.of("Team 8", "Team 9", "Team 10").contains(e.getName()))
                .toList();
        var expectedFourGames = teams.stream()
                .filter(e -> !List.of("Team 8", "Team 9", "Team 10").contains(e.getName()))
                .filter(Team::isFakeTeam)
                .toList();

        var generatedFixtures = generator.generateFixtures(teams);

        assertNotNull(generatedFixtures);
        assertEquals(2, generatedFixtures.size());
        assertAllMatchesOnOddPitches(generatedFixtures.get(0));
        assertAllMatchesOnEvenPitches(generatedFixtures.get(1));
        assertNumberOfFixturesForEachTeam(generatedFixtures, expectedThreeGames, 3);
        assertNumberOfFixturesForEachTeam(generatedFixtures, expectedFourGames, 4);
        assertSameTeamsDontPlayEachOtherMoreThanOnce(getAllFixtures(generatedFixtures));
        assertHomeTeamsAreAlwaysHomeTeams(generatedFixtures);
        assertNoFakeTeams(generatedFixtures.get(0));
        assertFakeTeamsAreAwayTeams(generatedFixtures.get(1));
    }

    @Test
    void shouldGenerateFixturesFor16Teams() {
        var teams = buildTeams(16);
        var generatedFixtures = generator.generateFixtures(teams);
        assertNotNull(generatedFixtures);
        assertEquals(2, generatedFixtures.size());
        assertAllMatchesOnOddPitches(generatedFixtures.get(0));
        assertAllMatchesOnEvenPitches(generatedFixtures.get(1));
        assertNumberOfFixturesForEachTeam(generatedFixtures, teams, 4);
        assertSameTeamsDontPlayEachOtherMoreThanOnce(getAllFixtures(generatedFixtures));
        assertHomeTeamsAreAlwaysHomeTeams(generatedFixtures);
    }

    private void assertNoFakeTeams(GeneratedFixtures generatedFixtures) {
        assertTrue(getHomeTeams(generatedFixtures.getMatches()).stream().noneMatch(Team::isFakeTeam));
        assertTrue(getAwayTeams(generatedFixtures.getMatches()).stream().noneMatch(Team::isFakeTeam));
    }

    private void assertFakeTeamsAreAwayTeams(GeneratedFixtures generatedFixtures) {
        assertTrue(getHomeTeams(generatedFixtures.getMatches()).stream().noneMatch(Team::isFakeTeam));
        assertTrue(getAwayTeams(generatedFixtures.getMatches()).stream().anyMatch(Team::isFakeTeam));
    }

    private void assertAllMatchesOnOddPitches(GeneratedFixtures generatedFixtures) {
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_1).size() > 0);
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_3).size() > 0);
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_5).size() > 0);
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_7).size() > 0);
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_2).size());
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_4).size());
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_6).size());
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_8).size());
    }

    private void assertAllMatchesOnEvenPitches(GeneratedFixtures generatedFixtures) {
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_2).size() > 0);
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_4).size() > 0);
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_6).size() > 0);
        assertTrue(getHomeTeams(generatedFixtures.getMatches(), PITCH_8).size() > 0);
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_1).size());
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_3).size());
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_5).size());
        assertEquals(0, getHomeTeams(generatedFixtures.getMatches(), PITCH_7).size());
    }

    private void assertNumberOfFixturesForEachTeam(List<GeneratedFixtures> generatedFixtures, List<Team> teams, int numberOfMatches) {
        teams.forEach(team -> {
            var count = getAllFixtures(generatedFixtures).stream()
                    .filter(e -> e.getHomeTeam().equals(team) && !e.getAwayTeam().equals(fakeTeam())
                            || e.getAwayTeam().equals(team) && !e.getHomeTeam().equals(fakeTeam()))
                    .count();
            assertEquals(numberOfMatches, count);
        });
    }

    private void assertSameTeamsDontPlayEachOtherMoreThanOnce(List<Fixture> allFixtures) {
        var filteredMatches = allFixtures.stream()
                .filter(fixture -> !fixture.getHomeTeam().isFakeTeam())
                .filter(fixture -> !fixture.getAwayTeam().isFakeTeam())
                .toList();
        var numberOfMatches = (long) filteredMatches.size();
        var distinctMatches = filteredMatches.stream().distinct().count();
        assertEquals(numberOfMatches, distinctMatches);
    }

    private void assertHomeTeamsAreAlwaysHomeTeams(List<GeneratedFixtures> generatedFixtures) {
        generatedFixtures.forEach(fixtures -> {
            var homeTeams = getHomeTeams(fixtures.getMatches());
            var awayTeams = getAwayTeams(fixtures.getMatches());
            assertFalse(homeTeams.stream().anyMatch(awayTeams::contains));
        });
    }

    private List<Fixture> getAllFixtures(List<GeneratedFixtures> generatedFixtures) {
        return generatedFixtures.stream()
                .map(GeneratedFixtures::getMatches)
                .map(Map::values)
                .toList()
                .stream()
                .flatMap(Collection::stream)
                .map(Map::values)
                .flatMap(Collection::stream)
                .toList();
    }
}