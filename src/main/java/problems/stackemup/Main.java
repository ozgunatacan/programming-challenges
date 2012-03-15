package problems.stackemup;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {

    public void solution() {
       String input = readLn();
       int exampleSize = Integer.parseInt(input.trim());
       readLn(); //empty line
       for (int i=0; i < exampleSize; i++){
           Deck deck = new Deck();
           input = readLn();
           int shuffleSize = Integer.parseInt(input.trim());
           List<List<Integer>> shuffles = new ArrayList<List<Integer>>();
           for (int j=0; j < shuffleSize; j++){                
               List<Integer> shuffle = new ArrayList<Integer>();
               while(shuffle.size() != 52){
                   input = readLn();
                   String[] numbers = input.trim().split("\\s+");
                   for (String number : numbers) {
                      shuffle.add(Integer.parseInt(number)); 
                   }
               }
               shuffles.add(shuffle);
           }

           input = readLn();
           while (input != null && !input.equals("")){
               int shuffleIndex = Integer.parseInt(input.trim());
               deck.shuffle(shuffles.get(shuffleIndex-1));
               input = readLn();
           }
           deck.print();

           if(!(i+1 == exampleSize))
                out.println();
       }
    }

    public static class Deck {
        int[] orderOfCards = new int[52];
        public Deck(){
            for (int i = 0; i < orderOfCards.length; i++) {
                orderOfCards[i]= i+1;
            }
        }
        public void shuffle(List<Integer> shuffle){
            int[] newOrder = new int[52]; //No pun intended
            for (int i = 0; i < shuffle.size(); i++) {
                newOrder[i] = orderOfCards[shuffle.get(i)-1];
            }
            this.orderOfCards = newOrder;
        }
        public void print(){
            for (int i = 0; i < orderOfCards.length; i++) {
                System.out.println(new Card(orderOfCards[i]).toString());
            }
        }
    }
    
    public static enum Suit {
        Clubs(1),Diamonds(13),Hearts(26),Spades(39);
        private int value;
        Suit(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public static class Card {
        private Suit suit;
        private String value;

        public Card(int value) {
            if(value <= Suit.Diamonds.getValue()){
                this.suit = Suit.Clubs;
            }else if (value <= Suit.Hearts.getValue()){
                value = value - Suit.Diamonds.getValue();
                this.suit = Suit.Diamonds;
            }else if (value <= Suit.Spades.getValue()) {
                value = value - Suit.Hearts.getValue();
                this.suit = Suit.Hearts;
            }else {
                value = value - Suit.Spades.getValue();
                this.suit = Suit.Spades;
            }
            handleValue(value);
        }

        private void handleValue(int value){
            if(value < 10){
                this.value = String.valueOf(value + 1);
            }else {
                specials(value);
            }
        }

        private void specials(int value) {
            switch (value){
                case 10 : this.value = "Jack";
                          break;
                case 11 : this.value = "Queen";
                          break;
                case 12 : this.value = "King";
                          break;
                case 13 : this.value = "Ace";
                          break;
            }
        }

        @Override
        public String toString() {
            return value +" of " + suit;
        }
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
