package com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration;

/**
 * Created by nickstamp on 10/9/2015.
 */
public enum EnumEquipment {
    BARBELL(1),
    DUMBBELL(2),
    MEDICINE_BALL(3),
    KETTLEBELL(4),
    BODY_ONLY(5);

    private int value;

    private EnumEquipment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case BARBELL:
                return "Barbell";

            case DUMBBELL:
                return "Dumbbells";

            case MEDICINE_BALL:
                return "Medicine Ball";

            case KETTLEBELL:
                return "Kettlebell";

            case BODY_ONLY:
                return "Body Only";

            default:
                return null;
        }
    }

}
