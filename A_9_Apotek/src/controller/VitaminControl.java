package controller;

import dao.VitaminDAO;
import model.Vitamin;
import model.Produk; // Import kelas Produk List<Produk>
import java.util.List;

public class VitaminControl {
    VitaminDAO vitaminDao = new VitaminDAO();
    
    public void insertDataVitamin(Vitamin V) {
        V.setId_produk("P" + vitaminDao.generateId());
        vitaminDao.insert(V);
    }
    
    public String showStringVitamin(){
        List<Produk> listP = vitaminDao.showData("Vitamin");
        String produkString = "";
        for(Produk p : listP) {
            produkString += p.getString() + "\n";
        }
        return produkString;
    }
    
    public void updateDataVitamin(Vitamin V, String id_produk_lama, String cara_konsumsi_baru) {
        vitaminDao.update(V, id_produk_lama, cara_konsumsi_baru);
    }
}
