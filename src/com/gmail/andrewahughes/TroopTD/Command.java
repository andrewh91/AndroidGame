package com.gmail.andrewahughes.TroopTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Rect;

import com.gmail.andrewahughes.TroopTD.GameScreen.GameState;
import com.gmail.andrewahughes.framework.Graphics;


public class Command {//this class will contain all the methods to interact with the gameplay, e.g. control troop movement

    enum interactionState {
        select, //select troops or destinations
        direct,//direct troops
        edit//edit directions for troops
    }

    interactionState state = interactionState.select;
    
	boolean commandState,selectMode,movementMode,editMode;
	int destX,destY;
	List<Troop> troops = new ArrayList<Troop>();
	int troopSelected=0;//might need an array instead
	int destinationSelected=0;
	public Command()
	{
		commandState = true;//movement is allowed - not paused
		selectMode = true;//touch selects troops
		movementMode = false;//touch directs troops
		editMode = false;//
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
        if(state==interactionState.select)
        {
			for (int i = 0; i < len; i++) {
				
		        	if(troops.get(i).rectangle.contains(positionX, positionY))
		        	{
		        		state=interactionState.direct;
		        		troopSelected =i;
		        		break;
		        	}
		        for(int j = 0;j<troops.get(i).destination.size();j++)
				{
		        	if(troops.get(i).destination.get(j).rectangle.contains(positionX, positionY))
		        	{
		        		destinationSelected=j;
		        		troopSelected=i;
		        		state=interactionState.edit;
		        	}
				}
	        }	
        }
        else if (state==interactionState.direct)
        {
        	directTo(troopSelected,positionX,positionY);
    		state=interactionState.select;
        }
        else if(state==interactionState.edit){
        	troops.get(troopSelected).editDestination(destinationSelected, positionX, positionY);
    		state=interactionState.select;
        }
       
	}
	public void directTo(int troop,int x,int y)
	{
			//troops.get(troop).setDirection(x, y);//adds a new destination to the troop
		troops.get(troop).addDestination(x, y);
		
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
		else if(commandState==true)
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
    		troops.get(i).paint(graphics);
        }
	}
}
