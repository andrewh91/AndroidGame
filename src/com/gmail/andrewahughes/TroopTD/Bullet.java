package com.gmail.andrewahughes.TroopTD;

import com.gmail.andrewahughes.framework.Image;

import android.graphics.Rect;

public class Bullet {

	Rect rectangle;
	Image image;
	int width,height;
	
	public Bullet()//default position
	{
		rectangle=new Rect(100,100,150,150);
		image=Assets.menu;
	}
	public Bullet(Rect r)//specify position and width 
	{
		rectangle = r;
	}
	public Bullet(int x, int y)
	{
		rectangle=new Rect(x,y,x+image.getWidth(),y+image.getHeight());
		image=Assets.menu;
	}
	public Image getImage()
	{
		return image;
	}
	public Rect getRect()
	{
		return rectangle;
	}
}
