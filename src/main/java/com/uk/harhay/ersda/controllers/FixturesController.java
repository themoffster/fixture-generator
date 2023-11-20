package com.uk.harhay.ersda.controllers;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.uk.harhay.ersda.domain.GeneratedFixtures;
import com.uk.harhay.ersda.domain.Team;
import com.uk.harhay.ersda.service.FoursFixtureGenerator;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/fixtures", produces = APPLICATION_JSON_VALUE)
public class FixturesController {

    private final FoursFixtureGenerator foursFixtureGenerator;

    @GetMapping(value = "/4s/generate/test")
    public ResponseEntity<String> test() {
        return ok("GeneratedFixtures.builder().build())");
    }

    @PostMapping(value = "/4s/generate", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeneratedFixtures>> generateFixtures(@RequestBody @Size(min = 8, max = 16, message = "There must be between 8 and 16 teams") List<Team> teams) {
        return ok(foursFixtureGenerator.generateFixtures(teams));
    }
}