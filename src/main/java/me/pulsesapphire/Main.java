package me.pulsesapphire;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var app = Javalin.create()
                .get("/", ctx -> {
                    ctx.result("Hello World!!");
                })
                .start(9000);
    }
}