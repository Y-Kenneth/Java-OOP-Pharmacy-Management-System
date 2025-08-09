package model;

import java.time.LocalDate;

public class Vitamin extends Produk{
    private String cara_konsumsi;

    public Vitamin(String cara_konsumsi, String id_produk, String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa) {
        super(id_produk, nama_produk, jenis, stok, harga, tgl_kadaluarsa);
        this.cara_konsumsi = cara_konsumsi;
    }

    public Vitamin(String cara_konsumsi, String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa) {
        super(nama_produk, jenis, stok, harga, tgl_kadaluarsa);
        this.cara_konsumsi = cara_konsumsi;
    }
    
    // Tambahan Konstuktor karena Update Bug
    public Vitamin(String id_produk, String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa, String cara_konsumsi) {
        super(id_produk, nama_produk, jenis, stok, harga, tgl_kadaluarsa);
        this.cara_konsumsi = cara_konsumsi;
    }

    public String getCara_konsumsi() {
        return cara_konsumsi;
    }

    public void setCara_konsumsi(String cara_konsumsi) {
        this.cara_konsumsi = cara_konsumsi;
    }
    
    @Override
    public String getSpecial(){
        return cara_konsumsi;
    }
}
