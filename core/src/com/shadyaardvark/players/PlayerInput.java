package com.shadyaardvark.players;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.Region;

public class PlayerInput extends InputAdapter {
    private static final int PLAYER_TEAM = 0;
    private final GameBoard gameBoard;
    private final Camera camera;

    private Region lastRegionClicked;

    public PlayerInput(GameBoard gameBoard, Camera camera) {
        this.gameBoard = gameBoard;
        this.camera = camera;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameBoard.getCurrentPlayer() != 0) {
            return false;
        }

        Vector3 point = camera.unproject(new Vector3(screenX, screenY, 0));
        Region clicked = gameBoard.getRegion(point.x, point.y);

        if (clicked != null) {
            if (lastRegionClicked == null) {
                if (clicked.getDice() > 1 && clicked.getTeam() == PLAYER_TEAM) {
                    lastRegionClicked = clicked;
                    gameBoard.highlightNeighbors(lastRegionClicked, true);
                }
            } else if (clicked.getNeighboringRegions()
                    .contains(lastRegionClicked.getId())) {
                gameBoard.highlightNeighbors(lastRegionClicked, false);
                gameBoard.attack(lastRegionClicked, clicked);
                lastRegionClicked = null;
            } else {
                gameBoard.highlightNeighbors(lastRegionClicked, false);
                lastRegionClicked = null;
            }

            return true;
        }

        return false;
    }
}
