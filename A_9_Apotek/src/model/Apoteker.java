package model;

public class Apoteker {
    private int id_apoteker;
    private String nama_apoteker;
    private String email;
    private int gaji;
    private String username;
    private String password;

    public Apoteker(int id_apoteker, String nama_apoteker, String email, int gaji, String username, String password) {
        this.id_apoteker = id_apoteker;
        this.nama_apoteker = nama_apoteker;
        this.email = email;
        this.gaji = gaji;
        this.username = username;
        this.password = password;
    }

    public Apoteker(String nama_apoteker, String email, int gaji, String username, String password) {
        this.nama_apoteker = nama_apoteker;
        this.email = email;
        this.gaji = gaji;
        this.username = username;
        this.password = password;
    }
    
    public Apoteker(String nama_apoteker, String email, int gaji){
        this.nama_apoteker = nama_apoteker;
        this.email = email;
        this.gaji = gaji;
    }

    public int getId_apoteker() {
        return id_apoteker;
    }

    public String getNama_apoteker() {
        return nama_apoteker;
    }

    public String getEmail() {
        return email;
    }

    public int getGaji() {
        return gaji;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId_apoteker(int id_apoteker) {
        this.id_apoteker = id_apoteker;
    }

    public void setNama_apoteker(String nama_apoteker) {
        this.nama_apoteker = nama_apoteker;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGaji(int gaji) {
        this.gaji = gaji;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
