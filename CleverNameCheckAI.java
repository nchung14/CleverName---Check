// Nolan Chungs
// AI to play chess on Jaz's CleverName-Check

/* Classes
 * 
 * Piece(type, value, coordinate, danger, potential moves)
 * 
 * */

import info.gridworld.grid.Grid;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import java.util.ArrayList;
import info.gridworld.grid.Location;

public class CleverNameCheckAI extends Bug
{
  public ArrayList choosePiece()
  {
    ArrayList<Location> possibleMoveActors = new ArrayList<Location>();
    possibleMoveActors = getGrid().getOccupiedLocations();
    for(int i = 0; i < possibleMoveActors.size() - 1; i++)
    {
      if(possibleMoveActors.get(i) instanceof WhiteKing || possibleMoveActors.get(i) instanceof WhitePiece)
      {
        possibleMoveActors.remove(i);
      }
      if(possibleMoveActors.get(i).inDanger() == false)
      {
        possibleMoveActors.remove(i);
      }
    }
    return possibleMoveActors.get((int)((Math.random()*possibleMoveActors.size())));
  }
  
  public void act()
  {  
  }
}