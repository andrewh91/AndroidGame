package com.gmail.andrewahughes.framework;

public abstract class Screen {
	protected final Game game;
	
	public Screen(Game game){
		this.game = game;//this.game is the game we created outside this method
	}
	public abstract void update(float deltaTime);
	
	public abstract void paint(float deltaTime);

	public abstract void pause();
	
	public abstract void resume();

	public abstract void dispose();
	
	public abstract void backButton();


}
