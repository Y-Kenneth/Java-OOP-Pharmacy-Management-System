package table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Transaksi; // Import kelas Transaksi
import model.DetailTransaksi; // Import kelas DetailTransaksi
import model.Produk; // Import kelas Produk

public class TableTransaksi extends AbstractTableModel {
    private List<Transaksi> list; // List berisi objek Transaksi

    public TableTransaksi(List<Transaksi> list) {
        this.list = list;
    }

    public List<Transaksi> getList() {
        return list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
        // Jumlah kolom: ID Transaksi, Pelanggan, Apoteker, Tanggal, Total Harga, Detail Produk
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Mendapatkan objek Transaksi dari baris yang dipilih
        Transaksi transaksi = list.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return transaksi.getId_transaksi(); // ID Transaksi
            case 1: return transaksi.getPelanggan().getNama(); // Nama Pelanggan
            case 2: return transaksi.getApoteker().getNama_apoteker(); // Nama Apoteker yang melayani
            case 3: return transaksi.getTanggal().toString(); // Tanggal Transaksi
            case 4: return transaksi.getTotal_harga(); // Total Harga Transaksi
            case 5: // Kolom baru: Ringkasan Detail Produk (semua item)
                List<DetailTransaksi> details = transaksi.getDetailTransaksi();
                if (details == null || details.isEmpty()) {
                    return "Tidak ada item";
                }
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (DetailTransaksi detail : details) {
                    if (!first) {
                        sb.append("; "); // Pemisah antar item
                    }
                    sb.append(detail.getProduk().getNama_produk());
                    sb.append(" (").append(detail.getJumlah()).append("x)");
                    first = false;
                }
                return sb.toString();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        // Menentukan nama kolom yang akan ditampilkan pada tampilan JTable
        switch (column) {
            case 0: return "ID Transaksi";
            case 1: return "Pelanggan";
            case 2: return "Apoteker";
            case 3: return "Tanggal";
            case 4: return "Total Harga";
            case 5: return "Item Produk"; 
            default: return null;
        }
    }
}
