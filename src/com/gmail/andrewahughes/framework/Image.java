package com.gmail.andrewahughes.framework;

import com.gmail.andrewahughes.framework.Graphics.ImageFormat;

public interface Image {
	public int getWidth();
	public int getHeight();
	public ImageFormat getFormat();
	public void dispose();
}
