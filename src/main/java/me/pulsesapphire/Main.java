package me.pulsesapphire;
import io.javalin.Javalin;
import me.pulsesapphire.clipackage.Terminal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var app = Javalin.create()
                .get("/", ctx -> {
                    ctx.result("Hello World!!");
                })
                .start(9000);

        Scanner scanner = new Scanner(System.in);

        Terminal terminal = new Terminal("cmd /c start.bat", true);

        while (true) {
            String input = scanner.nextLine();

            terminal.sendInput(input);
        }
    }
}