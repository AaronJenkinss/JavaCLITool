package me.pulsesapphire;
import me.pulsesapphire.clipackage.Terminal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        Terminal terminal = new Terminal(command, true);

        while (true) {
            String input = scanner.nextLine();
            try {
                if (terminal.isActive()) {
                    terminal.sendInput(input);
                } else {
                    terminal = new Terminal(input, true);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}