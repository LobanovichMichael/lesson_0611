package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Path path = Path.of("usersData/data.txt");
        // Способ 1:
        String text = null;
        try {
            text = Files.readString(path);
        } catch (NoSuchFileException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(text);
        // Cпособ 2:
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (NoSuchFileException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(lines.get(0));
//        System.out.println(lines.get(1));
//        System.out.println(lines.get(2));
//        for(String line : lines) {
//            System.out.println(line);
//        }

        // Способ 3:
        try (Scanner sc = new Scanner(path)) {
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    System.out.println(sc.nextInt());
                } else {
                    System.out.println(sc.next());
                }
            }
        } catch (IOException e) {

        }

        // Способ 4:
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
        }
    }


}
