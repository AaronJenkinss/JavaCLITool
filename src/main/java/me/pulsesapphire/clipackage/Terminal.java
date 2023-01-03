package me.pulsesapphire.clipackage;

import java.io.*;
import java.util.LinkedList;

public class Terminal {
    private Thread threadStdOut;
    private Thread threadStdErr;
    private Process process;
    private boolean printToConsole;
    private boolean isActive;
    private LinkedList<String> outputStdOut;
    private LinkedList<String> outputStdErr;
    private int outputLines;
    private int exitCode;

    private BufferedWriter inputStream;

    public Terminal(boolean printToConsole, int outputLinesSizePerStream) {
        this.isActive = true;
        this.printToConsole = printToConsole;
        this.outputStdOut = new LinkedList<String>();
        this.outputStdErr = new LinkedList<String>();
        this.outputLines = outputLinesSizePerStream;

        String operatingSystem = System.getProperty("os.name");

        try {
            if (operatingSystem.equals("Linux")) {
                this.process = Runtime.getRuntime().exec("bash");
            } else if (operatingSystem.equals("Windows 10")) {
                this.process = Runtime.getRuntime().exec("cmd.exe");
            } else {
                this.isActive = false;
                throw new Exception("Operating system not supported.");
            }

            System.out.println("Initialized Java CLI Tool for: " + operatingSystem + ".");

            this.inputStream = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            threadStdOut = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        this.outputStdOut.add(line);
                        if (this.printToConsole) System.out.println(line);

                        if (this.outputStdOut.size() > this.outputLines) {
                            this.outputStdOut.removeFirst();
                        }
                    }

                    while (this.process.isAlive()) {
                        while ((line = reader.readLine()) != null) {
                            this.outputStdOut.add(line);
                            if (this.printToConsole) System.out.println(line);

                            if (this.outputStdOut.size() > this.outputLines) {
                                this.outputStdOut.removeFirst();
                            }
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

            threadStdOut.start();

            threadStdErr = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        this.outputStdErr.add(line);
                        if (this.printToConsole) System.out.println(line);

                        if (this.outputStdErr.size() > this.outputLines) {
                            this.outputStdErr.removeFirst();
                        }
                    }

                    while (this.process.isAlive()) {
                        while ((line = reader.readLine()) != null) {
                            this.outputStdErr.add(line);
                            if (this.printToConsole) System.out.println(line);

                            if (this.outputStdErr.size() > this.outputLines) {
                                this.outputStdErr.removeFirst();
                            }
                        }

                        Thread.sleep(100);
                    }

                    this.isActive = false;

                    if (printToConsole) System.out.println("Process exited with code: " + this.exitCode);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    this.isActive = false;
                }
            });

            threadStdErr.start();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.isActive = false;
        }
    }

    public void sendInput(String input) {
        try {
            this.inputStream.write(input);
            this.inputStream.newLine();
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

    public LinkedList<String> getOutputStdOut() {
        return this.outputStdOut;
    }

    public LinkedList<String> getOutputStdErr() {
        return this.outputStdErr;
    }
}