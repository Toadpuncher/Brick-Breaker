package com.game.src.main;

import java.awt.*;
import java.io.File;

public class Menu {

    private Game game;

    private enum STATE{
        PLAY,
        SCOREBOARD,
        SETTINGS
    };
    private STATE state;
    private int stateIndex;
    
    Menu(Game game){
        this.game = game;
        stateIndex = 0;
        state = STATE.PLAY;

    }

    public void render(Graphics g){
        state = STATE.values()[stateIndex];
        Font fnt0 = game.mainFont.deriveFont(50f);
        Font fnt1 = game.mainFont.deriveFont(24f);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("BREAKDOWN",Game.WIDTH/2,100);
        g.setFont(fnt1);
        switch (state){
            case PLAY:
                g.setColor(Color.yellow);
                g.drawString("Play",Game.WIDTH/2 + 120,300);
                g.setColor(Color.white);
                g.drawString("Scoreboard",Game.WIDTH/2 + 120,350);
                g.drawString("Settings",Game.WIDTH/2 + 120,400);
                break;
            case SETTINGS:
                g.setColor(Color.yellow);
                g.drawString("Settings",Game.WIDTH/2 + 120,400);
                g.setColor(Color.white);
                g.drawString("Scoreboard",Game.WIDTH/2 + 120,350);
                g.drawString("Play",Game.WIDTH/2 + 120,300);
                break;
            case SCOREBOARD:
                g.setColor(Color.yellow);
                g.drawString("Scoreboard",Game.WIDTH/2 + 120,350);
                g.setColor(Color.white);
                g.drawString("Play",Game.WIDTH/2 + 120,300);
                g.drawString("Settings",Game.WIDTH/2 + 120,400);
                break;
        }

    }
    public boolean indexUpperbound(){
        if(this.stateIndex == 0)
            return true;
        else return false;
    }
    public boolean indexLowerbound(){
        if(this.stateIndex == 2)
            return true;
        else return false;
    }
    public void incIndex(){
        this.stateIndex++;
    }

    public void decIndex(){
        this.stateIndex--;
    }

    public int getStateIndex() {
        return stateIndex;
    }

    public STATE getState() {
        return state;
    }
}
