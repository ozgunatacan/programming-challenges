package problems.jollyjumpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.in;

public class Main {
    private static final String JOLLY = "Jolly";
    private static final String NOT_JOLLY = "Not jolly";
    
    public void solution() {
        String input;
        while((input = Main.readLn(30000)) != null) {
            String[] numbers = input.trim().split("\\s+");
            int n = Integer.parseInt(numbers[0]); // n < 3000
            if(numbers.length != (n+1)){
                System.out.println(NOT_JOLLY); // wrong input
            }else {

                Set differences = new HashSet();

                for(int i = 1; i<(numbers.length-1);i++){ //start from 1 skip the last one
                    int diff = Math.abs(Integer.parseInt(numbers[i]) - Integer.parseInt(numbers[i+1]));
                    if(diff <= n-1 && diff >= 1)
                        differences.add(diff);
                }
                if(differences.size() == (n-1)){
                    System.out.println(JOLLY);
                } else {
                    System.out.println(NOT_JOLLY);
                }
            }
        }
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
