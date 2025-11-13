import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    private final Parrot KEY_TO_SEARCH_AND_DELETE = new Parrot("Чижик", "Жако");
    private final Parrot KEY_TO_ADD = new Parrot("Оскар", "Розела");

    private final String VALUE_TO_SEARCH_AND_DELETE = "Єва";
    private final String VALUE_TO_ADD = "Марина";

    private HashMap<Parrot, String> hashtable;
    private LinkedHashMap<Parrot, String> treeMap;

    /**
     * Компаратор для сортування Map.Entry за значеннями String.
     * Використовує метод String.compareTo() для порівняння імен власників.
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Parrot, String>> {
        @Override
        public int compare(Map.Entry<Parrot, String> e1, Map.Entry<Parrot, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    /**
     * Внутрішній клас Parrot для зберігання інформації про домашню тварину.
     * 
     * Реалізує Comparable<Parrot> для визначення природного порядку сортування.
    * Природний порядок: спочатку за кличкою (nickname) за спаданням, потім за видом (species) за зростанням.
     */
    public static class Parrot implements Comparable<Parrot> {
        private final String nickname;
        private final String species;

        public Parrot(String nickname) {
            this.nickname = nickname;
            this.species = null;
        }

        public Parrot(String nickname, String species) {
            this.nickname = nickname;
            this.species = species;
        }

        public String getNickname() { 
            return nickname; 
        }

        public String getSpecies() {
            return species;
        }

        /**
         * Порівнює цей об'єкт Parrot з іншим для визначення порядку сортування.
         * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за видом (species) за спаданням.
         * 
         * @param other Parrot об'єкт для порівняння
         * @return негативне число, якщо цей Parrot < other; 
         *         0, якщо цей Parrot == other; 
         *         позитивне число, якщо цей Parrot > other
         * 
         * Критерій порівняння: поля nickname (кличка) за зростанням та species (вид) за спаданням.
         * 
         * Цей метод використовується:
         * - TreeMap для автоматичного сортування ключів Parrot за nickname (зростання), потім за species (спадання)
         * - Collections.sort() для сортування Map.Entry за ключами Parrot
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Parrot other) {
            if (other == null) return 1;
            
            // Спочатку порівнюємо за кличкою (за спаданням)
            int nicknameComparison = 0;
            if (this.nickname == null && other.nickname == null) {
                nicknameComparison = 0;
            } else if (this.nickname == null) {
                // null вважаємо найменшим при спаданні, тобто буде в кінці
                nicknameComparison = 1;
            } else if (other.nickname == null) {
                nicknameComparison = -1;
            } else {
                // Інвертуємо природне порівняння для отримання спадного порядку
                nicknameComparison = other.nickname.compareTo(this.nickname);
            }

            // Якщо клички різні, повертаємо результат
            if (nicknameComparison != 0) {
                return nicknameComparison;
            }

            // Якщо клички однакові, порівнюємо за видом (за зростанням)
            if (this.species == null && other.species == null) return 0;
            if (this.species == null) return -1;  // null йде в кінець при зростанні
            if (other.species == null) return 1;
            return this.species.compareTo(other.species);
        }

        /**
         * Перевіряє рівність цього Parrot з іншим об'єктом.
         * Два Parrot вважаються рівними, якщо їх клички (nickname) та види (species) однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: поля nickname (кличка) та species (вид).
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та species.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Parrot Parrot = (Parrot) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(Parrot.nickname) : Parrot.nickname == null;
            boolean speciesEquals = species != null ? species.equals(Parrot.species) : Parrot.species == null;
            
            return nicknameEquals && speciesEquals;
        }

        /**
         * Повертає хеш-код для цього Parrot.
         * 
         * @return хеш-код, обчислений на основі nickname та species
         * 
         * Базується на полях nickname та species для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Parrot рівні за equals()
         * (мають однакові nickname та species), вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            // Початкове значення: хеш-код поля nickname (або 0, якщо nickname == null)
            int result = nickname != null ? nickname.hashCode() : 0;
            
            // Комбінуємо хеш-коди полів за формулою: result = 31 * result + hashCode(поле)
            // Множник 31 - просте число, яке дає хороше розподілення хеш-кодів
            // і оптимізується JVM як (result << 5) - result
            // Додаємо хеш-код виду (або 0, якщо species == null) до загального результату
            result = 31 * result + (species != null ? species.hashCode() : 0);
            
            return result;
        }

        /**
         * Повертає строкове представлення Parrot.
         * 
         * @return кличка тварини (nickname), вид (species) та hashCode
         */
        @Override
        public String toString() {
            if (species != null) {
                return "Parrot{nickname='" + nickname + "', species='" + species + "', hashCode=" + hashCode() + "}";
            }
            return "Parrot{nickname='" + nickname + "', hashCode=" + hashCode() + "}";
        }
    }

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
    * @param hashtable HashMap з початковими даними (ключ: Parrot, значення: ім'я власника)
    * @param treeMap LinkedHashMap з початковими даними (ключ: Parrot, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(HashMap<Parrot, String> hashtable, LinkedHashMap<Parrot, String> treeMap) {
        this.hashtable = hashtable;
        this.treeMap = treeMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
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

    /**
     * Порівняння продуктивності операцій вставки, пошуку та видалення
     * для HashMap і LinkedHashMap.
     * 
     * @param size кількість елементів для тесту
     */
    


    // ===== Методи для HashMap =====

    /**
     * Виводить вміст HashMap без сортування.
     * HashMap не гарантує жодного порядку елементів.
     */
    private void printHashMap() {
        System.out.println("\n=== Пари ключ-значення в HashMap ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Parrot, String> entry : hashtable.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в HashMap");
    }

    /**
     * Сортує HashMap за ключами.
     * Використовує Collections.sort() з природним порядком Parrot (Parrot.compareTo()).
     * Перезаписує HashMap відсортованими даними.
     */
    private void sortHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список ключів і сортуємо за природним порядком Parrot
        List<Parrot> sortedKeys = new ArrayList<>(hashtable.keySet());
        Collections.sort(sortedKeys);

        // Створюємо LinkedHashMap з відсортованими ключами для збереження порядку вставки
        LinkedHashMap<Parrot, String> sortedMap = new LinkedHashMap<>();
        for (Parrot key : sortedKeys) {
            sortedMap.put(key, hashtable.get(key));
        }

        // Перезаписуємо оригінальну hashtable, використовуючи новий HashMap з порядком з sortedMap
        hashtable = new HashMap<>(sortedMap);

        PerformanceTracker.displayOperationTime(timeStart, "сортування HashMap за ключами (через LinkedHashMap)");
    }

    /**
     * Здійснює пошук елемента за ключем в HashMap.
     * Використовує Parrot.hashCode() та Parrot.equals() для пошуку.
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
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Parrot, String>> entries = new ArrayList<>(hashtable.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Parrot, String> searchEntry = new Map.Entry<Parrot, String>() {
            public Parrot getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (position >= 0) {
            Map.Entry<Parrot, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Parrot: " + foundEntry.getKey());
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

        System.out.println("Додано новий запис: Parrot='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з HashMap за ключем.
     */
    void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з HashMap за значенням.
     */
    void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();

        List<Parrot> keysToRemove = new ArrayList<>();
        for (Map.Entry<Parrot, String> entry : hashtable.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Parrot key : keysToRemove) {
            hashtable.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для LinkedHashMap =====

    /**
     * Виводить вміст LinkedHashMap.
     * LinkedHashMap зберігає порядок вставки ключів.
     */
    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Parrot, String> entry : treeMap.entrySet()) {
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
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInLinkedHashMap() {
        long timeStart = System.nanoTime();

        // Створюємо список Entry та сортуємо за значеннями
        List<Map.Entry<Parrot, String>> entries = new ArrayList<>(treeMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        // Створюємо тимчасовий Entry для пошуку
        Map.Entry<Parrot, String> searchEntry = new Map.Entry<Parrot, String>() {
            public Parrot getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);

        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в LinkedHashMap");

        if (position >= 0) {
            Map.Entry<Parrot, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Parrot: " + foundEntry.getKey());
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

        System.out.println("Додано новий запис: Parrot='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з LinkedHashMap за ключем.
     */
    void removeByKeyFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з LinkedHashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з LinkedHashMap за значенням.
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Parrot> keysToRemove = new ArrayList<>();
        for (Map.Entry<Parrot, String> entry : treeMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        
        for (Parrot key : keysToRemove) {
            treeMap.remove(key);
        }

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані (ключ: Parrot, значення: ім'я власника)
        HashMap<Parrot, String> hashtable = new HashMap<>();
        hashtable.put(new Parrot("Шурик", "Ара"), "Артем");
        hashtable.put(new Parrot("Чижик", "Жако"), "Ірина");
        hashtable.put(new Parrot("Цізар", "Корела"), "Діана");
        hashtable.put(new Parrot("Чижик", "Лорі"), "Єва");
        hashtable.put(new Parrot("Фенікс", "Амазон"), "Захар");
        hashtable.put(new Parrot("Умка", "Какаду"), "Інна");
        hashtable.put(new Parrot("Тіма", "Еклектус"), "Єва");
        hashtable.put(new Parrot("Соня", "Нестор"), "Костя");
        hashtable.put(new Parrot("Ромка", "Ара"), "Лілія");
        hashtable.put(new Parrot("Пірат", "Волнистий"), "Інна");

        LinkedHashMap<Parrot, String> treeMap = new LinkedHashMap<>() {{
            put(new Parrot("Шурик", "Ара"), "Артем");
            put(new Parrot("Чижик", "Жако"), "Віктор");
            put(new Parrot("Цізар", "Корела"), "Діана");
            put(new Parrot("Чижик", "Лорі"), "Єва");
            put(new Parrot("Фенікс", "Амазон"), "Захар");
            put(new Parrot("Умка", "Какаду"), "Інна");
            put(new Parrot("Тіма", "Еклектус"), "Єва");
            put(new Parrot("Соня", "Нестор"), "Костя");
            put(new Parrot("Ромка", "Ара"), "Лілія");
            put(new Parrot("Пірат", "Волнистий"), "Інна");
        }};

        // Створюємо об'єкт і виконуємо операції
        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashtable, treeMap);
        operations.executeDataOperations();
    }
}
