package com.enigma.enigpus.util;

import java.util.Scanner;

public class Util {

    public static String input(String info) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("%s : ", info);
            String input = scanner.nextLine();
            if (!input.isEmpty()) return input;
        }
    }

}
