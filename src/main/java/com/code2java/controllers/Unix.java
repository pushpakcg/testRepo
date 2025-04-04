package com.code2java.controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Unix {
	public static void main(String[] args) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Windows
        processBuilder.command("cmd.exe", "/c", "ping -n 3 google.com");
        //processBuilder.command("bash", "-c", "ls /home/mkyong/");

        try {

            Process process = processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
