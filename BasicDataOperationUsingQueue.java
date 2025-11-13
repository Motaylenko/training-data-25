
import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue реалізує роботу з колекціями типу Queue для числових даних.
 * 
 * <p>Основні функції класу:</p>
 * <ul>
 *   <li>{@link #runDataProcessing()} - Запускає комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив чисел.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить мінімальне і максимальне значення в масиві.</li>
 *   <li>{@link #findInQueue()} - Пошук значення в черзі.</li>
 *   <li>{@link #locateMinMaxInQueue()} - Знаходить граничні значення в черзі.</li>
 *   <li>{@link #performQueueOperations()} - Виконує операції peek і poll з чергою.</li>
 * </ul>
 * 
 */
public class BasicDataOperationUsingQueue {
    private double dateTimeValueToSearch;
    private Double[] dateTimeArray;
    private Queue<Double> dateTimeQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param dateTimeValueToSearch Значення для пошуку
     * @param dateTimeArray Масив числових даних
     */
    BasicDataOperationUsingQueue(double dateTimeValueToSearch, Double[] dateTimeArray) {
        // Перетворюємо рядок в число
        this.dateTimeValueToSearch = dateTimeValueToSearch;
        this.dateTimeArray = dateTimeArray.clone();
        
        // Ініціалізуємо чергу з масиву double
        this.dateTimeQueue = new PriorityQueue<>();
        for (double value : dateTimeArray) {
            this.dateTimeQueue.add(value);
        }
    }
    
    /**
     * Запускає комплексну обробку даних з використанням черги.
     * 
     * Метод завантажує дані, виконує операції з чергою та масивом чисел.
     */
    public void runDataProcessing() {
        // спочатку обробляємо чергу дати та часу
        findInQueue();
        locateMinMaxInQueue();
        performQueueOperations();

        // потім працюємо з масивом
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(dateTimeArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив чисел та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void performArraySorting() {
        // вимірюємо тривалість упорядкування масиву дати та часу
        long timeStart = System.nanoTime();

        Arrays.sort(dateTimeArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дати i часу");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    private void findInArray() {
        // відстежуємо час виконання пошуку в масиві
        long timeStart = System.nanoTime();
        
        int position = Arrays.binarySearch(this.dateTimeArray, dateTimeValueToSearch);
        
        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дати i часу");

        if (position >= 0) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві чисел.
     */
    private void locateMinMaxInArray() {
        if (dateTimeArray == null || dateTimeArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        // відстежуємо час на визначення граничних значень
        long timeStart = System.nanoTime();

        double minValue = dateTimeArray[0];
        double maxValue = dateTimeArray[0];

        for (double currentValue : dateTimeArray) {
            if (currentValue < minValue) {
                minValue = currentValue;
            }
            if (currentValue > maxValue) {
                maxValue = currentValue;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в масивi");

        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    /**
     * Здійснює пошук конкретного значення в черзі дати та часу.
     */
    private void findInQueue() {
        // вимірюємо час пошуку в черзі
        long timeStart = System.nanoTime();

        boolean elementExists = this.dateTimeQueue.contains(dateTimeValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в Queue дати i часу");

        if (elementExists) {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Елемент '" + dateTimeValueToSearch + "' відсутній в Queue.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в черзі чисел.
     */
    private void locateMinMaxInQueue() {
        if (dateTimeQueue == null || dateTimeQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // відстежуємо час пошуку граничних значень
        long timeStart = System.nanoTime();

        double minValue = Collections.min(dateTimeQueue);
        double maxValue = Collections.max(dateTimeQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в Queue");

        System.out.println("Найменше значення в Queue: " + minValue);
        System.out.println("Найбільше значення в Queue: " + maxValue);
    }

    /**
     * Виконує операції peek і poll з чергою чисел.
     */
    private void performQueueOperations() {
        if (dateTimeQueue == null || dateTimeQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        double headElement = dateTimeQueue.peek();
        System.out.println("Головний елемент черги (peek): " + headElement);

        headElement = dateTimeQueue.poll();
        System.out.println("Видалений елемент черги (poll): " + headElement);

        headElement = dateTimeQueue.peek();
        System.out.println("Новий головний елемент черги: " + headElement);
    }
}