package test.numbergenerator.entity;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Реализация для хранения в памяти
 */
@Repository
public class InMemCarNumberRepository implements CarNumberRepository {
    /**
     * Доступный набор символов для букв автомобильного номера
     */
    private final char[] availableChars = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
    private final String availableCharsString;
    /**
     * Максимальный номер числового
     */
    private final int MAX_NUMBER = 999;
    private final int CHAR_LENGTH = 3;
    private CarNumber currentCarNumber;
    private HashMap<String, HashSet<Integer>> usedCarNumbers = null;

    /**
     * Конструктор с инициализацией первого номера <i>А000АА 116 RUS</i>
     * и хранилища использованных автомобильных номеров.
     */
    private InMemCarNumberRepository(){
        Arrays.sort(availableChars);
        availableCharsString = String.valueOf(availableChars);
        char[] charNumber = new char[CHAR_LENGTH];
        for (int n = CHAR_LENGTH; n > 0; n--)
            charNumber[n - 1] = availableChars[0];
        currentCarNumber = new CarNumber(String.valueOf(charNumber), 0);

        usedCarNumbers = new HashMap<>();
    }

    /**
     * Проверка на повторную выдачу номера
     * @param carNumber автомобильный номер {@link CarNumber}
     * @return <i>true</i> уже выдан
     */
    private boolean isExistNumber(CarNumber carNumber){
        boolean existNumber = false;
        if (usedCarNumbers.containsKey(carNumber.chars())) {
            if (usedCarNumbers.get(carNumber.chars()).contains(carNumber.digits())) {
                existNumber = true;
            }
        }
        return existNumber;
    }

    /**
     * Сохранение в памяти выданного автомобильного номера
     * @param carNumber автомобильный номер {@link CarNumber}
     * @return <i>true</i> - Сохранение успешно
     */
    private boolean saveCarNumber(CarNumber carNumber){
        boolean saveResult = false;
        if (isExistNumber(carNumber)){
            return saveResult;
        }else {
            if (usedCarNumbers.containsKey(carNumber.chars())) {
                usedCarNumbers.get(carNumber.chars()).add(carNumber.digits());
            } else {
                usedCarNumbers.put(carNumber.chars(), new HashSet<>(carNumber.digits()));
            }
            saveResult = true;
        }
        return saveResult;
    }

    /**
     * @see CarNumberRepository#getRandom()
     * @return Автомобильный номер {@link CarNumber}
     */
    @Override
    public CarNumber getRandom(){
        char[] charNumber = new char[CHAR_LENGTH];
        for (int n = CHAR_LENGTH - 1; n >= 0; n--)
            charNumber[n] = availableChars[new Random().nextInt(availableChars.length - 1)];
        int digits = new Random().nextInt(MAX_NUMBER);
        CarNumber randomCarNumber = new CarNumber(String.valueOf(charNumber), digits);
        if (isExistNumber(randomCarNumber)) {
            return getRandom();//TODO подумать, как все-таки случайно перебрать из свободных номеров.
        }else {
            if (saveCarNumber(randomCarNumber)){
                currentCarNumber = randomCarNumber;
            }else{
                throw new RuntimeException("Ошибка сохранения номера.");
            }
        }
        return randomCarNumber;
    }

    /**
     * @see CarNumberRepository#getNext()
     * @return Автомобильный номер {@link CarNumber}
     */
    private CarNumber getNext(CarNumber currentCarNumber) {
        int digits;
        char[] chars = currentCarNumber.chars().toCharArray();
        if (currentCarNumber.digits() != MAX_NUMBER) {
            digits = currentCarNumber.digits() + 1;
        } else {
            digits = 0;
            int countAvailableChars = availableChars.length;
            boolean switch_char = false;
            int n = (chars.length - 1);
            while ((!switch_char) && (n >= 0)) {
                int charPosition = availableCharsString.indexOf(chars[n]);
                if (charPosition != -1) {
                    if (charPosition == (countAvailableChars - 1)) {
                        chars[n] = availableChars[0];
                        n--;
                    } else {
                        chars[n] = availableChars[charPosition + 1];
                        switch_char = true;
                    }
                } else {
                    throw new RuntimeException("Не верный символ в номере");
                }
            }
            if (!switch_char) {
                if (n < 0) {
                    throw new RuntimeException("Достигнут максимальный номер");
                } else {
                    throw new RuntimeException("Ошибка поиска следующего номера");
                }
            }
        }
        CarNumber carNumber = new CarNumber(String.valueOf(chars), digits);
        if (isExistNumber(carNumber)) {
            return getNext(carNumber);
        }else {
            if (saveCarNumber(carNumber)) {
                return carNumber;
            } else {
                throw new RuntimeException("Ошибка сохранения номера.");
            }
        }
    }

    /**
     * @see CarNumberRepository#getNext()
     * @return Автомобильный номер {@link CarNumber}
     */
    @Override
    public CarNumber getNext(){
        return getNext(currentCarNumber);
    }
}