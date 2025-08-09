package dao;

import connection.DbConnection;
import interfaceDAO.IDAO;
import interfaceDAO.IShowForDropdown;
import model.Produk; // Superclass
import model.Obat;   // Subclass
import model.Vitamin; // Subclass

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate; // Untuk tgl_kadaluarsa
import java.util.ArrayList;
import java.util.List;

public class ProdukDAO implements IDAO<Produk, String>, IShowForDropdown<Produk>{
    protected DbConnection dbCon = new DbConnection();
    protected Connection con;

    // --- INSERT (untuk atribut umum ke tabel `produk`) ---
    @Override
    public void insert(Produk produk) {
        con = dbCon.makeConnection();

        // SQL untuk memasukkan data umum produk ke tabel `produk`
        String sql = "INSERT INTO `produk` (`id_produk`, `nama_produk`, `jenis`, `stok`, `harga`, `tgl_kadaluarsa`) "
                + "VALUES ('" + produk.getId_produk() + "', '" + produk.getNama_produk() + "', '" + produk.getJenis() + "', "
                + produk.getStok() + ", " + produk.getHarga() + ", '" + produk.getTgl_kadaluarsa().toString() + "')";

        System.out.println("Adding Produk (General Data)...");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Added " + result + " Produk (General Data).");
        } catch (Exception e) {
            System.out.println("Error adding Produk (General Data)...");
            System.out.println(e);
            e.printStackTrace(); // Penting untuk debugging
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- READ (Show All / Search by query) ---
    @Override
    public List<Produk> showData(String query) { // query bisa berupa nama, jenis, atau ID produk
        con = dbCon.makeConnection();
        List<Produk> listProduk = new ArrayList<>();

        // SQL untuk mengambil data umum produk dan JOIN dengan tabel obat/vitamin untuk atribut spesifik
        // Gunakan LEFT JOIN karena tidak semua produk punya detail di `obat` atau `vitamin`
        String sql = "SELECT p.id_produk, p.nama_produk, p.jenis, p.stok, p.harga, p.tgl_kadaluarsa, "
                + "o.jenis_resep, v.cara_konsumsi "
                + "FROM `produk` p "
                + "LEFT JOIN `obat` o ON p.id_produk = o.id_produk "
                + "LEFT JOIN `vitamin` v ON p.id_produk = v.id_produk "
                + "WHERE p.nama_produk LIKE '%" + query + "%' OR p.jenis LIKE '%" + query + "%' OR p.id_produk LIKE '%" + query + "%'";

        System.out.println("Fetching Produk Data...");

        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Produk produk = null;
                    String jenisProduk = rs.getString("jenis");

                    // Membaca atribut umum terlebih dahulu
                    String id_produk = rs.getString("id_produk");
                    String nama_produk = rs.getString("nama_produk");
                    int stok = rs.getInt("stok");
                    int harga = rs.getInt("harga"); // Menggunakan int sesuai revisi
                    LocalDate tgl_kadaluarsa = rs.getDate("tgl_kadaluarsa") != null ? rs.getDate("tgl_kadaluarsa").toLocalDate() : null;
                    
                    if (rs.getString("jenis").equals("Obat")) {
                        produk = new Obat(
                            rs.getString("jenis_resep"),
                            id_produk, nama_produk,
                            jenisProduk, stok, harga, tgl_kadaluarsa);
                    } else{
                        produk = new Vitamin(
                            rs.getString("cara_konsumsi"),
                            id_produk, nama_produk,
                            jenisProduk, stok, harga, tgl_kadaluarsa);
                    }
                    
                    listProduk.add(produk);
                }
                rs.close();
                statement.close();
            }
        } catch (Exception e) {
            System.out.println("Error Fetching Produk data...");
            System.out.println(e);
        } finally {
            dbCon.closeConnection();
        }
        return listProduk;
    }

    // --- UPDATE (untuk atribut umum di tabel `produk`) ---
    @Override
    public void update(Produk produk, String id_produk_lama) { // id_produk_lama adalah PK lama jika PK bisa berubah
        con = dbCon.makeConnection();

        String sql = "UPDATE `produk` SET "
                + "`id_produk` = '" + produk.getId_produk() + "', " // Jika ID produk juga bisa diupdate
                + "`nama_produk` = '" + produk.getNama_produk() + "', "
                + "`jenis` = '" + produk.getJenis() + "', "
                + "`stok` = " + produk.getStok() + ", "
                + "`harga` = " + produk.getHarga() + ", "
                + "`tgl_kadaluarsa` = '" + produk.getTgl_kadaluarsa().toString() + "' "
                + "WHERE `id_produk` = '" + id_produk_lama + "'";

        System.out.println("Updating Produk...");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Edited " + result + " Produk " + id_produk_lama);
        } catch (Exception e) {
            System.out.println("Error Updating Produk...");
            System.out.println(e);
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    // --- DELETE (menghapus dari tabel `produk` dan juga tabel spesifik `obat`/`vitamin`) ---
    @Override
    public void delete(String id_produk) {
        con = dbCon.makeConnection();

        String sql = "DELETE FROM `produk` WHERE `id_produk` = '" + id_produk + "'";
        System.out.println("Deleting Produk...");

        try (Statement statement = con.createStatement()) {
            int result = statement.executeUpdate(sql);
            System.out.println("Deleted " + result + " Produk " + id_produk);
        }catch (Exception e) {
            System.out.println("Error Deleting Produk...");
            System.out.println(e);
        }finally {
            dbCon.closeConnection();
        }
    }

    // --- SEARCH (by ID) ---
    @Override
    public Produk search(String id_produk) {
        con = dbCon.makeConnection();
        Produk produk = null;

        // SQL yang sama dengan showData, tapi dengan WHERE spesifik ID
        String sql = "SELECT p.id_produk, p.nama_produk, p.jenis, p.stok, p.harga, p.tgl_kadaluarsa, "
                + "o.jenis_resep, v.cara_konsumsi "
                + "FROM `produk` p "
                + "LEFT JOIN `obat` o ON p.id_produk = o.id_produk "
                + "LEFT JOIN `vitamin` v ON p.id_produk = v.id_produk "
                + "WHERE p.id_produk = '" + id_produk + "'";

        System.out.println("Searching Produk by ID: " + id_produk + "...");

        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) { // Hanya satu hasil yang diharapkan
                    String jenisProduk = rs.getString("jenis");

                    String id_produk_found = rs.getString("id_produk");
                    String nama_produk = rs.getString("nama_produk");
                    int stok = rs.getInt("stok");
                    int harga = rs.getInt("harga");
                    LocalDate tgl_kadaluarsa = rs.getDate("tgl_kadaluarsa") != null ? rs.getDate("tgl_kadaluarsa").toLocalDate() : null;

                    if ("Obat".equals(jenisProduk)) {
                        String jenis_resep = rs.getString("jenis_resep");
                        produk = new Obat(jenis_resep, id_produk_found, nama_produk, jenisProduk, stok, harga, tgl_kadaluarsa);
                    } else if ("Vitamin".equals(jenisProduk)) {
                        String cara_konsumsi = rs.getString("cara_konsumsi");
                        produk = new Vitamin(cara_konsumsi, id_produk_found, nama_produk, jenisProduk, stok, harga, tgl_kadaluarsa);
                    } else {
                        throw new IllegalArgumentException("Jenis produk tidak dikenal: " + jenisProduk);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error Searching Produk: " + e.getMessage());
            System.out.println(e);
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return produk;
    }
    
    // --- SEARCH (by Nama Produk) ---
    public Produk searchByNama(String input_nama){
        con = dbCon.makeConnection();
        Produk produk = null;

        String sql = "SELECT p.id_produk, p.nama_produk, p.jenis, p.stok, p.harga, p.tgl_kadaluarsa, "
                + "o.jenis_resep, v.cara_konsumsi "
                + "FROM `produk` p "
                + "LEFT JOIN `obat` o ON p.id_produk = o.id_produk "
                + "LEFT JOIN `vitamin` v ON p.id_produk = v.id_produk "
                + "WHERE p.nama_produk LIKE '%" + input_nama + "%'"; // Menggunakan LIKE dan nama produk

        System.out.println("Mencari Produk berdasarkan Nama: " + input_nama + "...");

        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) { // Hanya satu hasil yang diharapkan
                    String jenisProduk = rs.getString("jenis");

                    String id_produk_found = rs.getString("id_produk");
                    String nama_produk = rs.getString("nama_produk");
                    int stok = rs.getInt("stok");
                    int harga = rs.getInt("harga");
                    LocalDate tgl_kadaluarsa = rs.getDate("tgl_kadaluarsa") != null ? rs.getDate("tgl_kadaluarsa").toLocalDate() : null;

                    if ("Obat".equals(jenisProduk)) {
                        String jenis_resep = rs.getString("jenis_resep");
                        produk = new Obat(jenis_resep, id_produk_found, nama_produk, jenisProduk, stok, harga, tgl_kadaluarsa);
                    } else if ("Vitamin".equals(jenisProduk)) {
                        String cara_konsumsi = rs.getString("cara_konsumsi");
                        produk = new Vitamin(cara_konsumsi, id_produk_found, nama_produk, jenisProduk, stok, harga, tgl_kadaluarsa);
                    } else {
                        throw new IllegalArgumentException("Jenis produk tidak dikenal: " + jenisProduk);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error Mencari Produk berdasarkan Nama: " + e.getMessage());
            System.err.println(e);
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return produk;
    }
    
    @Override
    public List<Produk> IShowForDropdown() { // Mengimplementasikan metode dari interface IShowForDropdown
        con = dbCon.makeConnection();

        // SQL untuk mengambil semua produk dan JOIN dengan tabel obat/vitamin untuk atribut spesifik
        String sql = "SELECT p.id_produk, p.nama_produk, p.jenis, p.stok, p.harga, p.tgl_kadaluarsa, "
                + "o.jenis_resep, v.cara_konsumsi "
                + "FROM `produk` p "
                + "LEFT JOIN `obat` o ON p.id_produk = o.id_produk "
                + "LEFT JOIN `vitamin` v ON p.id_produk = v.id_produk;"; 

        System.out.println("Fetching Produk Data for Dropdown...");
        List<Produk> list = new ArrayList<>();

        try (Statement S = con.createStatement()) {
            try (ResultSet RS = S.executeQuery(sql)) {
                while (RS.next()) {
                    Produk produk = null;
                    String jenisProduk = RS.getString("jenis");

                    // Membaca atribut umum
                    String id_produk = RS.getString("id_produk");
                    String nama_produk = RS.getString("nama_produk");
                    int stok = RS.getInt("stok");
                    int harga = RS.getInt("harga");
                    LocalDate tgl_kadaluarsa = RS.getDate("tgl_kadaluarsa") != null ? RS.getDate("tgl_kadaluarsa").toLocalDate() : null;

                    // Membedakan dan membuat objek subclass
                    if ("Obat".equals(jenisProduk)) {
                        String jenis_resep = RS.getString("jenis_resep");
                        produk = new Obat(jenis_resep, id_produk, nama_produk, jenisProduk, stok, harga, tgl_kadaluarsa);
                    } else if ("Vitamin".equals(jenisProduk)) {
                        String cara_konsumsi = RS.getString("cara_konsumsi");
                        produk = new Vitamin(cara_konsumsi, id_produk, nama_produk, jenisProduk, stok, harga, tgl_kadaluarsa);
                    } else {
                        throw new IllegalArgumentException("Unknown product type from database: " + jenisProduk + " for ID: " + id_produk);
                    }
                    list.add(produk);
                }
            }
        } catch (Exception e) {
            System.err.println("Error Fetching Produk Data for Dropdown: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return list;
    }

    public int generateId() {
        con = dbCon.makeConnection();
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(id_produk, 2) AS SIGNED)), 0) "
                + "AS highest_number FROM produk WHERE id_produk LIKE 'P%'; ";
        // Mendapatkan nilai tertinggi dari id yang ada di database, yang diawali huruf B
        // Menggunakan COALESCE untuk memastikan nilai 0 jika MAX mengembalikan NULL

        System.out.println("Generating ID Produk...");
        int id = 0;

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if (rs != null && rs.next()) {
                if (!rs.wasNull()) {
                    id = rs.getInt("highest_number") + 1;
                }
            }

            //nemasukan id terakhir ke dalam variabel id
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error Fetching data...");
            System.out.println(e);
        }
        dbCon.closeConnection();
        return id;
    }

    // --- Metode Tambahan untuk Cek Perubahan Jenis (mirip cekPerubahanJenis di BukuDAO) ---
    public boolean cekPerubahanJenis(String newJenis, String id_produk) {
        con = dbCon.makeConnection();
        String sql = "SELECT (jenis != '" + newJenis + "') AS result FROM produk WHERE id_produk = '" + id_produk + "';";

        System.out.println("Checking Product Type Change...");
        boolean result = false;

        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) {
                    result = rs.getBoolean("result");
                }
            }
        } catch (Exception e) {
            System.out.println("Error Checking Product Type Change: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return result;
    }
}
