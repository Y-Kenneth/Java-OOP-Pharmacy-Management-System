package controller;

import dao.TransaksiDAO;
import model.Transaksi;
import model.Produk;
import table.TableTransaksi; 
import java.util.List;
import java.time.LocalDate;

public class TransaksiControl {
    private TransaksiDAO tDao = new TransaksiDAO();

    // Memasukkan data transaksi baru
    public void insertDataTransaksi(Transaksi T) {
        tDao.insert(T);
    }

    // Menampilkan data transaksi dalam bentuk tabel
    // Parameter 'query' di sini tidak digunakan untuk filtering kueri SQL
    // karena tDao.showData(Integer) hanya mencari by ID.
    // showAllData() dari TransaksiDAO akan dipanggil untuk menampilkan semua.
    public TableTransaksi showTable(String query) { // query parameter tidak digunakan untuk filter di DAO ini
        List<Transaksi> dataTransaksi = tDao.showAllData(); // Memanggil metode untuk mendapatkan semua data
        TableTransaksi TPK = new TableTransaksi(dataTransaksi);
        return TPK;
    }

    // Mengupdate data transaksi
    public void updateTransaksi(Transaksi T, int id_transaksi) {
        tDao.update(T, id_transaksi);
    }

    // Menghapus data transaksi
    public void deleteTransaksi(int id_transaksi) {
        tDao.delete(id_transaksi);
    }

    // Pencarian transaksi berdasarkan ID
    public Transaksi searchTransaksiByAll(String input) {
        try {
            int temp = Integer.parseInt(input); // Coba konversi input ke integer (untuk ID)
            return tDao.search(temp); // Cari berdasarkan ID
        } catch (NumberFormatException e) {
            // Jika bukan ID, saat ini tidak ada pencarian berdasarkan string (misal: nama pelanggan)
            // di TransaksiDAO.search(String). Jika diperlukan, metode tersebut harus ditambahkan di DAO.
            System.out.println("Input bukan ID numerik. Pencarian hanya mendukung ID transaksi.");
            return null;
        }
    }
    
    public List<Produk> getPurchasedObatByPelanggan(int idPelanggan){
        return tDao.getPurchasedObatByPelanggan(idPelanggan);
    }
    
    public LocalDate getProductPurchasedDate(int idPelanggan, String idProduk){
        return tDao.getLatestPurchaseDate(idPelanggan, idProduk);
    }
}
