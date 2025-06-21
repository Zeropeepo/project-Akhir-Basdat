/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.neva.basdatsupermarket;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

/**
 *
 * @author rayha
 */
public class DeleteData extends javax.swing.JFrame {
     private boolean editMode = false;
    private int editingRow = -1;

    public DeleteData() {
        initComponents();

        // Initialize ComboBox and load initial data
        ComboBoxDataTable.setModel(new DefaultComboBoxModel<>(new String[]{
            "Kategori","Supplier","Staf","Produk","Mutasi_Stok"
        }));
        loadData(ComboBoxDataTable.getSelectedItem().toString(), null);

        // ComboBox selection listener
        ComboBoxDataTable.addActionListener(e ->
            loadData(ComboBoxDataTable.getSelectedItem().toString(), null)
        );

        // Search button listener
        BtnSearch.addActionListener(e ->
            loadData(ComboBoxDataTable.getSelectedItem().toString(), SearchField.getText().trim())
        );

        // Delete button listener
        BtnDelete.addActionListener(e -> {
            int row = DataTable.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih baris untuk dihapus.");
                return;
            }
            String table = ComboBoxDataTable.getSelectedItem().toString();
            String pkCol = DataTable.getColumnName(0);
            Object pkVal = DataTable.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this,
                    "Yakin ingin menghapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION
                ) == JOptionPane.YES_OPTION) {
                try (Connection conn = connectDb();
                     PreparedStatement pst = conn.prepareStatement(
                         "DELETE FROM " + table + " WHERE " + pkCol + " = ?"
                     )) {
                    pst.setObject(1, pkVal);
                    pst.executeUpdate();
                    loadData(table, null);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal hapus: " + ex.getMessage());
                }
            }
        });

        // Edit/Save button listener
        BtnEdit.addActionListener(e -> {
            if (!editMode) {
                // Enter edit mode: ensure a row selected
                editingRow = DataTable.getSelectedRow();
                if (editingRow < 0) {
                    JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit.");
                    return;
                }
                editMode = true;
                BtnEdit.setText("Save");
                BtnCancel.setEnabled(true);
                // Enable cell editing
                DataTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
            } else {
                // Save changes
                // Stop editor to commit any active cell edit
                if (DataTable.isEditing()) {
                    DataTable.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) DataTable.getModel();
                String table = ComboBoxDataTable.getSelectedItem().toString();
                String pkCol = model.getColumnName(0);
                Object pkVal = model.getValueAt(editingRow, 0);
                int colCount = model.getColumnCount();

                // Build UPDATE SQL
                StringBuilder sql = new StringBuilder("UPDATE ").append(table).append(" SET ");
                for (int i = 1; i < colCount; i++) {
                    sql.append(model.getColumnName(i)).append(" = ?");
                    if (i < colCount - 1) sql.append(", ");
                }
                sql.append(" WHERE ").append(pkCol).append(" = ?");

                try (Connection conn = connectDb();
                     PreparedStatement pst = conn.prepareStatement(sql.toString())) {
                    // Set parameters
                    for (int i = 1; i < colCount; i++) {
                        pst.setObject(i, model.getValueAt(editingRow, i));
                    }
                    pst.setObject(colCount, pkVal);

                    int updated = pst.executeUpdate();
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Tidak ada perubahan.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Gagal update: " + ex.getMessage());
                }

                // Exit edit mode
                editMode = false;
                editingRow = -1;
                BtnEdit.setText("Edit");
                BtnCancel.setEnabled(false);
                // Disable cell editing
                DataTable.setDefaultEditor(Object.class, null);
                // Refresh table
                loadData(table, null);
            }
        });

        // Cancel button listener
        BtnCancel.addActionListener(e -> {
            // Exit edit mode without saving
            editMode = false;
            editingRow = -1;
            BtnEdit.setText("Edit");
            BtnCancel.setEnabled(false);
            DataTable.setDefaultEditor(Object.class, null);
            loadData(ComboBoxDataTable.getSelectedItem().toString(), null);
        });

        // Initial state: disable Cancel and editing
        BtnCancel.setEnabled(false);
        DataTable.setDefaultEditor(Object.class, null);
    }

    /** Single DB connection method */
    private Connection connectDb() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemInventarisDB;encrypt=true;trustServerCertificate=true";
        String user = "sa";  // adjust as needed
        String pass = "1234"; // adjust as needed
        return DriverManager.getConnection(url, user, pass);
    }

    /** Load data and build table model with editable only for editingRow */
    private void loadData(String tableName, String keyword) {
        String sql = "SELECT * FROM " + tableName;
        if (keyword != null && !keyword.isEmpty()) {
            sql += " WHERE ";
        }
        try (Connection conn = connectDb();
             Statement stmt = conn.createStatement()) {
            if (keyword != null && !keyword.isEmpty()) {
                ResultSet rsCols = stmt.executeQuery("SELECT TOP 1 * FROM " + tableName);
                ResultSetMetaData md = rsCols.getMetaData();
                int cnt = md.getColumnCount();
                StringBuilder where = new StringBuilder();
                for (int i = 1; i <= cnt; i++) {
                    where.append(md.getColumnName(i)).append(" LIKE '%").append(keyword).append("%'");
                    if (i < cnt) where.append(" OR ");
                }
                sql += where.toString();
            }
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int cnt = md.getColumnCount();
            Vector<String> cols = new Vector<>();
            for (int i = 1; i <= cnt; i++) cols.add(md.getColumnName(i));
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= cnt; i++) row.add(rs.getObject(i));
                data.add(row);
            }
            DefaultTableModel model = new DefaultTableModel(data, cols) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return editMode && row == editingRow && col > 0;
                }
            };
            DataTable.setModel(model);
            DataTable.setRowSelectionAllowed(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
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
        BtnEdit = new javax.swing.JButton();
        BtnCancel = new javax.swing.JButton();

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

        BtnEdit.setText("Edit");

        BtnCancel.setText("Cancel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(BtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 517, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBoxDataTable, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(222, 222, 222))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(BtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(ComboBoxDataTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 516, Short.MAX_VALUE)
                        .addComponent(BtnDelete)
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnEdit)
                            .addComponent(BtnCancel))
                        .addGap(47, 47, 47))))
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
//         int selectedRow = DataTable.getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
//            return;
//        }
//
//        String table = ComboBoxDataTable.getSelectedItem().toString();
//        String primaryKey = DataTable.getColumnName(0);
//        Object pkValue = DataTable.getValueAt(selectedRow, 0);
//
//        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            try (Connection conn = connectDb();
//                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + table + " WHERE " + primaryKey + " = ?")) {
//                stmt.setObject(1, pkValue);
//                int deleted = stmt.executeUpdate();
//                if (deleted > 0) {
//                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
//                    loadData(table, null);
//                }
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(this, "Gagal menghapus: " + e.getMessage());
//            }
//        }
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
    private javax.swing.JButton BtnCancel;
    private javax.swing.JButton BtnDelete;
    private javax.swing.JButton BtnEdit;
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
