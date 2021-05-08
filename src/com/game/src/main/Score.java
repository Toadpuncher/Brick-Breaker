package com.game.src.main;

import java.io.Serializable;

public class Score implements Serializable {
    public String name;
    public int score;

    public Score(String name,int score){
        this.name = name;
        this.score = score;
    }
}
