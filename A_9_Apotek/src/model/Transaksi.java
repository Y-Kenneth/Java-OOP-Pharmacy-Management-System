package model;

import java.time.LocalDate;
import java.util.List;

public class Transaksi {
    private int id_transaksi;
    private Pelanggan pelanggan;
    private Apoteker apoteker;
    private LocalDate tanggal;
    private int total_harga;
    private List<DetailTransaksi> detailTransaksi;

    public Transaksi(int id_transaksi, Pelanggan pelanggan, Apoteker apoteker, LocalDate tanggal, int total_harga, List<DetailTransaksi> detailTransaksi) {
        this.id_transaksi = id_transaksi;
        this.pelanggan = pelanggan;
        this.apoteker = apoteker;
        this.tanggal = tanggal;
        this.total_harga = total_harga;
        this.detailTransaksi = detailTransaksi;
    }

    public Transaksi(Pelanggan pelanggan, Apoteker apoteker, LocalDate tanggal, int total_harga, List<DetailTransaksi> detailTransaksi) {
        this.pelanggan = pelanggan;
        this.apoteker = apoteker;
        this.tanggal = tanggal;
        this.total_harga = total_harga;
        this.detailTransaksi = detailTransaksi;
    }
    
    public Transaksi(){}

    public int getId_transaksi() {
        return id_transaksi;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Apoteker getApoteker() {
        return apoteker;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public List<DetailTransaksi> getDetailTransaksi() {
        return detailTransaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public void setApoteker(Apoteker apoteker) {
        this.apoteker = apoteker;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public void setDetailTransaksi(List<DetailTransaksi> detailTransaksi) {
        this.detailTransaksi = detailTransaksi;
    }
}
