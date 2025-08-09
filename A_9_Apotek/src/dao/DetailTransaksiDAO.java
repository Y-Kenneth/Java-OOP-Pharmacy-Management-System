package dao;

import model.DetailTransaksi;
import model.Produk; // Diperlukan untuk me-load objek Produk
import model.Transaksi; // Diperlukan untuk referensi objek Transaksi
import connection.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Interface IDAO tidak diimplementasikan disini karena Untuk DetailTransaksiDAO, operasi CRUD (insert, update, delete)
// hanya melibatkan PK komposit (id_transaksi, id_produk). Metode showData dan search tidak akan sesuai dengan 
// signature IDAO<T, I> jika I hanya satu nilai (misalnya String atau Integer).

public class DetailTransaksiDAO { 
    private DbConnection dbCon = new DbConnection();
    private Connection con;

    private ProdukDAO produkDAO; // Akan diinisialisasi melalui konstruktor atau setter

    // Konstruktor untuk menginisialisasi ProdukDAO
    public DetailTransaksiDAO() {
        this.produkDAO = new ProdukDAO(); 
    }

    // --- INSERT ---
    // Untuk PK komposit (id_transaksi, id_produk), tidak ada id_detail_transaksi auto-increment
    public void insert(DetailTransaksi detail) {
        con = dbCon.makeConnection();
        // SQL untuk memasukkan detail transaksi (PK Komposit: id_transaksi, id_produk)
        String sql = "INSERT INTO `detail_transaksi` "
                + "(`id_transaksi`, `id_produk`, `jumlah`, `sub_total`) "
                + "VALUES (" + detail.getTransaksi().getId_transaksi() + ", "
                + "'" + detail.getProduk().getId_produk() + "', "
                + detail.getJumlah() + ", "
                + detail.getSub_total() + ")";

        System.out.println("Adding DetailTransaksi for Transaksi ID: " + detail.getTransaksi().getId_transaksi() + ", Produk ID: " + detail.getProduk().getId_produk() + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Added " + result + " DetailTransaksi.");
        } catch (Exception e) { 
            System.out.println("Error Adding DetailTransaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- READ (Mengambil semua detail untuk satu Transaksi Header) ---
    // Ini bukan showData(I data) dari IDAO karena parameternya adalah ID Transaksi Header
    public List<DetailTransaksi> getDetailsByTransaksiId(int idTransaksi) {
        List<DetailTransaksi> listDetails = new ArrayList<>();
        con = dbCon.makeConnection();
        String sql = "SELECT `id_transaksi`, `id_produk`, `jumlah`, `sub_total` " +
                     "FROM `detail_transaksi` WHERE `id_transaksi` = " + idTransaksi;

        System.out.println("Mengambil DetailTransaksi untuk Transaksi ID: " + idTransaksi + "...");

        try (Statement S = con.createStatement()) {
            try (ResultSet RS = S.executeQuery(sql)) {
                while (RS.next()) {
                    DetailTransaksi detail = new DetailTransaksi();
                    
                    // Set referensi ke Transaksi header (hanya ID, objek penuh bisa di-load nanti jika perlu)
                    Transaksi transaksiHeader = new Transaksi(); // Buat objek placeholder
                    transaksiHeader.setId_transaksi(RS.getInt("id_transaksi"));
                    detail.setTransaksi(transaksiHeader);

                    // Load objek Produk lengkap menggunakan ProdukDAO (ini adalah bagian krusial)
                    Produk produk = produkDAO.search(RS.getString("id_produk")); // Asumsi ProdukDAO punya metode search(id)
                    detail.setProduk(produk); // Set objek Produk

                    detail.setJumlah(RS.getInt("jumlah"));
                    detail.setSub_total(RS.getInt("sub_total"));
                    listDetails.add(detail);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Fetching DetailTransaksi by Transaksi ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listDetails;
    }

    // --- UPDATE (untuk PK komposit) ---
    // Membutuhkan ID lama (id_transaksi dan id_produk) untuk WHERE clause
    public void update(DetailTransaksi detail, int oldIdTransaksi, String oldIdProduk) {
        con = dbCon.makeConnection();
        String sql = "UPDATE `detail_transaksi` SET "
                + "`jumlah` = " + detail.getJumlah() + ", "
                + "`sub_total` = " + detail.getSub_total() + " "
                + "WHERE `id_transaksi` = " + oldIdTransaksi + " AND `id_produk` = '" + oldIdProduk + "'";

        System.out.println("Updating DetailTransaksi for Transaksi ID: " + oldIdTransaksi + ", Produk ID: " + oldIdProduk + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Updated " + result + " DetailTransaksi.");
        } catch (Exception e) {
            System.out.println("Error Updating DetailTransaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- DELETE (untuk PK komposit) ---
    // Membutuhkan ID Transaksi dan ID Produk dari detail yang akan dihapus
    public void delete(int idTransaksi, String idProduk) {
        con = dbCon.makeConnection();
        String sql = "DELETE FROM `detail_transaksi` WHERE `id_transaksi` = " + idTransaksi + " AND `id_produk` = '" + idProduk + "'";

        System.out.println("Deleting DetailTransaksi for Transaksi ID: " + idTransaksi + ", Produk ID: " + idProduk + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Deleted " + result + " DetailTransaksi.");
        } catch (Exception e) {
            System.out.println("Error Deleting DetailTransaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- DELETE by Transaksi ID (Metode Bantuan untuk TransaksiDAO) ---
    public void deleteByTransaksiId(int idTransaksi) {
        con = dbCon.makeConnection();
        String sql = "DELETE FROM `detail_transaksi` WHERE `id_transaksi` = " + idTransaksi;

        System.out.println("Deleting all DetailTransaksi for Transaksi ID: " + idTransaksi + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sql);
            System.out.println("Deleted " + result + " DetailTransaksi items.");
        } catch (Exception e) {
            System.out.println("Error Deleting DetailTransaksi by Transaksi ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }
}