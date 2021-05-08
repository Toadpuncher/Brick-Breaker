package com.game.src.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage grabPad(int col){
        BufferedImage img = image.getSubimage((col*154)-154,0,154,41);
        return img;
    }
    public BufferedImage grabBrick(int col,int row){
        BufferedImage img = image.getSubimage((col*85)-85,(row*29)-29,85,29);
        return img;
    }
    public BufferedImage grabBall(int col){
        BufferedImage img = image.getSubimage((col*33)-33,0,33,33);
        return img;
    }
}
