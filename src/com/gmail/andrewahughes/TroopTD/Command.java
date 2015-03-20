package com.gmail.andrewahughes.TroopTD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;

import com.gmail.andrewahughes.TroopTD.GameScreen.GameState;
import com.gmail.andrewahughes.framework.Graphics;

public class Command {// this class will contain all the methods to interact
						// with the gameplay, e.g. control troop movement

	enum interactionState {
		select, // select troops or destinations
		direct, // direct troops
		edit// edit directions for troops
	}

	enum selectionState {
		tap, // normal tap to select mode
		marquee, // draw a rectangle to select mode
	}

	interactionState state = interactionState.select;
	selectionState selState = selectionState.tap;

	boolean commandState, selectMode, movementMode, editMode;
	int destX, destY;
	int marqueeOriginX, marqueeOriginY;
	List<Troop> troops = new ArrayList<Troop>();
	List<Integer> troopSelected = new ArrayList<Integer>();
	// int troopSelected=0;//might need an array instead
	int destinationSelected = 0;
	Rect marqueeRect = new Rect();

	public Command() {
		commandState = true;// movement is allowed - not paused

		// make some random troops
		for (int i = 0; i < 20; i++) {
			Random n;
			n = new Random();
			troops.add(new Troop((int) (1280 * n.nextDouble()), (int) (800 * n
					.nextDouble())));
		}

	}

	public void evaluateTouch(int positionX, int positionY) {
		int len = troops.size();
		if (state == interactionState.select) {
			if (selState == selectionState.tap) {
				for (int i = 0; i < len; i++) {

					if (troops.get(i).rectangle.contains(positionX, positionY)) {
						state = interactionState.direct;
						troopSelected.clear();
						troopSelected.add(i);
						break;
					}
					for (int j = 0; j < troops.get(i).destination.size(); j++) {
						if (troops.get(i).destination.get(j).rectangle
								.contains(positionX, positionY)) {
							destinationSelected = j;
							troopSelected.clear();
							troopSelected.add(i);
							state = interactionState.edit;
						}
					}
				}
			} else if (selState == selectionState.marquee) {
				finishMarquee(positionX, positionY);
				troopSelected.clear();
				for (int i = 0; i < len; i++) {
					if (marqueeRect.contains(troops.get(i).rectangle)) {
						state = interactionState.direct;
						troopSelected.add(i);
					}
				}
			}
		}

		else if (state == interactionState.direct) {
			directTo(troopSelected, positionX, positionY);
			state = interactionState.select;
		} else if (state == interactionState.edit) {
			for (int i = 0; i < troopSelected.size(); i++) {
				troops.get(troopSelected.get(i)).editDestination(
						destinationSelected, positionX, positionY);
			}
			state = interactionState.select;
		}

	}

	public void directTo(List<Integer> troop, int x, int y) {
		// troops.get(troop).setDirection(x, y);//adds a new destination to the
		// troop
		for (int i = 0; i < troop.size(); i++) {
			troops.get(troop.get(i)).addDestination(x, y);
		}

	}

	public void update(float dt) {

		int len = troops.size();
		if (commandState) {
			for (int i = 0; i < len; i++) {
				if (troops.get(i).destination.size() > 0) {
					troops.get(i).moveTo(dt);
				}
			}
		}
	}

	public void startMarquee(int x, int y) {
		marqueeRect.left = x;
		marqueeRect.top = y;
	}

	public void finishMarquee(int x, int y) {
		if(x>marqueeRect.left)
		{
			marqueeRect.right = x;
		}
		else
		{
			marqueeRect.right=marqueeRect.left;
			marqueeRect.left=x;
		}
		if(y>marqueeRect.top)
		{
			marqueeRect.bottom = y;
		}
		else
		{
			marqueeRect.bottom=marqueeRect.top;
			marqueeRect.top=y;
		}
	}

	public void updateMarquee(int x, int y) {
		if (selState == selectionState.marquee) {
			marqueeRect.right = x;
			marqueeRect.bottom = y;
		}
	}

	public void toggleSelState() {
		if (selState == selectionState.marquee) {
			selState = selectionState.tap;
		} else if (selState == selectionState.tap) {
			selState = selectionState.marquee;
		}
	}

	public void toggleCameraState() {
		if (selState == selectionState.marquee) {
			selState = selectionState.tap;
		} else if (selState == selectionState.tap) {
			selState = selectionState.marquee;
		}
	}

	public Rect getMarqueeRect() {
		return marqueeRect;
	}

	public void createTroop() {
		troops.add(new Troop());
	}

	public void createTroop(int positionX, int positionY) {
		troops.add(new Troop(positionX, positionY));
	}

	public void commandStateFalse() {
		commandState = false;
	}

	public void commandStateTrue() {
		commandState = true;
	}

	public boolean getCommandState() {
		return commandState;
	}

	public void commandStateToggle() {
		if (commandState == false) {
			commandState = true;
		} else if (commandState == true) {
			commandState = false;
		}
	}

	public void drawMarquee(Graphics g,Point cameraDrag) {
		if (selState == selectionState.marquee) {
			g.drawRect(getMarqueeRect().left+cameraDrag.x,getMarqueeRect().top+cameraDrag.y,
					getMarqueeRect().right+cameraDrag.x,getMarqueeRect().bottom+cameraDrag.y, Color.argb(50, 50, 50, 255));
		}
	}

	public void paint(Graphics graphics, Point camera) {
		int len = troops.size();
		for (int i = 0; i < len; i++) {
			graphics.drawImage(troops.get(i).image,
					(int) troops.get(i).position.x + camera.x,
					(int) troops.get(i).position.y + camera.y);
			graphics.drawRect(new Rect(troops.get(i).rectangle.left + camera.x,
					troops.get(i).rectangle.top + camera.y,
					troops.get(i).rectangle.right + camera.x,
					troops.get(i).rectangle.bottom + camera.y), Color.argb(100,
					255, 0, 0));
			troops.get(i).paint(graphics, camera);
		}
	}
}
