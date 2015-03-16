package com.gmail.andrewahughes.TroopTD;

import com.gmail.andrewahughes.framework.Graphics;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Button {

	Rect rectangle;
	int size = 70;
	String stringOn="on";
	String stringOff="off";
	String string="btn";
	boolean on=false;
	int colour= new Color().DKGRAY;;
	public Button()
	{
		
	}
	public Button(int x, int y)
	{
		rectangle = new Rect(x, y, x+size, y+size);
	}
	public Button(int x, int y,String sOn,String sOff, boolean initial)
	{
		rectangle = new Rect(x, y, x+size, y+size);
		stringOn=sOn;
		stringOff=sOff;
		on=initial;
		if(on)
		{
			colour = new Color().YELLOW;
			string=stringOn;
		}
		else
		{
			colour = new Color().DKGRAY;
			string=stringOff;
		}
	}
	public void setString(String sOn,String sOff)
	{
		stringOn=sOn;
		stringOff=sOff;
	}
	public void toggle()
	{
		if(on==false)
		{
			colour = new Color().YELLOW;
			on=true;
			string=stringOn;
		}
		else
		{
			colour = new Color().DKGRAY;
			on=false;
			string=stringOff;
		}
	}	
	public void paint(Graphics graphics,Paint paint)
	{
        graphics.drawRect(rectangle, colour);
        graphics.drawString(string, rectangle.centerX(), rectangle.centerY(), paint);
	}
}