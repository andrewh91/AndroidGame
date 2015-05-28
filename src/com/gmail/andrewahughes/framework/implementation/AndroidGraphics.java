package com.gmail.andrewahughes.framework.implementation;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.gmail.andrewahughes.framework.Graphics;
import com.gmail.andrewahughes.framework.Image;

public class AndroidGraphics implements Graphics {
	AssetManager assets;
	Bitmap frameBuffer;
	Canvas canvas;
	Paint paint;
	Rect srcRect = new Rect();
	Rect dstRect = new Rect();
	
	public AndroidGraphics(AssetManager assets, Bitmap frameBuffer){
		this.assets=assets;
		this.frameBuffer = frameBuffer;
		this.canvas = new Canvas(frameBuffer);
		this.paint = new Paint();
	}
	@Override
	public Image newImage(String fileName,ImageFormat format){
		Config config = null;
		if(format == ImageFormat.RGB565)
			config = Config.RGB_565;
		else if (format == ImageFormat.ARGB4444)
			config = Config.ARGB_4444;
		else
			config = Config.ARGB_8888;
		
		Options options = new Options();
		options.inPreferredConfig=config;
		
		InputStream in = null;
		Bitmap bitmap = null;
		try{
			in=assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(in,null,options);
			if(bitmap ==null)
			{
				throw new RuntimeException("Couldn't load bitmap from asset '"+fileName + "'");
			}
			}
		catch(IOException e){
			throw new RuntimeException("Couldn't load bitmap from asset '"+fileName+"'");
					
			}
		finally{
			if (in !=null){
				try{
					in.close();
				}
				catch(IOException e){
					
				}
			}
		}
		if(bitmap.getConfig()==Config.RGB_565)
		{
			format =ImageFormat.RGB565;
		}
		else if (bitmap.getConfig()==Config.ARGB_4444)
		{
			format = ImageFormat.ARGB4444;
		}
		else 
		{
			format = ImageFormat.ARGB8888;
		}
		return new AndroidImage(bitmap,format);
	}
	@Override
	public void clearScreen(int color){
		canvas.drawRGB((color&0xff0000)>>16,(color&0xff00)>>8,(color&0xff));		
	}
	@Override
	public void drawLine(int x, int y, int x2, int y2, int color){
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
	}
	@Override
	public void drawRect(int x,int y, int width, int height, int color){
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x,y,width,height,paint);
	}
	@Override
	public void drawRect(Rect rectangle, int color){
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(rectangle,paint);
	}
	@Override
	public void drawRect(PointF centre,int width,int height,float scale,PointF camera, int color){
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect( new Rect(
				(int)((centre.x-(width/2))*scale+camera.x),
				(int)((centre.y-(height/2))*scale+camera.y),
				(int)((centre.x+(width/2))*scale+camera.x),
				(int)((centre.y+(height/2))*scale+camera.y)),
				paint);
	}
	@Override
	public void drawARGB(int a, int r, int g, int b ){
		paint.setStyle(Style.FILL);
		canvas.drawARGB(a, r, g, b);
	}
	@Override
	public void drawString(String text, int x, int y, Paint paint){
		canvas.drawText(text, x, y, paint);
	}
	public void drawImage(Image Image, int x, int y, int srcX, int srcY,int srcWidth,int srcHeight){
		srcRect.left=srcX;
		srcRect.top=srcY;
		srcRect.right=srcX+srcWidth;
		srcRect.bottom=srcY+srcHeight;
		
		dstRect.left=x;
		dstRect.top=y;
		dstRect.right=x+srcWidth;
		dstRect.bottom=y+srcHeight;
		
		canvas.drawBitmap(((AndroidImage) Image).bitmap, srcRect, dstRect,null);
	}

	@Override
	public void drawImage(Image Image,int x, int y){
		canvas.drawBitmap(((AndroidImage)Image).bitmap,x,y,null);
	}
	@Override
	public void drawImage(Image Image,PointF p){
		canvas.drawBitmap(((AndroidImage)Image).bitmap,p.x,p.y,null);
	}
	public void drawImageCentred(Image Image,int x, int y){
		canvas.drawBitmap(((AndroidImage)Image).bitmap,x-(Image.getWidth()/2),y-(Image.getHeight()/2),null);
	}
	
	public void drawScaledImage(Image Image,int x,int y, int width, int height,/* int srcX,int srcY, int srcWidth, int srcHeight,*/ float scale/*,int originX, int originY*/){
		/*srcRect.left=srcX;
		srcRect.top=srcY;
		srcRect.right=srcX+srcWidth;
		srcRect.bottom=srcY+srcHeight;*/

		srcRect.left=0;
		srcRect.top=0;
		srcRect.right=Image.getWidth();
		srcRect.bottom=Image.getHeight();
		
		dstRect.left= (int) (x);
		dstRect.top=(int) (((y)));
		dstRect.right=(int) (((x+(width*scale))));
		dstRect.bottom=(int) (((y+(height*scale))));	
		/*
		dstRect.left= (int) (originX+((x-originX)*scale));
		dstRect.top=(int) (originY+((y-originY)*scale));
		dstRect.right=(int) (originX+((x+width-originX)*scale));
		dstRect.bottom=(int) (originY+((y+height-originY)*scale));*/
		
		canvas.drawBitmap(((AndroidImage)Image).bitmap,srcRect,dstRect,null);
	}

	@Override 
	public int getWidth(){
		return frameBuffer.getWidth();
	}
	@Override
	public int getHeight(){
		return frameBuffer.getHeight();
	}
}
