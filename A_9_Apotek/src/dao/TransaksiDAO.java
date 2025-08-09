package dao;

import model.*; 
import connection.DbConnection; 
import interfaceDAO.IDAO; 

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.sql.Date;

public class TransaksiDAO implements IDAO<Transaksi, Integer>{
    private DbConnection dbCon = new DbConnection();
    private Connection con;

    // Untuk mengelola detail transaksi secara terpisah
    private DetailTransaksiDAO detailTransaksiDAO = new DetailTransaksiDAO();
    
    private PelangganDAO pelangganDAO = new PelangganDAO();
    private ApotekerDAO apotekerDAO = new ApotekerDAO();
    private ProdukDAO produkDAO = new ProdukDAO(); // Diperlukan untuk me-load objek Produk di DetailTransaksiDAO

    // --- INSERT (untuk Transaksi Header dan Detailnya) ---
    @Override
    public void insert(Transaksi transaksi) {
        con = dbCon.makeConnection();
        
        String sqlHeader = "INSERT INTO `transaksi` "
                + "(`id_pelanggan`, `id_apoteker`, `tanggal`, `total_harga`)"
                + " VALUES (" + transaksi.getPelanggan().getId_pelanggan() + ", "
                + transaksi.getApoteker().getId_apoteker() + ", "
                + "'" + transaksi.getTanggal().toString() + "', " // Menggunakan toString() LocalDate
                + transaksi.getTotal_harga() + ")";

        System.out.println("Adding Transaksi Header...");

        try (Statement S = con.createStatement()) {
            // Jalankan INSERT dan coba dapatkan ID yang baru di-generate
            S.executeUpdate(sqlHeader, Statement.RETURN_GENERATED_KEYS); // Tambahkan Statement.RETURN_GENERATED_KEYS

            // Ambil ID Transaksi yang baru di-generate
            try (ResultSet rs = S.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedIdTransaksi = rs.getInt(1);
                    transaksi.setId_transaksi(generatedIdTransaksi); // Set ID ke objek transaksi header

                    // Sekarang, simpan detail transaksi
                    System.out.println("Adding Detail Transaksi for ID: " + generatedIdTransaksi);
                    for (DetailTransaksi detail : transaksi.getDetailTransaksi()) {
                        detail.setTransaksi(transaksi); // Pastikan detail punya referensi ke header yang baru di-generate ID-nya
                        detailTransaksiDAO.insert(detail); // Panggil DAO detail untuk menyimpan
                    }
                }
            }
        } catch (Exception e) { 
            System.out.println("Error Adding Transaksi: " + e.getMessage());
            e.printStackTrace(); // Cetak stack trace untuk debugging
        } finally {
            dbCon.closeConnection(); // Tutup koneksi setelah semua operasi selesai
        }
    }

    // --- READ (Show 1 Transaksi) ---
    @Override
    public List<Transaksi> showData(Integer query) { // Menggunakan Integer untuk ID transaksi pada showData
        
        List<Transaksi> listTransaksi = new ArrayList<>();
        con = dbCon.makeConnection();

        // SQL untuk mengambil header transaksi, JOIN dengan pelanggan dan apoteker
        String sql = "SELECT t.id_transaksi, t.tanggal, t.total_harga, "
                + "p.id_pelanggan, p.nama AS nama_pelanggan, p.umur, p.notelp, "
                + "a.id_apoteker, a.nama_apoteker, a.email, a.gaji, a.username, a.password "
                + "FROM `transaksi` t "
                + "JOIN `pelanggan` p ON t.id_pelanggan = p.id_pelanggan "
                + "JOIN `apoteker` a ON t.id_apoteker = a.id_apoteker "
                + "WHERE t.id_transaksi = " + query; // Filter berdasarkan ID Transaksi

        System.out.println("Mengambil data Transaksi dengan ID: " + query + "...");

        try (Statement S = con.createStatement()) {
            try (ResultSet RS = S.executeQuery(sql)) {
                if (RS.next()) { // Hanya akan ada satu hasil untuk pencarian by ID
                    // Buat objek Pelanggan
                    Pelanggan pelanggan = new Pelanggan(
                        RS.getInt("id_pelanggan"),
                        RS.getString("nama_pelanggan"),
                        RS.getInt("umur"),
                        RS.getString("notelp")
                    );

                    // Buat objek Apoteker
                    Apoteker apoteker = new Apoteker(
                        RS.getInt("id_apoteker"),
                        RS.getString("nama_apoteker"),
                        RS.getString("email"),
                        RS.getInt("gaji"),
                        RS.getString("username"),
                        RS.getString("password")
                    );

                    // Buat objek Transaksi header
                    Transaksi transaksi = new Transaksi(
                            RS.getInt("id_transaksi"),
                            pelanggan,
                            apoteker,
                            RS.getDate("tanggal").toLocalDate(), // Konversi java.sql.Date ke LocalDate
                            RS.getInt("total_harga"),
                            null // Detail akan diisi di bawah
                    );

                    // Ambil dan set detail transaksinya
                    List<DetailTransaksi> details = detailTransaksiDAO.getDetailsByTransaksiId(transaksi.getId_transaksi());
                    transaksi.setDetailTransaksi(details);

                    listTransaksi.add(transaksi);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Fetching Data Transaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listTransaksi; // Mengembalikan List (untuk konsisten dengan IDAO), tapi mungkin hanya 1 item
    }
    
    // --- READ (Show All Transaksi) ---
    public List<Transaksi> showAllData() {
        con = dbCon.makeConnection();
        List<Transaksi> listTransaksi = new ArrayList<>();

        String sql = "SELECT t.id_transaksi, t.tanggal, t.total_harga, "
                + "p.id_pelanggan, p.nama AS nama_pelanggan, p.umur, p.notelp, "
                + "a.id_apoteker, a.nama_apoteker, a.email, a.gaji, a.username, a.password "
                + "FROM `transaksi` t "
                + "JOIN `pelanggan` p ON t.id_pelanggan = p.id_pelanggan "
                + "JOIN `apoteker` a ON t.id_apoteker = a.id_apoteker";

        System.out.println("Mengambil semua data Transaksi...");

        try (Statement S = con.createStatement()) {
            try (ResultSet RS = S.executeQuery(sql)) {
                while (RS.next()) {
                    Pelanggan pelanggan = new Pelanggan(
                        RS.getInt("id_pelanggan"),
                        RS.getString("nama_pelanggan"),
                        RS.getInt("umur"),
                        RS.getString("notelp")
                    );

                    Apoteker apoteker = new Apoteker(
                        RS.getInt("id_apoteker"),
                        RS.getString("nama_apoteker"),
                        RS.getString("email"),
                        RS.getInt("gaji"),
                        RS.getString("username"),
                        RS.getString("password")
                    );

                    Transaksi transaksi = new Transaksi(
                            RS.getInt("id_transaksi"),
                            pelanggan,
                            apoteker,
                            RS.getDate("tanggal").toLocalDate(),
                            RS.getInt("total_harga"),
                            null
                    );
                    // Ambil dan set detail transaksinya
                    List<DetailTransaksi> details = detailTransaksiDAO.getDetailsByTransaksiId(transaksi.getId_transaksi());
                    transaksi.setDetailTransaksi(details);

                    listTransaksi.add(transaksi);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Fetching All Data Transaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listTransaksi;
    }


    // --- UPDATE (untuk Transaksi Header dan Detailnya) ---
    @Override
    public void update(Transaksi transaksi, Integer idTransaksi) {
        con = dbCon.makeConnection();
        // SQL untuk mengupdate header transaksi
        String sqlHeader = "UPDATE `transaksi` SET "
                + "`id_pelanggan` = " + transaksi.getPelanggan().getId_pelanggan() + ", "
                + "`id_apoteker` = " + transaksi.getApoteker().getId_apoteker() + ", "
                + "`tanggal` = '" + transaksi.getTanggal().toString() + "', "
                + "`total_harga` = " + transaksi.getTotal_harga() + " "
                + "WHERE `id_transaksi` = " + idTransaksi;

        System.out.println("Editing Transaksi Header with ID: " + idTransaksi + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sqlHeader);
            System.out.println("Edited " + result + " Transaksi Header.");

            // Strategi Update Detail: Hapus semua detail lama, lalu masukkan kembali semua detail baru.
            // Metode deleteByTransaksiId di DetailTransaksiDAO harus ada
            detailTransaksiDAO.deleteByTransaksiId(idTransaksi); 
            System.out.println("Updating Detail Transaksi for ID: " + idTransaksi);
            
            for (DetailTransaksi detail : transaksi.getDetailTransaksi()) {
                detail.setTransaksi(transaksi); // Pastikan detail punya ID transaksi header yang benar
                detailTransaksiDAO.insert(detail); // Panggil DAO detail untuk menyimpan
            }

        } catch (Exception e) {
            System.out.println("Error Updating Transaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- DELETE (untuk Transaksi Header dan semua Detailnya) ---
    @Override
    public void delete(Integer idTransaksi) {
        con = dbCon.makeConnection();
        try {
            // Pertama, hapus semua detail transaksi yang terkait
            detailTransaksiDAO.deleteByTransaksiId(idTransaksi);
            System.out.println("Deleting Detail Transaksi for Header ID: " + idTransaksi);

            // Kemudian, hapus header transaksinya
            String sqlHeader = "DELETE FROM `transaksi` WHERE `id_transaksi` = " + idTransaksi;
            System.out.println("Deleting Transaksi Header with ID: " + idTransaksi);

            try (Statement S = con.createStatement()) {
                int result = S.executeUpdate(sqlHeader);
                System.out.println("Deleted " + result + " Transaksi Header " + idTransaksi);
            }
        } catch (Exception e) {
            System.out.println("Error Deleting Transaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- SEARCH (by ID, sesuai IDAO) ---
    @Override
    public Transaksi search(Integer idTransaksi) {
        con = dbCon.makeConnection();
        Transaksi transaksi = null;
        String sql = "SELECT t.id_transaksi, t.tanggal, t.total_harga, "
                + "p.id_pelanggan, p.nama AS nama_pelanggan, p.umur, p.notelp, "
                + "a.id_apoteker, a.nama_apoteker, a.email, a.gaji, a.username, a.password "
                + "FROM `transaksi` t "
                + "JOIN `pelanggan` p ON t.id_pelanggan = p.id_pelanggan "
                + "JOIN `apoteker` a ON t.id_apoteker = a.id_apoteker "
                + "WHERE t.id_transaksi = " + idTransaksi;

        try (Statement S = con.createStatement()) {
            try (ResultSet RS = S.executeQuery(sql)) {
                if (RS.next()) {
                    Pelanggan pelanggan = new Pelanggan(
                        RS.getInt("id_pelanggan"),
                        RS.getString("nama_pelanggan"),
                        RS.getInt("umur"),
                        RS.getString("notelp")
                    );

                    Apoteker apoteker = new Apoteker(
                        RS.getInt("id_apoteker"),
                        RS.getString("nama_apoteker"),
                        RS.getString("email"),
                        RS.getInt("gaji"),
                        RS.getString("username"),
                        RS.getString("password")
                    );

                    transaksi = new Transaksi(
                            RS.getInt("id_transaksi"),
                            pelanggan,
                            apoteker,
                            RS.getDate("tanggal").toLocalDate(),
                            RS.getInt("total_harga"),
                            null
                    );
                    // Ambil dan set detail transaksinya
                    List<DetailTransaksi> details = detailTransaksiDAO.getDetailsByTransaksiId(transaksi.getId_transaksi());
                    transaksi.setDetailTransaksi(details);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Searching Transaksi: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return transaksi;
    }
    
    // --- Mendapatkan daftar produk (Khususnya Obat) yang pernah dibeli pelanggan tertentu ---
    public List<Produk> getPurchasedObatByPelanggan(int idPelanggan) {
        con = dbCon.makeConnection();
        List<Produk> purchasedObatList = new ArrayList<>();
        String sql = "SELECT DISTINCT p.id_produk, p.nama_produk, p.jenis, p.stok, p.harga, p.tgl_kadaluarsa, " +
                     "o.jenis_resep " + 
                     "FROM `produk` p " +
                     "JOIN `detail_transaksi` dt ON p.id_produk = dt.id_produk " +
                     "JOIN `transaksi` t ON dt.id_transaksi = t.id_transaksi " +
                     "LEFT JOIN `obat` o ON p.id_produk = o.id_produk " + 
                     "WHERE t.id_pelanggan = " + idPelanggan + " AND p.jenis = 'Obat' AND o.jenis_resep = 'Wajib'"; 
        
        try (Statement S = con.createStatement();
             ResultSet rs = S.executeQuery(sql)) {
            while (rs.next()) {
                String id_produk = rs.getString("id_produk");
                String nama_produk = rs.getString("nama_produk");
                String jenisProduk = rs.getString("jenis");
                int stok = rs.getInt("stok");
                int harga = rs.getInt("harga");
                LocalDate tgl_kadaluarsa = rs.getDate("tgl_kadaluarsa") != null ? rs.getDate("tgl_kadaluarsa").toLocalDate() : null;
                String jenis_resep = rs.getString("jenis_resep");
                
                purchasedObatList.add(new Obat(id_produk, nama_produk, jenisProduk, stok, harga, tgl_kadaluarsa, jenis_resep));
            }
        } catch (Exception e) {
            System.err.println("Error mendapatkan Obat yang pernah dibeli pelanggan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return purchasedObatList;
    }
    
    // --- Mengambil Tanggal sebuah Produk dibeli, untuk digunakan sebagai tanggal Pembuatan Resep ---
    public LocalDate getLatestPurchaseDate(int idPelanggan, String idProduk) {
        con = dbCon.makeConnection();
        LocalDate latestDate = null;

        String sql = "SELECT MAX(t.tanggal) AS latest_date " +
                     "FROM `transaksi` t " +
                     "JOIN `detail_transaksi` dt ON t.id_transaksi = dt.id_transaksi " +
                     "WHERE t.id_pelanggan = " + idPelanggan + " AND dt.id_produk = '" + idProduk + "'";
        
        System.out.println("Mengambil tanggal pembelian terbaru: " + sql); 

        try (Statement S = con.createStatement();
             ResultSet RS = S.executeQuery(sql)) {
            if (RS.next()) {
                Date sqlDate = RS.getDate("latest_date"); // Mendapatkan java.sql.Date
                if (sqlDate != null) {
                    latestDate = sqlDate.toLocalDate(); // Konversi ke LocalDate
                }
            }
        } catch (Exception e) { 
            System.err.println("Error mendapatkan tanggal pembelian terbaru: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return latestDate;
    }
}
