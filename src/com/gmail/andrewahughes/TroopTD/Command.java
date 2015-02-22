package com.gmail.andrewahughes.TroopTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Rect;

import com.gmail.andrewahughes.framework.Graphics;


public class Command {//this class will contain all the methods to interact with the gameplay, e.g. control troop movement

	boolean commandState,selectMode,movementMode;
	int destX,destY;
	List<Troop> troops = new ArrayList<Troop>();
	int selected;//might need an array instead
	public Command()
	{
		commandState = false;
		selectMode = false;
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
		for (int i = 0; i < len; i++) {
        	if(troops.get(i).rectangle.contains(positionX, positionY))
        	{
        		movementMode=true;
        		selected =i;
        	}
        }	
       
	}
	public void converge(int x,int y,float t,int i)
	{
		if(commandState)
		{
			
	//this is a bad way to move things
	            if(troops.get(i).rectangle.left>x){
	            	troops.get(i).rectangle.left-=10.0*t;
	            } 
	            if(troops.get(i).rectangle.left<x){
	            	troops.get(i).rectangle.left+=10.0*t;
	            } 
	            if(troops.get(i).rectangle.top>y){
	            	troops.get(i).rectangle.top-=10.0*t;
	            } 
	            if(troops.get(i).rectangle.top<y){
	            	troops.get(i).rectangle.top+=10.0*t;
	            } 
			
		}
		
	}
	public void cmndMove(int destinationX,int destinationY)
	{
		destX = destinationX;
		destY = destinationY;
		if(commandState){
			
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
	public void paint(Graphics graphics)
	{
        int len = troops.size();
        for (int i = 0; i < len; i++) {
        	graphics.drawImage(troops.get(i).image, troops.get(i).position);
        }
	}
}
