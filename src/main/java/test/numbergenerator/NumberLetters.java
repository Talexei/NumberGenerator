package test.numbergenerator;

import java.util.Arrays;
import java.util.Random;

public class NumberLetters {
    private static char[] availableChars;
    public NumberLetters(){
        this("");
    }
    public NumberLetters(String availableCharsString){
        if (!availableCharsString.isEmpty() && !availableCharsString.isBlank()){
            availableChars = availableCharsString.toCharArray();
        }else{
            availableChars = new char[]{'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
        }
        Arrays.sort(availableChars);
    }
    public Object getLetter(int i){
        if (i >= 0 && i < availableChars.length - 1){
            return availableChars[i];
        }else{
            return null;
        }
    }
    public Object getNextLetter(int i){
        return getLetter(i+1);
    }
    public Object getNextLetter(String letter){
        int i = String.valueOf(availableChars).indexOf(letter);
        return getNextLetter(i);
    }
    public Object getNextLetter(char letter){
        return getNextLetter(String.valueOf(letter));
    }
    private char getRandomLetter(){
        Random r = new Random();
        return availableChars[r.nextInt(availableChars.length)];
    }
    public String getRandomLetters(int count){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < count; i++){
            result.append(getRandomLetter());
        }
        return result.toString();
    }
}
