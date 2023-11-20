package com.uk.harhay.ersda.domain;

import static com.uk.harhay.ersda.domain.Round.HALF_PAST;
import static com.uk.harhay.ersda.domain.Round.ON_THE_HOUR;
import static com.uk.harhay.ersda.domain.Round.QUARTER_PAST;
import static com.uk.harhay.ersda.domain.Round.QUARTER_TO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratedFixtures {

    private Map<Round, Map<Pitch, Fixture>> matches;

    @JsonIgnore
    public List<Team> getHomeTeams() {
        return Stream.of(
                matches.get(ON_THE_HOUR).values().stream()
                        .map(Fixture::getHomeTeam)
                        .distinct()
                        .toList(),
                matches.get(QUARTER_PAST).values().stream()
                        .map(Fixture::getHomeTeam)
                        .distinct()
                        .toList(),
                matches.get(HALF_PAST).values().stream()
                        .map(Fixture::getHomeTeam)
                        .distinct()
                        .toList(),
                matches.get(QUARTER_TO).values().stream()
                        .map(Fixture::getHomeTeam)
                        .distinct()
                        .toList()
        ).flatMap(Collection::stream).filter(Objects::nonNull).distinct().toList();
    }

    @JsonIgnore
    public List<Team> getAwayTeams() {
        return Stream.of(
                matches.get(ON_THE_HOUR).values().stream()
                        .map(Fixture::getAwayTeam)
                        .distinct()
                        .toList(),
                matches.get(QUARTER_PAST).values().stream()
                        .map(Fixture::getAwayTeam)
                        .distinct()
                        .toList(),
                matches.get(HALF_PAST).values().stream()
                        .map(Fixture::getAwayTeam)
                        .distinct()
                        .toList(),
                matches.get(QUARTER_TO).values().stream()
                        .map(Fixture::getAwayTeam)
                        .distinct()
                        .toList()
        ).flatMap(Collection::stream).filter(Objects::nonNull).distinct().toList();
    }

    @JsonIgnore
    public List<Team> getHomeTeams(Pitch pitch) {
        return Stream.of(
                Optional.ofNullable(matches.get(ON_THE_HOUR).get(pitch)).map(Fixture::getHomeTeam).orElse(null),
                Optional.ofNullable(matches.get(QUARTER_PAST).get(pitch)).map(Fixture::getHomeTeam).orElse(null),
                Optional.ofNullable(matches.get(HALF_PAST).get(pitch)).map(Fixture::getHomeTeam).orElse(null),
                Optional.ofNullable(matches.get(QUARTER_TO).get(pitch)).map(Fixture::getHomeTeam).orElse(null)
        ).filter(Objects::nonNull).distinct().toList();
    }
}
