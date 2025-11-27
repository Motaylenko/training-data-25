import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Клас DataFileHandler управляє роботою з файлами числових даних.
 */
public class DataFileHandler {
    /**
     * Завантажує масив чисел з файлу.
     * 
     * @param filePath Шлях до файлу з даними.
     * @return Масив чисел.
     */
    public static Double[] loadArrayFromFile(String filePath) {
        Double[] temporaryArray = new Double[1000];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", ""))
                    .filter(currentLine -> !currentLine.isEmpty())
                    .map(currentLine -> Double.parseDouble(currentLine, timeFormatter))
                    .toArray(LocalDateTime[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }


        Double[] resultArray = new Double[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Зберігає масив чисел у файл.
     * 
     * @param numbersArray Масив чисел.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Double[] numbersArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            String content = Arrays.stream(dateTimeArray)
                    .map(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator()));
           
            fileWriter.write(content);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
