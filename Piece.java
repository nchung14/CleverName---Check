// Nolan Chung
//

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
    for(int i = 0; i < neighbors.size() - 1; i++)
    {
      if(Location.getDirectionToward(neighbors.get(i)) + 180
    }
  }
}
