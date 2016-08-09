package com.shadyaardvark.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.Region;

public class LocalPlayer implements Player {
    private final int team;
    private final Camera camera;

    private GameBoard board;
    private Region lastRegion;

    public LocalPlayer(int team, Camera camera) {
        this.team = team;
        this.camera = camera;
    }

    public int getTeam() {
        return team;
    }

    public void doTurn(GameBoard board) {
        if (this.board == null) {
            this.board = board;
        }

        if (Gdx.input.justTouched()) {
            Vector3 point = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Region clicked = board.getRegion(point.x, point.y);
            if (clicked != null) {
                if (lastRegion == null) {
                    if (clicked.getDice() > 1) {
                        lastRegion = clicked;
                        board.highlightNeighbors(lastRegion, true);
                    }
                } else if (clicked.getNeighboringRegions()
                        .contains(lastRegion.getId())) {
                    board.highlightNeighbors(lastRegion, false);
                    board.attack(lastRegion, clicked);
                    lastRegion = null;
                } else {
                    board.highlightNeighbors(lastRegion, false);
                    lastRegion = null;
                }
            }
        }
    }
}
