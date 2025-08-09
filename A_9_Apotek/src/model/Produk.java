package model;

import java.time.LocalDate;

public abstract class Produk {
    protected String id_produk;
    protected String nama_produk;
    protected String jenis; // Obat / Vitamin
    protected int stok;
    protected int harga;
    protected LocalDate tgl_kadaluarsa;

    public Produk(String id_produk, String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa) {
        this.id_produk = id_produk;
        this.nama_produk = nama_produk;
        this.jenis = jenis;
        this.stok = stok;
        this.harga = harga;
        this.tgl_kadaluarsa = tgl_kadaluarsa;
    }

    public Produk(String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa) {
        this.nama_produk = nama_produk;
        this.jenis = jenis;
        this.stok = stok;
        this.harga = harga;
        this.tgl_kadaluarsa = tgl_kadaluarsa;
    }

    public String getId_produk() {
        return id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public String getJenis() {
        return jenis;
    }

    public int getStok() {
        return stok;
    }

    public int getHarga() {
        return harga;
    }

    public LocalDate getTgl_kadaluarsa() {
        return tgl_kadaluarsa;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setTgl_kadaluarsa(LocalDate tgl_kadaluarsa) {
        this.tgl_kadaluarsa = tgl_kadaluarsa;
    }
    
    public String getString(){
        String specialAttribute = "";
        if ("Obat".equals(this.getJenis())) {
            specialAttribute = "Jenis Resep: " + getSpecial();
        } else if ("Vitamin".equals(this.getJenis())) {
            specialAttribute = "Cara Konsumsi: " + getSpecial();
        }
        String tampilan = "\nID Produk : " + id_produk + "  |  " + jenis + ": " + nama_produk + "\n" +       
                          "Sisa Stok : " + stok + "  |  Harga: Rp " + harga + "\n" +
                          "Tanggal Kadaluarsa : " + (tgl_kadaluarsa != null ? tgl_kadaluarsa.toString() : "-") + "\n" +
                          specialAttribute;
        return tampilan;
    }
    
    @Override
    public String toString(){
        return "[" + getJenis() + "] : " + getNama_produk();
    }
    
    public abstract String getSpecial();
}
