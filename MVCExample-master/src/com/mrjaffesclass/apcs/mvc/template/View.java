package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

/**
 * MVC Template This is a template of an MVC framework used by APCS for the
 * LandMine project (and others)
 *
 * @author Roger Jaffe
 * @version 1.0
 *
 */
public class View extends javax.swing.JFrame implements MessageHandler {

    private final Messenger mvcMessaging;

    public static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 100;

    Graphics g;

    /**
     * Creates a new view
     *
     * @param messages mvcMessaging object
     */
    public View(Messenger messages) {
        mvcMessaging = messages;   // Save the calling controller instance
        initComponents();           // Create and init the GUI components
        g = panel1.getGraphics();
        //panel1.paintComponents(g);

    }

    /**
     * Initialize the model here and subscribe to any required messages
     */
    public void init() {
        // Subscribe to messages here
        mvcMessaging.subscribe("boardChanged", this);
        mvcMessaging.subscribe("noMoves", this);
        mvcMessaging.subscribe("blackPieces", this);
        mvcMessaging.subscribe("whitePieces", this);

    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        if (messagePayload != null) {
            System.out.println("MSG: received by view: " + messageName + " | " + messagePayload.toString());
        } else {
            System.out.println("MSG: received by view: " + messageName + " | No data sent");
        }

        if (messageName.equals("boardChanged")) {
            String[][] board = (String[][]) messagePayload;
            for (int w = 0; w < board.length; w++) {
                for (int i = 0; i < board[0].length; i++) {
                    if (board[w][i].equals("B")) {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setColor(Color.black);
                        g.fillOval((w * 100) + 15, (i * 100) + 15, 70, 70);
                    }
                    if (board[w][i].equals("W")) {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setColor(Color.white);
                        g.fillOval((w * 100) + 15, (i * 100) + 15, 70, 70);
                    }
                }
            }
        }

        if (messageName.equals("noMoves")) {
            this.mvcMessaging.notify("playerMove", this);
        }

        if (messageName.equals("blackPieces")) {
            String b = messagePayload.toString();
            blackNum.setText(b);
        }
        
        if (messageName.equals("whitePieces")) {
            String w = messagePayload.toString();
            whiteNum.setText(w);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new java.awt.Panel();
        startButton = new javax.swing.JButton();
        newGameButton = new javax.swing.JButton();
        blackLabel = new javax.swing.JLabel();
        blackNum = new javax.swing.JLabel();
        whiteLabel = new javax.swing.JLabel();
        whiteNum = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel1.setBackground(new java.awt.Color(51, 153, 0));
        panel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onClick(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );

        startButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        startButton.setText("Start Game");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        newGameButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        newGameButton.setText("New Game");
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameButtonActionPerformed(evt);
            }
        });

        blackLabel.setText("Black:");

        whiteLabel.setText("White:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(blackLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(blackNum)
                .addGap(114, 114, 114)
                .addComponent(whiteLabel)
                .addGap(18, 18, 18)
                .addComponent(whiteNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startButton)
                .addGap(32, 32, 32)
                .addComponent(newGameButton)
                .addGap(39, 39, 39))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(startButton)
                        .addComponent(newGameButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(blackLabel)
                        .addComponent(blackNum)
                        .addComponent(whiteLabel)
                        .addComponent(whiteNum)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//
//    protected void paintComponent(Graphics g) {
//        super.paintComponents(g);
//        g.setColor(Color.BLACK);
//        //draw grid lines
//        for (int i = 0; i < 7; i++) {
//            g.drawLine(i, (100 * i + 100), 800, (100 * i + 100));
//        }
//        for (int i = 0; i < 7; i++) {
//            g.drawLine((100 * i + 100), i, (100 * i + 100), 800);
//        }
//    }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.black);

        //draw grid lines
        for (int i = 0; i < 7; i++) {
            g.drawLine(i, (100 * i + 100), 800, (100 * i + 100));
        }
        for (int i = 0; i < 7; i++) {
            g.drawLine((100 * i + 100), i, (100 * i + 100), 800);
        }
        //place starting 4 pieces
        g.fillOval((4 * 100) + 15, (3 * 100) + 15, 70, 70);
        g.fillOval((3 * 100) + 15, (4 * 100) + 15, 70, 70);
        g2.setColor(Color.white);
        g.fillOval((4 * 100) + 15, (4 * 100) + 15, 70, 70);
        g.fillOval((3 * 100) + 15, (3 * 100) + 15, 70, 70);

    }//GEN-LAST:event_startButtonActionPerformed

    private void onClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onClick
        // TODO add your handling code here:
        int x = evt.getX() / CELL_SIZE;
        int y = evt.getY() / CELL_SIZE;
        String placeX = String.valueOf(x);
        String placeY = String.valueOf(y);
        String place = placeX + placeY;
        this.mvcMessaging.notify("playerMove", place);

        //code to place piece needs to move to messageHandler
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(Color.black);
//        g.fillOval((x * 100) + 15, (y * 100) + 15, 70, 70);
    }//GEN-LAST:event_onClick

    private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameButtonActionPerformed
        // TODO add your handling code here:
        this.mvcMessaging.notify("newGame", this);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        //place starting 4 pieces
        g.fillOval((4 * 100) + 15, (3 * 100) + 15, 70, 70);
        g.fillOval((3 * 100) + 15, (4 * 100) + 15, 70, 70);
        g.setColor(Color.white);
        g.fillOval((4 * 100) + 15, (4 * 100) + 15, 70, 70);
        g.fillOval((3 * 100) + 15, (3 * 100) + 15, 70, 70);

        //reset
        //panel1.repaint();

    }//GEN-LAST:event_newGameButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blackLabel;
    private javax.swing.JLabel blackNum;
    private javax.swing.JButton newGameButton;
    private java.awt.Panel panel1;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel whiteLabel;
    private javax.swing.JLabel whiteNum;
    // End of variables declaration//GEN-END:variables

}
