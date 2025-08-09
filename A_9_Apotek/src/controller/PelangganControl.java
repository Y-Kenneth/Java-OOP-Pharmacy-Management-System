package controller;

import model.Pelanggan;
import dao.PelangganDAO;
import java.util.List;

public class PelangganControl {
    PelangganDAO pDao = new PelangganDAO();

    // Metode untuk memasukkan data pelanggan
    public void insertDataPelanggan(Pelanggan P) {
        pDao.insert(P);
    }

    // Metode untuk menampilkan semua data pelanggan dalam format String
    public String showAllStringPelanggan() {
        List<Pelanggan> listP = pDao.showData(0); // Memanggil showData tanpa filter
        String pelangganString = "";
        int i = 0;

        for (Pelanggan p : listP) {
            i++;
            // Memformat output string sesuai atribut Pelanggan
            pelangganString += i + ". " + p.getNama() + " || " + p.getUmur() + " || " + p.getNotelp() + "\n";
        }
        return pelangganString;
    }

    // Metode untuk mencari pelanggan berdasarkan nama
    public Pelanggan searchPelangganByName(String nama) {
        return pDao.search(nama); // Memanggil overloaded search(String nama) di PelangganDAO
    }

    // Metode untuk mencari pelanggan berdasarkan ID
    public Pelanggan searchPelangganById(int id_pelanggan) {
        return pDao.search(id_pelanggan); // Memanggil search(Integer id) dari IDAO
    }

    // Metode untuk mencari pelanggan berdasarkan ID atau nama (menggunakan exception handling)
    public Pelanggan searchPelangganByAll(String input) {
        try {
            int temp = Integer.parseInt(input); // Coba konversi ke integer
            return searchPelangganById(temp);    // Jika berhasil, cari berdasarkan ID
        } catch (NumberFormatException e) {
            return searchPelangganByName(input); // Jika gagal, cari berdasarkan nama
        }
    }

    // Metode untuk mengupdate data pelanggan
    public void updateDataPelanggan(Pelanggan P, int id_pelanggan) {
        pDao.update(P, id_pelanggan);
    }

    // Metode untuk menghapus data pelanggan
    public void deleteDataPelanggan(int id_pelanggan) {
        pDao.delete(id_pelanggan);
    }

    // Metode untuk menampilkan daftar pelanggan untuk dropdown
    public List<Pelanggan> showListPelanggan() {
        List<Pelanggan> dataPelanggan = pDao.IShowForDropdown();
        return dataPelanggan;
    }
    
    // --- Method untuk Mengambil daftar pelanggan yang sudah bertransaksi ---
    public List<Pelanggan> showListPelangganWithTransaksi() {
        return pDao.getPelangganWithTransaksi();
    }
}
