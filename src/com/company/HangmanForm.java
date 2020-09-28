package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanForm extends JFrame {

    private JLabel hangmanPic;
    private JPanel mainPanel;
    private JLabel lblWordToGuess;
    private JTextField txtLetter;
    private JButton btnGuess;
    private JButton btnRestart;

    private Game game;

    public HangmanForm() throws HeadlessException {
        super("Hangman");

        setSize(300, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void start() throws Exception {
        try {
            game = new Game();
        } catch (Exception e) {
            throw e;
        }
        initUi();
        setVisible(true);
    }

    private void initUi() {

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        ImageIcon img = new ImageIcon(Game.FileUrl + "1.jpg");
        hangmanPic = new JLabel(img);
        hangmanPic.setBounds(40,0, 200, 200);

        mainPanel.add(hangmanPic);

        lblWordToGuess = new JLabel(getHiddenNameWithSpaces());
        lblWordToGuess.setBounds(0, 200, 280, 50);

        var font = new Font("Serif", Font.PLAIN, 40);

        lblWordToGuess.setFont(font);
        lblWordToGuess.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(lblWordToGuess);

        txtLetter = new JTextField();
        txtLetter.setBounds(10, 260, 50, 50);
        txtLetter.setFont(font);

        mainPanel.add(txtLetter);

        btnGuess = new JButton("Guess");
        btnGuess.setBounds(70, 260, 100, 50);

        btnGuess.addActionListener(e -> {
            if(txtLetter.getText().length() != 1) {
                return;
            }

            var result = game.guessLetter(txtLetter.getText().charAt(0));

            refreshGameState(result);

            txtLetter.setText("");
        });

        mainPanel.add(btnGuess);

        btnRestart = new JButton("Restart");
        btnRestart.setBounds(180, 260, 100, 50);

        btnRestart.addActionListener(e -> {
            restartGame();
        });

        mainPanel.add(btnRestart);

        setContentPane(mainPanel);
    }

    private void restartGame() {
        try {
            game.restart();

            txtLetter.setEnabled(true);
            btnGuess.setEnabled(true);

            lblWordToGuess.setText(getHiddenNameWithSpaces());

            ImageIcon img = new ImageIcon(Game.FileUrl + "1.jpg");
            hangmanPic.setIcon(img);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshGameState(GuessResult guessResult) {
        switch (guessResult) {
            case ALREADY_USED -> {
                JOptionPane.showMessageDialog(null, "This letter has been used already!");
                break;
            }
            case SUCCESS -> {
                lblWordToGuess.setText(getHiddenNameWithSpaces());
                break;
            }
            case DOES_NOT_EXIST -> {
                var picIndex = game.getRetryCount() + 1;
                ImageIcon img = new ImageIcon(Game.FileUrl + picIndex + ".jpg");
                hangmanPic.setIcon(img);
                break;
            }
            case GAME_OVER -> {
                ImageIcon img = new ImageIcon(Game.FileUrl + "7.jpg");
                hangmanPic.setIcon(img);
                JOptionPane.showMessageDialog(null, "Sorry, game is over, the word was " + game.getNameToGuess());

                txtLetter.setEnabled(false);
                btnGuess.setEnabled(false);

                break;
            }
            case WORD_GUEST -> {
                JOptionPane.showMessageDialog(null,"Great job!!!");
                lblWordToGuess.setText(getHiddenNameWithSpaces());

                txtLetter.setEnabled(false);
                btnGuess.setEnabled(false);

                break;
            }
        }
    }

    private String getHiddenNameWithSpaces() {
        var initialText = "";

        for (var letter:game.getHiddenName().split("")) {
            initialText += letter + " ";
        }

        return initialText.substring(0, initialText.length()-1);
    }
}
