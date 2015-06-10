package com.gmail.andrewahughes.TroopTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.gmail.andrewahughes.TroopTD.GameScreen.GameState;
import com.gmail.andrewahughes.framework.Graphics;
public class EnemyUpdate {


	enum interactionState {
		select, // select troops or destinations
		direct, // direct troops
		edit// edit directions for troops
	}

	enum selectionState {
		marquee, // draw a rectangle to select mode
	}

	interactionState state = interactionState.select;
	selectionState selState = selectionState.marquee;

	Paint paint;
	String text;
	
	boolean commandState, selectMode, movementMode, editMode;
	int destX, destY;
	int marqueeOriginX, marqueeOriginY;
	List<Enemy> enemies = new ArrayList<Enemy>();
	// int troopSelected=0;//might need an array instead
	int destinationSelected = 0;
	Rect marqueeRect = new Rect();
	boolean marqueeAlive=false;
	PointF enemyBase = new PointF(400,400);
	
	
	float enemySquadTimer=500,enemyTimer=100;
	int enemySquadSize=0;
	
	public EnemyUpdate() {
		commandState = true;// movement is allowed - not paused
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(20);

		text = new String();
		// make some random enemies

		for(int j=0;j<10;j++)
		{
			enemies.add(new Enemy(100,100));
		}

	}



	public void update(float dt) {
		int len = enemies.size();
		if (commandState) {
			for(int j = 0;j<len;j++)
			{
				if(enemies.get(j).destination.size()>0)
				{
					enemies.get(j).moveTo(dt);
				}
			}
		}
		text = "dt "+dt+" squadtimer "+enemySquadTimer+" enemytimer "+enemyTimer+" enemy squad size "+enemySquadSize+" alive? "+enemies.get(0).alive;
		if(enemySquadTimer<0)
		{

			if(enemySquadSize<5)
			{
				for(int j = 0;j<enemies.size();j++)
				{
					if(enemies.get(j).alive==false)
					{
						//enemies.get(j).position=enemyBase;
						enemies.get(j).addDestination(100, 600);
						enemies.get(j).addDestination(500, 100);
						enemies.get(j).addDestination(1100, 600);
						enemySquadSize++;
						enemies.get(j).alive=true;
						enemyTimer=500;
					
					}
				}
			}
			else
			{			
				for(int j = 0;j<enemies.size();j++)
				{
					enemies.get(j).alive=false;
				}
					
				enemySquadSize=0;
				enemySquadTimer=500;
			}
		}
		
		
		enemySquadTimer-=dt;
		enemyTimer-=dt;
		
		

	}

	public void startMarquee(int x, int y) {
		marqueeRect.left = x;
		marqueeRect.top = y;
		marqueeAlive=true;
	}

	public int getMarqueeSize(int x,int y)
	{
		int largestSize,width,height; 
		width = marqueeRect.left-x;
		if(width<0)
			width=width*-1;
		height = marqueeRect.top-y;
		if(height<0)
			height=height*-1;
		if(width>height)
			largestSize=width;
		else
			largestSize=height;
		return largestSize;
	}
	public void finishMarquee(int x, int y) {
		if(x>marqueeRect.left)
		{
			marqueeRect.right = x;
		}
		else
		{
			marqueeRect.right=marqueeRect.left;
			marqueeRect.left=x;
		}
		if(y>marqueeRect.top)
		{
			marqueeRect.bottom = y;
		}
		else
		{
			marqueeRect.bottom=marqueeRect.top;
			marqueeRect.top=y;
		}
	}

	public void updateMarquee(int x, int y) {
		if (selState == selectionState.marquee) {
			marqueeRect.right = x;
			marqueeRect.bottom = y;
		}
	}



	public Rect getMarqueeRect() {
		return marqueeRect;
	}


	public void commandStateFalse() {
		commandState = false;
	}

	public void commandStateTrue() {
		commandState = true;
	}

	public boolean getCommandState() {
		return commandState;
	}

	public void commandStateToggle() {
		if (commandState == false) {
			commandState = true;
		} else if (commandState == true) {
			commandState = false;
		}
	}

	public void drawMarquee(Graphics g,Point cameraDrag,float zoom) {
		if(marqueeAlive)
		{
			if (selState == selectionState.marquee) {
				g.drawRect((int)(getMarqueeRect().left*zoom+cameraDrag.x),(int)(getMarqueeRect().top*zoom+cameraDrag.y),
						(int)(getMarqueeRect().right*zoom+cameraDrag.x),(int)(getMarqueeRect().bottom*zoom+cameraDrag.y), Color.argb(50, 50, 50, 255));
			}
		}
	}

	public void paint(Graphics graphics, Point camera, float zoom) {

		graphics.drawString(text, 20, 750, paint);
		for(int j = 0; j < enemies.size();j++){
			graphics.drawScaledImage(enemies.get(j).image,(int) (enemies.get(j).position.x*zoom+camera.x),(int) (  enemies.get(j).position.y*zoom+camera.y),enemies.get(j).rectangle.width(),enemies.get(j).rectangle.height(),zoom);
			
			graphics.drawRect(new Rect((int)(enemies.get(j).rectangle.left*zoom + camera.x),
					(int)(enemies.get(j).rectangle.top*zoom + camera.y),
					(int)(enemies.get(j).rectangle.right*zoom + camera.x),
					(int)(enemies.get(j).rectangle.bottom*zoom + camera.y)), Color.argb(100,
					255, 0, 0));
			enemies.get(j).paint(graphics, camera);
		}
	}
}
