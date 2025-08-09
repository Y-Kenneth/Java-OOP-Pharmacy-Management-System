package model;

public class Pelanggan {
    private int id_pelanggan;
    private String nama;
    private int umur;
    private String notelp;

    public Pelanggan(int id_pelanggan, String nama, int umur, String notelp) {
        this.id_pelanggan = id_pelanggan;
        this.nama = nama;
        this.umur = umur;
        this.notelp = notelp;
    }

    public Pelanggan(String nama, int umur, String notelp) {
        this.nama = nama;
        this.umur = umur;
        this.notelp = notelp;
    }

    public int getId_pelanggan() {
        return id_pelanggan;
    }

    public String getNama() {
        return nama;
    }

    public int getUmur() {
        return umur;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setId_pelanggan(int id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }
    
    @Override
    public String toString(){
        return getNama();
    }
}
