package com.gmail.andrewahughes.TroopTD;

import android.graphics.PointF;
import android.graphics.Rect;

public class Destination {
	PointF pointF;
	Rect rectangle;
	int size=10;
	
	public Destination(int pointX,int pointY)
	{
		pointF = new PointF(pointX,pointY);
		rectangle = new Rect(pointX-size,pointY-size,pointX+(size*2),pointY+(size*2));//create rect around the point
	}
	public void changeDest(float positionX, float positionY){
		pointF = new PointF(positionX,positionY);
		rectangle = new Rect((int)positionX-size,(int)positionY-size,(int)positionX+(size*2),(int)positionY+(size*2));//create rect around the point
	}

}
