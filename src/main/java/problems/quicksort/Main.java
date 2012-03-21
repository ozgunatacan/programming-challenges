package problems.quicksort;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.in;

public class Main {

    private int numberOfComparisons = 0;
    
    public enum Mode { FIRST,LAST,MEDIAN};
    
    public void solution(){
        String input = null;
        List<Integer> numbers = new ArrayList<Integer>();
        while((input = readLn()) !=null){
           numbers.add(Integer.parseInt(input.trim()));
        }
        Integer[] a = numbers.toArray(new Integer[numbers.size()]);
        quicksort(Mode.FIRST,a,0,numbers.size()-1);
        System.err.println(Arrays.toString(a));
        System.out.println(numberOfComparisons);

        numberOfComparisons = 0;
        a = numbers.toArray(new Integer[numbers.size()]);
        quicksort(Mode.LAST,a,0,numbers.size()-1);
        System.err.println(Arrays.toString(a));
        System.out.println(numberOfComparisons);

        numberOfComparisons = 0;
        a = numbers.toArray(new Integer[numbers.size()]);
        quicksort(Mode.MEDIAN,a,0,numbers.size()-1);
        System.err.println(Arrays.toString(a));
        System.out.println(numberOfComparisons);

    }
    
    public void quicksort(Mode mode,Integer[] a,int q, int r) {
        if(q < r){
            int p = partition(mode,a,q,r);
            quicksort(mode,a,q,p-1);
            quicksort(mode,a,p+1,r);
        }
    }
    private int partition(Mode mode,Integer[] a, int q, int r) {
        numberOfComparisons = numberOfComparisons + (r-q);
        int pivotIndex = q;
        if(mode == Mode.MEDIAN){
            pivotIndex = median(a,q,r); 
        } else if(mode == Mode.LAST) {
            pivotIndex = r;
        }

        int pivot = a[pivotIndex];
        a[pivotIndex] = a[q];
        a[q] = pivot;
        
        int i = q;
        for(int j = q+1;j<=r;j++){
            if(a[j] < pivot){
                i = i+1;
                int swap = a[j];
                a[j] = a[i];
                a[i] = swap;
            }
        }
        a[q] = a[i];
        a[i] = pivot;
        return i;
    }
    
    private int median(Integer[] a,int q, int r){
        int midIndex = (q + r) / 2;
        int first = a[q];
        int last = a[r];
        int mid = a[midIndex];
        if (first > mid) {
            if (mid > last) {
                return midIndex;
            } else if (first > last) {
                return r;
            } else {
                return q;
            }
        } else {
            if (first > last) {
                return q;
            } else if (mid > last) {
                return r;
            } else {
                return midIndex;
            }
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
