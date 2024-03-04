package test.numbergenerator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.numbergenerator.entity.CarNumberRepository;

@RestController
public class NumberGeneratorController {
    private final CarNumberRepository carNumberRepository;

    public NumberGeneratorController(CarNumberRepository carNumberRepository){
        this.carNumberRepository = carNumberRepository;
    }
    @GetMapping(value= "/random", produces="text/plain")
    public ResponseEntity<?> random(){
        return ResponseEntity.ok(carNumberRepository.getRandom().toString());
    }
    @GetMapping(value= "/next", produces="text/plain")
    public ResponseEntity<?> next(){
        return ResponseEntity.ok(carNumberRepository.getNext().toString());
    }
}
