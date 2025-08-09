package model;

public class DetailTransaksi {
    private Transaksi transaksi;
    private Produk produk;
    private int jumlah;
    private int sub_total;

    public DetailTransaksi(Transaksi transaksi, Produk produk, int jumlah, int sub_total) {
        this.transaksi = transaksi;
        this.produk = produk;
        this.jumlah = jumlah;
        this.sub_total = sub_total;
    }
    
    public DetailTransaksi(){}

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public Produk getProduk() {
        return produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getSub_total() {
        return sub_total;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public void setProduk(Produk produk) {
        this.produk = produk;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setSub_total(int sub_total) {
        this.sub_total = sub_total;
    }
}
