
package com.neva.basdatsupermarket;


public class loginPage extends javax.swing.JFrame {
    
    public loginPage() {
        initComponents();
        
        jPanel1.setFocusable(true);
        jPanel1.requestFocusInWindow();
        
        passwordText.setText("PASSWORD");
        passwordText.setEchoChar((char)0);
        this.getRootPane().setDefaultButton(loginButton);
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        usernameText = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        passwordText = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Manage Inventaris");
        setResizable(false);
        setSize(new java.awt.Dimension(1360, 768));

        jPanel1.setBackground(new java.awt.Color(33, 72, 192));
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 768));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameText.setBackground(new java.awt.Color(33, 72, 192));
        usernameText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        usernameText.setForeground(new java.awt.Color(255, 255, 255));
        usernameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameText.setText("USERNAME");
        usernameText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        usernameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTextFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                usernameTextFocusLost(evt);
            }
        });
        usernameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextActionPerformed(evt);
            }
        });
        jPanel1.add(usernameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, 330, 70));

        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        loginButton.setForeground(new java.awt.Color(33, 72, 192));
        loginButton.setText("LOGIN");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        jPanel1.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 570, 330, 60));

        passwordText.setBackground(new java.awt.Color(33, 72, 192));
        passwordText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        passwordText.setForeground(new java.awt.Color(255, 255, 255));
        passwordText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordText.setText("PASSWORD");
        passwordText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        passwordText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTextFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordTextFocusLost(evt);
            }
        });
        passwordText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextActionPerformed(evt);
            }
        });
        jPanel1.add(passwordText, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 450, 330, 70));

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 1, 100)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("INVENTARIS APP");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 90, 1360, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextActionPerformed

    private void usernameTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTextFocusGained
        if(usernameText.getText().equals("USERNAME")){
            usernameText.setText("");
        }
    }//GEN-LAST:event_usernameTextFocusGained

    private void usernameTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTextFocusLost
        if(usernameText.getText().isEmpty()){
            usernameText.setText("USERNAME");
        }
    }//GEN-LAST:event_usernameTextFocusLost

    private void passwordTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextActionPerformed

    private void passwordTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTextFocusGained
        char[] pass = passwordText.getPassword();
        
        if(String.valueOf(pass).equals("PASSWORD")){
            passwordText.setText("");
            passwordText.setEchoChar('*');

        }
    }//GEN-LAST:event_passwordTextFocusGained

    private void passwordTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTextFocusLost
        char[] pass = passwordText.getPassword();

        if (pass.length == 0) {
            passwordText.setEchoChar((char)0);
            passwordText.setText("PASSWORD");
            
        }
    }//GEN-LAST:event_passwordTextFocusLost

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        if(usernameText.getText().equals(usernameAdmin) && passwordText.getText().equals(passwordAdmin)){
            javax.swing.JOptionPane.showMessageDialog(this, "ADMIN USER Login Successful!");
            ManagerPanel panel = new ManagerPanel();
            panel.setVisible(true);
            dispose();
        }
        else if(usernameText.getText().equals(userNormal) && passwordText.getText().equals(passNormal)){
            javax.swing.JOptionPane.showMessageDialog(this, "Login Successful!");
            StaffPanel panel = new StaffPanel();
            panel.setVisible(true);
            dispose();
        }
        else{
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid Username or Password.");
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    String usernameAdmin = "admin";
    String passwordAdmin = "admin123123";
    String userNormal = "user";
    String passNormal = "123123123";
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordText;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
