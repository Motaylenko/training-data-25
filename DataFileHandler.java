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
    public static double[] loadArrayFromFile(String filePath) {
        double[] temporaryArray = new double[1000];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                // Видаляємо можливі невидимі символи та BOM
                currentLine = currentLine.trim().replaceAll("^\\uFEFF", "");
                if (!currentLine.isEmpty()) {
                    temporaryArray[currentIndex++] = Double.parseDouble(currentLine);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        double[] resultArray = new double[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Зберігає масив чисел у файл.
     * 
     * @param numbersArray Масив чисел.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(double[] numbersArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (double value : numbersArray) {
                fileWriter.write(String.valueOf(value));
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
