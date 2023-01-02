package me.pulsesapphire.clipackage;

import java.io.*;
import java.util.ArrayList;

public class Terminal {
    private Thread thread;
    private Process process;
    private boolean printToConsole;
    private String command;
    private boolean isActive;
    private ArrayList<String> output;
    private int exitCode;

    private BufferedWriter inputStream;

    public Terminal(String command, boolean printToConsole) {
        this.command = command;
        this.isActive = true;
        this.printToConsole = printToConsole;
        this.output = new ArrayList<String>();

        try {
            this.process = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.inputStream = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        thread = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    this.output.add(line);
                    if (this.printToConsole) System.out.println(line);
                }

                while (this.process.isAlive()) {
                    while ((line = reader.readLine()) != null) {
                        this.output.add(line);
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

    public void sendInput(String input) {
        try {
            this.inputStream.write(input + "\n");
            this.inputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}