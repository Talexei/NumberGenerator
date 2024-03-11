package test.numbergenerator;

/**
 * Интерфейс для работы с генератором автомобильных номеров
 * @author Talexei
 */
public interface CarNumberRepository {

    /**
     * Проверка свободен ли номер
     * @param carNumber автомобильный номер {@link CarNumber}
     * @return True - номер можно выдать
     */
    public boolean isAvailable(CarNumber carNumber);
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
    /**
     *
     * @return Последний сгенерированный автомобильный номер {@link CarNumber}
     */
    public CarNumber getCurrent();
    /**
     * Сохранить выданный автомобильный номер
     *
     * @param carNumber автомобильный номер {@link CarNumber}
     * @return
     */
    public int save(CarNumber carNumber);
}
