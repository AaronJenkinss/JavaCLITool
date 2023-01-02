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

        while (true) {
            String input = scanner.nextLine();
            System.out.println("user input: " + input);

            Terminal terminal = new Terminal(input, true);
        }
    }
}