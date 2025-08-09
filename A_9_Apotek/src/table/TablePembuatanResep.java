package table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.PembuatanResep; // Import kelas PembuatanResep
import model.DetailPembuatanResep;

public class TablePembuatanResep extends AbstractTableModel {
    private List<PembuatanResep> list; // List berisi objek PembuatanResep

    public TablePembuatanResep(List<PembuatanResep> list) {
        this.list = list;
    }

    public List<PembuatanResep> getList() {
        return list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
        // Jumlah kolom: ID Resep, Pelanggan, Dokter, Apoteker, Tanggal, Detail Resep
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Mendapatkan objek Transaksi dari baris yang dipilih
        PembuatanResep resep = list.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return resep.getId_resep(); // ID Resep
            case 1: return resep.getPelanggan().getNama(); // Nama Pelanggan
            case 2: return resep.getNama_dokter(); // Nama Dokter yang melayani
            case 3: return resep.getApoteker().getNama_apoteker(); // Nama Apoteker yang melayani
            case 4: return resep.getTanggal_resep().toString(); // Tanggal Pembuatan Resep
            case 5: // Kolom baru: Ringkasan Detail Pembuatan Resep (semua item)
                List<DetailPembuatanResep> details = resep.getDetailResep();
                if (details == null || details.isEmpty()) {
                    return "Tidak ada item";
                }
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (DetailPembuatanResep detail : details) {
                    if (!first) {
                        sb.append("; "); // Pemisah antar item
                    }
                    sb.append(detail.getProduk().getNama_produk());
                    sb.append("Aturan Pakai: ").append(detail.getAturan_pakai());
                    first = false;
                }
                return sb.toString();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        // Menentukan nama kolom yang akan ditampilkan pada tampilan
        switch (column) {
            case 0: return "ID Resep";
            case 1: return "Nama Pelanggan";
            case 2: return "Nama Dokter";
            case 3: return "Nama Apoteker";
            case 4: return "Tanggal Resep";
            case 5: return "Detail Resep";
            default: return null;
        }
    }
}
