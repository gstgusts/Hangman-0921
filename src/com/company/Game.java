package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int retryCount;
    private List<String> triedLetters;
    private String nameToGuess;

    public Game() {
        restart();
    }

    public void restart() {
        retryCount = 0;
        triedLetters = new ArrayList<>();
        getRandomNameFromFile();
    }

    private void getRandomNameFromFile() {
        nameToGuess = "";

        Path path = Paths.get("c:\\Temp\\hangman.txt");

        try {
            var lines = Files.readAllLines(path);

            if(lines.size() > 0) {
                // randomly pick one name from the list
                nameToGuess = "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
