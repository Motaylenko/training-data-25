/**
 * Клас PerformanceTracker відстежує продуктивність операцій з даними.
 */
public class PerformanceTracker {
    /**
     * Відображає тривалість виконання операції в мікросекундах.
     * 
     * @param startTime Початковий час операції в наносекундах.
     * @param operationName Назва операції.
     */
    public static void displayOperationTime(long startTime, String operationName) {
        long finishTime = System.nanoTime();
        long executionTime = (finishTime - startTime) / 1000;  // Конвертуємо в мікросекунди
        System.out.println("\n========= Тривалість операції '" + operationName + "': " + executionTime + " мкс =========");
    }
}