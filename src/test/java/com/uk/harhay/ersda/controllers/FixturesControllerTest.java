package com.uk.harhay.ersda.controllers;

import static com.uk.harhay.ersda.domain.Pitch.PITCH_1;
import static com.uk.harhay.ersda.domain.Round.ON_THE_HOUR;
import static com.uk.harhay.ersda.utils.FileUtils.fileToString;
import static com.uk.harhay.ersda.utils.TeamUtils.buildTeams;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uk.harhay.ersda.controllers.advice.ExceptionControllerAdvice;
import com.uk.harhay.ersda.domain.Fixture;
import com.uk.harhay.ersda.domain.GeneratedFixtures;
import com.uk.harhay.ersda.service.FoursFixtureGenerator;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@Import(ExceptionControllerAdvice.class)
@WebMvcTest(value = FixturesController.class)
class FixturesControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FoursFixtureGenerator mockFoursFixtureGenerator;

    @Test
    void shouldCreateFoursFixtures() throws Exception {
        var teams = buildTeams(8);
        var fixtures = List.of(GeneratedFixtures.builder()
                .matches(Map.of(ON_THE_HOUR, Map.of(PITCH_1, new Fixture(teams.get(0), teams.get(1)))))
                .build());
        when(mockFoursFixtureGenerator.generateFixtures(teams)).thenReturn(fixtures);

        mvc.perform(post("/fixtures/4s/generate")
                        .content(mapper.writeValueAsString(teams))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(fileToString("4s-fixtures.json", getClass()), true));
    }

    @Test
    void shouldReturnBadRequestGeneratingFoursFixturesForLessThanEightTeams() throws Exception {
        mvc.perform(post("/fixtures/4s/generate")
                        .content(mapper.writeValueAsString(buildTeams(7)))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .string("{\"code\":400,\"message\":\"generateFixtures.teams: There must be between 8 and 16 teams\"}"));

        verifyNoInteractions(mockFoursFixtureGenerator);
    }

    @Test
    void shouldReturnBadRequestGeneratingFoursFixturesForMoreThanSixteenTeams() throws Exception {
        mvc.perform(post("/fixtures/4s/generate")
                        .content(mapper.writeValueAsString(buildTeams(17)))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content()
                                .string("{\"code\":400,\"message\":\"generateFixtures.teams: There must be between 8 and 16 teams\"}"));

        verifyNoInteractions(mockFoursFixtureGenerator);
    }
}
