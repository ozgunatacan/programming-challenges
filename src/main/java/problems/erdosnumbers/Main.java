package problems.erdosnumbers;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.in;

public class Main {
    private Map<String,Set<String>> relationships = new HashMap<String, Set<String>>();
    private Map<String,Integer> erdosNumber = new HashMap<String, Integer>();
    private static final Pattern authorPattern = Pattern.compile("(\\w+,\\s*[\\w{1}\\.]*)");
    private static final String ERDOS = "Erdos,P.";

    public Main() {
        erdosNumber.put(ERDOS,0);
    }

    public void solution() {
        String input = Main.readLn();
        int numberOfScenarios = Integer.parseInt(input.trim());
        for (int i = 0; i < numberOfScenarios; i++) {
            input = Main.readLn();
            if(input == null) break;
            String[] numbers = input.trim().split("\\s+");
            int numberOfPapers = Integer.parseInt(numbers[0]);
            int numberOfQuestionedAuthors = Integer.parseInt(numbers[1]);
            for (int j = 0; j < numberOfPapers; j++) {
                input = Main.readLn();
                createRelationships(input);
            }

            System.out.println("Scenario "+(i+1));
            for (int j = 0; j < numberOfQuestionedAuthors; j++) {
                input = readLn();
                String author = input.trim().replaceAll(" ", "");
                findErdosNumber(author);
                String erdosNumber = getErdosNumber(author);
                System.out.println(author.replaceAll(",", ", ") + " " + erdosNumber);
            }

        }
    }

    private void createRelationships(String input) {
        Matcher matcher = authorPattern.matcher(input.trim());
        Set<String> authors = new HashSet<String>();
        while(matcher.find()){
             authors.add(matcher.group().replaceAll(" ", ""));
        }

        for (String author : authors) {
            if(relationships.get(author) == null){
                relationships.put(author,new HashSet<String>());
            }
            for (String coauthors : authors) {
               if(!coauthors.equals(author))
                  relationships.get(author).add(coauthors);
            }
        }
    }

    /*
      Breadth first traverse our relationship graph
     */    
    private void findErdosNumber(String author) {
        if(erdosNumber.get(author) !=null) return;
        LinkedList<String> queue = new LinkedList<String>();
        queue.add(ERDOS);
        Set<String> visited = new HashSet<String>();
        visited.add(ERDOS);
        int count = 1;
        boolean found = false;
        while(!queue.isEmpty()) {
            String t = queue.poll();
            if(t.equals(author)){
                found = true;
                break;
            }
            for (String child : relationships.get(t)) {
                 if(!visited.contains(child)){
                     if(erdosNumber.get(child) == null){
                         erdosNumber.put(child,erdosNumber.get(t)+1);
                     }
                     visited.add(child);
                     queue.add(child);
                 }
            }
        }
    }

    private String getErdosNumber(String author) {
        Integer number = erdosNumber.get(author);
        if(number != null && number != Integer.MAX_VALUE){
            return String.valueOf(number);
        }else {
            return "infinity";
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
