package org.example;

// Импорты: классы для чтения (BufferedReader, Scanner, Files),
// для работы с путями (Path, Files, NoSuchFileException),
// для записи (StandardOpenOption) и для работы с файловой системой (File).

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Обратите внимание: метод main объявляет throws IOException.
    // Это значит, что часть ошибок ввода-вывода мы не обрабатываем внутри,
    // а «пробрасываем» наверх — JVM напечатает стектрейс и завершит программу.
    // В реальном коде лучше обрабатывать исключения явно (try/catch).
    public static void main(String[] args) throws IOException {

        // ============================================================
        // ЧТЕНИЕ ФАЙЛА. Path — современный способ задать путь к файлу.
        // Файл usersData/data.txt должен существовать, иначе будут ошибки.
        // ============================================================
        Path path = Path.of("usersData/data.txt");

        // ---------- Способ 1: прочитать весь файл в одну строку ----------
        String text = null;
        try {
            // Files.readString читает всё содержимое файла в String.
            // Удобно для небольших текстовых файлов.
            text = Files.readString(path);
        } catch (NoSuchFileException e) {
            // Отдельно ловим случай, когда файла нет.
            // Это подкласс IOException, поэтому его важно ставить ПЕРВЫМ.
            System.out.println("File not found");
        } catch (IOException e) {
            // Любая другая ошибка ввода-вывода (нет прав, файл занят и т.п.).
            e.printStackTrace();
        }
        System.out.println(text); // Может напечатать null, если была ошибка.

        // ---------- Способ 2: прочитать файл в список строк ----------
        // Каждая строка файла становится элементом List<String>.
        // Удобно, когда нужно обрабатывать файл построчно.
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (NoSuchFileException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ВНИМАНИЕ: если файла нет, lines == null,
        // и следующий вызов lines.get(0) бросит NullPointerException.
        // В учебном коде это допустимо, но в реальном — нужно проверять на null.
        System.out.println(lines.get(0));
        System.out.println(lines.get(1));
        System.out.println(lines.get(2));
        // Перебор всех строк через for-each.
        for (String line : lines) {
            System.out.println(line);
        }

        // ---------- Способ 3: Scanner ----------
        // Scanner умеет читать не только строки, но и числа, слова и т.д.
        // Здесь читаем два целых числа из файла.
        // try-with-resources автоматически закроет Scanner после блока.
        int a = 0, b = 0;
        try (Scanner sc = new Scanner(path)) {
            a = sc.nextInt(); // читает первое целое число
            b = sc.nextInt(); // читает второе целое число
        } catch (IOException e) {
            // Пустой catch — плохая практика (ошибка «проглатывается»),
            // но в учебных целях допустимо.
        }
        System.out.println(a);
        System.out.println(b);

        // ---------- Способ 4: BufferedReader ----------
        // Классический построчный читатель.
        // try-with-resources закрывает поток автоматически.
        // readLine() возвращает null, когда достигнут конец файла.
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // снова пустой catch
        }

        // ============================================================
        // ЗАПИСЬ В ФАЙЛ. path2 — файл usersData/out.txt.
        // ============================================================
        Path path2 = Path.of("usersData/out.txt");

        // ---------- Способ 1: записать строку ----------
        // Files.writeString по умолчанию ПЕРЕЗАПИСЫВАЕТ файл
        // (если файла нет — создаёт его).
        try {
            Files.writeString(path2, "Привет");
        } catch (IOException e) {
            System.out.println("Возникла ошибка при записи в файл");
        }

        // ---------- Способ 2: записать список строк ----------
        // Каждый элемент List станет отдельной строкой в файле.
        // Также перезаписывает файл целиком.
        List<String> lines2 = List.of("Петя", "Дима", "Коля");
        Files.write(path2, lines2);

        // ---------- Способ 3: дописать в конец файла ----------
        // StandardOpenOption.CREATE — создать файл, если его нет.
        // StandardOpenOption.APPEND — не перезаписывать, а дописывать в конец.
        // Обратите внимание: "\n" в начале — чтобы новая строка не приклеилась
        // к предыдущему содержимому.
        Files.writeString(path2, "\nПока",
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        // ============================================================
        // РАБОТА С ПАПКАМИ через старый класс File.
        // new File(".") — текущая рабочая директория (папка проекта).
        // ============================================================
        File dir = new File(".");
        File[] files = dir.listFiles(); // null, если это не папка или нет прав
        if (files == null) {
            System.out.println("Это не папка или нет доступа");
        } else {
            for (File file : files) {
                String fileType;
                if (file.isDirectory()) {
                    fileType = "Папка";
                } else {
                    fileType = "Файл";
                }
                // getName() — имя, length() — размер в байтах.
                // Для папок length() может быть неинформативным.
                System.out.println(fileType + " " + file.getName() + " " + file.length());
            }
        }

        // ============================================================
        // УДАЛЕНИЕ И СОЗДАНИЕ ФАЙЛОВ через Files.
        // ============================================================

        // Files.delete бросает исключение, если файла нет
        // (здесь — NoSuchFileException, которое пробрасывается через throws в main).
        Files.delete(Path.of("new.txt"));

        // Files.deleteIfExists — безопасный вариант: не бросает исключение,
        // если файла нет. Возвращает true/false.
        Files.deleteIfExists(Path.of("new.txt"));

        // Files.createFile создаёт новый пустой файл.
        // Если файл уже существует — бросит FileAlreadyExistsException.
        Files.createFile(Path.of("new.txt"));
    }
}