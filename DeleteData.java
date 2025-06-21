/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.neva.basdatsupermarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rayha
 */
public class DeleteData extends javax.swing.JFrame {

    /**
     * Creates new form DeleteData
     */
    
    public DeleteData() {
        initComponents();

    // ComboBox event
    ComboBoxDataTable.addActionListener(e -> {
        String selectedTable = ComboBoxDataTable.getSelectedItem().toString();
        loadData(selectedTable, null);
    });

    // Tombol search event
    BtnSearch.addActionListener(e -> {
        String tableName = ComboBoxDataTable.getSelectedItem().toString();
        String keyword = SearchField.getText().trim();
        loadData(tableName, keyword);
    });

    // Load initial data based on default combo box selection
    String selectedTable = ComboBoxDataTable.getSelectedItem().toString();
    loadData(selectedTable, null);
        
    }
    
    private void loadData(String tableName, String keyword) {
    String url = "jdbc:sqlserver://RAYHAN_RZ:1433;databaseName=TestDB;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

    String sql = "SELECT * FROM " + tableName;

    if (keyword != null && !keyword.isEmpty()) {
        sql += " WHERE ";
    }

    try (Connection conn = DriverManager.getConnection(url);
         Statement stmt = conn.createStatement()) {

        if (keyword != null && !keyword.isEmpty()) {
            ResultSet rsCols = stmt.executeQuery("SELECT TOP 1 * FROM " + tableName);
            ResultSetMetaData meta = rsCols.getMetaData();
            int colCount = meta.getColumnCount();
            StringBuilder where = new StringBuilder();
            for (int i = 1; i <= colCount; i++) {
                where.append(meta.getColumnName(i)).append(" LIKE '%").append(keyword).append("%'");
                if (i < colCount) where.append(" OR ");
            }
            sql += where.toString();
        }

        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(meta.getColumnName(i));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        DataTable.setModel(model);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal membaca data: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DataTable = new javax.swing.JTable();
        SearchField = new javax.swing.JTextField();
        ComboBoxDataTable = new javax.swing.JComboBox<>();
        BtnSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BtnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(33, 72, 192));
        jPanel1.setPreferredSize(new java.awt.Dimension(1170, 720));

        DataTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        DataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(DataTable);

        SearchField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        ComboBoxDataTable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kategori", "Supplier", "Staf", "Produk", "Mutasi_Stok" }));
        ComboBoxDataTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxDataTableActionPerformed(evt);
            }
        });

        BtnSearch.setBackground(new java.awt.Color(51, 204, 0));
        BtnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnSearch.setForeground(new java.awt.Color(255, 255, 255));
        BtnSearch.setText("SEARCH");
        BtnSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search Bar");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Table Chooser");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(242, 242, 242));
        jLabel3.setText("Hapus Data");

        BtnDelete.setText("Hapus");
        BtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 517, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxDataTable, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(222, 222, 222))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(23, 23, 23)
                            .addComponent(BtnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1060, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(ComboBoxDataTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 521, Short.MAX_VALUE)
                .addComponent(BtnDelete)
                .addGap(58, 58, 58))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 78, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(39, 39, 39)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 87, Short.MAX_VALUE)))
        );

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

    private void ComboBoxDataTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxDataTableActionPerformed
        
    }//GEN-LAST:event_ComboBoxDataTableActionPerformed

    private void BtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteActionPerformed
        int selectedRow = DataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
            return;
        }

        String tableName = ComboBoxDataTable.getSelectedItem().toString();
        String primaryKeyColumn = DataTable.getColumnName(0); // Assume first column is PK
        Object primaryKeyValue = DataTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String url = "jdbc:sqlserver://RAYHAN_RZ:1433;databaseName=TestDB;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
            String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";

            try (Connection conn = DriverManager.getConnection(url);
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
             
                stmt.setObject(1, primaryKeyValue);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                    loadData(tableName, null); // Reload table
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_BtnDeleteActionPerformed

    
    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeleteData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnDelete;
    private javax.swing.JButton BtnSearch;
    private javax.swing.JComboBox<String> ComboBoxDataTable;
    private javax.swing.JTable DataTable;
    private javax.swing.JTextField SearchField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
