package com.game.src.main.classes;

import java.awt.*;

public interface EntityPlayer {

    public void tick();
    public void render(Graphics g);

    public double getX();
    public double getY();
    public Rectangle getBounds();


    void setVelX(double x);
}
