package com.gmail.andrewahughes.framework;

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public interface Graphics {
	public static enum ImageFormat{
		ARGB8888, ARGB4444, RGB565
	}
	
	public Image newImage(String fileName, ImageFormat format);
	
	public void clearScreen(int color);
	
	public void drawLine(int x, int y, int x2, int y2, int color);
	
	public void drawRect(int x, int y, int width, int height, int color);
	
	public void drawRect(Rect rectangle, int color);
	
	public void drawImage(Image image,int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
	
	public void drawImage(Image image, int x, int y);
	
	void drawImage(Image Image, PointF p);
	
	public void drawImageCentred(Image image, int i, int j);
	
	void drawString(String text, int x, int y, Paint paint);
	
	public int getWidth();
	
	public int getHeight();
	
	public void drawARGB(int i, int j,int k, int l);



}
