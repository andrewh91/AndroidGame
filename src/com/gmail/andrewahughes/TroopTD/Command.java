package com.gmail.andrewahughes.TroopTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Rect;

import com.gmail.andrewahughes.framework.Graphics;


public class Command {//this class will contain all the methods to interact with the gameplay, e.g. control troop movement

	boolean commandState,selectMode,movementMode;
	int destX,destY;
	List<Troop> troops = new ArrayList<Troop>();
	int selected=0;//might need an array instead
	public Command()
	{
		commandState = true;
		selectMode = true;
		movementMode = false;
		//make some random troops
		for(int i = 0; i<20;i++){
			Random n;
			n = new Random();
			troops.add( new Troop((int)(1280*n.nextDouble()),(int)(800*n.nextDouble())));
		}
		
	}
	public void evaluateTouch(int positionX, int positionY)
	{
        int len = troops.size();
        if(selectMode)
        {
			for (int i = 0; i < len; i++) {
	        	if(troops.get(i).rectangle.contains(positionX, positionY))
	        	{
	        		movementMode=true;
	        		selectMode=false;
	        		selected =i;
	        		break;
	        	}
	        }	
        }
        else if (movementMode)
        {
        	directTo(selected,positionX,positionY);
        	movementMode=false;
        	selectMode=true;
        }
       
	}
	public void directTo(int troop,int x,int y)
	{
			troops.get(troop).setDirection(x, y);//adds a new destination to the troop
		
	}
	public void update(float dt)
	{

        int len = troops.size();
        if(commandState){
        for (int i = 0; i < len; i++) {
	        if(troops.get(i).destination.size()>0)
	        	{
	        		troops.get(i).moveTo(dt);
	        	}
	        }	
        }
	}
	public void createTroop()
	{
		troops.add( new Troop());
	}
	public void createTroop(int positionX,int positionY)
	{
		troops.add( new Troop(positionX,positionY));
	}
	public void commandStateFalse()
	{
		commandState = false;
	}
	public void commandStateTrue()
	{
		commandState = true;
	}
	public boolean getCommandState()
	{
		return commandState;
	}
	public void commandStateToggle()
	{
		if(commandState==false)
		{
			commandState=true;
		}
		if(commandState==true)
		{
			commandState=false;
		}
	}
	public void paint(Graphics graphics)
	{
        int len = troops.size();
        for (int i = 0; i < len; i++) {
        	graphics.drawImage(troops.get(i).image, troops.get(i).position);
    		graphics.drawRect(troops.get(i).rectangle, Color.argb(100,255,0,0));
        }
	}
}
