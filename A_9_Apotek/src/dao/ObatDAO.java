package dao;

import model.Obat;
import model.Produk; // Superclass untuk casting di updateRole
import connection.DbConnection;
import interfaceDAO.IProdukDAO; // Import IProdukDAO

import java.sql.Connection;
import java.sql.ResultSet; // Diperlukan untuk isObat
import java.sql.Statement;

public class ObatDAO extends ProdukDAO implements IProdukDAO{
    
    public void insert(Obat O) { // Metode insert khusus untuk objek Obat
        super.insert(O); // Panggil insert dari ProdukDAO untuk data umum
        insertSpecificData(O); // Insert data spesifik Obat
    }

    public void insertSpecificData(Obat O) {
        con = dbCon.makeConnection(); 

        String sql = "INSERT INTO `obat` (`id_produk`, `jenis_resep`) "
                + "VALUES ('" + O.getId_produk() + "', '" + O.getJenis_resep() + "')";
        System.out.println("Adding Obat...");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Obat");
            statement.close();
        } catch (Exception e) {
            System.err.println("Error adding Obat specific data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    @Override
    public void deleteOldRole(String id_produk) { // Mengimplementasikan metode dari IProdukDAO
        con = dbCon.makeConnection(); 
        String sql = "DELETE FROM `obat` WHERE `id_produk` = '" + id_produk + "'";
        System.out.println("Deleting Obat...");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Deleted" + result + " Produk " + id_produk);
            statement.close();
        } catch (Exception e) {
            System.err.println("Error deleting Obat: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }   

    public void update(Obat O, String id_produk_lama, String jenis_resep_baru) {
    O.setJenis_resep(jenis_resep_baru); 
        if (cekPerubahanJenis("Obat", id_produk_lama)) { 
            deleteOldRole(id_produk_lama); 
            insertSpecificData(O); 
        } else {
            updateSpecificData(O, id_produk_lama); 
        }
        super.update(O, id_produk_lama); 
    }

    public void updateSpecificData(Produk P, String id_produk_lama) {
        con = dbCon.makeConnection();

        String sql = "UPDATE `obat` SET `jenis_resep` = '" + P.getSpecial() + "' "
                + "WHERE `id_produk` = '" + id_produk_lama + "'";
        
        System.out.println("Updating Obat");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Edited" + result + " Buku " + id_produk_lama);
            statement.close();
        } catch (Exception e) {
            System.err.println("Error updating Obat: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }
    
    // Metode ini dipanggil oleh ProdukDAO.delete() untuk melakukan delete pada Produk berjenis Obat
    public boolean isObat(String id_produk) {
        con = dbCon.makeConnection();
        String sql = "SELECT COUNT(*) AS count FROM `obat` WHERE `id_produk` = '" + id_produk + "'";
        boolean exists = false;
        try (Statement S = con.createStatement();
             ResultSet rs = S.executeQuery(sql)) {
            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.err.println("Error checking if product is Obat: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return exists;
    }
}
