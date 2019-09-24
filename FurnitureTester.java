/*
 * Name: Justin Senia
 * E-Number: E00851822
 * Date: 10/6/2017
 * Class: COSC 461
 * Project: #1
 */
import java.io.*;
import java.util.*;

public class FurnitureTester
{

    //Main method for testing
    public static void main(String[] args) throws IOException
    {
      //creating buffered reader for getting user input
      java.io.BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

      //message welcoming to the program/giving instructions
      System.out.println("******************************************");
      System.out.println("*    Welcome to the Furniture program    *");
      System.out.println("******************************************");
      System.out.println("*   Please enter in a filename to start  *");
      System.out.println("*  or type quit to terminate the program *");
      System.out.println("******************************************");

      //start a loop that continues querying for input as long as user
      //does not enter "quit" (without the quotes)
      while (true)
      {
        System.out.print("Please make your entry now: ");
        String userIn = ""; //used for file entry and/or quitting

        userIn = keyIn.readLine(); //reading user input

        if (userIn.equalsIgnoreCase("quit")) //if user typed quit, stops program
          break;
        else{
              try
              {
                //establishing working directory for file I/O
                String currentDir = System.getProperty("user.dir");
                File fIn = new File(currentDir + '\\' + userIn);

                //using scanner with new input file based on user input
                Scanner scanIn = new Scanner(fIn);

                //creating printwriter for file output
                File fOut = new File("output_" + userIn);
                PrintWriter PWOut = new PrintWriter(fOut, "UTF-8");

                //scanning external file for number of items
                int numItems = scanIn.nextInt();

                //creating table based off of number of items
                int[][] transpInfoTable = new int[3][numItems];

                //creates a table a row at a time, getting
                //the item number, then the weight, then the cost.
                for (int i = 0; i < numItems; i++)
                {
                  transpInfoTable[0][i] = scanIn.nextInt();
                  transpInfoTable[1][i] = scanIn.nextInt();
                  transpInfoTable[2][i] = scanIn.nextInt();
                }

                int weightLimit = scanIn.nextInt(); //get the weight limit

                //outputs formatted data on the screen and also in an external file
                System.out.println("\n\n******************************************");
                System.out.printf("* Number of items: %-4d Max Weight: %4d *\n", numItems, weightLimit);
                System.out.println("******************************************");
                System.out.println("* Item #  *  Item Weight  *  Item Cost   *");
                System.out.println("******************************************");
                for (int i =0; i < numItems; i++)
                    {
                      System.out.printf("* %6d  *  %11d  *  %9d   *\n",
                      transpInfoTable[0][i],
                      transpInfoTable[1][i],
                      transpInfoTable[2][i]);
                    }
                System.out.println("******************************************");
                System.out.println();


                PWOut.println("\n\n*****************************************");
                PWOut.printf("* Number of items: %-4d Max Weight: %4d *\n", numItems, weightLimit);
                PWOut.println("*****************************************");
                PWOut.println("* Item #  *  Item Weight  *  Item Cost  *");
                PWOut.println("*****************************************");
                for (int i =0; i < numItems; i++)
                    {
                      PWOut.printf("* %6d  *  %11d  *  %9d  *\n",
                      transpInfoTable[0][i],
                      transpInfoTable[1][i],
                      transpInfoTable[2][i]);
                    }
                PWOut.println("*****************************************");
                PWOut.println();

                //creating furniture object with parameters taken from external file
                Furniture f = new Furniture(numItems, transpInfoTable, weightLimit, PWOut);

                //calling the solve method
                f.solve();

                //closing I/O objects
                scanIn.close();
                PWOut.close();
              }
              catch (IOException e)
              {
                ;
              }
            }
      }
    }
}
