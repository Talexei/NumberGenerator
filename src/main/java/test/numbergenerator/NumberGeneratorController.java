package test.numbergenerator;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberGeneratorController {
    private final CarNumberRepository carNumberRepository;

    public NumberGeneratorController(CarNumberRepository carNumberRepository){
        this.carNumberRepository = carNumberRepository;
    }
    @GetMapping(value= "/random", produces="text/plain")
    @Transactional
    public ResponseEntity<?> random(){
        var carNumber = carNumberRepository.getRandom();
        if (carNumber == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(carNumber.toString());
        }
    }
    @GetMapping(value= "/next", produces="text/plain")
    @Transactional
    public ResponseEntity<?> next(){
        var carNumber = carNumberRepository.getNext();
        if (carNumber == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(carNumber.toString());
        }
    }
}
