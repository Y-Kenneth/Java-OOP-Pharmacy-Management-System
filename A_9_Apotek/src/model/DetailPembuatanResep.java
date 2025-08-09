package model;

public class DetailPembuatanResep {
    private PembuatanResep pembuatanResep;
    private Produk produk;
    private int jumlah_resep;
    private String aturan_pakai;

    public DetailPembuatanResep(PembuatanResep pembuatanResep, Produk produk, int jumlah_resep, String aturan_pakai) {
        this.pembuatanResep = pembuatanResep;
        this.produk = produk;
        this.jumlah_resep = jumlah_resep;
        this.aturan_pakai = aturan_pakai;
    }
    
    public DetailPembuatanResep(){}

    public PembuatanResep getPembuatanResep() {
        return pembuatanResep;
    }

    public void setPembuatanResep(PembuatanResep pembuatanResep) {
        this.pembuatanResep = pembuatanResep;
    }

    public Produk getProduk() {
        return produk;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public int getJumlah_resep() {
        return jumlah_resep;
    }

    public void setJumlah_resep(int jumlah_resep) {
        this.jumlah_resep = jumlah_resep;
    }

    public String getAturan_pakai() {
        return aturan_pakai;
    }

    public void setAturan_pakai(String aturan_pakai) {
        this.aturan_pakai = aturan_pakai;
    }
}
