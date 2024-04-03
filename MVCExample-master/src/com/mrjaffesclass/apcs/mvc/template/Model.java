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
    private boolean moveLegal;

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
            legalMovePt1(row, col, board);
            if (this.board[row][col].equals("") && moveLegal) {
                if (isBlackMove) {
                    this.board[row][col] = "B";
                } else if (!isBlackMove) {
                    this.board[row][col] = "W";
                }

                //flipPieces here...
                isBlackMove = !isBlackMove;
                this.mvcMessaging.notify("boardChanged", this.board);
            }
            if (this.board[row][col].equals("") && !moveLegal) {
                this.mvcMessaging.notify("boardChanged", this.board);
            }
        }
        this.mvcMessaging.notify("boardChanged", this.board);

    }

    public void legalMovePt1(int row, int col, String[][] board) {
        String playerColor = "";
        if (isBlackMove) {
            playerColor = "B";
        } else if (!isBlackMove) {
            playerColor = "W";
        }

        String nw = Integer.toString(row - 1) + Integer.toString(col - 1);
        String nn = Integer.toString(row - 1) + Integer.toString(col);
        String ne = Integer.toString(row - 1) + Integer.toString(col + 1);

        String ww = Integer.toString(row) + Integer.toString(col - 1);
        String ee = Integer.toString(row) + Integer.toString(col + 1);

        String sw = Integer.toString(row - 1) + Integer.toString(col - 1);
        String ss = Integer.toString(row + 1) + Integer.toString(col);
        String se = Integer.toString(row + 1) + Integer.toString(col + 1);

        String[] points = {nn, nw, ne, ww, ee, sw, ss, se};

        for (String direction : points) {
            Integer testRow = new Integer(direction.substring(0, 1));
            Integer testCol = new Integer(direction.substring(1, 2));
            if (!board[testRow][testCol].equals("")) {
                isLegalMove(testRow, testCol, board, direction, playerColor);
//                if (!board[testRow][testCol].equals(color)) {
//                    isLegalMove(testRow, testCol, board, direction);
//                } else if (board[testRow][testCol].equals(color)) {
//                    moveLegal = true;
//                    break;
//                    //flip in between pieces here...
//                } else {
//                    moveLegal = false;
//                }
            }
            if (moveLegal = true) {
                break;
            }
        }
    }

    public void isLegalMove(int row, int col, String[][] board, String vector, String color) {
        if (!board[row][col].equals(color) && !board[row][col].equals("")) {
            isLegalMove(row, col, board, vector, color);
        } else if (board[row][col].equals(color)) {
            moveLegal = true;
            //break;
            //flip in between pieces here...
        } else {
            moveLegal = false;
        }
    }
}
