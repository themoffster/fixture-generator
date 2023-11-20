package com.uk.harhay.ersda.domain;

public enum Pitch {

    PITCH_1,
    PITCH_2,
    PITCH_3,
    PITCH_4,
    PITCH_5,
    PITCH_6,
    PITCH_7,
    PITCH_8;

    public int getPitchNumber() {
        return Integer.parseInt(name().replaceAll("PITCH_", ""));
    }
}
