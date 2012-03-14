package problems.pokerhands;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

import static java.lang.System.in;

public class Main {

    public void solution (){
        String input;
        while((input=Main.readLn(500)) != null) {
            String[] cardLines = input.trim().split("\\s+");
            if(cardLines.length != 10) continue;
            List<Card> black = new ArrayList<Card>();
            List<Card> white = new ArrayList<Card>();
            for (int i = 0; i < cardLines.length; i++) {
                if(i<5){
                    black.add(new Card(cardLines[i]));
                }else {
                    white.add(new Card(cardLines[i]));
                }
            }
            Hand blackHand = HandFactory.createHand(black);
            Hand whiteHand = HandFactory.createHand(white);

            if(blackHand.compareTo(whiteHand) > 0){
                System.out.println("Black wins.");
            }else if (blackHand.compareTo(whiteHand) < 0){
                System.out.println("White wins.");
            }else {
                System.out.println("Tie.");
            }

        }
    }

    public static abstract class HandFactory {
        public static Hand createHand(List<Card> cards){
            Hand hand = new StraightFlush(cards);
            if(hand.isValid()) return hand;
            hand = new FourOfAKind(cards);
            if(hand.isValid()) return hand;
            hand = new FullHouse(cards);
            if(hand.isValid()) return hand;
            hand = new Flush(cards);
            if(hand.isValid()) return hand;
            hand = new Straight(cards);
            if(hand.isValid()) return hand;
            hand = new ThreeOfAKind(cards);
            if(hand.isValid()) return hand;
            hand = new TwoPairs(cards);
            if(hand.isValid()) return hand;
            hand = new Pair(cards);
            if(hand.isValid()) return hand;
            hand = new HighCard(cards);
            return hand;
        }
    }
    
    public static abstract class Hand implements Comparable<Hand>{
        protected List<Card> cards;
        public Hand(List<Card> cards){
            Collections.sort(cards);
            Collections.reverse(cards);
            this.cards = cards;
        }
        public List<Card> getCards() {
            return cards;
        }
        
        //Didn't used a Set because of removing all of them (they are sorted anyway)
        protected List<Card> removeDuplicates(List<Card> cards) {
            List<Card> results = new ArrayList<Card>();
            List<Card> pairs = pairs(cards);
            for (int i = 0; i < cards.size(); i++) {
                if(!pairs.contains(cards.get(i))){
                    results.add(cards.get(i));
                }
            }
            return results;
        }

        protected List<Card> pairs(List<Card> cards) {
            List<Card> results = new ArrayList<Card>();
            for(int i=1; i < cards.size();i++) {
                if(cards.get(i-1).equals(cards.get(i)))
                    results.add(cards.get(i));
            }
            return results;
        }

        protected Card pair(List<Card> cards) {
            for(int i=1; i < cards.size();i++) {
                if(cards.get(i-1).equals(cards.get(i)))
                    return cards.get(i);
            }
            return null;
        }

        protected int maxDuplicates() {
            Map<Integer,Integer> counts = new HashMap<Integer, Integer>();           
            for (int i = 0; i < cards.size(); i++) {
                if(counts.get(cards.get(i).getValue()) == null){
                    counts.put(cards.get(i).getValue(),1);
                }else {
                    counts.put(cards.get(i).getValue(),counts.get(cards.get(i).getValue())+1);
                }
            }
            int max = Integer.MIN_VALUE;
            for (Integer count : counts.values())
               max = (count > max ? count: max);
            return max;
        }

        //unique and sorted cards
        protected boolean areCardsSameSuit(List<Card> cards) {
            for (int i = 0; i < cards.size()-1; i++) {
                Card card1 =  cards.get(i);
                Card card2 =  cards.get(i+1);
                if(card1.getSuit() != card2.getSuit())
                    return false;
            }
            return true;
        }

        //unique and sorted cards
        protected boolean areCardsConsecutive(List<Card> cards) {
            for (int i = 0; i < cards.size()-1; i++) {
                Card card1 =  cards.get(i);
                Card card2 =  cards.get(i+1);
                if(card1.getValue()-card2.getValue() != 1)
                    return false;
            }
            return true;
        }

        public boolean isValid(){
            return isValid(cards);
        }
        public abstract boolean isValid(List<Card> cards);
        public abstract int getRank();
    }

    public static class HighCard extends Hand {
        @Override
        public int getRank() {
            return 0;
        }

        public HighCard(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if(uniqueCards.size() == 5){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                for(int i = 0; i < cards.size();i++){
                    if(cards.get(i).compareTo(other.getCards().get(i)) != 0){
                        return cards.get(i).compareTo(other.getCards().get(i));
                    }
                }

            }
            return 0;
        }
    }

    public static class Pair extends Hand {
        @Override
        public int getRank() {
            return 1;
        }

        public Pair(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if(uniqueCards.size() == 4 && maxDuplicates()==2){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                Card pair = pair(cards);
                Card otherPair = pair(other.getCards());
                if(pair.compareTo(otherPair) == 0){
                    List<Card> uniqueCards = removeDuplicates(cards);
                    List<Card> otherUniqueCards = removeDuplicates(other.getCards());
                    for(int i = 0; i < uniqueCards.size();i++){
                        if(uniqueCards.get(i).compareTo(otherUniqueCards.get(i)) != 0){
                            return uniqueCards.get(i).compareTo(otherUniqueCards.get(i));
                        }
                    }
                    return 0; // tie
                }else {
                   return pair.compareTo(otherPair);
                }
            }
            return 1;
        }
        
    }

    public static class TwoPairs extends Hand {
        @Override
        public int getRank() {
            return 2;
        }

        public TwoPairs(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if(uniqueCards.size() == 3 && maxDuplicates() == 2){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                List<Card> pairs = pairs(cards);
                List<Card> otherPairs = pairs(other.getCards());
                for(int i = 0; i < pairs.size();i++){
                    if(pairs.get(i).compareTo(otherPairs.get(i)) != 0){
                        return pairs.get(i).compareTo(otherPairs.get(i));
                    }
                }

                List<Card> uniqueCards = removeDuplicates(cards);
                List<Card> otherUniqueCards = removeDuplicates(other.getCards());
                return uniqueCards.get(0).compareTo(otherUniqueCards.get(0));
            }
            return 1;
        }
    }

    public static class ThreeOfAKind extends Hand {
        @Override
        public int getRank() {
            return 3;
        }

        public ThreeOfAKind(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if((uniqueCards.size() == 3 ) && maxDuplicates() == 3){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                Card pair = pair(cards);
                Card otherPair = pair(other.getCards());
                return pair.compareTo(otherPair);
            }
            return 1;
        }
    }

    public static class Straight extends Hand {
        @Override
        public int getRank() {
            return 4;
        }

        public Straight(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if(uniqueCards.size() == 5 && areCardsConsecutive(cards) && !areCardsSameSuit(cards)){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                Card first = cards.get(0);
                Card otherFirst = other.getCards().get(0);
                return first.compareTo(otherFirst);
            }
            return 1;
        }
    }

    public static class Flush extends Hand {
        @Override
        public int getRank() {
            return 5;
        }

        public Flush(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if(uniqueCards.size() == 5 && areCardsSameSuit(cards)){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                for(int i = 0; i < cards.size();i++){
                    if(cards.get(i).compareTo(other.getCards().get(i)) != 0){
                        return cards.get(i).compareTo(other.getCards().get(i));
                    }
                }

            }
            return 1;
        }
    }

    public static class FullHouse extends Hand {
        @Override
        public int getRank() {
            return 6;
        }

        public FullHouse(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if(uniqueCards.size() == 2 && maxDuplicates() == 3){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                Card triplet = triplet(cards);
                Card tripletPair = triplet(other.getCards());
                return triplet.compareTo(tripletPair);
            }
            return 1;
        }
        
        private Card triplet(List<Card> cards) {
            for (int i = 0; i < cards.size()-2; i++) {
                Card card1 =  cards.get(i);
                Card card2 =  cards.get(i+1);
                Card card3 =  cards.get(i+2);
                if(card1.getValue().equals(card2.getValue()) && card2.getValue().equals(card3.getValue())){
                   return card1;
                }
            }
            return null;
        }
    }

    public static class FourOfAKind extends Hand {
        @Override
        public int getRank() {
            return 7;
        }

        public FourOfAKind(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if((uniqueCards.size() == 2 ) && maxDuplicates() == 4){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                Card pair = pair(cards);
                Card otherPair = pair(other.getCards());
                return pair.compareTo(otherPair);
            }
            return 1;
        }
    }

    public static class StraightFlush extends Hand {
        @Override
        public int getRank() {
            return 8;
        }

        public StraightFlush(List<Card> cards) {
            super(cards);
        }

        @Override
        public boolean isValid(List<Card> cards) {
            Set<Card> uniqueCards = new HashSet<Card>();
            uniqueCards.addAll(cards);
            if((uniqueCards.size() == 5 ) && areCardsSameSuit(cards) && areCardsConsecutive(cards) ){
                return true;
            }else {
                return false;
            }
        }

        @Override
        public int compareTo(Hand other) {
            if(other.getRank() > getRank()){
                return -1;
            }else if(other.getRank() == getRank()) {
                return 0;
            }
            return 1;
        }
    }

    public static enum Suit {
        C,D,S,H
    }
    
    public static class Card implements Comparable<Card>{
        private Suit suit;
        private Integer value;

        public Card(String code) {
            if(code == null)
                throw new IllegalArgumentException();
            this.suit = Suit.valueOf(code.substring(1,2));
            String valueCode = code.substring(0,1);
            valueCode = valueCode.trim();

            if(valueCode.length() == 1) {
                char valChar = valueCode.charAt(0);
                if(Character.isDigit(valChar)) {
                    this.value = Integer.parseInt(valueCode);
                }else {
                    switch (valChar){
                        case 'T': this.value = 10;
                                  break;
                        case 'J': this.value = 11;
                                  break;
                        case 'Q': this.value = 12;
                                  break;
                        case 'K': this.value = 13;
                                  break;
                        case 'A': this.value = 14;
                                  break;
                    }
                }
            }else {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public int compareTo(Card other) {
           return this.value.compareTo(other.value);
        }

        public Integer getValue() {
            return value;
        }

        public Suit getSuit() {
            return suit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Card card = (Card) o;

            if (!value.equals(card.value)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return "Card{" +
                    "suit=" + suit +
                    ", value=" + value +
                    '}';
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
