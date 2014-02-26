// Nolan Chung
// Piece class code to be integrated with Jaz's Piece class

// ONLY WORKS FOR BLACK PIECES

import info.gridworld.grid.Grid;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import java.util.ArrayList;
import info.gridworld.grid.Location;

public class Piece extends Bug
{
  Location loc = getLocation();
  ArrayList<Actor> neighbors = getGrid().getNeighbors(getLocation());
  
  public boolean inDanger()
  {
    boolean output = false;
    for(int i = 0; i < neighbors.size() - 1; i++)
    {
      Location playerPieceLoc = neighbors.get(i).getLocation();
      int oppositeDirection = loc.getDirectionToward(playerPieceLoc) + 180;
      if(oppositeDirection >= 360)
      {
        oppositeDirection = oppositeDirection - 360;
      }
      Location oppositeDirectionLocation = loc.getAdjacentLocation(oppositeDirection); 
      if(neighbors.get(i) instanceof WhiteKing)
      {
        if(getGrid().get(oppositeDirectionLocation) == null)
        {
          output = true;
          return output;
        }
      }
      if(neighbors.get(i) instanceof WhitePiece)
      {
        int pieceDirection = neighbors.get(i).getDirection();
        if(pieceDirection == 0 || pieceDirection == 45 || pieceDirection == 315)
        {
          if(getGrid().get(oppositeDirectionLocation) == null)
          {
            output = true;
            return output;
          }
        }
      }
    }
    return output;
  }
}
