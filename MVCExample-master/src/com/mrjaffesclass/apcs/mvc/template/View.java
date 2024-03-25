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
        //panel1.paintCompoennts(g);
    }

    /**
     * Initialize the model here and subscribe to any required messages
     */
    public void init() {
        // Subscribe to messages here
        mvcMessaging.subscribe("testMessage", this);

    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        if (messagePayload != null) {
            System.out.println("MSG: received by view: " + messageName + " | " + messagePayload.toString());
        } else {
            System.out.println("MSG: received by view: " + messageName + " | No data sent");
        }

        if (messagePayload.equals("testMessage")) {

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel1.setBackground(new java.awt.Color(51, 153, 0));
        panel1.setPreferredSize(new java.awt.Dimension(800, 800));
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
            .addGap(0, 800, Short.MAX_VALUE)
        );

        startButton.setText("New Game");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.black);
        for (int i = 0; i < 7; i++) {
            g.drawLine(i, (100 * i + 100), 800, (100 * i + 100));
        }
        for (int i = 0; i < 7; i++) {
            g.drawLine((100 * i + 100), i, (100 * i + 100), 800);
        }

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

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.black);

        g.fillOval((x * 100) + 15, (y * 100) + 15, 70, 70);
    }//GEN-LAST:event_onClick

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Panel panel1;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
