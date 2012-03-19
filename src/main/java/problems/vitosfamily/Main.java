package problems.vitosfamily;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static java.lang.System.in;

public class Main {

    public void solution() {
        String input = readLn();
        int numberOfTestCases = Integer.parseInt(input);
        for(int i = 0;i <numberOfTestCases;i++){          
            input = readLn();          
            String[] numbers = input.trim().split("\\s+");                    
            int[] doorNumbers = new int[numbers.length-1];
            for (int j =1;j<numbers.length;j++) {
                doorNumbers[j-1]=Integer.parseInt(numbers[j]);
            }
            Arrays.sort(doorNumbers);
            int median = doorNumbers[doorNumbers.length/2];
            int distanceSum = 0;
            for (int k=0; k<doorNumbers.length;k++) {
                distanceSum = distanceSum + Math.abs(median - doorNumbers[k]);
            }
            System.out.println(distanceSum);
        }
    }

    ////////////////////////////////////JUDGE RELATED////////////////////////////////////
    public static final int READ_SIZE = 3000;

    public static void setTestData(InputStream testIn,PrintStream testOut) {
        System.setIn(testIn);
        System.setOut(testOut);
    }

    public static String readLn() {
        return readLn(READ_SIZE);
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
