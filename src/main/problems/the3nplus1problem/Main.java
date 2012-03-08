package problems.the3nplus1problem;

import java.io.*;
import java.util.*;

class Main {

    public void begin() {
        String input;

        while ((input = Main.readLn(255)) != null) {

            String[] data = input.trim().split("\\s+");

            if (data.length == 2) {
                int low = Integer.parseInt(data[0]);
                int high = Integer.parseInt(data[1]);
                int max = low < high ? findMax(low, high) : findMax(high, low);
                System.out.println(low + " " + high + " " + max);
            }

        }
    }

    public int findMax(int low, int high) {
        int max = Integer.MIN_VALUE;
        for (int i = low; i <= high; i++) {
            int length = cycleLength(i);
            if (length > max)
                max = length;
        }
        return max;
    }

    private int cycleLength(int i) {
        long n = i;
        int length = 1;
        while (n > 1) {
            n = ((n & 1) == 0) ? n >> 1 : 3*n + 1;
            length++;
        }
        return length;
    }

    static String readLn(int maxLg) {
        byte lin[] = new byte[maxLg];
        int lg = 0, car = -1;
        String line = "";

        try {
            while (lg < maxLg) {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin[lg++] += car;
            }
        } catch (IOException e) {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String(lin, 0, lg));
    }

    public static void main (String args[])  // entry point from OS
    {
        Main myWork = new Main();  // create a dinamic instance
        myWork.begin();            // the true entry point
    }
}