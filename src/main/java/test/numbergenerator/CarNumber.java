package test.numbergenerator;

import java.util.Arrays;

public class CarNumber implements Comparable<CarNumber>{
    private static final String region = "116";
    private static final String country = "RUS";

    private final String letters;
    private final int digits;

    public CarNumber(char[] letters, int digits) {
        this(String.valueOf(letters), digits);
    }
    public CarNumber(String letters, int digits) {
        this.letters = letters;
        this.digits = digits;
    }

    public String getLetters() {
        return letters;
    }

    public int getDigits() {
        return digits;
    }

    @Override
    public String toString(){
        char[] numberChars = letters.toCharArray();
        return numberChars[0] + String.format("%03d", digits) +
                numberChars[1] + numberChars[2] + " " + region + " " +
                country;
    }

    @Override public int compareTo(CarNumber carNumber)
    {
        if (this.letters.compareTo(carNumber.letters) != 0) {
            return this.letters.compareTo(carNumber.letters);
        }
        else {
            return this.digits - carNumber.digits;
        }
    }
}
