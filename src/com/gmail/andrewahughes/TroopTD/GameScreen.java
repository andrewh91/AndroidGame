package com.gmail.andrewahughes.TroopTD;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.gmail.andrewahughes.TroopTD.Command.selectionState;
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
	boolean cameraMode = false;
	//boolean cameraMode = true;
	int no = 0;
	Point cameraOrigin;
	Point cameraDrag;
	PointF zoomOrigin, zoomDrag, zoomDrag2, finger1, finger2;
	float zoomScaleInitial = 1, zoomPinchDistanceInitial, zoomPinchDistance,
			zoomIncrease=1, zoomScale = 1,zoomScale2=1;
	//Button selectBtn;//don't need cos marquee select can also tap select
	//Button cameraBtn;//don't need cos can control camera with two fingers

	// Rect b,c;
	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		blackText = new Paint();
		blackText.setColor(Color.BLACK);
		blackText.setTextSize(20);

		pointerPos = new String();

		bullet = new Bullet();
		command = new Command();
		cameraOrigin = new Point();
		cameraDrag = new Point(0, 0);
		finger1 = new PointF(0, 0);
		finger2 = new PointF(0, 0);

		zoomOrigin = new PointF(1,1); //zoomDrag, zoomDrag2, finger1, finger2;
		//selectBtn = new Button(1200, 10, "Select", "Marquee Select", true);
		//cameraBtn = new Button(1200, 90, "Camera", "No camera", true);
		// b = new Rect(100,100,100,100);
		// c = new Rect();
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

		command.update(deltaTime);
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			//pointerPos = "touch "+event.x+","+event.y+"zoomOrigin "+zoomOrigin+"zoomDrag"+zoomDrag+"zoomScale"+zoomScale+"distinit"+zoomPinchDistanceInitial+"dist"+zoomPinchDistance;

			//pointerPos = "f1 "+finger1+"f2 "+finger2+"pointer "+event.pointer+"type "+event.type+"initial dist "+zoomPinchDistanceInitial+"dist "+zoomPinchDistance+"scale "+zoomScale+"increase "+zoomIncrease;
			//pointerPos="pos "+event.x+" "+event.y+" fin "+finger1+" fin2 "+finger2+" dist init "+zoomPinchDistanceInitial+" dist "+zoomPinchDistance+" "+zoomIncrease+" "+zoomScale+" "+zoomOrigin;
			pointerPos="dist "+zoomPinchDistance+" distinit "+zoomPinchDistanceInitial+" z1 "+zoomScale+" z2 "+zoomScale2+" p1 "+finger1+" p2 "+finger2;
			if (event.type == TouchEvent.TOUCH_DOWN) {
				// button logic
				// if we touch a button do nothing, but if touch up event is
				// also on button then press button
				/*if (selectBtn.rectangle.contains(event.x, event.y)) {

				} else if (cameraBtn.rectangle.contains(event.x, event.y)) {

				} else */// if no button is pressed
				if (event.pointer>0) //if two fingers down
				{
					
				}
				else
				{
				
			
					command.startMarquee(event.x - cameraDrag.x, event.y
							- cameraDrag.y);
					/*
					 * if(no<3) { command.createTroop(event.x,event.y); no++; }
					 * else {
					 */
					// }

				}
			}

			if (event.type == TouchEvent.TOUCH_UP) {

				// button logic
				// if we touch a button do nothing, but if touch up event is
				// also on button then press button
				/*if (selectBtn.rectangle.contains(event.x, event.y)) {
					selectBtn.toggle();
					command.toggleSelState();
				} else if (cameraBtn.rectangle.contains(event.x, event.y)) {
					cameraBtn.toggle();
					if (cameraMode) {
						cameraMode = false;
					} else if (cameraMode == false) {
						cameraMode = true;
					}
				} else*/ {
					command.evaluateTouch(event.x - cameraDrag.x, event.y
							- cameraDrag.y);
				}
				zoomScaleInitial=zoomScale;
				cameraMode=false;
				

			}
			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				// c.left=event.x;
				// c.right=event.x;
				// c.top=event.y;
				// c.bottom=event.y;
				cameraControl1(event);
				
				if (event.pointer>0) {
					if(cameraMode==false)
					{
						cameraControlInitiate(event);	
					}
					cameraControl(event);
				} else {

					command.updateMarquee(event.x - cameraDrag.x, event.y
							- cameraDrag.y);
				}
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
				state = GameState.Running;
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
		// g.drawRect(0, 0, 1280, 800, Color.argb(255, 153, 217,
		// 234));//cornflower blue :)
		g.drawARGB(255, 153, 217, 234);// another way to draw a blue background

		command.paint(g, cameraDrag,zoomOrigin,zoomScale,zoomScale2);

		g.drawString(pointerPos, 10, 30, blackText);
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

		command.drawMarquee(g, cameraDrag);
		//selectBtn.paint(g, paint);
		//cameraBtn.paint(g, paint);
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
		if (state == GameState.Running) {
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
		// pause();
		command.commandStateToggle();
	}
	public void cameraControlInitiate(TouchEvent event)
	{
			//zoomOrigin = new PointF(event.x, event.y);
	
		cameraMode=true;
		if(event.pointer==0)
			{
				finger1=new PointF(event.x,event.y);
			}
		if(event.pointer==1)
		{
			finger2=new PointF(event.x,event.y);
		}

		cameraOrigin = new Point((int)(finger1.x+finger2.x)/2 - cameraDrag.x,(int) (finger1.y+finger2.y)/2
				- cameraDrag.y);
		zoomOrigin=new PointF((finger1.x+finger2.x)/2,(finger1.y+finger2.y)/2);
		zoomPinchDistanceInitial=0;
		if(event.pointer>0)
		{
			command.storeOffSet();
			//zoomScale=zoomScale2;
			zoomScale2=1;
			zoomPinchDistance = 		(float) Math.sqrt(((finger1.x-finger2.x)*(finger1.x-finger2.x))+((finger1.y-finger2.y)*(finger1.y-finger2.y)));//find the length of the vector
			zoomPinchDistanceInitial = 	(float) Math.sqrt(((finger1.x-finger2.x)*(finger1.x-finger2.x))+((finger1.y-finger2.y)*(finger1.y-finger2.y)));//find the length of the vector
		}
		
	}
	public void cameraControl1(TouchEvent event)
	{
		if(event.pointer==0)
		{
			finger1 = new PointF(event.x,event.y);
		}
	}
	public void cameraControl(TouchEvent event)
	{
	if(event.pointer==1)
	{
		finger2 = new PointF(event.x,event.y);
	}
	if(event.pointer>0)
	{
		zoomPinchDistance = (float) Math.sqrt(((finger1.x-finger2.x)*(finger1.x-finger2.x))+((finger1.y-finger2.y)*(finger1.y-finger2.y)));//find the length of the vector
		if(zoomPinchDistanceInitial!=0)
		{
			zoomIncrease=zoomScaleInitial*(zoomPinchDistance/zoomPinchDistanceInitial);			
			zoomScale = zoomIncrease;
			zoomScale2=zoomPinchDistance/zoomPinchDistanceInitial;
		}
	}
	
	cameraDrag = new Point(	(int) ((finger1.x+finger2.x)/2 - cameraOrigin.x),
							(int) ((finger1.y+finger2.y)/2 - cameraOrigin.y));
	}
}