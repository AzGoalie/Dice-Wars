import com.badlogic.gdx.utils.Array;
import com.shadyaardvark.map.GameBoard;
import com.shadyaardvark.map.Region;

public class AI implements Player {
	
	private final int team;
	
	
	public AI(int team){
	}
	
	@Override
	public int getTeam(){
		return team;
	}
	
	@Override
	public void doTurn(GameBoard board){
		sense();
	}
	
	public void sense(GameBoard board){
			for(Region region : board.getTeamRegions(team)){
			if(region.getDice() != 1){
				think(region,board);
			}
		}
		board.endTurn();
	}
	
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
	
	public void act(GameBoard board, Region region, Region neighbor){
		if(board.attack(region,neighbor)){
			think(neighbor,board);
		}
	}
	
}
