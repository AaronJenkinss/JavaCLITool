package me.pulsesapphire.clipackage;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Terminal {
    private Thread thread;
    private Process process;
    private boolean printToConsole;
    private String command;
    private boolean isActive;
    private ArrayList<String> output;
    private int exitCode;

    private BufferedWriter inputStream;

    public Terminal(boolean printToConsole) {
        this.isActive = true;
        this.printToConsole = printToConsole;
        this.output = new ArrayList<String>();

        String operatingSystem = System.getProperty("os.name");
        System.out.println(operatingSystem);

        try {
            if (operatingSystem.equals("Linux")) {
                this.process = Runtime.getRuntime().exec("bash");
            } else if (operatingSystem.equals("Windows 10")) {
                this.process = Runtime.getRuntime().exec("cmd.exe");
            } else {
                throw new Exception("Operating system not supported.");
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
                    this.isActive = false;
                }
            });

            thread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.isActive = false;
        }
    }

    public void sendInput(String input) {
        try {
            this.inputStream.write(input + "\n");
            this.inputStream.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean getPrintToConsole() {
        return this.printToConsole;
    }

    public void setPrintToConsole(boolean printToConsole) {
        this.printToConsole = printToConsole;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public int getExitCode() {
        return this.exitCode;
    }

    public ArrayList<String> getOutput() {
        return this.output;
    }
}