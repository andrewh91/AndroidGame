package com.gmail.andrewahughes.TroopTD;

import java.util.List;

import com.gmail.andrewahughes.framework.Image;

import android.graphics.PointF;
import android.graphics.Rect;

public class Troop {

	List<PointF> destination;
	PointF position;
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
		image = Assets.menu;
		rectangle = new Rect(posX-margin,posY-margin,posX+image.getWidth()+margin,posY+image.getHeight()+margin);
	}
}
