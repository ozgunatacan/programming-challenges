package problems.minesweeper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

public class Main {

    public void begin() {
        String input;
        int fieldCount = 1;
        while ((input = Main.readLn(255)) != null) {

            if(input.trim().equals("0 0")) break; //EOF
            if(fieldCount != 1)
                out.println();
            String[] matrixSizes = input.trim().split("\\s+");
            if (matrixSizes.length == 2) {
                out.println("Field #" + fieldCount + ":");
                int m = Integer.parseInt(matrixSizes[0]);
                int n = Integer.parseInt(matrixSizes[1]);
                handleField(m, n);
            }
          fieldCount++;
        }
    }

    private void handleField(int m, int n) {
        char [] [] field = readField(m,n);
        char [] [] decodedField = decodeField(field);
        printField(decodedField);
    }

    private char[][] readField(int m,int n) {
        char[][] field = new char[m][n];
        for(int i=0;i<m;i++){
            String line = Main.readLn(255).trim();
            for(int j=0;j<n;j++){
                field[i][j]=line.charAt(j);
            }
        }
        return field;
    }

    private char[][] decodeField(char[][] field) {
        char[][] decodedField = new char[field.length][field[0].length];
        for(int m=0;m<field.length;m++){
            for(int n=0;n<field[0].length;n++){
                if(field[m][n] == '*'){
                    decodedField[m][n] = '*';
                }else {
                    int mineCount = discoverMineCount(field,m,n);
                    decodedField[m][n] = Character.forDigit(mineCount,10);
                }
            }
        }
        return decodedField;
    }

    private void printField(char[][] decodedField) {
        for(int m=0;m<decodedField.length;m++){
            for(int n=0;n<decodedField[0].length;n++){
                out.print(decodedField[m][n]);
            }
            out.println();
        }
    }

    private int discoverMineCount(char[][] field,int m, int n) {
        int count = 0;
        int height = field.length;
        int width = field[0].length;
        List<Tuple<Integer,Integer>>tuples = getAllPossiblePositions(height,width,m,n);
        for(Tuple<Integer,Integer> tuple:tuples){
            if(isThereAnyMine(field,tuple.getM(),tuple.getN())){
                count++;
            }
        }
        return count;
    }

    private boolean isThereAnyMine(char[][] field,int m, int n){
        return (field[m][n] == '*');
    }

    private List<Tuple<Integer,Integer>> getAllPossiblePositions(int height,int width,int m,int n) {
        ArrayList<Integer> ms = new ArrayList<Integer>();
        ArrayList<Integer> ns = new ArrayList<Integer>();
        ns.add(n);
        if(n-1>=0) ns.add(n-1);
        if(n+1<width) ns.add(n+1);

        ms.add(m);
        if(m-1>=0) ms.add(m-1);
        if(m+1<height) ms.add(m+1);

        List<Tuple<Integer,Integer>> tuples = new ArrayList<Tuple<Integer, Integer>>();
        for (int mAdd:ms){
           for(int nAdd:ns){
               tuples.add(new Tuple<Integer, Integer>(mAdd,nAdd));
           }
        }
        return tuples;
    }

    private class Tuple<M, N> {
        private M m;
        private N n;
        public Tuple(M m, N n) {
            this.m = m;
            this.n = n;
        }
        public M getM() {
            return this.m;
        }
        public N getN() {
            return this.n;
        }
    }
    ////////////////////////////////////JUDGE RELATED////////////////////////////////////
    private static boolean isTest = false;
    private static InputStream testData;

    public static void setTest(boolean test) {
        Main.isTest = test;
    }

    public static void setTestData(InputStream testData) {
       Main.testData = testData;
    }

    public static String readLn(int maxLg) {
        byte lin[] = new byte[maxLg];
        int lg = 0, car = -1;
        String line = "";

        try {
            while (lg < maxLg) {
                if(isTest){
                    car = testData.read();
                }else{
                    car = in.read();
                }
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
