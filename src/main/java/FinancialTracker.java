package org.example;

import java.util.List;
import java.util.Scanner;

public class FinancialTracker {
    public static void main(String[] args) {
        Transaction transaction = new Transaction();
        Scanner scanner = new Scanner(System.in);

        if (!transaction.isConnected()) {
            System.out.println("Tidak dapat terhubung ke database.");
            return;
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Tambah Transaksi");
            System.out.println("2. Update Transaksi");
            System.out.println("3. Hapus Transaksi");
            System.out.println("4. Lihat Daftar Transaksi");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Membersihkan newline

            switch (option) {
                case 1:
                    // Tambah Transaksi
                    System.out.print("Masukkan nama transaksi: ");
                    String namaTransaksi = scanner.nextLine();
                    System.out.print("Masukkan kategori: ");
                    String kategori = scanner.nextLine();
                    System.out.print("Masukkan jumlah: ");
                    int jumlah = scanner.nextInt();
                    transaction.insertTransaction(namaTransaksi, kategori, jumlah);
                    System.out.println("Transaksi berhasil ditambahkan.");
                    break;

                case 2:
                    // Update Transaksi
                    System.out.print("Masukkan ID transaksi yang ingin diupdate: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan newline
                    System.out.print("Masukkan nama transaksi baru: ");
                    String newNamaTransaksi = scanner.nextLine();
                    System.out.print("Masukkan kategori baru: ");
                    String newKategori = scanner.nextLine();
                    System.out.print("Masukkan jumlah baru: ");
                    int newJumlah = scanner.nextInt();
                    transaction.updateTransaction(updateId, newNamaTransaksi, newKategori, newJumlah);
                    System.out.println("Transaksi berhasil diupdate.");
                    break;

                case 3:
                    // Hapus Transaksi
                    System.out.print("Masukkan ID transaksi yang ingin dihapus: ");
                    int deleteId = scanner.nextInt();
                    transaction.deleteTransaction(deleteId);
                    System.out.println("Transaksi berhasil dihapus.");
                    break;

                case 4:
                    // Lihat Daftar Transaksi
                    List<TransactionRecord> allTransactions = transaction.selectAllTransactions();
                    System.out.println("Daftar Semua Transaksi:");
                    for (TransactionRecord record : allTransactions) {
                        System.out.println("ID: " + record.id + ", Nama: " + record.nama_transaksi + ", Kategori: " + record.kategori + ", Jumlah: " + record.jumlah);
                    }
                    break;

                case 5:
                    // Keluar
                    System.out.println("Terima kasih! Sampai jumpa.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi.");
            }
        }
    }
}
