package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TambahTransaksiForm extends JFrame {
    private JTextField txtNamaTransaksi;
    private JComboBox<String> cmbKategori;
    private JTextField txtJumlah;
    private JButton btnTambahTransaksi;
    private JButton btnViewList;
    private JButton btnEditTransaksi;
    private JList<TransactionRecord> transactionList;
    private DefaultListModel<TransactionRecord> listModel;
    private Transaction transaction;

    public TambahTransaksiForm() {
        transaction = new Transaction();
        setTitle("Tambah Transaksi");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblNamaTransaksi = new JLabel("Nama Transaksi:");
        txtNamaTransaksi = new JTextField();

        JLabel lblKategori = new JLabel("Kategori:");
        String[] categories = {"Kebutuhan", "Hiburan", "Makanan", "Transportasi", "Pendidikan"};
        cmbKategori = new JComboBox<>(categories);

        JLabel lblJumlah = new JLabel("Jumlah:");
        txtJumlah = new JTextField();

        btnTambahTransaksi = new JButton("Tambah Transaksi");
        btnViewList = new JButton("Lihat Daftar Transaksi");
        btnEditTransaksi = new JButton("Edit Transaksi");

        listModel = new DefaultListModel<>();
        transactionList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(transactionList);

        add(lblNamaTransaksi);
        add(txtNamaTransaksi);
        add(lblKategori);
        add(cmbKategori);
        add(lblJumlah);
        add(txtJumlah);
        add(new JLabel()); // Spacer
        add(btnTambahTransaksi);
        add(btnViewList);
        add(btnEditTransaksi);
        add(scrollPane);

        btnTambahTransaksi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahTransaksi();
            }
        });

        btnViewList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTransactionList();
            }
        });

        btnEditTransaksi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTransaksi();
            }
        });

        setVisible(true);
    }

    private void tambahTransaksi() {
        String namaTransaksi = txtNamaTransaksi.getText();
        String kategori = (String) cmbKategori.getSelectedItem();
        int jumlah;

        try {
            jumlah = Integer.parseInt(txtJumlah.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (transaction.isConnected()) {
            try {
                int generatedId = transaction.insertTransaction(namaTransaksi, kategori, jumlah);
                JOptionPane.showMessageDialog(this, "Transaksi berhasil ditambahkan dengan ID: " + generatedId, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadTransactionList(); // Refresh list after adding
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan transaksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tidak dapat terhubung ke database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTransactionList() {
        listModel.clear();
        List<TransactionRecord> allTransactions = transaction.selectAllTransactions();
        for (TransactionRecord record : allTransactions) {
            listModel.addElement(record);
        }
    }

    private void editTransaksi() {
        TransactionRecord selectedRecord = transactionList.getSelectedValue();
        if (selectedRecord == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih transaksi untuk diedit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String newNamaTransaksi = JOptionPane.showInputDialog(this, "Masukkan nama transaksi baru:", selectedRecord.nama_transaksi);
        String newKategori = (String) cmbKategori.getSelectedItem();
        String jumlahStr = JOptionPane.showInputDialog(this, "Masukkan jumlah baru:", selectedRecord.jumlah);
        int newJumlah;

        try {
            newJumlah = Integer.parseInt(jumlahStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        transaction.updateTransaction(selectedRecord.id, newNamaTransaksi, newKategori, newJumlah);
        JOptionPane.showMessageDialog(this, "Transaksi berhasil diupdate.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        loadTransactionList(); // Refresh list after editing
    }

    private void clearFields() {
        txtNamaTransaksi.setText("");
        txtJumlah.setText("");
        cmbKategori.setSelectedIndex(0); // Reset to first category
    }

    public static void main(String[] args) {
        new TambahTransaksiForm();
    }
}
