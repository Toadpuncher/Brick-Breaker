package com.game.src.main;

import java.awt.*;

public class Endscreen {

    Game game;
    private enum STATE{
        NONE,
        DEFEAT,
        VICTORY,
    }

    private STATE state;
    private int stateIndex;

    Endscreen(Game game){
        this.game = game;
        stateIndex = 0;
        state = STATE.NONE;

    }
    public void render(Graphics g){
        state = STATE.values()[stateIndex];
        Font fnt0 = game.mainFont.deriveFont(50f);
        Font fnt1 = game.mainFont.deriveFont(24f);
        switch (state) {
            case NONE:
                break;
            case DEFEAT:
                g.setFont(fnt0);
                g.setColor(Color.red);
                g.drawString("DEFEAT", Game.WIDTH / 2, 100);
                g.setFont(fnt1);
                g.setColor(Color.red);
                g.drawString("Press space to continiue to main menu", Game.WIDTH / 2 + 120, 350);
                break;
            case VICTORY:
                g.setFont(fnt0);
                g.setColor(Color.GREEN);
                g.drawString("VICTORY", Game.WIDTH / 2, 100);
                g.setFont(fnt1);
                g.setColor(Color.GREEN);
                g.drawString("Press space to continiue", Game.WIDTH / 2 + 120, 350);
                break;
        }
        g.setFont(fnt1);
        g.setColor(Color.white);
        g.drawString("Score: "+ game.getScore(), Game.WIDTH - 125,25);
    }

    public int getStateIndex() {
        return stateIndex;
    }
    public void setState(int state) {
        this.stateIndex = state;
    }


    public STATE getState() {
        return state;
    }
}
