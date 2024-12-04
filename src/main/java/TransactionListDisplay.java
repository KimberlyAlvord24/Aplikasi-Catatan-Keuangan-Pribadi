package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionListDisplay {
    public static void main(String[] args) {
        Transaction transaction = new Transaction();

        // Memanggil metode untuk mendapatkan semua transaksi
        List<TransactionRecord> transactions = transaction.selectAllTransactions();

        // Membuat JFrame untuk menampilkan data
        JFrame frame = new JFrame("Daftar Transaksi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Menggunakan JTextArea untuk menampilkan daftar transaksi
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        // Menambahkan data transaksi ke JTextArea
        for (TransactionRecord record : transactions) {
            textArea.append("ID: " + record.id + "\n");
            textArea.append("Nama Transaksi: " + record.nama_transaksi + "\n");
            textArea.append("Kategori: " + record.kategori + "\n");
            textArea.append("Jumlah: " + record.jumlah + "\n");
            textArea.append("-------------------------\n");
        }

        // Menambahkan JTextArea ke JFrame
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
