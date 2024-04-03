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
        String playerColor = isBlackMove ? "B" : (!isBlackMove ? "W" : "");

        int[][] vectors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] direction : vectors) {
            int testRow = row + direction[0];
            int testCol = col + direction[1];
            if (isValidPosition(testRow, testRow, board.length) && !board[testRow][testCol].equals("") && !board[testRow][testCol].equals(playerColor)) {
                isLegalMove(testRow, testCol, board, direction, playerColor);
//                if (!board[testRow][testCol].equals(color)) {
//                    isLegalMove(testRow, testCol, board, direction);

                //first iteration
            } else if (isValidPosition(testRow, testCol, board.length) && board[testRow][testCol].equals(playerColor)) {
                moveLegal = false;
                break;
//                    //flip in between pieces here...
//                } else {
//                    moveLegal = false;
//                }
            }
            if (moveLegal) {
                break;
            }
        }
    }

    public void isLegalMove(int row, int col, String[][] board, int[] vector, String color) {
        while (isValidPosition(row, col, board.length) && !board[row][col].equals(color) && !board[row][col].equals("")) {

            if (vector[0] == -1 && vector[1] == 0) {
                row--;
            }
            if (vector[0] == 1 && vector[1] == 0) {
                row++;
            }
            if (vector[1] == -1 && vector[0] == 0) {
                col--;
            }
            if (vector[1] == 1 && vector[0] == 0) {
                col++;
            }
            if (vector[0] == -1 && vector[1] == -1) {
                row--;
                col--;
            }
            if (vector[0] == 1 && vector[1] == 1) {
                row++;
                col++;
            }
            if (vector[0] == 1 && vector[1] == -1) {
                row++;
                col--;
            }
            if (vector[0] == -1 && vector[1] == +1) {
                row--;
                col++;
            }
        }

        if (isValidPosition(row, col, board.length) && board[row][col].equals(color)) {
            moveLegal = true;
            //break;
            //flip in between pieces here...
        } else {
            moveLegal = false;
        }
    }

    public boolean isValidPosition(int row, int col, int boardSize) {
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize) {
            return true;
        }
        return false;
    }

}
