package com.shadyaardvark.players;

import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.Region;

public class AI implements Player {
    private final int team;

    public AI(int team) {
        this.team = team;
    }

    @Override
    public int getTeam() {
        return team;
    }

    @Override
    public void doTurn(GameBoard board) {
        for (Region region : board.getTeamRegions(team)) {
            if (region.getDice() != 1) {
                attack(region, board);
            }
        }

        board.endTurn();
    }

    private void attack(Region region, GameBoard board) {
        if (region.getDice() == 1) {
            return;
        }

        Array<Region> neighbors = region.getNeighboringRegions();
        neighbors.sort();

        for (Region neighbor : neighbors) {
            if (neighbor.getTeam() != team && region.getDice() >= neighbor.getDice()) {
                if (board.attack(region, neighbor)) {
                    attack(neighbor, board);
                }
            }
        }
    }
}
