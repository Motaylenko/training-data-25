
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекціями типу ArrayList для числових даних.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив чисел.</li>
 *   <li>{@link #findInArray()} - Здійснює пошук елемента в масиві.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає найменше і найбільше значення в масиві.</li>
 *   <li>{@link #sortList()} - Сортує колекцію List.</li>
 *   <li>{@link #findInList()} - Пошук конкретного значення в списку.</li>
 *   <li>{@link #locateMinMaxInList()} - Пошук мінімального і максимального значення в списку.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private double doubleValueToSearch;
    private Double[] doubleArray;
    private List<Double> doubleList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param doubleValueToSearch Значення для пошуку
     * @param numbersArray Масив чисел
     */
    BasicDataOperationUsingList(double doubleValueToSearch, Double[] doubleArray) {
        this.doubleValueToSearch = doubleValueToSearch;
        this.doubleArray = doubleArray.clone();
        this.doubleList = new LinkedList<>();
        
        // Заповнюємо список даними з масиву
        for (double value : doubleArray) {
            this.doubleList.add(value);
        }
    }
    
    /**
     * Виконує комплексні операції з структурами даних.
     * 
     * Метод завантажує масив і список чисел, 
     * здійснює сортування та пошукові операції.
     */
    public void executeDataOperations() {
        // спочатку працюємо з колекцією List
        findInList();
        locateMinMaxInList();
        
        sortList();
        
        findInList();
        locateMinMaxInList();

        // потім обробляємо масив дійсного числа
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        
        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(doubleArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив чисел за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();
        doubleArray = Arrays.stream(doubleArray)
                              .sorted()
                              .toArray(Double[]::new);


        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дійсного числа");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дійсного числа.
     */
    void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.stream(doubleArray)
                .map(Arrays.asList(doubleArray)::indexOf)
                .filter(i -> doubleValueToSearch == doubleArray[i])
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дійсного числа");

        if (position >= 0) {
            System.out.println("Елемент '" + doubleValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + doubleValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві дійсного числа.
     */
    void locateMinMaxInArray() {
        if (doubleArray == null || doubleArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        double min = Arrays.stream(doubleArray)
                                  .mapToDouble(Double::doubleValue)
                                  .min()
                                  .orElse(0.0);


        double max = Arrays.stream(doubleArray)
                                  .mapToDouble(Double::doubleValue)
                                  .max()
                                  .orElse(0.0);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + min);
        System.out.println("Найбільше значення в масивi: " + max);
    }

    /**
     * Шукає конкретне значення дійсного числа в колекції ArrayList.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = doubleList.stream()
            .map(doubleList::indexOf)
            .filter(i -> doubleValueToSearch == doubleList.get(i))
            .findFirst()
            .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в List дійсного числа");        

        if (position >= 0) {
            System.out.println("Елемент '" + doubleValueToSearch + "' знайдено в ArrayList за позицією: " + position);
        } else {
            System.out.println("Елемент '" + doubleValueToSearch + "' відсутній в ArrayList.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції ArrayList з датами.
     */
    void locateMinMaxInList() {
        if (doubleList == null || doubleList.isEmpty()) {
            System.out.println("Колекція ArrayList є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();

        double minValue = Collections.min(doubleList);
        double maxValue = Collections.max(doubleList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в List");

        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    /**
     * Упорядковує колекцію List з числами за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        doubleList = doubleList.stream()
                .sorted()
                .collect(java.util.stream.Collectors.toList());

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування ArrayList дійсного числа");
    }
}