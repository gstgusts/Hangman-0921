package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Game game = new Game();

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Enter letter: ");
            var userInput = input.nextLine();

            var guessResult = game.guessLetter(userInput.charAt(0));

            switch (guessResult) {
                case ALREADY_USED -> {
                    System.out.println("Letter " + userInput.charAt(0) + " has already been used");
                    continue;
                }
                case SUCCESS -> {
                    System.out.println(game.getHiddenName());
                    continue;
                }
                case DOES_NOT_EXIST -> {
                    System.out.println(game.getHiddenName());
                    System.out.println("Mistakes: " + game.getRetryCount());
                    continue;
                }
                case GAME_OVER -> {
                    System.out.println("Game over !!!");
                    System.out.println("Word was: " + game.getNameToGuess());
                    break;
                }
                case WORD_GUEST -> {
                    System.out.println("You guest the word !!!");
                    System.out.println(game.getHiddenName());
                    break;
                }
            }

            break;
        }
    }
}
