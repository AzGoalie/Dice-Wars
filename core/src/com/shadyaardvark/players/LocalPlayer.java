package com.shadyaardvark.players;

import com.shadyaardvark.map.GameBoard;

public class LocalPlayer implements Player {
    private final int team;

    public LocalPlayer(int team) {
        this.team = team;
    }

    public int getTeam() {
        return team;
    }

    public void doTurn(GameBoard board) {
        if (board.getTeamRegions(team).size == 0) {
            board.endTurn();
        }
    }
}
