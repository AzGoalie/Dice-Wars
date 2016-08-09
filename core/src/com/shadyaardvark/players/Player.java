package com.shadyaardvark.players;

import com.shadyaardvark.map.GameBoard;

public interface Player {
    int getTeam();

    void doTurn(GameBoard board);
}
