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
    private final String[][] board;
    private boolean isBlackMove = true;
    //private boolean gameOver = false;

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
        mvcMessaging.subscribe("newGame", this);
    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        if (messagePayload != null) {
            System.out.println("MSG: received by model: " + messageName + " | " + messagePayload.toString());
        } else {
            System.out.println("MSG: received by model: " + messageName + " | No data sent");
        }
        boolean isBoardFull = boardFull(board);

        if (messageName.equals("playerMove") && !isBoardFull) {
            String position = (String) messagePayload;
            Integer row = new Integer(position.substring(0, 1));
            Integer col = new Integer(position.substring(1, 2));
            String playerColor = isBlackMove ? "B" : (!isBlackMove ? "W" : "");
            boolean checkAll = checkAll(playerColor, board);
            if (!checkAll) {
                isBlackMove = !isBlackMove;
                this.mvcMessaging.notify("noMoves", this.board);
            }
            boolean isLegalMove = legalMove(row, col, board, playerColor);
            if (this.board[row][col].equals("") && isLegalMove) {
                flipPieces(row, col, board, playerColor);
                if (isBlackMove) {
                    this.board[row][col] = "B";
                } else if (!isBlackMove) {
                    this.board[row][col] = "W";
                }
                isBlackMove = !isBlackMove;

                int blackCount = blackPieces(board);
                int whiteCount = whitePieces(board);

                if (isBoardFull) {
                    if (blackCount > whiteCount) {
                        this.mvcMessaging.notify("blackWin", this);
                    }
                    if (blackCount < whiteCount) {
                        this.mvcMessaging.notify("whiteWin", this);
                    } else {
                        this.mvcMessaging.notify("gameTie", this);
                    }
                    
                }

                this.mvcMessaging.notify("boardChanged", this.board);
                this.mvcMessaging.notify("blackPieces", blackCount);
                this.mvcMessaging.notify("whitePieces", whiteCount);
            }

        }

//        if (messageName.equals("newGame")) {
//            for (int w = 0; w < board.length; w++) {
//                for (int i = 0; i < board[0].length; i++) {
//                    this.board[w][i] = "";
//                }
//            }
//            this.board[4][3] = "B";
//            this.board[3][4] = "B";
//            this.board[3][3] = "W";
//            this.board[4][4] = "W";
//
//            isBlackMove = true;
//        }

    }

    public boolean legalMove(int row, int col, String[][] board, String playerColor) {
        int[][] vectors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        if (!board[row][col].equals("")) {
            return false;
        }

        for (int[] direction : vectors) {
            int x = direction[0];
            int y = direction[1];
            int testX = row + x;
            int testY = col + y;

            while (isValidPosition(testX, testY, board.length) && !board[testX][testY].equals(playerColor) && !board[testX][testY].equals("")) {
                testX += x;
                testY += y;
            }

            if (isValidPosition(testX, testY, board.length) && board[testX][testY].equals(playerColor)) {
                testX -= x;
                testY -= y;
                while (testX != row || testY != col) {
                    if (!board[testX][testY].equals(playerColor) && !board[testX][testY].equals("")) {
                        //source of error
                        //board[testX][testY] = playerColor;
                        return true;
                    }
                    testX -= x;
                    testY -= y;
                }
            }
        }

        return false;

    }

    public void flipPieces(int row, int col, String[][] board, String playerColor) {
        int[][] vectors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] direction : vectors) {
            int x = direction[0];
            int y = direction[1];
            int testX = row + x;
            int testY = col + y;

            while (isValidPosition(testX, testY, board.length) && !board[testX][testY].equals(playerColor) && !board[testX][testY].equals("")) {
                testX += x;
                testY += y;
            }

            if (isValidPosition(testX, testY, board.length) && board[testX][testY].equals(playerColor)) {
                testX -= x;
                testY -= y;
                while (testX != row || testY != col) {
                    if (!board[testX][testY].equals(playerColor) && !board[testX][testY].equals("")) {
                        board[testX][testY] = playerColor;
                    }
                    testX -= x;
                    testY -= y;
                }
            }
        }
    }

    //prevents out of bounds error
    public boolean isValidPosition(int row, int col, int boardSize) {
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize) {
            return true;
        }
        return false;
    }

    //checks all positions to see if there's a legal move
    public boolean checkAll(String color, String[][] board) {
        int legalMoveCount = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                boolean isLegalAll = legalMove(r, c, board, color);
                if (isLegalAll) {
                    legalMoveCount++;
                }
            }
        }
        if (legalMoveCount == 0) {
            return false;
        }
        return true;
    }

    public int blackPieces(String[][] board) {
        int blackCount = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (this.board[r][c].equals("B")) {
                    blackCount++;
                }
            }
        }
        return blackCount;
    }

    public int whitePieces(String[][] board) {
        int whiteCount = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (this.board[r][c].equals("W")) {
                    whiteCount++;
                }
            }
        }
        return whiteCount;
    }

    public boolean boardFull(String[][] board) {
        int count = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].equals("")) {
                    count++;
                }
            }

        }
        if (count == 0) {
            return true;
        }
        return false;
    }
}
