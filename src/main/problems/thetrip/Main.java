package problems.thetrip;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {

   
    public void begin() {
        String input;
        Trip trip = null;

        while ((input = Main.readLn(255)) != null) {

            if(input.trim().equals("0")){
                long exchangedAmount =trip.calculateAmountToBeExchanged();
                out.printf("$%d.%02d\n", exchangedAmount / 100, exchangedAmount % 100);
                break;
            }

            if(input.contains(".")) {
                trip.addExpense(input.trim());
            }else {
                if(trip != null){
                    long exchangedAmount =trip.calculateAmountToBeExchanged();
                    out.printf("$%d.%02d\n", exchangedAmount / 100, exchangedAmount % 100);
                }
                trip = new Trip();
            }                
        }
    }

    private class Trip {
        private List<Long> studentSpendings = new ArrayList<Long>();
        private BigDecimal HUNDRED = new BigDecimal("100");

        public void addExpense(String amount){
            studentSpendings.add(new BigDecimal(amount).multiply(HUNDRED).longValueExact());
        }
        
        public long calculateAmountToBeExchanged(){
            long amountToExchange;
            long sum = 0;
            long numberOfStudents = studentSpendings.size();
            for (long amount : studentSpendings){
                sum += amount;
            }

            long average = Math.round((double) sum / numberOfStudents );

            int totalReceived = 0, totalGiven = 0;
            for (int i = 0; i < numberOfStudents; i++) {
                long diff = studentSpendings.get(i) - average;
                if(diff > 0)
                    totalReceived += diff;
                else
                    totalGiven -= diff;
            }
            if (totalGiven == 0)
                totalGiven = totalReceived;

            if (totalReceived == 0)
                totalReceived = totalGiven;

            amountToExchange = totalReceived < totalGiven ? totalReceived : totalGiven;

            // out.println("Rec:"+totalReceived+"Giv:"+totalGiven);
            return amountToExchange;
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

    public static void main (String args[])  // entry point from OS
    {
        Main myWork = new Main();  // create a dinamic instance
        myWork.begin();            // the true entry point
    }
}
