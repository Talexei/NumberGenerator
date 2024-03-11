package test.numbergenerator;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
@Repository
public class JdbcOperationsCarNumber implements CarNumberRepository, RowMapper<CarNumber> {
    private final static int LETTER_COUNT = 3;
    private final static int MAX_DIGIT = 999;
    private final JdbcOperations jdbcOperations;
    private final NumberLetters numberLetters = new NumberLetters();
    private final Random random = new Random();
    public JdbcOperationsCarNumber(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public boolean isAvailable(CarNumber carNumber){
        return this.jdbcOperations.query("""
                select * from inovus.t_car_numbers where letters = ? and digits = ?
                """,
                this,
                carNumber.getLetters(), carNumber.getDigits())
                .stream().findFirst().isEmpty();
    }

    @Override
    public CarNumber getRandom() {

        CarNumber carNumber = new CarNumber(numberLetters.getRandomLetters(LETTER_COUNT),
                random.nextInt(MAX_DIGIT + 1));
        while (carNumber != null && !isAvailable(carNumber)) {
            carNumber = getNext(carNumber);
        }
        if (carNumber != null) {
            if (save(carNumber) == 0){
                carNumber = null;
            }
        }

        return carNumber;
    }

    @Override
    public CarNumber getNext() {
        CarNumber carNumber = getCurrent();
        if (carNumber == null){
            char[] letters = new char[LETTER_COUNT];
            Arrays.fill(letters, (char)numberLetters.getLetter(0));
            carNumber = new CarNumber(letters, 0);
        }else{
            carNumber = getNext(carNumber);
        }
        if (carNumber != null) {
            if (save(carNumber) == 0) {
                carNumber = null;
            }
        }
        return carNumber;
    }

    private CarNumber getNext(CarNumber carNumber) {
        boolean failed = true;
        int digits = carNumber.getDigits();
        char[] letters = carNumber.getLetters().toCharArray();
        if (digits < MAX_DIGIT){
            digits++;
            failed = false;
        }else if(digits == MAX_DIGIT){
            digits = 0;
            for (int i = letters.length - 1; i >= 0; i--){
                Object nextLetter = numberLetters.getNextLetter(letters[i]);
                if (nextLetter != null){
                    letters[i] = (char)nextLetter;
                    failed = false;
                    break;
                }else{
                    letters[i] = (char)numberLetters.getLetter(0);
                }
            }
        }
        return failed ? null : new CarNumber(letters, digits);
    }

    @Override
    public CarNumber getCurrent() {
        return this.jdbcOperations.query("""
                select * from inovus.t_car_numbers order by id desc
                """, this)
                .stream().findFirst().orElse(null);
    }

    public int save(CarNumber carNumber){
        return this.jdbcOperations.
                update("""
                        insert into inovus.t_car_numbers (letters, digits) values(?, ?)
                        """,
                        carNumber.getLetters(), carNumber.getDigits());
    }
    @Override
    public CarNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CarNumber(rs.getString("letters"), rs.getInt("digits"));
    }
}
