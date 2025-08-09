package controller;

import model.Apoteker;
import dao.ApotekerDAO;
import java.util.List;

public class ApotekerControl {
    ApotekerDAO aDao = new ApotekerDAO();

    // Metode untuk memasukkan data apoteker
    public void insertDataApoteker(Apoteker A) {
        // Username dan Password untuk Apoteker akan otomatis ter-generate, ketika sebuah Apoteker baru dibuat
        // Pola nya adalah : (Misal nama_apoteker = Niko)
        // Username = NikoNiko, Password = Niko123
        String generateUsername = A.getNama_apoteker() + A.getNama_apoteker();
        String generatePassword = A.getNama_apoteker() + "123";
        A.setUsername(generateUsername);
        A.setPassword(generatePassword);
        
        aDao.insert(A);
    }

    // Metode untuk menampilkan semua data apoteker dalam format String
    public String showAllStringApoteker() {
        List<Apoteker> listA = aDao.showData(null); // Memanggil showData tanpa filter
        String apotekerString = "";

        for (Apoteker a : listA) {
            // Memformat output string sesuai atribut Apoteker
            apotekerString += a.getId_apoteker() + ". " + a.getNama_apoteker() + " || " + a.getEmail() + " || Gaji: " + a.getGaji() + "\n";
        }
        return apotekerString;
    }

    // Metode untuk mencari apoteker berdasarkan nama
    public Apoteker searchApotekerByName(String nama_apoteker) {
        return aDao.search(nama_apoteker);
    }

    // Metode untuk mencari apoteker berdasarkan ID
    public Apoteker searchApotekerById(int id_apoteker) {
        return aDao.search(id_apoteker);
    }

    // Metode untuk mencari apoteker berdasarkan ID atau username (menggunakan exception handling)
    public Apoteker searchApotekerByAll(String input) {
        try {
            int temp = Integer.parseInt(input); // Coba konversi ke integer
            return searchApotekerById(temp);    // Jika berhasil, cari berdasarkan ID
        } catch (NumberFormatException e) {
            return searchApotekerByName(input); // Jika gagal, cari berdasarkan username
        }
    }

    // Metode untuk mengupdate data apoteker
    public void updateDataApoteker(Apoteker A, int id_apoteker) {
        aDao.update(A, id_apoteker);
    }

    // Metode untuk menghapus data apoteker
    public void deleteDataApoteker(int id_apoteker) {
        aDao.delete(id_apoteker);
    }

    // Metode untuk menampilkan daftar apoteker untuk dropdown
    public List<Apoteker> showListApoteker() {
        return aDao.IShowForDropdown();
    }
}
