package problems.inversions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static java.lang.System.*;

public class Main {
    private static final int COUNT = 100000;
    int numbers[] = new int[COUNT];
    
    public void solution() {
        String input;
        int position = 0;
        while((input = readLn()) != null){
            numbers[position] = Integer.parseInt(input.trim());
            position++;
        }

        long start = currentTimeMillis();
        long inversionCount = bruteForce();
        long elapsed = currentTimeMillis() - start;
        err.println("elapsed time = " + elapsed + "ms");
        err.println((elapsed * 1000.0) / 1000000 + " microseconds for brute force");

        start = currentTimeMillis();
        inversionCount = mergeSortAndInversionCount(numbers);
        elapsed = currentTimeMillis() - start;
        err.println("elapsed time = " + elapsed + "ms");
        err.println((elapsed * 1000.0) / 1000000 + " microseconds for divide and conquer");

        System.out.println(inversionCount);
    }

    private long mergeSortAndInversionCount(int numbers[]) {
        if(numbers.length == 1)
            return 0;
        else {
            int mid = numbers.length / 2;
            int [] left = new int[mid];
            System.arraycopy(numbers,0,left,0,mid);
            int [] right = new int[numbers.length-mid];
            System.arraycopy(numbers,mid,right,0,numbers.length-mid);
            long leftCount = mergeSortAndInversionCount(left);
            long rightCount = mergeSortAndInversionCount(right);
            long skipCount = mergeAndSkipCount(numbers,left,right);
            return leftCount + rightCount + skipCount;
        }
    }

    private long mergeAndSkipCount(int[] numbers, int[] left, int[] right) {
        int inversionCount = 0;
        int i=0,j=0;
        for (int k=0;k<numbers.length;k++){
           if(j >= right.length){
               numbers[k] = left[i];
               i++;
           }else if(i >= left.length){
                numbers[k] = right[j];
               inversionCount = inversionCount + (left.length - i);
                j++;
           }else if(left[i] < right[j]) {
                numbers[k] = left[i];
                i++;
           }else if(right[j] < left[i]) {
               numbers[k] = right[j];
               inversionCount = inversionCount + (left.length - i);
               j++;
           }
        }
        return inversionCount;
    }

    private long bruteForce() {
        long inversionCount = 0;
        for (int i = 0; i < numbers.length; i++) {
            int current = numbers[i];
            for (int j = i+1; j < numbers.length; j++) {
                int compare = numbers[j];
                if(current > compare)
                    inversionCount++;
            }
        }
        return inversionCount;
    }
    
    
    ////////////////////////////////////JUDGE RELATED////////////////////////////////////
    public static final int READ_SIZE = 1000;

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
