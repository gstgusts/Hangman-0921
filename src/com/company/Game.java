package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public static final String FileUrl = "c:\\Temp\\hangman\\";
    private static final int MaxRetryCount = 6;
    private static final String HiddenSign = "_";

    public int getRetryCount() {
        return retryCount;
    }

    private int retryCount;
    private List<Character> triedLetters;

    public String getNameToGuess() {
        return nameToGuess;
    }

    private String nameToGuess;

    public String getHiddenName() {
        return hiddenName;
    }

    public boolean isGameOver() {
        return retryCount == MaxRetryCount || !hiddenName.contains(HiddenSign);
    }

    private String hiddenName;

    public Game() throws Exception {
        restart();
    }

    public void restart() throws Exception {
        retryCount = 0;
        triedLetters = new ArrayList<>();
        try {
            getRandomNameFromFile();
        } catch (Exception e) {
            throw e;
        }
    }

    public GuessResult guessLetter(Character letter) {
        var lu = letter.toString().toUpperCase();
        var letterInUppercase = lu.charAt(0);

        if(triedLetters.contains(letterInUppercase)) {
            return GuessResult.ALREADY_USED;
        }

        triedLetters.add(letterInUppercase);

        if(nameToGuess.toUpperCase().contains(lu)) {

            var sb = new StringBuilder(hiddenName);

            for (int i = 0; i < nameToGuess.length(); i++) {
                if(nameToGuess.toUpperCase().charAt(i) == letterInUppercase) {
                    sb.setCharAt(i, letterInUppercase);
                }
            }

            hiddenName = sb.toString();

            return hiddenName.contains(HiddenSign) ? GuessResult.SUCCESS : GuessResult.WORD_GUEST;

        } else {

            ++retryCount;
            return  retryCount == MaxRetryCount ? GuessResult.GAME_OVER : GuessResult.DOES_NOT_EXIST;
        }
    }

    private void getRandomNameFromFile() throws Exception {
        nameToGuess = "";
        hiddenName = "";

        Path path = Paths.get(FileUrl + "hangman.txt");

        try {
            var lines = Files.readAllLines(path);

            System.out.println(lines.size());

            if(lines.size() > 0) {

                Random rnd = new Random();
                int low = 0;
                int high = lines.size();
                var index = rnd.nextInt(high - low) + low;

                nameToGuess = lines.get(index);

                System.out.println(nameToGuess);

                for (int i = 0; i < nameToGuess.length(); i++) {
                    hiddenName += HiddenSign;
                }

            } else {
                throw new Exception("Unable to get word from the file");
            }
        } catch (IOException e) {
            throw e;
        }
    }
}
