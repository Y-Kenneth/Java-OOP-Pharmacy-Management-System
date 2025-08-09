package controller;

import dao.ProdukDAO;
import model.Produk;
import java.util.List;

public class ProdukControl {
    ProdukDAO pDao = new ProdukDAO();

    // Generate ID untuk produk baru
    public String generateIdProduk() {
        return "P" + pDao.generateId();
    }

    // Mencari produk berdasarkan ID
    public Produk searchDataProdukById(String id) {
        return pDao.search(id);
    }
    
    // Mencari Produk berdasarkan Nama
    public Produk searchDataProdukByName(String name){
        return pDao.searchByNama(name);
    }
    
    // Search gabungan
    public Produk searchDataProdukByAll(String input){
        Produk P = searchDataProdukById(input);
        if (P == null){
            return searchDataProdukByName(input);
        }else{
            return P;
        }
    }

    // Menampilkan semua produk untuk dropdown atau daftar umum
    public List<Produk> showListProduk() {
        return pDao.IShowForDropdown();
    }

    // Menghapus data produk (akan memicu penghapusan data spesifik juga melalui ProdukDAO)
    public void deleteDataProduk(String id) {
        pDao.delete(id);
    }
    
    // Mengupdate data produk
    public void updateDataProduk(Produk P, String id){
        pDao.update(P, id);
    }
}
