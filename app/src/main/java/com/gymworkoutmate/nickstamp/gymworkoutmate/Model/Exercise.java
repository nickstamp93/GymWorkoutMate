package com.gymworkoutmate.nickstamp.gymworkoutmate.Model;

import android.database.Cursor;

import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumEquipment;
import com.gymworkoutmate.nickstamp.gymworkoutmate.Enumeration.EnumMuscleGroups;

/**
 * Created by nickstamp on 10/9/2015.
 */
public class Exercise {

    private int id, img1, img2, mechanics;
    private String title;
    private EnumMuscleGroups muscle;
    private EnumEquipment equipment;
    private String other_muscles;

    public Exercise(Cursor cursor) {
        id = cursor.getInt(0);
        title = cursor.getString(1);
        img1 = cursor.getInt(2);
        img2 = cursor.getInt(3);
        muscle = EnumMuscleGroups.valueOf(cursor.getInt(4));
        mechanics = cursor.getInt(5);
        equipment = EnumEquipment.valueOf(cursor.getInt(6));
    }

    public Exercise(String title, int image1, int image2, EnumMuscleGroups muscle,
                    String other_muscles, int mechanics, EnumEquipment equipment) {
        this.title = title;
        img1 = image1;
        img2 = image2;
        this.muscle = muscle;
        this.other_muscles = other_muscles;
        this.mechanics = mechanics;
        this.equipment = equipment;
    }


    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public int getMechanics() {
        return mechanics;
    }

    public void setMechanics(int mechanics) {
        this.mechanics = mechanics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EnumMuscleGroups getMuscle() {
        return muscle;
    }

    public void setMuscle(EnumMuscleGroups muscle) {
        this.muscle = muscle;
    }

    public EnumEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(EnumEquipment equipment) {
        this.equipment = equipment;
    }

    public String getOther_muscles() {
        return other_muscles;
    }

    public void setOther_muscles(String other_muscles) {
        this.other_muscles = other_muscles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
