package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;

/**
 * The model represents the data that the app uses.
 *
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

    // Messaging system for the MVC
    private final Messenger mvcMessaging;

    // Model's data variables
    private String[][] board;
    private boolean isBlackMove = true;
    private boolean gameOver = false;
    private int count = 0;

    /**
     * Model constructor: Create the data representation of the program
     *
     * @param messages Messaging class instantiated by the Controller for local
     * messages between Model, View, and controller
     */
    public Model(Messenger messages) {
        mvcMessaging = messages;
        this.board = new String[8][8];
        for (int w = 0; w < board.length; w++) {
            for (int i = 0; i < board[0].length; i++) {
                board[w][i] = "";
            }
        }
        board[4][3] = "B";
        board[3][4] = "B";
        board[3][3] = "W";
        board[4][4] = "W";
    }

    /**
     * Initialize the model here and subscribe to any required messages
     */
    public void init() {
        mvcMessaging.subscribe("playerMove", this);
    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        if (messagePayload != null) {
            System.out.println("MSG: received by model: " + messageName + " | " + messagePayload.toString());
        } else {
            System.out.println("MSG: received by model: " + messageName + " | No data sent");
        }

        if (messageName.equals("playerMove") && this.gameOver == false) {
            String position = (String) messagePayload;
            Integer row = new Integer(position.substring(0, 1));
            Integer col = new Integer(position.substring(1, 2));
            if (this.board[row][col].equals("")) {
                if (isBlackMove) {
                    board[row][col] = "B";
                } else if (!isBlackMove) {
                    board[row][col] = "W";
                }

                //flipPieces here...
                //check up vert first
                for (int i = row; i >= 0; i--) {
                    if (!board[i - 1][col].equals(board[row][col])) {
                        board[i - 1][col].equals(board[row][col]);
                    } else {
                        break;
                    }
                }

                isBlackMove = !isBlackMove;
                this.mvcMessaging.notify("boardChanged", this.board);
            }

        }
    }

}
