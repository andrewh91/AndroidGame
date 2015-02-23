package com.gmail.andrewahughes.TroopTD;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.gmail.andrewahughes.framework.Game;
import com.gmail.andrewahughes.framework.Graphics;
import com.gmail.andrewahughes.framework.Image;
import com.gmail.andrewahughes.framework.Screen;
import com.gmail.andrewahughes.framework.Input.TouchEvent;

public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;

    // Variable Setup
    // You would create game objects here.

    int livesLeft = 1;
    Paint paint;
    Paint blackText;
    String pointerPos;
    Bullet bullet;
    Command command;
    boolean commandState = true;
    int no =0;
    Rect b;
    public GameScreen(Game game) {
        super(game);

        // Initialize game objects here

        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        
        blackText=new Paint();
        blackText.setColor(Color.BLACK);
        blackText.setTextSize(20);
        
        pointerPos = new String();

        bullet = new Bullet();
        command = new Command();
        b = new Rect(100,100,100,100);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.
        // Depending on the state of the game, we call different update methods.
        // Refer to Unit 3's code. We did a similar thing without separating the
        // update methods.

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        
        // This example starts with a "Ready" screen.
        // When the user touches the screen, the game begins. 
        // state now becomes GameState.Running.
        // Now the updateRunning() method will be called!
        
        if (touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        
        //This is identical to the update() method from our Unit 2/3 game.
       /* if(commandState)
        {
            if(imageX>destX){
            	imageX=(float) (imageX-10.0*deltaTime);
            } 
            if(imageX<destX){
            	imageX=imageX+10;
            }
            if(imageY>destY){
            	imageY=imageY-10;
            }
            if(imageY<destY){
            	imageY=imageY+10;
            }
        }*/
    	//for(int i = 0; i<240;i++){
    	
    	command.update(deltaTime);
		
    	//}
        // 1. All touch input is handled here:
    	
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);

            pointerPos = "x["+event.x+"], y["+event.y+"] \r\n"+"Mode:\r\n select "+command.selected+"\r\nmovement "+command.movementMode+"\r\ncommand "+command.commandState;
            		
            if (event.type == TouchEvent.TOUCH_DOWN) {

            	/*if(no<3)
            	{
            		command.createTroop(event.x,event.y);
            		no++;
            	}
            	else
            	{*/
            	//}


            }

            if (event.type == TouchEvent.TOUCH_UP) {

                command.evaluateTouch(event.x, event.y);
                
            }

            
        }
        
        // 2. Check miscellaneous events like death:
        
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }
        
        
        // 3. Call individual update() methods here.
        // This is where all the game updates happen.
        // For example, robot.update();
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
            	state=GameState.Running;
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 300 && event.x < 980 && event.y > 100
                        && event.y < 500) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

    }

    @Override
    public void paint(float deltaTime) {
        // First draw the game elements.

        // Example:
        // g.drawImage(Assets.background, 0, 0);
        // g.drawImage(Assets.character, characterX, characterY);

        Graphics g = game.getGraphics();
		//g.drawRect(0, 0, 1280, 800, Color.argb(255, 153, 217, 234));//cornflower blue :)
		g.drawARGB(255, 153, 217, 234);//another way to draw a blue background
		
		command.paint(g);
		
        g.drawString(pointerPos,10, 30, blackText);
        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void nullify() {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;

        // Call garbage collector to clean up memory.
        System.gc();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap each side of the screen to move in that direction.",
                640, 300, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(100, 0, 0, 0);
        g.drawString("Tap to resume", 640, 600, paint);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 640, 300, paint);

    }

    @Override
    public void pause() {
        if (state == GameState.Running)
        {    
        	state = GameState.Paused;
        }

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    	command.commandStateToggle();
    }
}