package me.pulsesapphire.clipackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Terminal {
    private Thread thread;
    private Process process;
    private boolean printToConsole;
    private String command;
    private boolean isActive;
    private ArrayList<String> output;
    private int exitCode;

    public Terminal(String command, boolean printToConsole) {
        this.command = command;
        this.isActive = true;
        this.printToConsole = printToConsole;
        this.output = new ArrayList<String>();

        thread = new Thread(() -> {
            try {
                process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while (process.isAlive()) {
                    while ((line = reader.readLine()) != null) {
                        output.add(line);
                        if (this.printToConsole) System.out.println(line);
                    }

                    Thread.sleep(100);
                }

                this.isActive = false;
                this.exitCode = process.waitFor();

                if (printToConsole) System.out.println("Process exited with code: " + this.exitCode);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        thread.start();
    }
}