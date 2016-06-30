package com.shadyaardvark;

import com.badlogic.gdx.Game;
import com.shadyaardvark.screens.MainMenu;

public class DiceWars extends Game {

    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }
}
