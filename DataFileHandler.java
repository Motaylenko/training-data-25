import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(currentLine -> currentLine.trim().replaceAll("^\\uFEFF", ""))
                    .filter(currentLine -> !currentLine.isEmpty())
                    .map(Double::parseDouble)
                    .toArray(Double[]::new);
        } catch (IOException ioException) {
            throw new RuntimeException("Помилка читання даних з файлу: " + filePath, ioException);
        }
    }

    /**
     * Зберігає масив чисел у файл.
     * 
     * @param numbersArray Масив чисел.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Double[] numbersArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            String content = Arrays.stream(numbersArray)
                    .map(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator()));
           
            fileWriter.write(content);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
