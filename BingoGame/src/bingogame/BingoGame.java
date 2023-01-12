/* 
Name: Lorena Rosati
Title: Bingo Game
Date Created: March 3, 2022
Last modified: March 8, 2022
*/

package bingogame;

import java.util.ArrayList;
import java.util.Scanner;

public class BingoGame {

    public static void main(String[] args) throws InterruptedException {
        
        Scanner in = new Scanner (System.in);

        boolean restart = false; //boolean variable for whether to restart the game
        
        do {
            restart = false;
            boolean win = false; //boolean variable for whether or not there is a win
        
            String [][] card = new String[5][5];

            card = generateCard(card);
            ArrayList <String> cardNums = new ArrayList(); //array list of all numbers in the card
            ArrayList <String> calledNums = new ArrayList(); //array list of all the called numbers

            //saving all card numbers to an array list so that we can check for matches later 
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    cardNums.add(card[i][j]);
                }
            }

            do {            
                calledNums = pickNumber(calledNums); //update array list of numbers that have been called
                card = checkMatch(card, cardNums, calledNums); //updating the card after a number has been called
                showCard(card);
                win = checkWin(card); 
                if (win) {
                    System.out.println("Bingo! (you won)");
                }
                Thread.sleep(500);
                System.out.println();
            } while (win == false);

            System.out.println("Would you like to replay?");
            if (in.nextLine().equalsIgnoreCase("yes")) {
                restart=true;
            }
        
        } while (restart);   

    }//close main method
    
    public static String[][] generateCard(String [][] card) {
        
        ArrayList <String> nums = new ArrayList();
        
        for (int i = 0; i < 5; i++) {
            int j=0;
            while (j<5) {
                /* generate random numbers from (i*15+1) up to (i*15+16)
                   - this way, the columns will have the appropriate ranges e.g. B will have 1-15, I will have 16-30, etc.
                convert them into integers and then strings */
                String num = Integer.toString((int)(Math.random()*15)+(i*15+1));
                //if the number is not already in the card, then add it to the arraylist with the card numbers and add it to the card
                if (nums.contains(num) == false) {
                    nums.add(num);
                    //j controls the rows and i controls the columns because numbers are being added in columns 
                    card[j][i] = num;
                    j++;
                }//close if statement
            }//close while loop
        }//close for loop
        
        showCard(card);
        
        return card;
        
    }//close generateCard method
    
    public static ArrayList pickNumber(ArrayList <String> calledNums) {
        String num = "";
        while (true) {
            //generate numbers from 1-75 (convert to integers and then to strings)
            num = Integer.toString((int)(Math.random()*75)+1);
            //if the number hasn't already been called, add the number to the list of called numbers and leave loop
            //otherwise, regenerate a new number (keep repeating loop)
            if (calledNums.contains(num) == false) {
                calledNums.add(num);
                break;
            }//close if statement
        }//close while loop
        
        int numInt = Integer.parseInt(num); //save the number that was called (string) as an integer
        //if statements to call each number with the column that it's in - e.g. when calling 7, call B7
        if (0<numInt && numInt<16) System.out.println("B"+num);
        else if (15<numInt && numInt<31) System.out.println("I"+num); 
        else if (30<numInt && numInt<46) System.out.println("N"+num);
        else if (45<numInt && numInt<61) System.out.println("G"+num);
        else if (60<numInt && numInt<76) System.out.println("O"+num);

        return calledNums;
        
    }//close pickNumber method
        
    public static String[][] checkMatch(String [][] card, ArrayList <String> cardNums, ArrayList <String> calledNums) {
        //save the number that was called into calledNum string variable (use .get() with the index of the length of the array list - 1)
        String calledNum = calledNums.get(calledNums.size()-1);
        //if the called number is in the card, then go to markCard() method
        if (cardNums.contains(calledNum)) {
            card = markCard(card, calledNum);
        } //close if statement
        return card;
    }//close checkMatch method
    
    public static String[][] markCard(String[][] card, String calledNum) {
        //use two for loops to check each array index until it is equal to the called number
        //at that index, replace the number with an x and use break to exit the for loops
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (card[i][j].equalsIgnoreCase(calledNum)) {
                    card[i][j] = "X"; 
                    break;
                }  
            }//close for loop
        }//close for loop
        return card;
    }//close markCard method
    
    public static boolean checkWin(String [][] card) {
        
        boolean win = false; //boolean variable for whether or not there is a win
        
        //CHECK FOR HORIZONTAL WINS
        for (int i = 0; i < 5; i++) {
            int countX = 0;
            //use for loops to check each row for how many X's are in the row (use counter to count how many)
            //this will check each index and see if it is equal to X
            for (int j = 0; j < 5; j++) {
                if (card[i][j].equals("X")) {
                    countX++;
                }
            }
            //if there are 5 X's in a particular row, then there is a win; use break to leave the for loops
            if (countX == 5) {
                win = true;
                break;
            }
        }
        
        //CHECK FOR VERTICAL WINS
        for (int i = 0; i < 5; i++) {
            int countX = 0;
            //use for loops to check each column for how many X's are in the column (use counter to count how many)
            //this will check each index and see if it is equal to X
            for (int j = 0; j < 5; j++) {
                if (card[j][i].equals("X")) {
                    countX++;
                }
            }
            //if there are 5 X's in a particular column, then there is a win; use break to leave the for loops
            if (countX == 5) {
                win = true;
                break;
            }
        }
        
        //CHECK FOR DIAGONAL WINS
        int countX = 0;
        //note: check [0][0], [1][1], [2][2], [3][3], [4][4] for diagonal ranging from top left to bottom right of card
        for (int i = 0; i < 5; i++) {
            //check if the indexes are equal to X
            if (card[i][i].equals("X")) {
                countX++;
            }
        }
        //if there are 5 X's, then there is a win
        if (countX == 5) {
            win = true;
        }
        //check [0][4], [1][3], [2][2], [3][1], [4][0] for diagonal ranging from top right to bottom left of card
        countX=0;
        for (int i = 0; i < 5; i++) {
            //check if the indexes are equal to X
            //checking card[i][4-i] because we need to check [0][4], [1][3], [2][2], [3][1], [4][0]
            if (card[i][4-i].equals("X")) {
                countX++;
            }
        }
        //if there are 5 X's, then there is a win
        if (countX == 5) {
            win = true;
        }
        
        return win; //return true or false for whether or not there was a win
        
    }//close checkWin method
    
    public static void showCard(String [][] card) {
        System.out.println("B I N G O");
        //print each row with spaces in between indexes using for loops
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(card[i][j] + " ");
            }
            System.out.println();
        }
    }//close showCard method
    
}
