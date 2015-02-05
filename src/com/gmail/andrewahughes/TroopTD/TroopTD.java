package com.gmail.andrewahughes.TroopTD;

import com.gmail.andrewahughes.framework.Screen;
import com.gmail.andrewahughes.framework.implementation.AndroidGame;

public class TroopTD extends AndroidGame {
    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this); //
    }
    @Override
    public void onBackPressed() {
    getCurrentScreen().backButton();
    }
}