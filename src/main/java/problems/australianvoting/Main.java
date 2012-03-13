package problems.australianvoting;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {

    public static final int MAX_LG = 10000;
    private List<Election> elections;

    
    public void solution() {
        elections = new ArrayList<Election>();
        String input = Main.readLn(MAX_LG);
        int numberOfElections = Integer.parseInt(input.trim());
        Main.readLn(MAX_LG);//one line after elections size
        readElectionData(numberOfElections);
        Iterator<Election> i = elections.iterator();
        while (i.hasNext()) {
            Election election = i.next();
            election.findWinner();
            if(i.hasNext())
                out.println();// one empty line after each result
        }
    }
    
    private void readElectionData(int n) {
        for(int i=0;i<n;i++){
            Election election = new Election();
            readCandidates(election);
            readVotes(election);
            elections.add(election);
        }
    }

    private void readCandidates(Election election) {
        String input = Main.readLn(MAX_LG);
        while(input == null || input.equals(""))
            input = Main.readLn(MAX_LG);
        int numberOfCandidates = Integer.parseInt(input.trim());
        for (int i=0;i<numberOfCandidates;i++) {
            String name = Main.readLn(MAX_LG);
            election.addCandidate(name);
        }
    }

    private void readVotes(Election election) {
        String voteRow;

        while ((voteRow = Main.readLn(MAX_LG)) != null) {
           if(voteRow.equals("")) return;
           String[] votes = voteRow.trim().split("\\s+");
           election.vote(votes);
        }

    }

    private class Election {
        private List<Candidate> candidates;
        private List<Vote> votes;

        private Election() {
            candidates = new ArrayList<Candidate>();
            votes = new ArrayList<Vote>();
        }
        
        public void addCandidate(String name){
            candidates.add(new Candidate(name));
        }
        public void vote(String[] voteString) {
            Vote vote = new Vote(voteString,candidates);
            votes.add(vote);
        }

        public void findWinner(){
            if(votes.size() == 0 ) {
                printWinner(candidates);
                return;
            }
            findWinner(candidates);
        }
        public void findWinner(List<Candidate> candidates){
            Map<String, Integer> voteCount = countVotes(candidates);
            Map<String, Double> percentages = getPercentages(voteCount);
            Map<String, Double> fifty = candidatesOverFiftyPercent(percentages);

            if(isThereEnoughCandidatesForResolution(fifty)){
                printWinner(candidates,fifty);
            }else {
                List<String> min = findMinCandidates(candidates, percentages);
                if(candidates.size() - min.size() == 0){ //It is tied si we must print solution before removing all candidates
                    printWinner(candidates);
                    return;
                }
                removeMinFromCandidatesList(candidates, min);
                if(candidates.size() == 1 ){
                    printWinner(candidates);
                    return;
                }
                increaseVoteSelection(candidates);
                findWinner(candidates);
            }

        }

        private void removeMinFromCandidatesList(List<Candidate> candidates, List<String> min) {
            Iterator i = candidates.iterator();
            while (i.hasNext()){
                Candidate can = (Candidate) i.next();
                if (min.contains(can.getName())) {
                    i.remove();
                }
            }
        }

        private void increaseVoteSelection(List<Candidate> candidates) {
            for(Vote vote: votes) {
                while(!candidates.contains(vote.getEffectiveSelection())){
                    vote.increaseSelection();
                }
            }
        }

        private List<String> findMinCandidates(List<Candidate> candidates, Map<String, Double> percentages) {
            List<String> minCandidates = new ArrayList<String>();
            Double min = (Double) percentages.values().toArray()[0];
            for (Map.Entry<String,Double> per : percentages.entrySet()){
                if(per.getValue() <= min){
                    min = per.getValue();
                }
            }
            for (Map.Entry<String,Double> per : percentages.entrySet()){
                if(per.getValue().equals(min)){
                    minCandidates.add(per.getKey());
                }
            }
            return minCandidates;
        }

        //Stupid print methods to make judge happy
        private void printWinner(List<Candidate> candidates,Map<String, Double> fifty) {
            for(Candidate can:candidates){
                if(fifty.keySet().contains(can.getName())){
                    out.print(can.getName());
                    out.print("\n");
                }
            }
        }

        private void printWinner(List<Candidate> candidates) {
            for(Candidate can:candidates){
                out.print(can.getName());
                out.print("\n");
            }
        }

        private boolean isThereEnoughCandidatesForResolution(Map<String, Double> fifty) {
            return fifty.size() == 2 || fifty.size() == 1;
        }

        private Map<String, Double> candidatesOverFiftyPercent(Map<String, Double> percentages) {
            Map<String,Double> fifty = new HashMap<String, Double>();
            for (Map.Entry<String,Double> per : percentages.entrySet()){
                if(per.getValue() >= 50.0){
                  fifty.put(per.getKey(),per.getValue());
                }
            }
            return fifty;
        }

        private Map<String, Double> getPercentages(Map<String, Integer> voteCount) {
            Map<String,Double> percentages = new HashMap<String, Double>();
            for (Map.Entry<String,Integer> counts : voteCount.entrySet()){
                double percentage = (counts.getValue() * 100)/votes.size();
                percentages.put(counts.getKey(),percentage);
            }
            return percentages;
        }

        private Map<String, Integer> countVotes(List<Candidate> candidates) {
            Map<String, Integer> voteCount = initializeVoteCountMap(candidates);

            for(Vote vote : votes){
                int count = voteCount.get(vote.getEffectiveSelection().getName());
                count ++;
                voteCount.put(vote.getEffectiveSelection().getName(),count);
            }
            return voteCount;
        }

        private Map<String, Integer> initializeVoteCountMap(List<Candidate> candidates) {
            Map<String,Integer> voteCount = new HashMap<String, Integer>();

            for(Candidate can : candidates){
                voteCount.put(can.getName(),0);
            }
            return voteCount;
        }
    }
    
    private class Candidate {
        String name;

        private Candidate(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Candidate candidate = (Candidate) o;

            if (name != null ? !name.equals(candidate.name) : candidate.name != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
    
    private class Vote {
        private List<Candidate> selection;
        private int effectiveSelection;
        
        private Vote(String[] votes, List<Candidate> candidates) {
            this.selection = new ArrayList<Candidate>();
            for (int i=0;i<votes.length;i++){
                int candidateNo = Integer.parseInt(votes[i])-1; //Zero based index
                selection.add(candidates.get(candidateNo));
            }
            effectiveSelection = 0;
        }

        public Candidate getEffectiveSelection() {
            return selection.get(effectiveSelection);
        }
        
        public void increaseSelection() {
            this.effectiveSelection ++;
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
