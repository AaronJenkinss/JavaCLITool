package me.pulsesapphire;
import me.pulsesapphire.clipackage.Terminal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal(true, 100);

        Scanner scanner = new Scanner(System.in);

        while (terminal.isActive()) {
            String input = scanner.nextLine();
            terminal.sendInput(input);
        }
    }
}