package com.gmail.andrewahughes.TroopTD;

import java.util.ArrayList;
import java.util.List;

import com.gmail.andrewahughes.framework.Image;

import android.graphics.PointF;
import android.graphics.Rect;

public class Troop {

	List<PointF> destination = new ArrayList<PointF>();
	PointF position;
	float speed;
	Rect rectangle;
	Image image;
	int margin=5;
	public Troop()
	{
		image = Assets.menu;
		rectangle = new Rect(500,300,500+image.getWidth(),300+image.getHeight());
	}
	public Troop(int posX,int posY)
	{
		position=new PointF(posX, posY);
		speed = 5;
		image = Assets.menu;
		rectangle = new Rect(posX-margin,posY-margin,posX+image.getWidth()+margin,posY+image.getHeight()+margin);
		
	}
	public void setDirection(int posX,int posY)
	{
		destination.add(new PointF(posX,posY));
	}
	public void moveTo(float dt)
	{
		if(position.x<destination.get(0).x)
		{
			position.x+=speed*dt;
		}
		else if(position.x>destination.get(0).x)
		{
			position.x-=speed*dt;
		}
		if(position.y<destination.get(0).y)
		{
			position.y+=speed*dt;
		}
		else if(position.y>destination.get(0).y)
		{
			position.y-=speed*dt;
		}
	}
	public void removeDirection()
	{
		destination.remove(0);
	}
}
