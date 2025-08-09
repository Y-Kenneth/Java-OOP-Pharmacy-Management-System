package panelView;

import controller.TransaksiControl; // Perlu untuk mengakses DAO Transaksi
import controller.PembuatanResepControl; // Perlu untuk mengakses DAO Resep
import controller.ProdukControl; // Perlu untuk mendapatkan data produk (untuk detail item)
import dao.TransaksiDAO; // Akses langsung DAO
import dao.PembuatanResepDAO; // Akses langsung DAO
import model.Transaksi;
import model.PembuatanResep;
import model.DetailTransaksi;
import model.DetailPembuatanResep;
import model.Produk;
import model.Apoteker; // Import kelas Apoteker
import dao.ApotekerDAO; // Akses langsung ApotekerDAO untuk mendapatkan nama apoteker

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout; // Tetap pakai GridLayout jika ingin label di atas textarea
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator; // Untuk sorting
import java.util.stream.Collectors; // Untuk stream API

public class PanelLaporan extends javax.swing.JPanel {

    public PanelLaporan() {
        initComponents();
        setOpaque(false);

        transaksiDAO = new TransaksiDAO();
        pembuatanResepDAO = new PembuatanResepDAO();
        apotekerDAO = new ApotekerDAO(); 
        produkControl = new ProdukControl();

        generateAndDisplayReports();
    }
    
    // --- Kontroler ---
    private TransaksiDAO transaksiDAO; // Akses langsung DAO
    private PembuatanResepDAO pembuatanResepDAO; // Akses langsung DAO
    private ApotekerDAO apotekerDAO; // Akses langsung ApotekerDAO
    private ProdukControl produkControl; // Untuk mendapatkan detail produk
    
    // --- Metode Utama: Generate dan Tampilkan Laporan ---
    private void generateAndDisplayReports() {
        StringBuilder finalReport = new StringBuilder();

        // Tambahkan Laporan Transaksi
        finalReport.append(generateTransaksiReport());
        finalReport.append("\n\n"); // Spasi antar laporan

        // Tambahkan Laporan Pembuatan Resep
        finalReport.append(generatePembuatanResepReport());
        finalReport.append("\n\n"); // Spasi antar laporan

        // Tambahkan Laporan Apoteker
        finalReport.append(generateApotekerReport());

        laporanTextArea.setText(finalReport.toString());
        laporanTextArea.setCaretPosition(0); // Gulir ke atas
    }
    
        // --- Metode Pembantu untuk Membuat Laporan Transaksi ---
    private String generateTransaksiReport() {
        StringBuilder report = new StringBuilder();
        report.append("--- Laporan Transaksi Pembelian Produk ---\n");
        report.append("=========================================\n");

        try {
            List<Transaksi> allTransaksi = transaksiDAO.showAllData(); 
            
            if (allTransaksi.isEmpty()) {
                report.append("Tidak ada data transaksi pembelian produk.\n");
                return report.toString();
            }

            int totalPendapatan = allTransaksi.stream().mapToInt(Transaksi::getTotal_harga).sum();
            report.append(String.format("Total Pendapatan dari Transaksi: Rp %,d\n", totalPendapatan));
            report.append("-----------------------------------------\n");

            Map<String, Integer> transaksiPerBulan = allTransaksi.stream()
                .collect(Collectors.groupingBy(
                    t -> t.getTanggal().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    Collectors.summingInt(t -> 1)
                ));
            
            report.append("Jumlah Transaksi per Bulan:\n");
            transaksiPerBulan.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.append(String.format("  %s: %d transaksi\n", entry.getKey(), entry.getValue())));
            report.append("-----------------------------------------\n");

            Map<String, Integer> produkTerlaris = new HashMap<>();
            for (Transaksi t : allTransaksi) {
                for (DetailTransaksi dt : t.getDetailTransaksi()) {
                    if (dt.getProduk() != null && dt.getProduk().getNama_produk() != null) {
                        produkTerlaris.merge(dt.getProduk().getNama_produk(), dt.getJumlah(), Integer::sum);
                    }
                }
            }

            report.append("Produk Terlaris (Kuantitas):\n");
            produkTerlaris.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) 
                .limit(5)
                .forEach(entry -> report.append(String.format("  %s: %d unit\n", entry.getKey(), entry.getValue())));
            report.append("=========================================\n");

        } catch (Exception e) {
            report.append("Error saat membuat laporan transaksi: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }
        return report.toString();
    }

    // --- Metode Pembantu untuk Membuat Laporan Pembuatan Resep ---
    private String generatePembuatanResepReport() {
        StringBuilder report = new StringBuilder();
        report.append("--- Laporan Pembuatan Resep ---\n");
        report.append("=================================\n");

        try {
            List<PembuatanResep> allResep = pembuatanResepDAO.showAllData(); 
            
            if (allResep.isEmpty()) {
                report.append("Tidak ada data pembuatan resep.\n");
                return report.toString();
            }

            Map<String, Integer> resepPerDokter = allResep.stream()
                .collect(Collectors.groupingBy(
                    PembuatanResep::getNama_dokter,
                    Collectors.summingInt(pr -> 1)
                ));
            
            report.append("Jumlah Resep per Dokter:\n");
            resepPerDokter.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> report.append(String.format("  Dr. %s: %d resep\n", entry.getKey(), entry.getValue())));
            report.append("---------------------------------\n");

            Map<String, Integer> obatDiresepkan = new HashMap<>();
            for (PembuatanResep pr : allResep) {
                for (DetailPembuatanResep dpr : pr.getDetailResep()) {
                    if (dpr.getProduk() != null && dpr.getProduk().getNama_produk() != null) {
                        obatDiresepkan.merge(dpr.getProduk().getNama_produk(), dpr.getJumlah_resep(), Integer::sum);
                    }
                }
            }

            report.append("Obat Paling Sering Diresepkan (Kuantitas):\n");
            obatDiresepkan.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> report.append(String.format("  %s: %d unit\n", entry.getKey(), entry.getValue())));
            report.append("=================================\n");

        } catch (Exception e) {
            report.append("Error saat membuat laporan resep: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }
        return report.toString();
    }

    // --- Laporan Apoteker Paling Banyak Melayani Pelanggan ---
    private String generateApotekerReport() {
        StringBuilder report = new StringBuilder();
        report.append("--- Apoteker Paling Banyak Melayani Pelanggan ---\n");
        report.append("=================================================\n");

        try {
            List<Transaksi> allTransaksi = transaksiDAO.showAllData();
            List<PembuatanResep> allResep = pembuatanResepDAO.showAllData();

            Map<Integer, Integer> apotekerLayananCount = new HashMap<>(); 
            
            for (Transaksi t : allTransaksi) {
                if (t.getApoteker() != null) {
                    apotekerLayananCount.merge(t.getApoteker().getId_apoteker(), 1, Integer::sum);
                }
            }
            
            for (PembuatanResep pr : allResep) {
                if (pr.getApoteker() != null) {
                    apotekerLayananCount.merge(pr.getApoteker().getId_apoteker(), 1, Integer::sum);
                }
            }

            if (apotekerLayananCount.isEmpty()) {
                report.append("Tidak ada data layanan apoteker.\n");
                return report.toString();
            }

            report.append("Jumlah Layanan per Apoteker:\n");
            apotekerLayananCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) 
                .forEach(entry -> {
                    Apoteker apoteker = apotekerDAO.search(entry.getKey());
                    if (apoteker != null) {
                        report.append(String.format("  %s (ID: %d): %d layanan\n", apoteker.getNama_apoteker(), entry.getKey(), entry.getValue()));
                    } else {
                        report.append(String.format("  ID Apoteker %d: %d layanan (Nama tidak ditemukan)\n", entry.getKey(), entry.getValue()));
                    }
                });
            report.append("=================================================\n");

        } catch (Exception e) {
            report.append("Error saat membuat laporan apoteker: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }
        return report.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        coverPanel = new javax.swing.JPanel();
        pelangganPanel = new javax.swing.JPanel();
        headerLabel1 = new javax.swing.JLabel();
        headerLabel2 = new javax.swing.JLabel();
        laporanPane = new javax.swing.JScrollPane();
        laporanTextArea = new javax.swing.JTextArea();

        coverPanel.setPreferredSize(new java.awt.Dimension(1280, 720));

        pelangganPanel.setBackground(new java.awt.Color(134, 196, 66));

        headerLabel1.setFont(new java.awt.Font("Arial Black", 1, 70)); // NOI18N
        headerLabel1.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel1.setText("LAPORAN TRANSAKSI &");

        headerLabel2.setFont(new java.awt.Font("Arial Black", 1, 70)); // NOI18N
        headerLabel2.setForeground(new java.awt.Color(255, 255, 255));
        headerLabel2.setText("PEMBUATAN RESEP");

        laporanTextArea.setBackground(new java.awt.Color(255, 255, 255));
        laporanTextArea.setColumns(20);
        laporanTextArea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        laporanTextArea.setForeground(new java.awt.Color(0, 0, 0));
        laporanTextArea.setRows(5);
        laporanPane.setViewportView(laporanTextArea);

        javax.swing.GroupLayout pelangganPanelLayout = new javax.swing.GroupLayout(pelangganPanel);
        pelangganPanel.setLayout(pelangganPanelLayout);
        pelangganPanelLayout.setHorizontalGroup(
            pelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pelangganPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(pelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(laporanPane)
                    .addComponent(headerLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
                    .addComponent(headerLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        pelangganPanelLayout.setVerticalGroup(
            pelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pelangganPanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(headerLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(headerLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(laporanPane, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout coverPanelLayout = new javax.swing.GroupLayout(coverPanel);
        coverPanel.setLayout(coverPanelLayout);
        coverPanelLayout.setHorizontalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pelangganPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        coverPanelLayout.setVerticalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pelangganPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1304, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(coverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1304, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 809, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(coverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel coverPanel;
    private javax.swing.JLabel headerLabel1;
    private javax.swing.JLabel headerLabel2;
    private javax.swing.JScrollPane laporanPane;
    private javax.swing.JTextArea laporanTextArea;
    private javax.swing.JPanel pelangganPanel;
    // End of variables declaration//GEN-END:variables
}
