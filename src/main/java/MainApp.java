package org.example;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Transaction transaction = new Transaction();
        Scanner scanner = new Scanner(System.in);

        // Cek koneksi ke database
        if (transaction.isConnected()) {
            System.out.println("Koneksi ke database berhasil.");

            // Pilihan kategori
            String[] categories = {"Kebutuhan", "Hiburan", "Makanan", "Transportasi", "Pendidikan"};
            System.out.println("Pilih kategori transaksi:");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }

            // Input kategori dari pengguna
            System.out.print("Masukkan nomor kategori (1-" + categories.length + "): ");
            int categoryChoice = scanner.nextInt();
            scanner.nextLine(); // Membersihkan newline

            // Validasi pilihan kategori
            if (categoryChoice < 1 || categoryChoice > categories.length) {
                System.out.println("Pilihan kategori tidak valid.");
                return;
            }

            // Input nama transaksi dan jumlah
            System.out.print("Masukkan nama transaksi: ");
            String namaTransaksi = scanner.nextLine();
            System.out.print("Masukkan jumlah: ");
            int jumlah = scanner.nextInt();

            // Menambahkan transaksi baru
            int newTransactionId = transaction.insertTransaction(namaTransaksi, categories[categoryChoice - 1], jumlah);
            System.out.println("Transaksi baru ditambahkan dengan ID: " + newTransactionId);

            // Mengambil dan menampilkan semua transaksi
            List<TransactionRecord> allTransactions = transaction.selectAllTransactions();
            System.out.println("Daftar Semua Transaksi:");
            for (TransactionRecord record : allTransactions) {
                System.out.println("ID: " + record.id + ", Nama: " + record.nama_transaksi + ", Kategori: " + record.kategori + ", Jumlah: " + record.jumlah);
            }

            // Menghitung total jumlah transaksi
            int totalAmount = transaction.calculateTotalAmount();
            System.out.println("Total Jumlah Transaksi: " + totalAmount);

            // Mengambil transaksi berdasarkan kategori
            for (String category : categories) {
                List<TransactionRecord> transactionsByCategory = transaction.selectTransactionsByCategory(category);
                System.out.println("Transaksi Kategori '" + category + "':");
                for (TransactionRecord record : transactionsByCategory) {
                    System.out.println("ID: " + record.id + ", Nama: " + record.nama_transaksi + ", Jumlah: " + record.jumlah);
                }
            }

            // Mengambil transaksi terbaru
            List<TransactionRecord> latestTransactions = transaction.selectLatestTransactions(5);
            System.out.println("Transaksi Terbaru:");
            for (TransactionRecord record : latestTransactions) {
                System.out.println("ID: " + record.id + ", Nama: " + record.nama_transaksi + ", Jumlah: " + record.jumlah);
            }
        } else {
            System.out.println("Koneksi ke database gagal.");
        }

        scanner.close(); // Menutup scanner
    }
}
