package controller;

import dao.ObatDAO;
import model.Obat;
import model.Produk; // Import kelas Produk List<Produk>
import java.util.List;

public class ObatControl {
    ObatDAO obatDao = new ObatDAO();
    
    public void insertDataObat(Obat O) {
        O.setId_produk("P" + obatDao.generateId());
        obatDao.insert(O);
    }
    
    public String showStringObat(){
        List<Produk> listP = obatDao.showData("Obat");
        String produkString = "";
        for(Produk p : listP) {
            produkString += p.getString() + "\n";
        }
        return produkString;
    }
    
    public void updateDataObat(Obat O, String id_produk_lama, String jenis_resep_baru) {
        obatDao.update(O, O.getId_produk(), O.getJenis_resep());
    }
}
