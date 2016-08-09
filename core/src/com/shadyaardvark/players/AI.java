package com.shadyaardvark.players;

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
            if (region.getDice() == 1) {
                continue;
            }

            for (int i : region.getNeighboringRegions()) {
                Region neighbor = board.getRegionMap().get(i);
                if (neighbor.getTeam() != region.getTeam()
                        && region.getDice() >= neighbor.getDice()) {
                    board.attack(region, neighbor);
                }
            }
        }

        board.endTurn();
    }
}
