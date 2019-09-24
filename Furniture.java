/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 10/6/2017
 * Class: COSC 461
 * Project: #1
 */
import java.util.LinkedList;
import java.io.*;
import java.util.*;

//This program solves Furniture problem
public class Furniture
{
  //board class (inner class)
  private class Board{
    private int[][] array;  //array

    //constructor of board class
    private Board(int numItems)
    {
      array = new int[2][numItems]; //create array
      for (int i = 0; i < numItems; i++)
      {
          array[0][i] = transpTblInfo[1][i]; //holds weight information
          array[1][i] = 0; //zeroed out (to be added from full info table if
      }                    // that particular item will be used by a child)
    }
}

  //declaring private variables
  private int numOfItems; //total number of items that could potentially be shipped
  private int[][] transpTblInfo;  //full table of shipping info from external file
  private int weightLimitVal; //weight limit
  private PrintWriter pW; //print writer
  private Board bestConfig; //best configuration of items weight/value wise

  //constructor of Furniture class
  public Furniture(int numItems, int[][] transpTbl, int weightLimit, PrintWriter PWOut)
  {
    this.numOfItems = numItems; //total number of items
    this.transpTblInfo = transpTbl; //table of shipping info
    this.weightLimitVal = weightLimit; //weight limit
    this.bestConfig = new Board(numItems); //new board for best configuration storage
    this.pW = PWOut; //Printwriter variable
  }

  //method for solving  Furniture problem
  public void solve()
  {
    LinkedList<Board> openList = new LinkedList<Board>();
    LinkedList<Board> closedList = new LinkedList<Board>();

    Board initialBoard = new Board(numOfItems); //new board based on number of items

    //adding intialboard to openlist
    openList.addFirst(initialBoard);

    while (!openList.isEmpty()) //continues until open list become empty
    {
      Board board = openList.removeFirst(); // remove initial board from openlist

      closedList.addLast(board); //add initial board to closed list

      //if current child weighs less than the weight limit...
      if (calcWeight(board) <= weightLimitVal)
      {
        //and the value of the new board is higher than the old (best) configuration
        //change old best to new best configuration by copying new child board
        if (calcValue(board) > calcValue(bestConfig))
        {
          bestConfig = copy(board);
        }
      }

      //regardless of weight, generate new children to make sure all possibilities
      //are covered so that best may be found.
      LinkedList<Board> children = generate(board);//generate children

      //pop children off of stack one at a time
      for (int i = 0; i < children.size(); i++)
      {
        Board child = children.get(i);

        //if the child doesn't exist in the open or closed list, add to open list
        if (!exists(child, openList) && !exists(child, closedList))
          openList.addLast(child);
      }
    }

    //prints solution headers for output (both on screen and in file format)
    System.out.println("******************************************");
    System.out.println("*                The Solution            *");
    System.out.println("******************************************");

    pW.println("\n");
    pW.println("******************************************");
    pW.println("*                The Solution            *");
    pW.println("******************************************");

    //call method to display the best configuration
    display(bestConfig);

    System.out.println("");
    pW.println("");
  }

  //Method generates children of a board
  private LinkedList<Board> generate(Board board)
  {
    LinkedList<Board> children = new LinkedList<Board>();

    for (int i = 0; i < numOfItems; i ++)
    {
      Board child = copy(board);

      //assigning values to children's furniture items one at a time
      child.array[1][i] = transpTblInfo[2][i];

      //calculating if the child's weight is within acceptable parameters
      //if it is, add to children list
      if (calcWeight(child) < weightLimitVal)
        children.addLast(child);
      }

      //return list of children
      return children;
    }

  //this method calculates total weight of current child
  private int calcWeight(Board board)
  {
      int totalWeight = 0;
      for (int i = 0; i < numOfItems; i++)
      {
        if (board.array[1][i] != 0)
          totalWeight = totalWeight + board.array[0][i];
      }
      return totalWeight;
  }

  //this method calculates the total value of the current child
  private int calcValue(Board board)
  {
      int totalValue = 0;
      for (int i = 0; i < numOfItems; i++)
      {
          totalValue = totalValue + board.array[1][i];
      }
      return totalValue;
  }

  //Method makes copy of board
  private Board copy(Board board)
  {
    Board result = new Board(numOfItems);

    for (int i = 0; i < numOfItems; i++)
      {
        result.array[0][i] = board.array[0][i];
        result.array[1][i] = board.array[1][i];
      }

    return result;
  }

  //Method decides whether a board exists in a list
  private boolean exists(Board board, LinkedList<Board> list)
  {
    for (int i = 0; i < list.size(); i++)
      if (identical(board, list.get(i)))
        return true;

    return false;
  }

  //Method decides whether two boards are identical
  private boolean identical(Board p, Board q)
  {
    for (int i = 0; i < 2; i++)
      for (int j = 0; j < numOfItems; j++)
        if (p.array[i][j] != q.array[i][j])
          return false;

    return true;
  }

  //Method displays board on both monitor as well as in file output
  private void display(Board board)
  {
    System.out.println("The Optimal Solution:");
    pW.println("The Optimal Solution:");

    System.out.print("Furniture: ");
    pW.print("Furniture: ");

    for (int i = 0; i < numOfItems; i++)
    {
      if (board.array[1][i] != 0)
      {
        System.out.print(transpTblInfo[0][i] + " ");
        pW.print(transpTblInfo[0][i] + " ");
      }
    }

    System.out.println("");

    System.out.println("Weight: " + calcWeight(board));
    pW.println("Weight: " + calcWeight(board));

    System.out.println("Value: " + calcValue(board));
    pW.println("Value: " + calcValue(board));

    System.out.println("");
    pW.println("");
  }
}
