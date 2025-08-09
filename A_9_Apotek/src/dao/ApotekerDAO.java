package dao;

import connection.DbConnection;
import interfaceDAO.IDAO;
import interfaceDAO.IShowForDropdown; 
import model.Apoteker; // Import kelas Apoteker

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApotekerDAO implements IDAO<Apoteker, Integer>, IShowForDropdown<Apoteker>{
    protected DbConnection dbCon = new DbConnection();
    protected Connection con;

    @Override
    public void insert(Apoteker A) {
        con = dbCon.makeConnection();

        String sql = "INSERT INTO `apoteker` (`nama_apoteker`, `email`, `gaji`, `username`, `password`) "
                + "VALUES ('" + A.getNama_apoteker() + "', '" + A.getEmail() + "', " + A.getGaji() + ", '"
                + A.getUsername() + "', '" + A.getPassword() + "')";

        try (Statement S = con.createStatement()) {
            S.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error adding Apoteker: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    @Override
    public Apoteker search(Integer id_apoteker) {
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM `apoteker` WHERE `id_apoteker` = " + id_apoteker;
        Apoteker A = null;

        try (Statement S = con.createStatement();
            ResultSet RS = S.executeQuery(sql)) {
            if (RS != null){
                while (RS.next()){
                    A = new Apoteker(
                    RS.getInt("id_apoteker"),
                    RS.getString("nama_apoteker"),
                    RS.getString("email"),
                    RS.getInt("gaji"), 
                    RS.getString("username"),
                    RS.getString("password")
                );
                }
            }
            RS.close();
            S.close();
        } catch (Exception e) {
            System.err.println("Error searching Apoteker by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return A;
    }
    
    // Overload search method jika Anda ingin mencari berdasarkan username
    public Apoteker search(String nama_apoteker) {
        con = dbCon.makeConnection();
        String sql = "SELECT * FROM `apoteker` WHERE `nama_apoteker` = '" + nama_apoteker + "'";
        Apoteker A = null;

        try (Statement S = con.createStatement();
            ResultSet RS = S.executeQuery(sql)) {
            if (RS != null){
                while (RS.next()){
                    A = new Apoteker(
                    RS.getInt("id_apoteker"),
                    RS.getString("nama_apoteker"),
                    RS.getString("email"),
                    RS.getInt("gaji"),
                    RS.getString("username"),
                    RS.getString("password")
                );
                }
            }
            RS.close();
            S.close();
        } catch (Exception e) {
            System.err.println("Error searching Apoteker: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return A;
    }

    @Override
    public List<Apoteker> showData(Integer data) { // Mengasumsikan `data` tidak digunakan jika ingin show all
        con = dbCon.makeConnection();

        String sql = "SELECT * FROM `apoteker`"; // Show all
        List<Apoteker> listA = new ArrayList<>();

        try (Statement S = con.createStatement();
             ResultSet RS = S.executeQuery(sql)) {
            while (RS.next()) {
                listA.add(new Apoteker(
                    RS.getInt("id_apoteker"),
                    RS.getString("nama_apoteker"),
                    RS.getString("email"),
                    RS.getInt("gaji"),
                    RS.getString("username"),
                    RS.getString("password")));
            }
        } catch (Exception e) {
            System.err.println("Error fetching all Apoteker data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
        return listA;
    }
    
    @Override
    public void update(Apoteker A, Integer id_apoteker) {
        con = dbCon.makeConnection();

        String sql = "UPDATE `apoteker` SET "
                + "`nama_apoteker` = '" + A.getNama_apoteker() + "', "
                + "`email` = '" + A.getEmail() + "', "
                + "`gaji` = " + A.getGaji() + ", "
                + "`username` = '" + A.getUsername() + "', "
                + "`password` = '" + A.getPassword() + "' "
                + "WHERE `id_apoteker` = " + id_apoteker;

        try (Statement S = con.createStatement()) {
            S.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error updating Apoteker: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }

    @Override
    public void delete(Integer id_apoteker) {
        con = dbCon.makeConnection();
        String sql = "DELETE FROM `apoteker` WHERE `id_apoteker` = " + id_apoteker;

        try (Statement S = con.createStatement()) {
            S.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Error deleting Apoteker: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbCon.closeConnection();
        }
    }
    
    @Override
    public List<Apoteker> IShowForDropdown(){ // implementasi interface IShowForDropdown
        con = dbCon.makeConnection();
        
        String sql = "SELECT * FROM apoteker";
        System.out.println("Fetching Data...");
        List<Apoteker> c = new ArrayList();
        
        try{
            Statement S = con.createStatement();
            ResultSet RS = S.executeQuery(sql);
            
            if (RS != null){
                while (RS.next()){
                    c.add(new Apoteker(
                        RS.getInt("id_apoteker"),
                        RS.getString("nama_apoteker"),
                        RS.getString("email"),
                        RS.getInt("gaji"),
                        RS.getString("username"),
                        RS.getString("password")));
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
}
