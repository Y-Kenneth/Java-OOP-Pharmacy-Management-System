package controller;

import dao.PembuatanResepDAO;
import model.PembuatanResep;
import table.TablePembuatanResep; 
import java.util.List;

public class PembuatanResepControl {
    private PembuatanResepDAO prDao = new PembuatanResepDAO();

    public void insertDataPembuatanResep(PembuatanResep PR) {
        prDao.insert(PR);
    }

    public TablePembuatanResep showTable(String query) { 
        List<PembuatanResep> dataPembuatanResep = prDao.showAllData(); 
        TablePembuatanResep TPR = new TablePembuatanResep(dataPembuatanResep);
        return TPR;
    }

    public void updatePembuatanResep(PembuatanResep PR, int id_resep) {
        prDao.update(PR, id_resep);
    }

    public void deletePembuatanResep(int id_resep) {
        prDao.delete(id_resep);
    }

    public PembuatanResep searchPembuatanResepByAll(String input) {
        try {
            int temp = Integer.parseInt(input); 
            return prDao.search(temp); 
        } catch (NumberFormatException e) {
            System.out.println("Input bukan ID numerik. Pencarian hanya mendukung ID resep.");
            return null;
        }
    }
    
    // Mengecek apakah ada resep yang duplikat
    public boolean isResepExistForPelangganProduk(int idPelanggan, String idProduk){
        return prDao.checkExistingResep(idPelanggan, idProduk);
    }
}
