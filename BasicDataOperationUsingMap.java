import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для
 * зберігання пар ключ-значення.
 * 
 * <p>
 * Методи класу:
 * </p>
 * <ul>
 * <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними
 * Map.</li>
 * <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 * <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 * <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 * <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 * <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 * <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 * <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    private final Pet KEY_TO_SEARCH_AND_DELETE = new Pet("Чижик", "Жако");
    private final Pet KEY_TO_ADD = new Pet("Оскар", "Розела");

    private final String VALUE_TO_SEARCH_AND_DELETE = "Єва";
    private final String VALUE_TO_ADD = "Марина";

    private HashMap<Pet, String> hashtable;
    private LinkedHashMap<Pet, String> treeMap;

    /**
     * Компаратор для сортування Map.Entry за значеннями String.
     * Використовує метод String.compareTo() для порівняння імен власників.
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Pet, String>> {
        @Override
        public int compare(Map.Entry<Pet, String> e1, Map.Entry<Pet, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null)
                return 0;
            if (v1 == null)
                return -1;
            if (v2 == null)
                return 1;
            return v1.compareTo(v2);
        }
    }

    /**
     * Компаратор для сортування Pet.
     */
    public static class PetComparator implements Comparator<Pet> {
        @Override
        public int compare(Pet p1, Pet p2) {
            if (p1 == null && p2 == null)
                return 0;
            if (p1 == null)
                return 1;
            if (p2 == null)
                return -1;

            // Спочатку порівнюємо за кличкою (за спаданням)
            String n1 = p1.nickname();
            String n2 = p2.nickname();

            int nicknameComparison = 0;
            if (n1 == null && n2 == null)
                nicknameComparison = 0;
            else if (n1 == null)
                nicknameComparison = 1;
            else if (n2 == null)
                nicknameComparison = -1;
            else
                nicknameComparison = n2.compareTo(n1); // Descending

            if (nicknameComparison != 0)
                return nicknameComparison;

            // Якщо клички однакові, порівнюємо за видом (за зростанням)
            String s1 = p1.species();
            String s2 = p2.species();
            if (s1 == null && s2 == null)
                return 0;
            if (s1 == null)
                return -1;
            if (s2 == null)
                return 1;
            return s1.compareTo(s2);
        }
    }

    /**
     * Record Pet для зберігання інформації про домашню тварину.
     */
    public record Pet(String nickname, String species) {
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param hashtable HashMap з початковими даними (ключ: Pet, значення: ім'я
     *                  власника)
     * @param treeMap   LinkedHashMap з початковими даними (ключ: Pet, значення:
     *                  ім'я власника)
     */
    BasicDataOperationUsingMap(HashMap<Pet, String> hashtable, LinkedHashMap<Pet, String> treeMap) {
        this.hashtable = hashtable;
        this.treeMap = treeMap;
    }

    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та
     * сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з HashMap
        System.out.println("========= Операції з HashMap =========");
        System.out.println("Початковий розмір HashMap: " + hashtable.size());

        // Пошук до сортування
        findByKeyInHashMap();
        findByValueInHashMap();

        printHashMap();
        sortHashMap();
        printHashMap();

        // Пошук після сортування
        findByKeyInHashMap();
        findByValueInHashMap();

        addEntryToHashMap();

        removeByKeyFromHashMap();
        removeByValueFromHashMap();

        System.out.println("Кінцевий розмір HashMap: " + hashtable.size());

        // Потім обробляємо LinkedHashMap (раніше TreeMap)
        System.out.println("\n\n========= Операції з LinkedHashMap (раніше TreeMap) =========");
        System.out.println("Початковий розмір LinkedHashMap: " + treeMap.size());

        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();

        printLinkedHashMap();

        addEntryToLinkedHashMap();

        removeByKeyFromLinkedHashMap();
        removeByValueFromLinkedHashMap();

        System.out.println("Кінцевий розмір LinkedHashMap: " + treeMap.size());
    }

    // ===== Методи для HashMap =====

    /**
     * Виводить вміст HashMap без сортування.
     * HashMap не гарантує жодного порядку елементів.
     */
    private void printHashMap() {
        System.out.println("\n=== Пари ключ-значення в HashMap ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Pet, String> entry : hashtable.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в HashMap");
    }

    /**
     * Сортує HashMap за ключами.
     */
    private void sortHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо використовуючи PetComparator
        List<Pet> sortedKeys = new ArrayList<>(hashtable.keySet());
        Collections.sort(sortedKeys, new PetComparator());

        // Створюємо LinkedHashMap з відсортованими ключами для збереження порядку
        // вставки
        LinkedHashMap<Pet, String> sortedMap = new LinkedHashMap<>();
        for (Pet key : sortedKeys) {
            sortedMap.put(key, hashtable.get(key));
        }

        // Перезаписуємо оригінальну hashtable
        hashtable = new HashMap<>(sortedMap);

        PerformanceTracker.displayOperationTime(timeStart, "сортування HashMap за ключами (через LinkedHashMap)");
    }

    /**
     * Здійснює пошук елемента за ключем в HashMap.
     */
    void findByKeyInHashMap() {
        long timeStart = System.nanoTime();

        boolean found = hashtable.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в HashMap");

        if (found) {
            String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в HashMap.
     */
    void findByValueInHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Pet, String>> entries = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Pet, String> searchEntry = new Map.Entry<Pet, String>() {
            public Pet getKey() {
                return null;
            }

            public String getValue() {
                return VALUE_TO_SEARCH_AND_DELETE;
            }

            public String setValue(String value) {
                return null;
            }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (position >= 0) {
            Map.Entry<Pet, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    /**
     * Додає новий запис до HashMap.
     */
    void addEntryToHashMap() {
        long timeStart = System.nanoTime();

        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до HashMap");

        System.out.println("Додано новий запис: Pet='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з HashMap за ключем.
     */
    void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removedValue != null) {
            System.out.println(
                    "Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з HashMap за значенням.
     */
    void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();

        List<Pet> keysToRemove = new ArrayList<>();
        for (Map.Entry<Pet, String> entry : hashtable.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Pet key : keysToRemove) {
            hashtable.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");

        System.out.println(
                "Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для LinkedHashMap =====

    /**
     * Виводить вміст LinkedHashMap.
     * LinkedHashMap зберігає порядок вставки ключів.
     */
    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Pet, String> entry : treeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в LinkedHashMap");
    }

    /**
     * Здійснює пошук елемента за ключем в LinkedHashMap.
     */
    void findByKeyInLinkedHashMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в LinkedHashMap");

        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в LinkedHashMap.
     */
    void findByValueInLinkedHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Pet, String>> entries = new ArrayList<>(treeMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Pet, String> searchEntry = new Map.Entry<Pet, String>() {
            public Pet getKey() {
                return null;
            }

            public String getValue() {
                return VALUE_TO_SEARCH_AND_DELETE;
            }

            public String setValue(String value) {
                return null;
            }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в LinkedHashMap");

        if (position >= 0) {
            Map.Entry<Pet, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Додає новий запис до LinkedHashMap.
     */
    void addEntryToLinkedHashMap() {
        long timeStart = System.nanoTime();

        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до LinkedHashMap");

        System.out.println("Додано новий запис: Pet='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з LinkedHashMap за ключем.
     */
    void removeByKeyFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з LinkedHashMap");

        if (removedValue != null) {
            System.out.println(
                    "Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з LinkedHashMap за значенням.
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Pet> keysToRemove = new ArrayList<>();
        for (Map.Entry<Pet, String> entry : treeMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }

        for (Pet key : keysToRemove) {
            treeMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println(
                "Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Pet, значення: ім'я власника)
        HashMap<Pet, String> hashtable = new HashMap<>();
        hashtable.put(new Pet("Шурик", "Ара"), "Артем");
        hashtable.put(new Pet("Чижик", "Жако"), "Ірина");
        hashtable.put(new Pet("Цізар", "Корела"), "Діана");
        hashtable.put(new Pet("Чижик", "Лорі"), "Єва");
        hashtable.put(new Pet("Фенікс", "Амазон"), "Захар");
        hashtable.put(new Pet("Умка", "Какаду"), "Інна");
        hashtable.put(new Pet("Тіма", "Еклектус"), "Єва");
        hashtable.put(new Pet("Соня", "Нестор"), "Костя");
        hashtable.put(new Pet("Ромка", "Ара"), "Лілія");
        hashtable.put(new Pet("Пірат", "Волнистий"), "Інна");

        LinkedHashMap<Pet, String> treeMap = new LinkedHashMap<>() {
            {
                put(new Pet("Шурик", "Ара"), "Артем");
                put(new Pet("Чижик", "Жако"), "Віктор");
                put(new Pet("Цізар", "Корела"), "Діана");
                put(new Pet("Чижик", "Лорі"), "Єва");
                put(new Pet("Фенікс", "Амазон"), "Захар");
                put(new Pet("Умка", "Какаду"), "Інна");
                put(new Pet("Тіма", "Еклектус"), "Єва");
                put(new Pet("Соня", "Нестор"), "Костя");
                put(new Pet("Ромка", "Ара"), "Лілія");
                put(new Pet("Пірат", "Волнистий"), "Інна");
            }
        };

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, treeMap);
        operations.executeDataOperations();
    }
}
