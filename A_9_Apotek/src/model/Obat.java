package model;

import java.time.LocalDate;

public class Obat extends Produk{
    private String jenis_resep;

    public Obat(String jenis_resep, String id_produk, String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa) {
        super(id_produk, nama_produk, jenis, stok, harga, tgl_kadaluarsa);
        this.jenis_resep = jenis_resep;
    }

    public Obat(String jenis_resep, String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa) {
        super(nama_produk, jenis, stok, harga, tgl_kadaluarsa);
        this.jenis_resep = jenis_resep;
    }
    
    // Tambahan Konstuktor karena Update Bug
    public Obat(String id_produk, String nama_produk, String jenis, int stok, int harga, LocalDate tgl_kadaluarsa, String jenis_resep) {
        super(id_produk, nama_produk, jenis, stok, harga, tgl_kadaluarsa);
        this.jenis_resep = jenis_resep;
    }

    public String getJenis_resep() {
        return jenis_resep;
    }

    public void setJenis_resep(String jenis_resep) {
        this.jenis_resep = jenis_resep;
    }
    
    @Override
    public String getSpecial(){
        return jenis_resep;
    }
}
