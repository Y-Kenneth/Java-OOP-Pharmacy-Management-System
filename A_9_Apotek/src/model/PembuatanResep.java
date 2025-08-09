package model;

import java.util.List;
import java.time.LocalDate;

public class PembuatanResep {
    private int id_resep;
    private Pelanggan pelanggan;
    private Apoteker apoteker;
    private String nama_dokter;
    private LocalDate tanggal_resep;
    private List<DetailPembuatanResep> detailResep;

    public PembuatanResep(int id_resep, Pelanggan pelanggan, Apoteker apoteker, String nama_dokter, LocalDate tanggal_resep, List<DetailPembuatanResep> detailResep) {
        this.id_resep = id_resep;
        this.pelanggan = pelanggan;
        this.apoteker = apoteker;
        this.nama_dokter = nama_dokter;
        this.tanggal_resep = tanggal_resep;
        this.detailResep = detailResep;
    }

    public PembuatanResep(Pelanggan pelanggan, Apoteker apoteker, String nama_dokter, LocalDate tanggal_resep, List<DetailPembuatanResep> detailResep) {
        this.pelanggan = pelanggan;
        this.apoteker = apoteker;
        this.nama_dokter = nama_dokter;
        this.tanggal_resep = tanggal_resep;
        this.detailResep = detailResep;
    }
    
    public PembuatanResep(){}

    public int getId_resep() {
        return id_resep;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Apoteker getApoteker() {
        return apoteker;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public LocalDate getTanggal_resep() {
        return tanggal_resep;
    }

    public List<DetailPembuatanResep> getDetailResep() {
        return detailResep;
    }

    public void setId_resep(int id_resep) {
        this.id_resep = id_resep;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public void setApoteker(Apoteker apoteker) {
        this.apoteker = apoteker;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public void setTanggal_resep(LocalDate tanggal_resep) {
        this.tanggal_resep = tanggal_resep;
    }

    public void setDetailResep(List<DetailPembuatanResep> detailResep) {
        this.detailResep = detailResep;
    }
}
