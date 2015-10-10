package com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration;

/**
 * Created by nickstamp on 10/9/2015.
 */
public enum EnumMuscleGroups {
    CHEST(1),
    BACK(2),
    SHOULDERS(3),
    BICEPS(4),
    TRICEPS(5),
    LEGS(6),
    ABS(7);

    private int value;

    private EnumMuscleGroups(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case CHEST:
                return "Chest";

            case BACK:
                return "Back";

            case SHOULDERS:
                return "Shoulders";

            case BICEPS:
                return "Biceps";

            case TRICEPS:
                return "Triceps";

            case LEGS:
                return "Legs";

            case ABS:
                return "Abs";

            default:
                return null;
        }
    }
}
