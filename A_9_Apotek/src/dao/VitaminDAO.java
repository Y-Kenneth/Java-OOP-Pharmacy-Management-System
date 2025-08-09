package dao;

import model.Vitamin;
import model.Produk; 
import connection.DbConnection;
import interfaceDAO.IProdukDAO; 

import java.sql.Connection;
import java.sql.ResultSet; // Diperlukan untuk isVitamin
import java.sql.Statement;

public class VitaminDAO extends ProdukDAO implements IProdukDAO {
    
    // --- INSERT ---
    public void insert(Vitamin V) { 
        super.insert(V); 
        insertSpecificData(V); 
    }

    // --- INSERT SPECIFIC DATA INTO VITAMIN TABLE ---
    public void insertSpecificData(Vitamin V) {
        con = dbCon.makeConnection(); 

        String sql = "INSERT INTO `vitamin` (`id_produk`, `cara_konsumsi`) "
                + "VALUES ('" + V.getId_produk() + "', '" + V.getCara_konsumsi() + "')";

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Vitamin");
            statement.close();
        } catch (Exception e) {
            System.err.println("Error adding Vitamin specific data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection(); 
        }
    }

    // --- DELETE OLD ROLE (from IProdukDAO) ---
    @Override
    public void deleteOldRole(String id_produk) { 
        con = dbCon.makeConnection(); 
        String sql = "DELETE FROM `vitamin` WHERE `id_produk` = '" + id_produk + "'";
        System.out.println("Deleting Vitamin...");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Deleted" + result + " Produk " + id_produk);
            statement.close();
        } catch (Exception e) {
            System.err.println("Error deleting Vitamin: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection(); 
        }
    }

    // --- UPDATE ---
    public void update(Vitamin V, String id_produk_lama, String cara_konsumsi_baru) {
        V.setCara_konsumsi(cara_konsumsi_baru);
        if (cekPerubahanJenis("Vitamin", id_produk_lama)) { 
            deleteOldRole(id_produk_lama); 
            insertSpecificData(V); 
        } else {
            updateSpecificData(V, id_produk_lama); 
        }
        super.update(V, id_produk_lama); 
    }

    // --- UPDATE SPECIFIC DATA IN VITAMIN TABLE ---
    public void updateSpecificData(Produk P, String id_produk_lama) {
        con = dbCon.makeConnection();

        String sql = "UPDATE `vitamin` SET `cara_konsumsi` = '" + P.getSpecial() + "' "
                + "WHERE `id_produk` = '" + id_produk_lama + "'";
        System.out.println("Updating Vitamin...");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Edited" + result + " Buku " + id_produk_lama);
            statement.close();
        } catch (Exception e) {
            System.err.println("Error updating Vitamin: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection(); 
        }
    }
    
    // Metode ini dipanggil oleh ProdukDAO.delete() untuk melakukan delete pada Produk berjenis Vitamin
    public boolean isVitamin(String id_produk) {
        con = dbCon.makeConnection();
        String sql = "SELECT COUNT(*) AS count FROM `vitamin` WHERE `id_produk` = '" + id_produk + "'";
        boolean exists = false;
        try (Statement S = con.createStatement();
             ResultSet rs = S.executeQuery(sql)) {
            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.err.println("Error checking if product is Vitamin: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return exists;
    }
}

