package com.dhurd.club.login.core;

import javax.swing.JPanel;

public final class SetupVisualPanel1 extends JPanel {

    public SetupVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Welcome to Club Login";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        infoTextArea = new javax.swing.JTextArea();

        jScrollPane1.setBorder(null);

        infoTextArea.setEditable(false);
        infoTextArea.setBackground(new java.awt.Color(240, 240, 240));
        infoTextArea.setColumns(20);
        infoTextArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        infoTextArea.setLineWrap(true);
        infoTextArea.setRows(5);
        infoTextArea.setText(org.openide.util.NbBundle.getMessage(SetupVisualPanel1.class, "SetupVisualPanel1.infoTextArea.text")); // NOI18N
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setBorder(null);
        jScrollPane1.setViewportView(infoTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea infoTextArea;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
