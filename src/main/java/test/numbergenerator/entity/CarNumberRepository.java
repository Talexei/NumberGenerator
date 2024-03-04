package test.numbergenerator.entity;

/**
 * Интерфейс для работы с генератором автомобильных номеров
 * @author Talexei
 */
public interface CarNumberRepository {
    /**
     *
     * @return Случайный автомобильный номер {@link CarNumber}
     */
    public CarNumber getRandom();
    /**
     *
     * @return Следующий по алфавитному порядку автомобильный номер {@link CarNumber}
     */
    public CarNumber getNext();
}
