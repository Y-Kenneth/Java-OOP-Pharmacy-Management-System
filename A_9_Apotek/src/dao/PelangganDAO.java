package dao;

import connection.DbConnection;
import interfaceDAO.IDAO;
import interfaceDAO.IShowForDropdown;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Pelanggan;

public class PelangganDAO implements IDAO<Pelanggan, Integer>, IShowForDropdown<Pelanggan>{
    protected DbConnection dbCon = new DbConnection();
    protected Connection con;
    
    @Override
    public void insert(Pelanggan P) {
        con = dbCon.makeConnection();

        String sql = "INSERT INTO `pelanggan` (`nama`, `umur`, `notelp`) "
                + "VALUES ('" + P.getNama() + "', " + P.getUmur() + ", '" + P.getNotelp() + "')";

        try (Statement S = con.createStatement()) {
            S.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error adding Pelanggan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    @Override
    public Pelanggan search(Integer id_pelanggan) {
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM `pelanggan` WHERE `id_pelanggan` = " + id_pelanggan;
        Pelanggan P = null;

        try (Statement S = con.createStatement();
             ResultSet RS = S.executeQuery(sql)) {
            if (RS.next()) { // Gunakan if karena hanya mencari 1 hasil
                P = new Pelanggan(
                    RS.getInt("id_pelanggan"),
                    RS.getString("nama"),
                    RS.getInt("umur"),
                    RS.getString("notelp")
                );
            }
        } catch (Exception e) {
            System.err.println("Error searching Pelanggan by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return P;
    }
    
    // Overload search method jika Anda ingin mencari berdasarkan nama
    public Pelanggan search(String nama) {
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM `pelanggan` WHERE `nama` = '" + nama + "'";
        Pelanggan P = null;

        try (Statement S = con.createStatement();
             ResultSet RS = S.executeQuery(sql)) {
            if (RS.next()) {
                P = new Pelanggan(
                    RS.getInt("id_pelanggan"),
                    RS.getString("nama"),
                    RS.getInt("umur"),
                    RS.getString("notelp")
                );
            }
        } catch (Exception e) {
            System.err.println("Error searching Pelanggan by name: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return P;
    }

    @Override
    public List<Pelanggan> showData(Integer data) { // Mengasumsikan `data` tidak digunakan jika ingin show all
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM `pelanggan`"; // Show all
        List<Pelanggan> listP = new ArrayList<>();

        try (Statement S = con.createStatement();
             ResultSet RS = S.executeQuery(sql)) {
            while (RS.next()) {
                listP.add(new Pelanggan(
                    RS.getInt("id_pelanggan"),
                    RS.getString("nama"),
                    RS.getInt("umur"),
                    RS.getString("notelp")));
            }
        } catch (Exception e) {
            System.err.println("Error fetching all Pelanggan data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listP;
    }
    
    @Override
    public void update(Pelanggan P, Integer id_pelanggan) {
        con = dbCon.makeConnection();

        String sql = "UPDATE `pelanggan` SET "
                + "`nama` = '" + P.getNama() + "', "
                + "`umur` = " + P.getUmur() + ", "
                + "`notelp` = '" + P.getNotelp() + "' "
                + "WHERE `id_pelanggan` = " + id_pelanggan;

        try (Statement S = con.createStatement()) {
            S.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error updating Pelanggan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    @Override
    public void delete(Integer id_pelanggan) {
        con = dbCon.makeConnection();
        String sql = "DELETE FROM `pelanggan` WHERE `id_pelanggan` = " + id_pelanggan;

        try (Statement S = con.createStatement()) {
            S.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error deleting Pelanggan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }
    
    @Override
    public List<Pelanggan> IShowForDropdown(){ // implementasi interface IShowForDropdown
        con = dbCon.makeConnection();
        
        String sql = "SELECT * FROM pelanggan";
        System.out.println("Fetching Data...");
        List<Pelanggan> c = new ArrayList();
        
        try{
            Statement S = con.createStatement();
            ResultSet RS = S.executeQuery(sql);
            
            if (RS != null){
                while (RS.next()){
                    c.add(new Pelanggan(
                        RS.getInt("id_pelanggan"),
                        RS.getString("nama"),
                        RS.getInt("umur"),
                        RS.getString("notelp")));
                }
            }
            RS.close();
            S.close();
        }catch(Exception e){
            System.out.println("Error Fetching Data...");
            System.out.println(e);
        }
        dbCon.closeConnection();
        return c;
    }
    
    // --- Method untuk Mengambil Pelanggan yang Sudah Bertransaksi ---
    public List<Pelanggan> getPelangganWithTransaksi() {
        con = dbCon.makeConnection();
        List<Pelanggan> listP = new ArrayList<>();
        // Menggunakan DISTINCT untuk menghindari duplikasi pelanggan jika mereka punya banyak transaksi
        String sql = "SELECT DISTINCT p.id_pelanggan, p.nama, p.umur, p.notelp " +
                     "FROM `pelanggan` p JOIN `transaksi` t ON p.id_pelanggan = t.id_pelanggan";

        try (Statement S = con.createStatement();
             ResultSet RS = S.executeQuery(sql)) {
            while (RS.next()) {
                listP.add(new Pelanggan(
                    RS.getInt("id_pelanggan"),
                    RS.getString("nama"),
                    RS.getInt("umur"),
                    RS.getString("notelp")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error fetching Pelanggan with transactions: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listP;
    }
}
