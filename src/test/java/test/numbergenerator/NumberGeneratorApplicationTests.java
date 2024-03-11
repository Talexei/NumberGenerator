package test.numbergenerator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class NumberGeneratorApplicationTests {
    @Autowired
    private CarNumberRepository carNumberRepository;
    private String carNumberRegex = "[А,Е,Т,О,Р,Н,У,К,Х,С,В,М]\\d{3}[А,Е,Т,О,Р,Н,У,К,Х,С,В,М]{2} 116 RUS";
    @Test
    void verifyCarNumberFormatRandom() {
        String carNumber = carNumberRepository.getRandom().toString();
        Assert.isTrue(carNumber.matches(carNumberRegex), "Номер не прошел проверку на шаблон");
    }
    @Test
    void verifyCarNumberFormatNext() {
        String carNumber = carNumberRepository.getNext().toString();
        Assert.isTrue(carNumber.matches(carNumberRegex), "Номер не прошел проверку на шаблон");
    }

}