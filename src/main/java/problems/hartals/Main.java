package problems.hartals;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {

    private static final int RD_MAX = 256;
    
    public void solution(){
        String input = Main.readLn(RD_MAX);
        int hartalCount = Integer.parseInt(input.trim());
        for(int i = 0; i<hartalCount;i++){
            input = Main.readLn(RD_MAX);
            int howManyDays = Integer.parseInt(input.trim());
            
            List<Integer> hartalPeriods = new ArrayList<Integer>();
            input = Main.readLn(RD_MAX);
            int partyCount = Integer.parseInt(input.trim());
            for(int j = 0; j<partyCount;j++){
                input = Main.readLn(RD_MAX);
                int period = Integer.parseInt(input.trim());
                hartalPeriods.add(period);
            }
            Collections.sort(hartalPeriods);
            Set<Integer> unique = new HashSet<Integer>();
            unique.addAll(hartalPeriods);
            int daysMissing = 0;
            for(int day=1;day <= howManyDays;day++) {
                if(day % 7 != 6 && day % 7 != 0){
                    for (Integer period : unique) {
                        if(day % period == 0){
                            daysMissing ++;
                            break;
                        }
                    } 
                }
            }
            out.println(daysMissing);
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
