package problems.cryptkicker;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

import static java.lang.System.in;

/*******************************************************************
 *
 *
 * This solution is rubbish and wrong will fix it later with back tracking
 *
 *
 *******************************************************************
 */

public class Main {

    public void solution(){
      String input = readLn();       
      int dictionarySize = Integer.parseInt(input);
      Dictionary originalDictionary = new Dictionary();
      for(int i = 0;i<dictionarySize;i++){
        input = readLn();
          originalDictionary.addWord(input.trim());
      }
      boolean first = true;
      while ((input=readLn()) != null){
          if(!first)
          System.out.println();
          CryptText text = new CryptText(input.trim());
          Dictionary dictionary = (Dictionary) originalDictionary.clone();
          while(!text.isSolved()){
              int solvedCountStart = text.solvedCount();
              for (Map.Entry <String,String> word : text.getWords().entrySet() ) {
                  if(word.getValue() == null) {
                    String solved = dictionary.decrypt(word.getKey());
                    if(solved !=null)
                        text.addDecryptWordInfo(word.getKey(),solved);
                  }
              }
              int solvedCountEnd = text.solvedCount();
              if(solvedCountStart - solvedCountEnd == 0)// we cant solve with this strategy try back tracking
                  break;
          }

          if(!text.isSolved())
              permutateLeftOvers(text,dictionary);

          text.print();
          first = false;
      }
    }

    
    
    private void permutateLeftOvers(CryptText text,Dictionary dictionary) {
        Map<Integer,Set<String>> unresolvedCryptWords = text.getUnsolvedWords();
        Set<String> allc = new HashSet<String>();
        for (Set<String> strings : unresolvedCryptWords.values()) {
            allc.addAll(strings);
        }
        Map<Integer,Set<String>> unresolvedDictionaryWords = dictionary.getWordsBySize();
        Set<String> allo = new HashSet<String>();
        for (Set<String> strings : unresolvedDictionaryWords.values()) {
            allo.addAll(strings);
        }
        
        int i = 0;
        while (!text.isSolved())  {
            CryptAlphabet alphabet = dictionary.alphabet.clone();
            for(String unresolved : allc){                  
                Set<String> candidates = dictionary.findSameLengthAndSameNumberOfDuplicatesInSamePlaces(unresolved,unresolvedDictionaryWords.get(unresolved.length()));
                if(candidates.size() == 0)
                    candidates = dictionary.findSameLengthAndSameNumberOfUniqueChars(unresolved,unresolvedDictionaryWords.get(unresolved.length()));
                
                   String original = (String) candidates.toArray()[i % candidates.size()];
                   alphabet.updateAlphabetWithDecryptInfo(original, unresolved);
            }

                boolean allDone = true;
                for (String s : allc) {
                    Set<String> candidates = dictionary.findSameLengthAndSameNumberOfUniqueChars(s,unresolvedDictionaryWords.get(s.length()));
                    if(alphabet.decryptByLetterFromOptions(s,candidates) == null ){
                        allDone =false;
                        break;
                    }
                }
    
                if(allDone){
                    for (String s : allc) {
                        text.addDecryptWordInfo(s,alphabet.decryptByLetter(s));
                    }                    
                }
          i++;  
        }

    }

    private void processSolution(Set<String> allc,CryptText text, CryptAlphabet alphabet){
        for (String s : allc) {
            text.addDecryptWordInfo(s,alphabet.decryptByLetter(s));
        }
    }

    private boolean isSolution(Set<String> allc,Set<String> allo,CryptAlphabet alphabet) {
        boolean allDone = true;
        for (String s : allc) {
            String ac = alphabet.decryptByLetter(s);
            if(alphabet.decryptByLetter(s) == null || !allo.contains(ac)){
                allDone =false;
            }
        }
        return true;
    }

/*    public static void shuffle(String dummy, String input, CryptText text, Dictionary dictionary ){
        if(input.length() <= 1)   {
            CryptAlphabet alphabet = dictionary.alphabet.clone();
            String unresolvedCrypt = alphabet.getUnsolvedCryptLetters();
            String unresolvedOriginal = dummy+input;
            alphabet.updateAlphabetWithDecryptInfo(unresolvedOriginal,unresolvedCrypt);
            Map<Integer,Set<String>> unresolvedCryptWords = text.getUnsolvedWords();
            Map<Integer,Set<String>> unresolvedDictionaryWords = dictionary.getWordsBySize();

            Map<String,String> resolved = new HashMap<String,String>();
            for (Integer size : unresolvedCryptWords.keySet()) {                
                for (String crypt: unresolvedCryptWords.get(size)){
                    if(! alphabet.decryptByLetterAndValidate(crypt,unresolvedDictionaryWords.get(size))){
                        return;
                    }else {
                        resolved.put(crypt,alphabet.decryptByLetter(crypt));
                    }
                }
            }
            text.words.putAll(resolved);
        }else{
            for(int i=0; i <input.length();i++){
                input = input.substring(i,i+1) + input.substring(0,i) + input.substring(i+1);
                shuffle(dummy+input.substring(0,1),input.substring(1),text,dictionary);
            }
        }
    } */


    public static class CryptText {
        private Map<String,String> words;
        private String[] realLine;
        
        public CryptText(String line) {
            this.words = new TreeMap<String, String>();
            String[] wordList = line.split("\\s+");
            realLine = wordList;
            for (String word : wordList) {
               addWord(word); 
            }
        }
        public void addWord(String word){
           this.words.put(word,null);
        }
        public void addDecryptWordInfo(String word,String decrypt){
            this.words.put(word,decrypt);
        }
        public boolean isSolved(){
            for (String s : words.values()) {
                 if(s == null)
                     return false;
            }
            return true;
        }
        public int solvedCount(){
            int count = 0;
            for (String s : words.values()) {
                if(s != null)
                    count++;
            }
            return count;
        }
        public Map<String, String> getWords() {
            return words;
        }

        public Map<Integer,Set<String>> getUnsolvedWords() {
            Map<Integer,Set<String>> results = new HashMap<Integer, Set<String>>();
            for (String s : words.keySet()) {
                 if(words.get(s) == null){
                     if(results.get(s.length()) == null) {
                        results.put(s.length(),new HashSet<String>());
                     }
                     results.get(s.length()).add(s);
                 }
            }
            return results;
        }
        
        public void print(){            
            StringBuilder line = new StringBuilder();
            if(isSolved()){
                for (String s : realLine) {
                    line.append(words.get(s));
                    line.append(' ');
                }
            }else {
                for (String s : realLine) {
                    for(int i = 0;i<s.length();i++)
                        line.append('*');
                        line.append(' ');
                }
            }
            String lineString = line.toString();
            System.out.print(lineString.trim());
        }
    }

    public static class Dictionary{
        private static final int MAX_WORD_SIZE = 16; 
        private Map<Integer,Set<String>> wordsBySize;
        private Map<String,String> alreadyFound;                
        private CryptAlphabet alphabet;
        
        public Dictionary() {
            this.wordsBySize = new HashMap<Integer,Set<String>>();
            this.alreadyFound = new HashMap<String, String>();
            this.alphabet = new CryptAlphabet();
        }

        protected Dictionary clone() {
            Dictionary clone = new Dictionary();
            Map<Integer,Set<String>> cloneWordsBySize = new HashMap<Integer, Set<String>>();
            for (Integer key : this.wordsBySize.keySet()) {
                Set<String> cloneSet = new HashSet<String>();
                cloneSet.addAll(this.wordsBySize.get(key));
                cloneWordsBySize.put(key,cloneSet);
            }
            clone.wordsBySize = cloneWordsBySize;
            clone.alreadyFound = new HashMap<String, String>();
            clone.alphabet = new CryptAlphabet();
            return clone;
        }

        public void addWord(String word){
            if(word.length()>MAX_WORD_SIZE) return;
            int size = word.length();
            if(wordsBySize.get(size) == null){
                wordsBySize.put(size, new HashSet<String>());
            }
            wordsBySize.get(size).add(word);
        }

        public String decrypt(String text){
            if(alreadyFound.get(text) != null) {
                return alreadyFound.get(text);
            }else if(alphabet.canDecryptByLetter(text)){
                String found = alphabet.decryptByLetter(text);
                updateCache(text, found);
                return found;
            }

            Set<String> similarLength = wordsBySize.get(text.length());
            similarLength.removeAll(alreadyFound.values());

            String found = alphabet.decryptByLetterFromOptions(text,similarLength);

            if(found != null){
                updateCache(text, found);
                return found;
            }

            if(isThereOnlyOneOption(similarLength)) {
                return cacheAndReturn(text, similarLength);
            }else {
                Set<String> similarLengthAndUnique = findSameLengthAndSameNumberOfUniqueChars(text, similarLength);
                if(isThereOnlyOneOption(similarLengthAndUnique)) {
                    return cacheAndReturn(text, similarLengthAndUnique);
                }else if (similarLengthAndUnique.size() > 1) {
                    Set<String> similarLengthAndUniqueSamePlace = findSameLengthAndSameNumberOfDuplicatesInSamePlaces(text, similarLengthAndUnique);
                    if(isThereOnlyOneOption(similarLengthAndUniqueSamePlace)){
                        return cacheAndReturn(text, similarLengthAndUniqueSamePlace);                       
                    }
                }
            }
            return null;
        }

        private String cacheAndReturn(String text, Set<String> oneItemSet) {
            String found = (String) oneItemSet.toArray()[0];
            updateCache(text,found);
            return found;
        }

        private void updateCache(String crypt,String original){
            alreadyFound.put(crypt,original);
            alphabet.updateAlphabetWithDecryptInfo(original, crypt);
        }
        
        public Set<String> findSameLengthAndSameNumberOfDuplicatesInSamePlaces(String text, Set<String> similarLengthAndUnique) {
            Map<Character,Set<Integer>> dups = getDuplicateLocations(text);
            Set<String> similarLengthAndUniqueSamePlace = new HashSet<String>();
            if(dups.size() > 0){
                for (String candidate : similarLengthAndUnique) {
                    iterateAllSameNumberDuplicates(dups, similarLengthAndUniqueSamePlace, candidate);
                }
            }
            return similarLengthAndUniqueSamePlace;
        }

        private void iterateAllSameNumberDuplicates(Map<Character, Set<Integer>> dups, Set<String> similarLengthAndUniqueSamePlace, String candidate) {
            Map<Character,Set<Integer>> dupsForCandidate = getDuplicateLocations(candidate);
            if(dupsForCandidate.size() > 0 && dupsForCandidate.size() == dups.size()){
                boolean same = true;
                Iterator<Set<Integer>> idups = dups.values().iterator();
                Iterator<Set<Integer>> icandidate = dupsForCandidate.values().iterator();
                while (idups.hasNext() && icandidate.hasNext()) {
                    if(!icandidate.next().equals(idups.next())) {
                        same = false;
                        break;
                    }
                }
                if(same)
                    similarLengthAndUniqueSamePlace.add(candidate);
            }
        }

        private Set<String> findSameLengthAndSameNumberOfUniqueChars(String text, Set<String> similarLength) {
            int uniqueCharSize = findUniqueCharSize(text);
            Set<String> similarLengthAndUnique = new HashSet<String>();
            for (String s : similarLength) {
                 if(findUniqueCharSize(s) == uniqueCharSize)
                     similarLengthAndUnique.add(s);
            }
            return similarLengthAndUnique;
        }

        private boolean isThereOnlyOneOption(Set<String> similarLength) {
            return similarLength.size() == 1;
        }

        public Map<Character,Set<Integer>> getDuplicateLocations(String text){
            Map<Character,Set<Integer>> results = new HashMap<Character, Set<Integer>>();
            char[] arr = text.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                boolean dup = false;
                char original = arr[i];
                for (int j = i+1; j < arr.length; j++) {
                    char current = arr[j];
                    if(original == current){
                        dup = true;
                        if(results.get(original) == null){
                            results.put(original, new HashSet<Integer>());
                        }
                        results.get(original).add(j);
                    }
                }
                if(dup)
                    results.get(original).add(i);
            }
            return results;
        }
        
        private int findUniqueCharSize(String text) {
            Set chars = new HashSet();
            for (char aChar : text.toCharArray()) {
                chars.add(aChar);
            }
            return chars.size();
        }

        public Map<Integer, Set<String>> getWordsBySize() {
            return wordsBySize;
        }
    }

    public static class CryptAlphabet {
        private static final Character[] ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        private CryptLetter[] letters;

        public CryptAlphabet( ) {
            letters = new CryptLetter[ALPHABET.length];
            for (int i=0;i<ALPHABET.length;i++) {
               letters[i]= new CryptLetter(ALPHABET[i]);
            }
        }
        
        public CryptAlphabet clone() {
            CryptAlphabet clone = new CryptAlphabet();
            clone.letters = new CryptLetter[ALPHABET.length];
            for (int i = 0; i < letters.length; i++) {
                CryptLetter letter = new CryptLetter(letters[i].getOriginalChar());
                letter.setCryptChar(letters[i].getCryptChar());
                clone.letters[i] = letter;
            }
            return clone;
        }
        
        public String getUnsolvedOriginalLetters() {
            StringBuilder builder = new StringBuilder();            
            for (int i = 0; i < letters.length; i++) {
                if(!letters[i].isDecrypted())
                    builder.append(letters[i].getOriginalChar());
            }
            return builder.toString();            
        }

        public String getUnsolvedCryptLetters() {
            StringBuilder builder = new StringBuilder();
            Set<Character> abc = new HashSet<Character>();
            abc.addAll(Arrays.asList(ALPHABET));
            for (int i = 0; i < letters.length; i++) {
                if(letters[i].isDecrypted())
                    abc.remove(letters[i].getCryptChar());
            }
            for (Character character : abc) {
                  builder.append(character);
            }
            return builder.toString();
        }
        

        public boolean isSolved(){
            for (CryptLetter c : letters) {
                if(!c.isDecrypted()){
                    return false;
                }
            }
            return true;
        }

        public void updateAlphabetWithDecryptInfo(String original, String crypt) {
            if(original != null && crypt != null && original.length() == crypt.length()){
                for (int i = 0; i < original.length(); i++) {
                    char originalChar =  original.charAt(i);
                    char cryptChar =  crypt.charAt(i);
                    CryptLetter cryptLetter = getCryptLetterByOriginalChar(originalChar);
                    if(cryptLetter != null && !cryptLetter.isDecrypted())
                        cryptLetter.setCryptChar(cryptChar);
                }
            }
        }

        public CryptLetter getCryptLetterByOriginalChar(char key){
            for (int i = 0; i < letters.length; i++) {
                CryptLetter current = letters[i];
                if(current.getOriginalChar() == key){
                    return current;
                }
            }
            return null;
        }

        public CryptLetter getCryptLetterByCryptChar(char key){
            for (int i = 0; i < letters.length; i++) {
                CryptLetter current = letters[i];
                if(current.getCryptChar() == key){
                    return current;
                }
            }
            return null;
        }

        //bloody ugly stuff room for huge improvement maybe Hamming distance or something
        public String decryptByLetterFromOptions(String text,Set<String> options){
            char[] decryptSoFar = decryptPartially(text);

            int maxResemblance = 0;
            int[] resemblances = new int[options.size()];
            String [] optionsArray = options.toArray(new String[options.size()]);
            for(int j=0;j<optionsArray.length;j++){
                int count = 0;
                for(int i=0;i<text.length();i++){
                    if(decryptSoFar[i] != 0 && decryptSoFar[i] == optionsArray[j].charAt(i)){
                        count++;
                    }
                }
                resemblances[j] = count;
                maxResemblance = Math.max(count,maxResemblance);
            }
            
            int maxResemblanceCount = 0;
            int maxResemblanceIndex = 0;
            for(int i=0;i<resemblances.length;i++){
                if(resemblances[i] == maxResemblance){
                    maxResemblanceCount++;
                    maxResemblanceIndex = i;
                }
            }
            if(maxResemblanceCount == 1){
                return optionsArray[maxResemblanceIndex];
            }else {
                return null;
            }
        }

        private char[] decryptPartially(String text) {
            char[] decryptSoFar = new char[text.length()];
            for(int i=0;i<text.length();i++){
                if(getCryptLetterByCryptChar(text.charAt(i)) !=null)
                    decryptSoFar[i] = getCryptLetterByCryptChar(text.charAt(i)).getOriginalChar();
            }
            return decryptSoFar;
        }

        public boolean canDecryptByLetter(String text){
            for(int i=0;i<text.length();i++){
                if(getCryptLetterByCryptChar(text.charAt(i)) == null)
                    return false;
            }
            return true;
        }

        public String decryptByLetter(String text){
            if(canDecryptByLetter(text)){
                StringBuilder stringBuilder = new StringBuilder();
                for(int i=0;i<text.length();i++){
                    stringBuilder.append(getCryptLetterByCryptChar(text.charAt(i)).getOriginalChar());
                }
                return stringBuilder.toString();
            }
            return null;
        }
        public boolean decryptByLetterAndValidate(String text,Set<String> options){
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<text.length();i++){
                stringBuilder.append(getCryptLetterByCryptChar(text.charAt(i)).getOriginalChar());
            }
            return (options.contains(stringBuilder.toString()));            
        }

    }

    public static class CryptLetter {
        private char originalChar;
        private char cryptChar;

        public CryptLetter(char originalChar) {
            this.originalChar = originalChar;
            this.cryptChar = '*';
        }
        public boolean isDecrypted(){
            return !(this.cryptChar == '*');
        }
        public void setCryptChar(char cryptChar) {
            this.cryptChar = cryptChar;
        }
        public char getOriginalChar() {
            return originalChar;
        }
        public char getCryptChar() {
            return cryptChar;
        }

        @Override
        public String toString() {
            return "CryptLetter{" +
                    "originalChar=" + originalChar +
                    ", cryptChar=" + cryptChar +
                    '}';
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
