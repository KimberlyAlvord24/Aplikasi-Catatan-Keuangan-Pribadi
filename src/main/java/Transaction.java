package org.example;

import org.sql2o.*;
import java.util.List;

public class Transaction {
    private Sql2o sql2o;

    public Transaction() {
        this.sql2o = new Sql2o("jdbc:mysql://localhost:3306/CatatanKeuangan", "root", "");
    }

    public boolean isConnected() {
        try (Connection conn = sql2o.open()) {
            return conn != null && !conn.getJdbcConnection().isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    public int insertTransaction(String nama_transaksi, String kategori, int jumlah) {
        String query = "INSERT INTO transaction (nama_transaksi, kategori, jumlah) VALUES (:nama_transaksi, :kategori, :jumlah)";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .addParameter("nama_transaksi", nama_transaksi)
                    .addParameter("kategori", kategori)
                    .addParameter("jumlah", jumlah)
                    .executeUpdate().getKey(Integer.class);
        }
    }

    public void updateTransaction(int id, String nama_transaksi, String kategori, int jumlah) {
        String query = "UPDATE transaction SET nama_transaksi = :nama_transaksi, kategori = :kategori, jumlah = :jumlah WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(query)
                    .addParameter("nama_transaksi", nama_transaksi)
                    .addParameter("kategori", kategori)
                    .addParameter("jumlah", jumlah)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public void deleteTransaction(int id) {
        String query = "DELETE FROM transaction WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(query)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public List<TransactionRecord> selectAllTransactions() {
        String query = "SELECT * FROM transaction";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .executeAndFetch(TransactionRecord.class);
        }
    }

    public int calculateTotalAmount() {
        String query = "SELECT SUM(jumlah) FROM transaction";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .executeScalar(Integer.class);
        }
    }

    public List<TransactionRecord> selectTransactionsByCategory(String kategori) {
        String query = "SELECT * FROM transaction WHERE kategori = :kategori";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .addParameter("kategori", kategori)
                    .executeAndFetch(TransactionRecord.class);
        }
    }

    public List<TransactionRecord> selectLatestTransactions(int limit) {
        String query = "SELECT * FROM transaction ORDER BY id DESC LIMIT :limit";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query)
                    .addParameter("limit", limit)
                    .executeAndFetch(TransactionRecord.class);
        }
    }
}

// Kelas untuk merepresentasikan record transaksi
class TransactionRecord {
    public int id;
    public String nama_transaksi;
    public String kategori;
    public int jumlah;

    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + nama_transaksi + ", Kategori: " + kategori + ", Jumlah: " + jumlah;
    }
}
