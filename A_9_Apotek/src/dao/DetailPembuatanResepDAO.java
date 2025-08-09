package dao;

import model.DetailPembuatanResep;
import model.PembuatanResep;
import model.Produk; // Diperlukan untuk me-load objek Produk
import connection.DbConnection; 

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetailPembuatanResepDAO { // Tidak mengimplementasikan IDAO secara langsung karena PK komposit
    private DbConnection dbCon = new DbConnection();
    private Connection con;

    // Untuk DAO lain yang mungkin dibutuhkan untuk me-load objek Produk penuh
    private ProdukDAO produkDAO; 

    // Konstruktor untuk menginisialisasi ProdukDAO
    public DetailPembuatanResepDAO() {
        this.produkDAO = new ProdukDAO(); 
    }

    // --- INSERT ---
    // Untuk PK komposit (id_resep, id_produk), tidak ada id_detail_resep auto-increment
    public void insert(DetailPembuatanResep detail) {
        con = dbCon.makeConnection();
        // SQL untuk memasukkan detail resep (PK Komposit: id_resep, id_produk)
        String sql = "INSERT INTO `detail_pembuatan_resep` "
                + "(`id_resep`, `id_produk`, `jumlah_resep`, `aturan_pakai`) "
                + "VALUES (" + detail.getPembuatanResep().getId_resep() + ", "
                + "'" + detail.getProduk().getId_produk() + "', "
                + detail.getJumlah_resep() + ", "
                + "'" + detail.getAturan_pakai() + "')";

        System.out.println("Adding DetailPembuatanResep for Resep ID: " + detail.getPembuatanResep().getId_resep() + ", Produk ID: " + detail.getProduk().getId_produk() + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Added " + result + " DetailPembuatanResep.");
        } catch (Exception e) { // Menangkap Exception umum sesuai contoh Anda
            System.out.println("Error Adding DetailPembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- READ (Mengambil semua detail untuk satu PembuatanResep Header) ---
    public List<DetailPembuatanResep> getDetailsByResepId(int idResep) {
        List<DetailPembuatanResep> listDetails = new ArrayList<>();
        con = dbCon.makeConnection();
        String sql = "SELECT `id_resep`, `id_produk`, `jumlah_resep`, `aturan_pakai` " +
                     "FROM `detail_pembuatan_resep` WHERE `id_resep` = " + idResep;

        System.out.println("Mengambil DetailPembuatanResep untuk Resep ID: " + idResep + "...");

        try (Statement S = con.createStatement()) {
            try (ResultSet RS = S.executeQuery(sql)) {
                while (RS.next()) {
                    DetailPembuatanResep detail = new DetailPembuatanResep();
                    
                    // Set referensi ke PembuatanResep header (hanya ID)
                    PembuatanResep resepHeader = new PembuatanResep(); // Buat objek placeholder
                    resepHeader.setId_resep(RS.getInt("id_resep"));
                    detail.setPembuatanResep(resepHeader);

                    // Load objek Produk lengkap menggunakan ProdukDAO
                    Produk produk = produkDAO.search(RS.getString("id_produk")); // Asumsi ProdukDAO punya metode search(id)
                    detail.setProduk(produk); // Set objek Produk

                    detail.setJumlah_resep(RS.getInt("jumlah_resep"));
                    detail.setAturan_pakai(RS.getString("aturan_pakai"));
                    listDetails.add(detail);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Fetching DetailPembuatanResep by Resep ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listDetails;
    }

    // --- UPDATE (untuk PK komposit) ---
    // Membutuhkan ID lama (id_resep dan id_produk) untuk WHERE clause
    public void update(DetailPembuatanResep detail, int oldIdResep, String oldIdProduk) {
        con = dbCon.makeConnection();
        String sql = "UPDATE `detail_pembuatan_resep` SET "
                + "`jumlah_resep` = " + detail.getJumlah_resep() + ", "
                + "`aturan_pakai` = '" + detail.getAturan_pakai() + "' "
                + "WHERE `id_resep` = " + oldIdResep + " AND `id_produk` = '" + oldIdProduk + "'";

        System.out.println("Updating DetailPembuatanResep for Resep ID: " + oldIdResep + ", Produk ID: " + oldIdProduk + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Updated " + result + " DetailPembuatanResep.");
        } catch (Exception e) {
            System.out.println("Error Updating DetailPembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- DELETE (untuk PK komposit) ---
    // Membutuhkan ID Resep dan ID Produk dari detail yang akan dihapus
    public void delete(int idResep, String idProduk) {
        con = dbCon.makeConnection();
        String sql = "DELETE FROM `detail_pembuatan_resep` WHERE `id_resep` = " + idResep + " AND `id_produk` = '" + idProduk + "'";

        System.out.println("Deleting DetailPembuatanResep for Resep ID: " + idResep + ", Produk ID: " + idProduk + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Deleted " + result + " DetailPembuatanResep.");
        } catch (Exception e) {
            System.out.println("Error Deleting DetailPembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- DELETE by Resep ID (Metode Bantuan untuk PembuatanResepDAO) ---
    public void deleteByResepId(int idResep) {
        con = dbCon.makeConnection();
        String sql = "DELETE FROM `detail_pembuatan_resep` WHERE `id_resep` = " + idResep;

        System.out.println("Deleting all DetailPembuatanResep for Resep ID: " + idResep + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Deleted " + result + " DetailPembuatanResep items.");
        } catch (Exception e) {
            System.out.println("Error Deleting DetailPembuatanResep by Resep ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }
}
