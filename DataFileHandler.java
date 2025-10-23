import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public static double[] loadArrayFromFile(String filePath) {
        double[] temporaryArray = new double[1000];
        int currentIndex = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                // Видаляємо можливі невидимі символи та BOM
                currentLine = currentLine.trim().replaceAll("^\\uFEFF", "");
                if (!currentLine.isEmpty()) {
                    LocalDateTime dateTime = LocalDateTime.parse(currentLine, formatter);
                    temporaryArray[currentIndex++] = dateTime.toEpochSecond(java.time.ZoneOffset.UTC);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        double[] resultArray = new double[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Зберігає масив чисел у файл.
     * 
     * @param dateTimeArray Масив чисел.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(double[] dateTimeArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (double value : dateTimeArray) {
                LocalDateTime dateTime = LocalDateTime.ofEpochSecond((long)value, 0, java.time.ZoneOffset.UTC);
                fileWriter.write(dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
