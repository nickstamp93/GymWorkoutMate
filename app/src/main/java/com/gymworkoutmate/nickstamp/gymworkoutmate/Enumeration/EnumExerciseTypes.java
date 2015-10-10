package com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration;

/**
 * Created by nickstamp on 10/10/2015.
 */
public enum EnumExerciseTypes {
    MUSCLE_GROWTH(1),
    STRENGTH(2),
    STAMINA(3),
    FAT_LOSS(4),
    RIPPING(5),
    FITNESS(6);

    private int value;

    private EnumExerciseTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case MUSCLE_GROWTH:
                return "Muscle Growth";

            case STRENGTH:
                return "Strength";

            case STAMINA:
                return "Stamina";

            case FAT_LOSS:
                return "Fat Loss";

            case RIPPING:
                return "Ripping";

            case FITNESS:
                return "Fitness";

            default:
                return null;
        }
    }
}
