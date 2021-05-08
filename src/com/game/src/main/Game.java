package com.game.src.main;

import com.game.src.main.classes.EntityBall;
import com.game.src.main.classes.EntityBricks;
import com.game.src.main.classes.EntityPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable {

   public static final int WIDTH = 425; // 10 cigala stane
   public static final int HEIGHT = WIDTH / 12* 9;
   public static final int SCALE = 2;
   public final String TITLE = "Brick Breaker";

   private boolean running = false;
   private Thread thread;

   private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
   private BufferedImage playerSprite = null;
   private BufferedImage brickSprite = null;
   private BufferedImage ballSprite = null;


   private Player p;
   private Ball b;
   private Controller c;
   private Textures tex;
   private Menu menu;
   private Level level;
   public Font mainFont;
   private Endscreen endscreen;
   private Scoreboard scoreboard;

   private int velocity = 3;
   private int score = 0;
   private int blockCount ;
   private int blockBreaken = 0;
   private boolean gameLost = false;
   private boolean gameWon = false;
   private int selectedChar = 1;

   public LinkedList<EntityBricks> entityBricks;
   public EntityBall entityBall;
   public EntityPlayer entityPlayer;

   private enum STATE{
      MENU,
      SETTINGS,
      GAME,
      SCOREBOARD
   };

   private STATE state = STATE.MENU;

   public void init(){
      BufferedImageLoader loader = new BufferedImageLoader();
      String Path = new File("res/newFont.ttf").getAbsolutePath();
      try{
         InputStream fontStream = new BufferedInputStream(new FileInputStream(Path));
         mainFont = Font.createFont(Font.TRUETYPE_FONT,fontStream);

         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File(Path)));

      }
      catch (IOException | FontFormatException e){
         e.printStackTrace();

      }

      try{
         playerSprite = loader.loadImage("/pads.png");
         brickSprite = loader.loadImage("/brick_sprites_small.png");
         ballSprite = loader.loadImage("/balls.png");
      }catch (IOException e){
         e.printStackTrace();
      }
      addKeyListener(new KeyInput(this));

      scoreboard = new Scoreboard(this);
      level = new Level(this);
      tex = new Textures(this);
      menu = new Menu(this);
      endscreen = new Endscreen(this);
      c = new Controller(this,tex,level);
      c.addPlayer(new Player(this.WIDTH-154/2,this.HEIGHT*2-20-41,tex,this));
      entityPlayer = c.getEntityPlayer();
      c.addBall(new Ball(entityPlayer.getX()+154/2,entityPlayer.getY()-33,tex,this));
      entityBricks = c.getBricksEntity();
      entityBall = c.getEntityBall();
      blockCount = this.level.getBrickCount();
      scoreboard.loadScoreFile();
      scoreboard.Sort();
   }

   private synchronized void start(){
      if(running)
         return;
      running = true;
      thread = new Thread(this);
      thread.start();
   }

   private synchronized void stop(){
      if(!running)
         return;

      running = false;
      try {
         thread.join();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      System.exit(1);
   }

   public void run(){
      init();
      long lastTime = System.nanoTime();
      final double amountOfTicks = 60.0;
      double ns = 1e9 / amountOfTicks;
      double delta = 0;
      int updates = 0;
      int frames = 0;
      long timer = System.currentTimeMillis();
      while(running){
         long now = System.nanoTime();
         delta += (now - lastTime) / ns;
         lastTime = now;
         if(delta >= 1) {
            tick();
            updates++;
            delta--;
         }
         render();
         frames++;

         if(System.currentTimeMillis() - timer > 1000){
            timer += 1000;
            System.out.println(updates + "Ticks, fps "+ frames);
            updates = 0;
            frames = 0;
         }
      }
      stop();
   }
   private void tick(){
      if(state == STATE.GAME) {
         c.tick();
         if(blockBreaken >= blockCount){
            gameWon = true;
            endscreen.setState(2); // Victory Endscreen
            c.ballStop();
         }
         if(gameLost){
            c.ballStop();
            endscreen.setState(1); // Defeat Screen
         }
      }
   }

   private void render(){
      Toolkit.getDefaultToolkit().sync();
      BufferStrategy bs = this.getBufferStrategy();
      if(bs == null){

         createBufferStrategy(3);
         return;
      }

      Graphics g = bs.getDrawGraphics();
      ////////// crtanje ispod ////////////////

      g.drawImage(image,0,0,getWidth(),getHeight(),this);
//      b.render(g);
      if(state == STATE.GAME) {
         c.render(g);
         endscreen.render(g);
      }
      if(state == STATE.MENU){
         menu.render(g);
      }

      if(state == STATE.SCOREBOARD){
         scoreboard.render(g);
      }
      /////////////////////////////////////////
      g.dispose();
      bs.show();

   }

   public void keyPressed(KeyEvent e){
      int key = e.getKeyCode();
      if(state == STATE.GAME) {
         if (key == KeyEvent.VK_RIGHT) {
            entityPlayer.setVelX(5);
         } else if (key == KeyEvent.VK_LEFT) {
            entityPlayer.setVelX(-5);
         } else if (key == KeyEvent.VK_DOWN) {

         } else if (key == KeyEvent.VK_UP) {

         } else if (key == KeyEvent.VK_SPACE) {
            if(gameWon){
               blockBreaken = 0;
               score += 50;
               c.resetBall();
               if(level.isLastLevel()){
                  level.resetIndex();
                  score += 150;
                  velocity++;
               }else {
                  level.incIndex();
               }
               blockCount = level.getBrickCount();
               level.changeLevel(level.getCurrentLevelIndex());
               c.addLevel();
               endscreen.setState(0);
               gameWon = false;
            } else if(gameLost){
               gameLost = false;
               c.clearBricks();
               blockBreaken = 0;
               velocity = 3;
               level.setCurrentLevelIndex(1);
               level.changeLevel(level.getCurrentLevelIndex());
               blockCount = level.getBrickCount();
               endscreen.setState(0);
               c.addLevel();
               this.state = STATE.SCOREBOARD;
               scoreboard.setState(1);
            } else
               c.ballStart(velocity);
         }
      }
      if(state == STATE.SCOREBOARD){
         if(scoreboard.getStateIndex() == 0)
            if(key == KeyEvent.VK_SPACE)
               this.state = STATE.MENU;
            if(scoreboard.getStateIndex() == 1){
              if(key == KeyEvent.VK_LEFT) {
                 if (selectedChar > 1)
                    selectedChar = selectedChar - 1;
              }
              else if(key == KeyEvent.VK_RIGHT) {
                 if (selectedChar < 3)
                    selectedChar = selectedChar + 1;
              } else if (key == KeyEvent.VK_UP){
                 scoreboard.incCharNum(selectedChar);
              } else if (key == KeyEvent.VK_DOWN) {
                 scoreboard.decCharNum(selectedChar);
              }
              else if (key == KeyEvent.VK_ENTER){
                 scoreboard.addScore(scoreboard.getChars(),this.score);
                 scoreboard.setState(0);
                 this.score = 0;
                 selectedChar = 0;
              }
            }
      }

      if(state == STATE.MENU){
         if (key == KeyEvent.VK_DOWN) {
            if(!menu.indexLowerbound())
               menu.incIndex();
//            menu.setState(menu.getStateIndex());
         } else if (key == KeyEvent.VK_UP) {
            if(!menu.indexUpperbound())
               menu.decIndex();
 //           menu.setState(menu.getStateIndex());

         } else if (key == KeyEvent.VK_ENTER) {
            switch (menu.getStateIndex()){
               case 0:
                  this.state = STATE.GAME;
                  break;
               case 1:
                  this.state = STATE.SCOREBOARD;
                  break;
               case 2:
                  break;
            }
         }

      }
   }

   public void keyReleased(KeyEvent e){
      int key = e.getKeyCode();
      if(state == STATE.GAME) {
         if (key == KeyEvent.VK_RIGHT) {
            entityPlayer.setVelX(0);
         } else if (key == KeyEvent.VK_LEFT) {
            entityPlayer.setVelX(0);
         }
      }
   }
   public static void main(String[] args){
      Game game = new Game();

      game.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
      game.setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
      game.setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));

      JFrame frame = new JFrame(game.TITLE);
      frame.add(game);
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);

      game.start();
   }

   public BufferedImage getPlayerSprite(){
      return playerSprite;
   }
   public BufferedImage getBallSprite(){ return ballSprite;}
   public BufferedImage getBrickSprite(){ return brickSprite;}

   public int getBlockBreaken() {
      return blockBreaken;
   }

   public void setBlockBreaken(int blockBreaken) {
      this.blockBreaken = blockBreaken;
   }

   public void loseGame(){
      gameLost = true;
   }

   public void incScore(){
      this.score += 5;
   }

   public int getScore() {
      return score;
   }
}
