package problems.lcddisplay;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {

    private int [][][] lcd = new int [10][][];

    public Main() {
        int[][] zero = {
                {0,2,0},
                {1,0,1},
                {0,0,0},
                {1,0,1},
                {0,2,0},
        };
        int[][] one = {
                {0,0,0},
                {0,0,1},
                {0,0,0},
                {0,0,1},
                {0,0,0},
        };
        int[][] two = {
                {0,2,0},
                {0,0,1},
                {0,2,0},
                {1,0,0},
                {0,2,0},
        };
        int[][] three = {
                {0,2,0},
                {0,0,1},
                {0,2,0},
                {0,0,1},
                {0,2,0},
        };
        int[][] four = {
                {0,0,0},
                {1,0,1},
                {0,2,0},
                {0,0,1},
                {0,0,0},
        };
        int[][] five = {
                {0,2,0},
                {1,0,0},
                {0,2,0},
                {0,0,1},
                {0,2,0},
        };
        int[][] six = {
                {0,2,0},
                {1,0,0},
                {0,2,0},
                {1,0,1},
                {0,2,0},
        };
        int[][] seven = {
                {0,2,0},
                {0,0,1},
                {0,0,0},
                {0,0,1},
                {0,0,0},
        };
        int[][] eight = {
                {0,2,0},
                {1,0,1},
                {0,2,0},
                {1,0,1},
                {0,2,0},
        };
        int[][] nine = {
                {0,2,0},
                {1,0,1},
                {0,2,0},
                {0,0,1},
                {0,2,0},
        };

        lcd[0]=zero;
        lcd[1]=one;
        lcd[2]=two;
        lcd[3]=three;
        lcd[4]=four;
        lcd[5]=five;
        lcd[6]=six;
        lcd[7]=seven;
        lcd[8]=eight;
        lcd[9]=nine;

    }

    public void solution() {
        String input;
        while ((input = Main.readLn(255)) != null) {
           if(input.trim().equals("0 0")) break; //EOF
           String[] numbers = input.trim().trim().split("\\s+"); 
           String size = numbers[0];
           String number = numbers[1];
           outputLCDNumber(getLCDData(number),Integer.parseInt(size));
           out.println();//an empty line after each number
        }
    }

    private void outputLCDNumber(int[][][] number,int size){        
        for(int row=0;row<5;row++){     
            StringBuffer rowBuffer = new StringBuffer();
            boolean flm = isFirstLastMiddle(row);
            for (int digit=0; digit < number.length;digit++){
                for(int column=0;column<3;column++){
                    int data = number[digit][row][column];
                    decodeData(size, rowBuffer, flm, column, data);
                }
             if(digit != number.length -1)
                rowBuffer.append(" "); //empty column after every digit
            }

            printRow(size, rowBuffer, flm);
        }
    }

    private void printRow(int size, StringBuffer rowBuffer, boolean flm) {
        if(flm){
           out.println(rowBuffer);
        }else {
            for(int i =0 ; i <size;i++)
                out.println(rowBuffer);
        }
    }

    private void decodeData(int size, StringBuffer rowBuffer, boolean flm, int column, int data) {
        if(flm){
            if(data == 0){
                handleEmpty(size, rowBuffer, column);
            }else if (data == 2){
               for(int i =0 ; i <size;i++)
                   rowBuffer.append("-");
            }
        }else {
            if(data == 0){
                handleEmpty(size, rowBuffer, column);
            }else if (data == 1){
                rowBuffer.append("|");
            }
        }
    }

    private void handleEmpty(int size, StringBuffer rowBuffer, int column) {
        if(!firstOrLastCol(column)){
            for(int i =0 ; i <size;i++)
                rowBuffer.append(" ");
        }else {
            rowBuffer.append(" ");
        }
    }

    private boolean isFirstLastMiddle(int row) {
        if(row==0 || row == 4 || row == 2)
            return true;
        else
            return false;
    }
    
    private boolean firstOrLastCol(int col){
        if(col == 0 || col==2)
            return true;
        else
            return false;
    }

    private int[][][] getLCDData(String number) {
        int[][][] result = new int[number.length()][][];
        for(int i = 0; i<number.length();i++){
            result[i] = lcd[Character.getNumericValue(number.charAt(i))];
        }
        return result;
    }
    ////////////////////////////////////JUDGE RELATED////////////////////////////////////
    public static void setTestData(InputStream testIn,PrintStream testOut) {
        System.setIn(testIn);
        System.setOut(testOut);
    }

    public static String readLn(int maxLg) {
        byte lin[] = new byte[maxLg];
        int lg = 0, car = -1;
        String line = "";

        try {
            while (lg < maxLg) {
                car = in.read();
                if ((car < 0) || (car == '\n')) break;
                lin[lg++] += car;
            }
        } catch (IOException e) {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String(lin, 0, lg));
    }

    public static void main (String args[])
    {
        Main main = new Main();
        main.solution();
    }

}
