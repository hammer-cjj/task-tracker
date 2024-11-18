package com.cf;

import java.io.IOException;
import java.util.Scanner;

/**
 * Copyright(C) 2024- com.cf
 * FileName:    ${NAME}
 * Author:      cf
 * Date:        2024/11/15 20:30
 * Description: Task Tracker
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Task Tracker programme");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            // Input q to exit program
            if (input.equals("q")) {
                System.out.println("Bye!");
                break;
            }

            // Execute command
            HandleCommand.execute(input);
        }
    }
}