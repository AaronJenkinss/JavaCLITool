package me.pulsesapphire;
import me.pulsesapphire.clipackage.Terminal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("This CLI Tool is written in java and is created by PulseSapphire. Contact @PulseSapphire#6734 on discord to talk to them.");
        Terminal terminal = new Terminal(true, 100);

        Scanner scanner = new Scanner(System.in);
        while (terminal.isActive()) {
            String input = scanner.nextLine();
            terminal.sendInput(input);
        }
    }
}