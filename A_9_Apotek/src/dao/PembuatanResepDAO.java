package dao;

import model.*; // Import semua kelas model yang dibutuhkan
import connection.DbConnection; 
import interfaceDAO.IDAO; 

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement; 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PembuatanResepDAO implements IDAO<PembuatanResep, Integer>{
    private DbConnection dbCon = new DbConnection();
    private Connection con;

    // Untuk mengelola detail resep secara terpisah
    private DetailPembuatanResepDAO detailPembuatanResepDAO = new DetailPembuatanResepDAO();

    private PelangganDAO pelangganDAO = new PelangganDAO();
    private ApotekerDAO apotekerDAO = new ApotekerDAO();
    private ProdukDAO produkDAO = new ProdukDAO(); // Diperlukan untuk me-load objek Produk di DetailPembuatanResepDAO

    // --- INSERT (untuk Resep Header dan Detailnya) ---
    @Override
    public void insert(PembuatanResep resep) {
        con = dbCon.makeConnection();
        // SQL untuk memasukkan header resep
        String sqlHeader = "INSERT INTO `pembuatan_resep` "
                + "(`id_pelanggan`, `id_apoteker`, `nama_dokter`, `tanggal_resep`)"
                + " VALUES (" + resep.getPelanggan().getId_pelanggan() + ", "
                + resep.getApoteker().getId_apoteker() + ", "
                + "'" + resep.getNama_dokter() + "', "
                + "'" + resep.getTanggal_resep().toString() + "')"; // Menggunakan toString() LocalDate

        System.out.println("Adding PembuatanResep Header...");

        try (Statement S = con.createStatement()) {
            // Jalankan INSERT dan coba dapatkan ID yang baru di-generate
            S.executeUpdate(sqlHeader, Statement.RETURN_GENERATED_KEYS); // Tambahkan Statement.RETURN_GENERATED_KEYS

            // Ambil ID Resep yang baru di-generate
            try (ResultSet rs = S.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedIdResep = rs.getInt(1);
                    resep.setId_resep(generatedIdResep); // Set ID ke objek resep header

                    // Simpan detail resep
                    System.out.println("Adding Detail Resep for ID: " + generatedIdResep);
                    for (DetailPembuatanResep detail : resep.getDetailResep()) {
                        detail.setPembuatanResep(resep); 
                        detailPembuatanResepDAO.insert(detail); // Panggil DAO detail untuk menyimpan
                    }
                }
            }
        } catch (Exception e) { 
            System.out.println("Error Adding PembuatanResep: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            dbCon.closeConnection(); 
        }
    }

    // --- READ (Show All / Search by query) ---
    @Override
    public List<PembuatanResep> showData(Integer query) { // Menggunakan Integer untuk ID resep pada showData
        // Metode ini akan mengasumsikan query adalah ID Resep untuk menampilkan satu resep
        
        List<PembuatanResep> listResep = new ArrayList<>();
        con = dbCon.makeConnection();

        // SQL untuk mengambil header resep, JOIN dengan pelanggan dan apoteker
        String sql = "SELECT pr.id_resep, pr.nama_dokter, pr.tanggal_resep, "
                + "p.id_pelanggan, p.nama AS nama_pelanggan, p.umur, p.notelp, "
                + "a.id_apoteker, a.nama_apoteker, a.email, a.gaji, a.username, a.password "
                + "FROM `pembuatan_resep` pr "
                + "JOIN `pelanggan` p ON pr.id_pelanggan = p.id_pelanggan "
                + "JOIN `apoteker` a ON pr.id_apoteker = a.id_apoteker "
                + "WHERE pr.id_resep = " + query; // Filter berdasarkan ID Resep

        System.out.println("Mengambil data PembuatanResep dengan ID: " + query + "...");

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

                    // Buat objek PembuatanResep header
                    PembuatanResep pembuatanResep = new PembuatanResep(
                            RS.getInt("id_resep"),
                            pelanggan,
                            apoteker,
                            RS.getString("nama_dokter"),
                            RS.getDate("tanggal_resep").toLocalDate(), // Konversi java.sql.Date ke LocalDate
                            null // Detail akan diisi di bawah
                    );

                    // Ambil dan set detail resepnya
                    List<DetailPembuatanResep> details = detailPembuatanResepDAO.getDetailsByResepId(pembuatanResep.getId_resep());
                    pembuatanResep.setDetailResep(details);

                    listResep.add(pembuatanResep);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Fetching Data PembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listResep; // Mengembalikan List (untuk konsisten dengan IDAO), tapi mungkin hanya 1 item
    }

    // --- SHOW ALL DATA (Metode tambahan jika showData(Integer) hanya untuk search by ID) ---
    public List<PembuatanResep> showAllData() {
        con = dbCon.makeConnection();
        List<PembuatanResep> listResep = new ArrayList<>();

        String sql = "SELECT pr.id_resep, pr.nama_dokter, pr.tanggal_resep, "
                + "p.id_pelanggan, p.nama AS nama_pelanggan, p.umur, p.notelp, "
                + "a.id_apoteker, a.nama_apoteker, a.email, a.gaji, a.username, a.password "
                + "FROM `pembuatan_resep` pr "
                + "JOIN `pelanggan` p ON pr.id_pelanggan = p.id_pelanggan "
                + "JOIN `apoteker` a ON pr.id_apoteker = a.id_apoteker";

        System.out.println("Mengambil semua data PembuatanResep...");

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

                    PembuatanResep pembuatanResep = new PembuatanResep(
                            RS.getInt("id_resep"),
                            pelanggan,
                            apoteker,
                            RS.getString("nama_dokter"),
                            RS.getDate("tanggal_resep").toLocalDate(),
                            null
                    );
                    // Ambil dan set detail resepnya
                    List<DetailPembuatanResep> details = detailPembuatanResepDAO.getDetailsByResepId(pembuatanResep.getId_resep());
                    pembuatanResep.setDetailResep(details);

                    listResep.add(pembuatanResep);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Fetching All Data PembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listResep;
    }

    // --- UPDATE (untuk Resep Header dan Detailnya) ---
    @Override
    public void update(PembuatanResep resep, Integer idResep) {
        con = dbCon.makeConnection();
        // SQL untuk mengupdate header resep
        String sqlHeader = "UPDATE `pembuatan_resep` SET "
                + "`id_pelanggan` = " + resep.getPelanggan().getId_pelanggan() + ", "
                + "`id_apoteker` = " + resep.getApoteker().getId_apoteker() + ", "
                + "`nama_dokter` = '" + resep.getNama_dokter() + "', "
                + "`tanggal_resep` = '" + resep.getTanggal_resep().toString() + "' "
                + "WHERE `id_resep` = " + idResep;

        System.out.println("Editing PembuatanResep Header with ID: " + idResep + "...");

        try (Statement S = con.createStatement()) {
            int result = S.executeUpdate(sqlHeader);
            System.out.println("Edited " + result + " PembuatanResep Header.");

            // Strategi Update Detail: Hapus semua detail lama, lalu masukkan kembali semua detail baru.
            // Metode deleteByResepId di DetailPembuatanResepDAO harus ada
            detailPembuatanResepDAO.deleteByResepId(idResep); 
            System.out.println("Updating Detail Resep for ID: " + idResep);
            
            for (DetailPembuatanResep detail : resep.getDetailResep()) {
                detail.setPembuatanResep(resep); // Pastikan detail punya ID resep header yang benar
                detailPembuatanResepDAO.insert(detail); // Panggil DAO detail untuk menyimpan
            }

        } catch (Exception e) {
            System.out.println("Error Updating PembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- DELETE (untuk Resep Header dan semua Detailnya) ---
    @Override
    public void delete(Integer idResep) {
        con = dbCon.makeConnection();
        try {
            // Pertama, hapus semua detail resep yang terkait
            detailPembuatanResepDAO.deleteByResepId(idResep);
            System.out.println("Deleting Detail Resep for Header ID: " + idResep);

            // Kemudian, hapus header resepnya
            String sqlHeader = "DELETE FROM `pembuatan_resep` WHERE `id_resep` = " + idResep;
            System.out.println("Deleting PembuatanResep Header with ID: " + idResep);

            try (Statement S = con.createStatement()) {
                int result = S.executeUpdate(sqlHeader);
                System.out.println("Deleted " + result + " PembuatanResep Header " + idResep);
            }
        } catch (Exception e) {
            System.out.println("Error Deleting PembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- SEARCH (by ID, sesuai IDAO) ---
    @Override
    public PembuatanResep search(Integer idResep) {
        con = dbCon.makeConnection();
        PembuatanResep resep = null;
        String sql = "SELECT pr.id_resep, pr.nama_dokter, pr.tanggal_resep, "
                + "p.id_pelanggan, p.nama AS nama_pelanggan, p.umur, p.notelp, "
                + "a.id_apoteker, a.nama_apoteker, a.email, a.gaji, a.username, a.password "
                + "FROM `pembuatan_resep` pr "
                + "JOIN `pelanggan` p ON pr.id_pelanggan = p.id_pelanggan "
                + "JOIN `apoteker` a ON pr.id_apoteker = a.id_apoteker "
                + "WHERE pr.id_resep = " + idResep;

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

                    resep = new PembuatanResep(
                            RS.getInt("id_resep"),
                            pelanggan,
                            apoteker,
                            RS.getString("nama_dokter"),
                            RS.getDate("tanggal_resep").toLocalDate(),
                            null
                    );
                    List<DetailPembuatanResep> details = detailPembuatanResepDAO.getDetailsByResepId(resep.getId_resep());
                    resep.setDetailResep(details);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Searching PembuatanResep: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return resep;
    }
    
    // --- Method untuk Mengecek apakah resep sudah ada di database untuk pelanggan dan produk tertentu ---
    public boolean checkExistingResep(int idPelanggan, String idProduk) {
        con = dbCon.makeConnection();
        boolean exists = false;

        String sql = "SELECT COUNT(*) AS count FROM `pembuatan_resep` pr " +
                     "JOIN `detail_pembuatan_resep` dpr ON pr.id_resep = dpr.id_resep " +
                     "WHERE pr.id_pelanggan = " + idPelanggan + " AND dpr.id_produk = '" + idProduk + "'";

        System.out.println("Mengecek Resep Duplikat: " + sql);

        try (Statement S = con.createStatement();
             ResultSet RS = S.executeQuery(sql)) {
            if (RS.next()) {
                exists = RS.getInt("count") > 0;
            }
        } catch (Exception e) {
            System.err.println("Error mengecek resep duplikat: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return exists;
    }
}
