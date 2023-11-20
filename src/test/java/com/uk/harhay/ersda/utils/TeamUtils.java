package com.uk.harhay.ersda.utils;

import com.uk.harhay.ersda.domain.Team;
import java.util.List;
import java.util.stream.IntStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TeamUtils {

    public static List<Team> buildTeams(int numberOfTeams) {
        return IntStream.range(0, numberOfTeams).mapToObj(i -> Team.builder().name("Team " + i).build()).toList();
    }
}
