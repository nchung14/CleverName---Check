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
  Location loc = null; // Location of selected piece to be set later when one piece is selected
  ArrayList<Actor> neighbors = new ArrayList<Actor>();
  int counter; // Counter in forloop declared outside for access
  Location playerPieceLoc = neighbors.get(counter).getLocation(); 
  int oppositeDirection = loc.getDirectionToward(playerPieceLoc) + 180;
  Location oppositeDirectionLocation = loc.getAdjacentLocation(oppositeDirection); 
  ArrayList<Location> blackPiecesLocations = new ArrayList<Location>();
  
  public boolean inDanger(Actor actor)
  {
    loc = actor.getLocation();
    neighbors = getGrid().getNeighbors(loc);
    if(oppositeDirection >= 360)
    {
      oppositeDirection = oppositeDirection - 360;
    }
    boolean output = false;
    for(counter = 0; counter < neighbors.size() - 1; counter++)
    {
      if(neighbors.get(counter) instanceof WhiteKing)
      {
        if(getGrid().get(oppositeDirectionLocation) == null)
        {
          output = true;
          return output;
        }
      }
      if(neighbors.get(counter) instanceof WhitePiece)
      {
        int pieceDirection = neighbors.get(counter).getDirection();
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
  
  ArrayList<Location> possibleMoveActors = new ArrayList<Location>();
  
  public Actor choosePiece()
  {
    blackPiecesLocations = getGrid().getOccupiedLocations();
    for(int i = 0; i < getGrid().getOccupiedLocations().size() - 1; i++)
    {
      if(blackPiecesLocations.get(i) instanceof WhitePiece || blackPiecesLocations.get(i) instanceof WhiteKing)
      {
        blackPiecesLocations.remove(i);
        i--;
      }
    }
    
    possibleMoveActors = getGrid().getOccupiedLocations();
    for(int i = 0; i < possibleMoveActors.size() - 1; i++)
    {
      if(getGrid().get(possibleMoveActors.get(i)) instanceof WhiteKing || getGrid().get(possibleMoveActors.get(i)) instanceof WhitePiece || inDanger(getGrid().get(possibleMoveActors.get(i))) == false)
      {
        possibleMoveActors.remove(i);
        i--;
      }
    }
    if(possibleMoveActors.size() == 0)
      return null;
    if(possibleMoveActors.size() > 1)
      return getGrid().get(possibleMoveActors.get((int)(Math.random()*possibleMoveActors.size())));
  }
  
  public void block()
  {
    ArrayList<Actor> blockers = getGrid().getNeighbors(oppositeDirectionLocation);
    for(int i = 0; i < blockers.size() - 1; i++)
    {
      if(blockers.get(i) instanceof WhitePiece || blockers.get(i).getLocation() == loc)
      {
        blockers.remove(i);
        i--;
      }
    }
    if(blockers.size() == 0)
    {
      randomMove();
    }
    if(blockers.size() == 1)
    {
      blockers.get(0).moveTo(oppositeDirectionLocation);
    }
    if(blockers.size() < 1)
    {
      blockers.get((int)(Math.random()*blockers.size())).moveTo(oppositeDirectionLocation);
    }
    
  }
  
  public void randomMove()
  {
    BlackPiece randomBlack = getGrid().get(blackPiecesLocations.get((int)(Math.random()*blackPiecesLocations.size())));
    
    if(randomBlack.getAdjacentLocation(135) instanceof BlackSquare)
    {
      randomBlack.moveRight();
    }
    if(randomBlack.getAdjacentLocation(225) instanceof BlackSquare)
    {
      randomBlack.moveLeft();
    }
  }
  
  public void act()
  {  
  }
}