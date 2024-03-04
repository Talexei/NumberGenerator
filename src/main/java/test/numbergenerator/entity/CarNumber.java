package test.numbergenerator.entity;

/**
 * Автомобильный номер со свойствами <b>chars</b> <b>digits</b> <b>region</b> <b>country</b>
 * @author Talexei
 * @version 1.0
 */
public record CarNumber (String chars, int digits, String region, String country) {
    /**
     * Конструктор с предстановленными значениями <b>region</b>: <i>116</i> <b>country</b>: <i>RUS</i>
     * @param chars буквенные символы номера
     * @param digits цифровые символы номера
     * @see CarNumber#CarNumber(String, int)
     */
    public CarNumber(String chars, int digits) {
        this(chars, digits, "116", "RUS");
    }

    /**
     *
     * @return Вывод в стандарте автомобильного номера
     */
    @Override
    public String toString() {
        char[] numberChars = chars.toCharArray();
        return numberChars[0] + String.format("%03d", digits) +
                numberChars[1] + numberChars[2] + " " + region + " " +
                country;
    }
}