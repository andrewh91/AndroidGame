package com.gmail.andrewahughes.framework;

public interface Music {

	public void play();
	
	public void stop();
	
	public void pause();
	
	public void setLooping(boolean setLooping);
	
	public void setVolume(float volume);
	
	public boolean isPlaying();
	
	public boolean isStopped();
	
	public boolean isLooping();
	
	public void dispose();
	
	public void seekBegin();
}
