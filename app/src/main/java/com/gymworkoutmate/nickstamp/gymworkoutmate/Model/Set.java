package com.gymworkoutmate.nickstamp.gymworkoutmate.Model;

import java.io.Serializable;

/**
 * Created by nickstamp on 10/9/2015.
 */
public class Set implements Serializable {
    private int weight;

    public Set(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
