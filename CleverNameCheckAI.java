// Nolan Chung
// AI to play chess on Jaz's CleverName-Check

import info.gridworld.grid.Grid;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import java.util.ArrayList;
import info.gridworld.grid.Location;

public class CleverNameCheckAI extends Bug // AI to be implemented as an Actor on the grid so methods like getGrid() be executed; Idea Credit: Wyatt Smith
{
  Location loc = null; // Location of selected piece to be set later when one piece is selected
  ArrayList<Actor> neighbors = new ArrayList<Actor>();
  int counter; // Counter in forloop declared outside for access
  Location playerPieceLoc = neighbors.get(counter).getLocation(); // Saves Location of an opponent's piece
  int oppositeDirection = loc.getDirectionToward(playerPieceLoc) + 180; // Saves direction opposite to opponent's piece
  Location oppositeDirectionLocation = loc.getAdjacentLocation(oppositeDirection); // Gets adjacent location opposite to opponent's piece
  
  ArrayList<Location> possibleMoveActors = new ArrayList<Location>(); // List of actors to move that will be set and narrowed down
  ArrayList<Location> blackPiecesLocations = new ArrayList<Location>(); // Gets list of black pieces for random moves
  
  public boolean inDanger(Actor actor) // Method to check if piece is in danger
  {
    loc = actor.getLocation(); // Gets location of actor being checked
    neighbors = getGrid().getNeighbors(loc); // Gets all neighbors of this piece
    if(oppositeDirection >= 360) // Opposite direction adds 180 degrees to angle so since compass directions do not exceed 359, this gets the equivalent angle if it does
    {
      oppositeDirection = oppositeDirection - 360; // Subtracts 360 degrees to find the coterminal angle
    }
    boolean output = false; // Default output response set to true
    for(counter = 0; counter < neighbors.size() - 1; counter++) // For loop using the external counter
    {
      if(neighbors.get(counter) instanceof WhiteKing) // If the neighboring piece is an opponent's piece and the adjacent spot on the other end of the AI piece is empty the piece is in danger
      {
        if(getGrid().get(oppositeDirectionLocation) == null) //
        {
          output = true; // Sets the output to true
          return output; // Returns to get out of the loop as soon as one instance of danger is found
        }
      }
      if(neighbors.get(counter) instanceof WhitePiece) // Same concept for non King white piece
      {
        int pieceDirection = neighbors.get(counter).getDirection();
        if(pieceDirection == 0 || pieceDirection == 45 || pieceDirection == 315) // Since normal pieces can only move forward check to make sure the piece isn't just behind the AI facing the other way
        {
          if(getGrid().get(oppositeDirectionLocation) == null) // If the piece is facing the AI piece and there is nothing to block it then the given AI piece is in danger
          {
            output = true; // Sets output to true
            return output; // Returns to immediately exit for loop
          }
        }
      }
    }
    return output; // Returns the output which was default false unless the if statements changed it to true
  }
  
  public Actor choosePiece()
  {
    blackPiecesLocations = getGrid().getOccupiedLocations(); // Sets value of ArrayList to hold all AI pieces
    for(int i = 0; i < blackPiecesLocations.size() - 1; i++) // blackPiecesLocation currently holds all pieces in it and will be narrowed down
    {
      if(blackPiecesLocations.get(i) instanceof WhitePiece || blackPiecesLocations.get(i) instanceof WhiteKing) // If the actor at the location saved is White remove it from the list
      {
        blackPiecesLocations.remove(i);
        i--; // Keeps the for loop on track through the ArrayList
      }
    }
    
    possibleMoveActors = getGrid().getOccupiedLocations(); // Sets possibleMoveActors to hold all occupied locations to start
    for(int i = 0; i < possibleMoveActors.size() - 1; i++)
    {
      if(getGrid().get(possibleMoveActors.get(i)) instanceof WhiteKing || getGrid().get(possibleMoveActors.get(i)) instanceof WhitePiece || inDanger(getGrid().get(possibleMoveActors.get(i))) == false)
      {
        possibleMoveActors.remove(i); // If the actor on the list is a white piece or not in danger remove it from the list
        i--; // Keeps the for loop on track
      }
    }
    if(possibleMoveActors.size() == 0) // If there is no in danger black AI piece return null
      return null;
    if(possibleMoveActors.size() > 1) // If there are more than 1 in danger black AI piece pick one randomly
      return getGrid().get(possibleMoveActors.get((int)(Math.random()*possibleMoveActors.size())));
  }
  
  public void block() // Idea Credit: Zach Taira
  {
    ArrayList<Actor> blockers = getGrid().getNeighbors(oppositeDirectionLocation); // Gets the neighbors around the open coordinate at oppositeDirectionLocation to see if any can block for the danger piece
    for(int i = 0; i < blockers.size() - 1; i++)
    {
      if(blockers.get(i) instanceof WhitePiece || blockers.get(i).getLocation() == loc) // If the piece is white or the piece in danger it can't block for the danger piece so it is removed
      {
        blockers.remove(i);
        i--;
      }
    }
    if(blockers.size() == 0) // If there is no piece avaliable to block execute a random move
    {
      randomMove();
    }
    if(blockers.size() == 1) // If there is a piece avaliable to block then execute
    {
      blockers.get(0).moveTo(oppositeDirectionLocation); 
    }
    if(blockers.size() < 1) // If more than one piece may block randomly select one to do so
    {
      blockers.get((int)(Math.random()*blockers.size())).moveTo(oppositeDirectionLocation);
    }
    
  }
  
  public void randomMove() // Method to execute a random move should other means fail
  {
    BlackPiece randomBlack = getGrid().get(blackPiecesLocations.get((int)(Math.random()*blackPiecesLocations.size()))); Select a random black piece
    
    if(randomBlack.getAdjacentLocation(135) instanceof BlackSquare) // If there is only a black square in that adjacent location then it is open and can be moved to
    {
      randomBlack.moveRight();
    }
    if(randomBlack.getAdjacentLocation(225) instanceof BlackSquare) // If there is only a black square in that adjacent location then it is open and can be moved to
    {
      randomBlack.moveLeft();
    }
  }
  
  public void act() // Act method to be executed when the world is incremented one step further after the player's turn; Idea Credit: Wyatt Smith
  {
    choosePiece();
    block();
  }
}