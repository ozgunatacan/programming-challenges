package problems;

import java.io.IOException;

public class The3nPlus1Problem implements Runnable{
    public static String readLn(int maxLength){  // utility function to read from stdin,
        // Provided by Programming-challenges, edit for style only
        byte line[] = new byte [maxLength];
        int length = 0;
        int input = -1;
        try{
            while (length < maxLength){//Read untill maxlength
                input = System.in.read();
                if ((input < 0) || (input == '\n')) break; //or untill end of line ninput
                line [length++] += input;
            }

            if ((input < 0) && (length == 0)) return null;  // eof
            return new String(line, 0, length);
        }catch (IOException e){
            return null;
        }
    }

    public static void main(String args[]) {
        The3nPlus1Problem problem = new The3nPlus1Problem();
        problem.run();            // execute
    }

    public void run() {
        new Solution().run();
    }
}

class Solution implements Runnable{
    public void run(){
        // Your program here
    }
}
