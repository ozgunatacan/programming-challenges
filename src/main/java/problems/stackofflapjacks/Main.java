package problems.stackofflapjacks;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static java.lang.System.in;

public class Main {
    
    public void solution() {
        String input;
        while((input = readLn()) !=null){
            System.out.println(input);
            String[] numberString = input.trim().split("\\s+");
            int[] numbers = new int[numberString.length];
            for (int i=0;i<numberString.length;i++) {
                numbers[i] = Integer.parseInt(numberString[i]);                
            }

            
            for (int i=numbers.length-1;i>0;i--) {
                  int maxIndex = maxIndex(numbers,i-1);
                  if(numbers[i] < numbers[maxIndex]){
                      int flipIndex = numbers.length - maxIndex;
                      flip(numbers,flipIndex);
                      flipIndex = numbers.length - i;
                      flip(numbers,flipIndex);
                  }
            }

            System.out.println(0);
        }
    }
    
    public void flip(int[] a,int position){
        if(position >= a.length) return;
        System.out.print((position) +" ");

        int realPositionInArray = a.length - position;
        int mid = (realPositionInArray + 1) / 2;
        for(int i=0;i<mid;i++){
            int swap = a[i];
            a[i] = a[realPositionInArray-i];
            a[realPositionInArray-i] = swap;
        }
    }
    
    public int minIndex(int a[],int start){
        int min = a[start];
        int index = start;
        for (int i=start;i<a.length; i++) {
            if(a[i]<min){
                min = a[i];
                index = i;
            }            
        }
        return index;
    }
    public int maxIndex(int a[],int end){
        int max = a[end];
        int index = end;
        for (int i=0;i<end; i++) {
            if(a[i]>max){
                max = a[i];
                index = i;
            }
        }
        return index;
    }

    
    public boolean isSorted(int[] a){
        for(int i=1;i<a.length;i++){
            if(a[i] < a[i-1])
                return false;
        }
        return true;
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
