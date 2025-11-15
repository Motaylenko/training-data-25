
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною HashSet для числових даних.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив чисел.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    private double doubleValueToSearch;
    private Double[] doubleArray;
    private Set<Double> doubleSet;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param doubleValueToSearch Значення для пошуку
     * @param doubleArray Масив числових даних
     */
    BasicDataOperationUsingSet(double doubleValueToSearch, Double[] doubleArray) {
        this.doubleValueToSearch = doubleValueToSearch;
        this.doubleArray = doubleArray.clone();
        this.doubleSet = new HashSet<>();
        for (double value : doubleArray) {
            this.doubleSet.add(value);
        }
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини HashSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом чисел.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину дійсного числа
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(doubleArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив чисел за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(doubleArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву дійсного числа");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дійсного числа.
     */
    private void findInArray() {
        long timeStart = System.nanoTime();

        int position = Arrays.binarySearch(this.doubleArray, doubleValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в масивi дійсного числа");

        if (position >= 0) {
            System.out.println("Елемент '" + doubleValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + doubleValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в масиві чисел.
     */
    private void locateMinMaxInArray() {
        if (doubleArray == null || doubleArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        double minValue = doubleArray[0];
        double maxValue = doubleArray[0];

        for (double currentValue : doubleArray) {
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
     * Здійснює пошук конкретного значення в множині дійсного числа.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();

        boolean elementExists = this.doubleSet.contains(doubleValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук елемента в LinkedHashSet дійсного числа");

        if (elementExists) {
            System.out.println("Елемент '" + doubleValueToSearch + "' знайдено в LinkedHashSet");
        } else {
            System.out.println("Елемент '" + doubleValueToSearch + "' відсутній в LinkedHashSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині чисел.
     */
    private void locateMinMaxInSet() {
        if (doubleSet == null || doubleSet.isEmpty()) {
            System.out.println("LinkedHashSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        double minValue = Collections.min(doubleSet);
        double maxValue = Collections.max(doubleSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмальної i максимальної дати в LinkedHashSet");

        System.out.println("Найменше значення в LinkedHashSet: " + minValue);
        System.out.println("Найбільше значення в LinkedHashSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + doubleArray.length);
        System.out.println("Кiлькiсть елементiв в LinkedHashSet: " + doubleSet.size());

        boolean allElementsPresent = true;
        for (double value : doubleArray) {
            if (!doubleSet.contains(value)) {
                allElementsPresent = false;
                break;
            }
        }

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в LinkedHashSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в LinkedHashSet.");
        }
    }
}