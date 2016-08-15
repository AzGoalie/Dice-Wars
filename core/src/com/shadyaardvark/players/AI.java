import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.Region;
import com.shadyaardvark.players;

public class AI implements Player {
    
    private final int team;
    
    
    public AI(int team){
        this.team = team;
    }
    
    @Override
    public int getTeam(){
        return team;
    }
    
    @Override
    public void doTurn(GameBoard board){
            sense(board);
    }
    
    // The AI get's information about the board before thinking on it
    public void sense(GameBoard board){
            for(Region region : board.getTeamRegions(team)){
                    if(region.getDice() != 1){
                think(region,board);
            }
        }
        board.endTurn();
    }
    
    // The AI thinks about how it will act
    public void think(Region region, GameBoard board){
        if(region.getDice() == 1){
            return;
        }
        
        Array<Region> neighbors = region.getNeighboringRegions();
        neighbors.sort();
        
        for(Region neighbor : neighbors){
            if(neighbor.getTeam() != team && region.getDice() >= neighbor.getDice()){
                act(board,region,neighbor);
            }
        }
    }
    
    // The AI's actions
    public void act(GameBoard board, Region region, Region neighbor){
        if(board.attack(region,neighbor)){
            think(neighbor,board);
        }
    }
    
}
