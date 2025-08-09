package panelView;

import controller.PembuatanResepControl;
import controller.PelangganControl;
import controller.ApotekerControl;
import controller.ProdukControl;
import controller.TransaksiControl; // Untuk mendapatkan Tanggal ketika Produk dibeli
import exception.InputKosongException;
import exception.KeranjangKosongException;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.time.LocalDate;

import model.PembuatanResep;
import model.Pelanggan;
import model.Apoteker;
import model.Produk;
import model.Obat;
import model.Vitamin;
import model.DetailPembuatanResep;
import table.TablePembuatanResep;

public class PanelPembuatanResep extends javax.swing.JPanel {
    
    // --- Kontroler ---
    private PembuatanResepControl rc;
    private PelangganControl pc;
    private ApotekerControl ac;
    private ProdukControl prodC;
    private TransaksiControl tc;
    
    // --- Variabel State Panel ---
    private PembuatanResep R = null; 
    private String actionPembuatanResep = null;
    private int selectedIdResep = 0;
    private Apoteker loggedInApoteker;
    private LocalDate resepDate;
    private int jumlah_resep = 0;
    private boolean isInitializing = true;
    
    // List untuk dropdown dan tabel
    private List<Pelanggan> listPelanggan;
    private List<Apoteker> listApoteker;
    private List<Produk> listProduk;
    private List<DetailPembuatanResep> currentDetailItems; 
    
    private Component rootPane;
    
    public PanelPembuatanResep(Apoteker loginApoteker) {
        initComponents();
        setOpaque(false);
        
        isInitializing = true; 
        this.loggedInApoteker = loginApoteker;
        apotekerTextField.setText(loginApoteker.getNama_apoteker());
        
        rc = new PembuatanResepControl();
        pc = new PelangganControl();
        ac = new ApotekerControl();
        prodC = new ProdukControl();
        tc = new TransaksiControl();
        
        currentDetailItems = new ArrayList<>();
        
        populateDropdowns();
        showTableBySearch(null);
        
        clearText();
        setComponents(false);
        setEditDeleteButton(false);
        produkDropdown.setEnabled(false); // Dropdown Produk hanya bisa Diakses, apabila Pelanggan sudah dipilih
    }
    
    public void clearText(){
        namaPelangganDropdown.setSelectedIndex(-1);
        produkDropdown.setSelectedIndex(-1);
        
        idResepTextField.setText("");
        namaDokterInputTextField.setText("");
        aturanPakaiInputTextField.setText("");
        jumlahLabel.setText(Integer.toString(jumlah_resep));
        
        searchResepInputTextField.setText("");
    }
    
    public void setEditDeleteButton(boolean value){
//        barukanResepButton.setEnabled(value);
        hapusResepButton.setEnabled(value);
    }
    
    public void setComponents(boolean value){
        namaPelangganDropdown.setEnabled(value);
//        produkDropdown.setEnabled(value);

        tambahItemResepButton.setEnabled(value);
        buatResepButton.setEnabled(value);
        batalkanResepButton.setEnabled(value);
        listResepButton.setEnabled(value);
    }
    
    public void populateDropdowns() {
        // Mengisi dropdown Pelanggan (HANYA yang sudah bertransaksi)
        namaPelangganDropdown.removeAllItems();
        listPelanggan = pc.showListPelangganWithTransaksi(); // <<< PERUBAHAN DI SINI
        if (listPelanggan != null) {
            for (Pelanggan p : listPelanggan) {
                namaPelangganDropdown.addItem(p); 
            }
        }

        produkDropdown.removeAllItems(); // Produk Dropdown baru akan terisi ketika ada pelanggan terpilih
    }   
    
    // --- Metode untuk mengisi dropdown Produk Obat Wajib Resep berdasarkan pelanggan terpilih ---
    public void populateProdukResepDropdownByPelanggan(int idPelanggan) {
        produkDropdown.removeAllItems(); 
        listProduk = tc.getPurchasedObatByPelanggan(idPelanggan); 
        
        if (listProduk != null) {
            for (Produk prod : listProduk) {
                produkDropdown.addItem(prod); // Gunakan produkDropdown
            }
        }
    }
    
    // --- Metode untuk mengupdate tanggal resep otomatis (dipanggil saat dropdown berubah) ---
    public void updateTanggalResepOtomatis() {
        Pelanggan selectedPelanggan = (Pelanggan) namaPelangganDropdown.getSelectedItem(); 
        Produk selectedProduk = (Produk) produkDropdown.getSelectedItem(); 

        if (selectedPelanggan != null && selectedProduk != null && "Obat".equals(selectedProduk.getJenis())) {
            resepDate = tc.getProductPurchasedDate(selectedPelanggan.getId_pelanggan(), selectedProduk.getId_produk());
        } else {
            resepDate = null; // Reset tanggal jika kondisi tidak terpenuhi
        }
    }
    
    public void showTableBySearch(String target) {
        List<PembuatanResep> resepHeaders;
        if (target == null || target.isEmpty()) {
            resepHeaders = rc.showTable(null).getList(); // Mengambil semua data
        } else {
            PembuatanResep singlePembuatanResep = rc.searchPembuatanResepByAll(target);
            resepHeaders = new ArrayList<>();
            if (singlePembuatanResep != null) {
                resepHeaders.add(singlePembuatanResep);
            }
        }
        tablePembuatanResep.setModel(new TablePembuatanResep(resepHeaders));
    }
    
    // Exception
    public void inputKosongPembuatanResepException() throws InputKosongException{
        if (namaPelangganDropdown.getSelectedIndex() == -1 || produkDropdown.getSelectedIndex() == -1 
                || namaDokterInputTextField.getText().isEmpty() || aturanPakaiInputTextField.getText().isEmpty()){
            throw new InputKosongException();
        }
    }
    public void daftarResepMasihKosongException() throws KeranjangKosongException{
        if (currentDetailItems.isEmpty()){
            throw new KeranjangKosongException("Belum ada Item di Daftar Pembuatan Resep");
        }
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
        produkPanel = new javax.swing.JPanel();
        searchTransaksiInputPanel = new javax.swing.JPanel();
        searchResepInputLabel = new javax.swing.JLabel();
        searchResepInputTextField = new javax.swing.JTextField();
        searchResepInputButton = new javax.swing.JButton();
        resepFormPanel = new javax.swing.JPanel();
        namaPelangganPanel = new javax.swing.JPanel();
        namaPelangganInputLabel = new javax.swing.JLabel();
        namaPelangganDropdown = new javax.swing.JComboBox<>();
        aturanPakaiInputLabel = new javax.swing.JLabel();
        aturanPakaiInputTextField = new javax.swing.JTextField();
        buatResepButton = new javax.swing.JButton();
        tambahItemResepButton = new javax.swing.JButton();
        batalkanResepButton = new javax.swing.JButton();
        listResepButton = new javax.swing.JButton();
        namaDokterPanel = new javax.swing.JPanel();
        produkInputLabel1 = new javax.swing.JLabel();
        namaDokterInputTextField = new javax.swing.JTextField();
        produkDropdown = new javax.swing.JComboBox<>();
        produkInputLabel = new javax.swing.JLabel();
        jumlahResepLabel = new javax.swing.JLabel();
        jumlahLabel = new javax.swing.JLabel();
        resepButtonPanel = new javax.swing.JPanel();
        tambahResepButton = new javax.swing.JButton();
        hapusResepButton = new javax.swing.JButton();
        resepLabel = new javax.swing.JLabel();
        tabelResepScrollPane = new javax.swing.JScrollPane();
        tablePembuatanResep = new javax.swing.JTable();
        specialAttributePanel = new javax.swing.JPanel();
        idResepLabel = new javax.swing.JLabel();
        idResepTextField = new javax.swing.JTextField();
        apotekerLabel = new javax.swing.JLabel();
        apotekerTextField = new javax.swing.JTextField();
        ketentuanLabel = new javax.swing.JLabel();

        produkPanel.setBackground(new java.awt.Color(134, 196, 66));

        searchTransaksiInputPanel.setBackground(new java.awt.Color(134, 196, 66));

        searchResepInputLabel.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        searchResepInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchResepInputLabel.setText("Cari Pembuatan Resep di Apotek ASEAN");

        searchResepInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        searchResepInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        searchResepInputTextField.setForeground(new java.awt.Color(0, 0, 0));
        searchResepInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchResepInputTextFieldActionPerformed(evt);
            }
        });
        searchResepInputTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchResepInputTextFieldKeyTyped(evt);
            }
        });

        searchResepInputButton.setBackground(new java.awt.Color(24, 170, 176));
        searchResepInputButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        searchResepInputButton.setForeground(new java.awt.Color(255, 255, 255));
        searchResepInputButton.setText("Cari");
        searchResepInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchResepInputButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchTransaksiInputPanelLayout = new javax.swing.GroupLayout(searchTransaksiInputPanel);
        searchTransaksiInputPanel.setLayout(searchTransaksiInputPanelLayout);
        searchTransaksiInputPanelLayout.setHorizontalGroup(
            searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchTransaksiInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchResepInputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addGroup(searchTransaksiInputPanelLayout.createSequentialGroup()
                        .addComponent(searchResepInputTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(searchResepInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        searchTransaksiInputPanelLayout.setVerticalGroup(
            searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchTransaksiInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchResepInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchResepInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchResepInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        resepFormPanel.setBackground(new java.awt.Color(134, 196, 66));
        resepFormPanel.setForeground(new java.awt.Color(255, 255, 255));

        namaPelangganPanel.setBackground(new java.awt.Color(134, 196, 66));
        namaPelangganPanel.setForeground(new java.awt.Color(255, 255, 255));

        namaPelangganInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        namaPelangganInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        namaPelangganInputLabel.setText("Pelanggan yang Telah melakukan Transaksi");

        namaPelangganDropdown.setBackground(new java.awt.Color(255, 255, 255));
        namaPelangganDropdown.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        namaPelangganDropdown.setForeground(new java.awt.Color(0, 0, 0));
        namaPelangganDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaPelangganDropdownActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout namaPelangganPanelLayout = new javax.swing.GroupLayout(namaPelangganPanel);
        namaPelangganPanel.setLayout(namaPelangganPanelLayout);
        namaPelangganPanelLayout.setHorizontalGroup(
            namaPelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaPelangganPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(namaPelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(namaPelangganDropdown, 0, 322, Short.MAX_VALUE)
                    .addComponent(namaPelangganInputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        namaPelangganPanelLayout.setVerticalGroup(
            namaPelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaPelangganPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(namaPelangganInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaPelangganDropdown))
        );

        aturanPakaiInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        aturanPakaiInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        aturanPakaiInputLabel.setText("Aturan Pakai");

        aturanPakaiInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        aturanPakaiInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        aturanPakaiInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aturanPakaiInputTextFieldActionPerformed(evt);
            }
        });

        buatResepButton.setBackground(new java.awt.Color(247, 210, 84));
        buatResepButton.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        buatResepButton.setForeground(new java.awt.Color(0, 0, 0));
        buatResepButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/PembuatanResep.png"))); // NOI18N
        buatResepButton.setText("Buat Resep");
        buatResepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buatResepButtonActionPerformed(evt);
            }
        });

        tambahItemResepButton.setBackground(new java.awt.Color(24, 170, 176));
        tambahItemResepButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        tambahItemResepButton.setForeground(new java.awt.Color(255, 255, 255));
        tambahItemResepButton.setText("Tambah Resep ke Daftar");
        tambahItemResepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahItemResepButtonActionPerformed(evt);
            }
        });

        batalkanResepButton.setBackground(new java.awt.Color(237, 8, 0));
        batalkanResepButton.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        batalkanResepButton.setForeground(new java.awt.Color(255, 255, 255));
        batalkanResepButton.setText("Batalkan");
        batalkanResepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalkanResepButtonActionPerformed(evt);
            }
        });

        listResepButton.setBackground(new java.awt.Color(247, 210, 84));
        listResepButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/List.png"))); // NOI18N
        listResepButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listResepButtonMouseClicked(evt);
            }
        });
        listResepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listResepButtonActionPerformed(evt);
            }
        });

        namaDokterPanel.setBackground(new java.awt.Color(134, 196, 66));
        namaDokterPanel.setForeground(new java.awt.Color(255, 255, 255));

        produkInputLabel1.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        produkInputLabel1.setForeground(new java.awt.Color(255, 255, 255));
        produkInputLabel1.setText("Nama Dokter");

        namaDokterInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        namaDokterInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        namaDokterInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaDokterInputTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout namaDokterPanelLayout = new javax.swing.GroupLayout(namaDokterPanel);
        namaDokterPanel.setLayout(namaDokterPanelLayout);
        namaDokterPanelLayout.setHorizontalGroup(
            namaDokterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaDokterPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(namaDokterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(namaDokterPanelLayout.createSequentialGroup()
                        .addComponent(produkInputLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 86, Short.MAX_VALUE))
                    .addComponent(namaDokterInputTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
                .addContainerGap())
        );
        namaDokterPanelLayout.setVerticalGroup(
            namaDokterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaDokterPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(produkInputLabel1)
                .addGap(7, 7, 7)
                .addComponent(namaDokterInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        produkDropdown.setBackground(new java.awt.Color(255, 255, 255));
        produkDropdown.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        produkDropdown.setForeground(new java.awt.Color(0, 0, 0));
        produkDropdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produkDropdownActionPerformed(evt);
            }
        });

        produkInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        produkInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        produkInputLabel.setText("List Produk Wajib Resep :");

        jumlahResepLabel.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jumlahResepLabel.setForeground(new java.awt.Color(255, 255, 255));
        jumlahResepLabel.setText("Jumlah Resep : ");

        jumlahLabel.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jumlahLabel.setForeground(new java.awt.Color(255, 255, 255));
        jumlahLabel.setText("0");

        javax.swing.GroupLayout resepFormPanelLayout = new javax.swing.GroupLayout(resepFormPanel);
        resepFormPanel.setLayout(resepFormPanelLayout);
        resepFormPanelLayout.setHorizontalGroup(
            resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resepFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resepFormPanelLayout.createSequentialGroup()
                        .addComponent(tambahItemResepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(105, 105, 105))
                    .addGroup(resepFormPanelLayout.createSequentialGroup()
                        .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namaPelangganPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namaDokterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(resepFormPanelLayout.createSequentialGroup()
                                .addGap(282, 282, 282)
                                .addComponent(listResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22)))
                .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resepFormPanelLayout.createSequentialGroup()
                        .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(resepFormPanelLayout.createSequentialGroup()
                                .addComponent(aturanPakaiInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 96, Short.MAX_VALUE))
                            .addComponent(aturanPakaiInputTextField)
                            .addComponent(produkDropdown, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(38, 38, 38))
                    .addGroup(resepFormPanelLayout.createSequentialGroup()
                        .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(produkInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(resepFormPanelLayout.createSequentialGroup()
                                .addComponent(jumlahResepLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jumlahLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buatResepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(batalkanResepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(533, 533, 533))
        );
        resepFormPanelLayout.setVerticalGroup(
            resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resepFormPanelLayout.createSequentialGroup()
                .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(resepFormPanelLayout.createSequentialGroup()
                        .addGap(0, 173, Short.MAX_VALUE)
                        .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(batalkanResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlahResepLabel)
                            .addComponent(jumlahLabel)))
                    .addGroup(resepFormPanelLayout.createSequentialGroup()
                        .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, resepFormPanelLayout.createSequentialGroup()
                                .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(resepFormPanelLayout.createSequentialGroup()
                                        .addComponent(produkInputLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(produkDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(namaPelangganPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(namaDokterPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resepFormPanelLayout.createSequentialGroup()
                                        .addComponent(aturanPakaiInputLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(aturanPakaiInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, resepFormPanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(buatResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(resepFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(listResepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tambahItemResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        resepButtonPanel.setBackground(new java.awt.Color(134, 196, 66));

        tambahResepButton.setBackground(new java.awt.Color(24, 170, 176));
        tambahResepButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        tambahResepButton.setForeground(new java.awt.Color(255, 255, 255));
        tambahResepButton.setText("Tambah");
        tambahResepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahResepButtonActionPerformed(evt);
            }
        });

        hapusResepButton.setBackground(new java.awt.Color(24, 170, 176));
        hapusResepButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        hapusResepButton.setForeground(new java.awt.Color(255, 255, 255));
        hapusResepButton.setText("Hapus");
        hapusResepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusResepButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout resepButtonPanelLayout = new javax.swing.GroupLayout(resepButtonPanel);
        resepButtonPanel.setLayout(resepButtonPanelLayout);
        resepButtonPanelLayout.setHorizontalGroup(
            resepButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resepButtonPanelLayout.createSequentialGroup()
                .addComponent(tambahResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(hapusResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        resepButtonPanelLayout.setVerticalGroup(
            resepButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resepButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resepButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tambahResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusResepButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        resepLabel.setFont(new java.awt.Font("Arial Black", 1, 80)); // NOI18N
        resepLabel.setForeground(new java.awt.Color(255, 255, 255));
        resepLabel.setText("RESEP");

        tablePembuatanResep.setBackground(new java.awt.Color(255, 255, 255));
        tablePembuatanResep.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tablePembuatanResep.setForeground(new java.awt.Color(0, 0, 0));
        tablePembuatanResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablePembuatanResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePembuatanResepMouseClicked(evt);
            }
        });
        tabelResepScrollPane.setViewportView(tablePembuatanResep);

        specialAttributePanel.setBackground(new java.awt.Color(134, 196, 66));

        idResepLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        idResepLabel.setForeground(new java.awt.Color(255, 255, 255));
        idResepLabel.setText("ID Resep : ");

        idResepTextField.setBackground(new java.awt.Color(255, 255, 255));
        idResepTextField.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        idResepTextField.setForeground(new java.awt.Color(0, 0, 0));
        idResepTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idResepTextFieldActionPerformed(evt);
            }
        });

        apotekerLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        apotekerLabel.setForeground(new java.awt.Color(255, 255, 255));
        apotekerLabel.setText("Ditangani oleh Apoteker :");

        apotekerTextField.setBackground(new java.awt.Color(24, 170, 176));
        apotekerTextField.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 14)); // NOI18N
        apotekerTextField.setForeground(new java.awt.Color(255, 255, 255));
        apotekerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apotekerTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout specialAttributePanelLayout = new javax.swing.GroupLayout(specialAttributePanel);
        specialAttributePanel.setLayout(specialAttributePanelLayout);
        specialAttributePanelLayout.setHorizontalGroup(
            specialAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specialAttributePanelLayout.createSequentialGroup()
                .addComponent(idResepLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idResepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(apotekerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apotekerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );
        specialAttributePanelLayout.setVerticalGroup(
            specialAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(specialAttributePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(specialAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idResepLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idResepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apotekerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(apotekerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        ketentuanLabel.setFont(new java.awt.Font("Arial Black", 2, 12)); // NOI18N
        ketentuanLabel.setForeground(new java.awt.Color(255, 255, 255));
        ketentuanLabel.setText("* Klik Detail Resep, untuk melihat Rincian Pembuatan Resep");

        javax.swing.GroupLayout produkPanelLayout = new javax.swing.GroupLayout(produkPanel);
        produkPanel.setLayout(produkPanelLayout);
        produkPanelLayout.setHorizontalGroup(
            produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resepFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resepLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                            .addComponent(resepButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(produkPanelLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(specialAttributePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(produkPanelLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(searchTransaksiInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ketentuanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tabelResepScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1001, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        produkPanelLayout.setVerticalGroup(
            produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addComponent(resepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produkPanelLayout.createSequentialGroup()
                        .addComponent(searchTransaksiInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resepButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specialAttributePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)
                .addComponent(resepFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(ketentuanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabelResepScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout coverPanelLayout = new javax.swing.GroupLayout(coverPanel);
        coverPanel.setLayout(coverPanelLayout);
        coverPanelLayout.setHorizontalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1328, Short.MAX_VALUE)
            .addGroup(coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(produkPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1328, Short.MAX_VALUE))
        );
        coverPanelLayout.setVerticalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 963, Short.MAX_VALUE)
            .addGroup(coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(produkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1328, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(coverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 963, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(coverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchResepInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchResepInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchResepInputTextFieldActionPerformed

    private void searchResepInputTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchResepInputTextFieldKeyTyped

    }//GEN-LAST:event_searchResepInputTextFieldKeyTyped

    private void searchResepInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchResepInputButtonActionPerformed
        String query = searchResepInputTextField.getText();
        if (query.isEmpty()) {
            showTableBySearch(null);
        } else {
            try {
                Integer.parseInt(query);
                showTableBySearch(query);
                if (tablePembuatanResep.getModel().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Resep dengan ID '" + query + "' tidak ditemukan!", "Pencarian", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                showTableBySearch(null);
                JOptionPane.showMessageDialog(rootPane, "Pencarian hanya dapat dilakukan berdasarkan ID Resep.", "Pencarian", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan saat pencarian: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_searchResepInputButtonActionPerformed

    private void namaPelangganDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaPelangganDropdownActionPerformed

        Pelanggan selectedPelanggan = (Pelanggan) namaPelangganDropdown.getSelectedItem(); // Dapatkan pelanggan terpilih
        
        if (selectedPelanggan != null) {
            populateProdukResepDropdownByPelanggan(selectedPelanggan.getId_pelanggan()); // <<< PASTIKAN INI TERPANGGIL
            produkDropdown.setEnabled(true);
            namaDokterInputTextField.setEnabled(true);
            aturanPakaiInputTextField.setEnabled(true);
            tambahItemResepButton.setEnabled(true);
        } else {
            produkDropdown.removeAllItems(); // Kosongkan produk jika tidak ada pelanggan
            
            // Apabila pelanggan belum dipilih, maka komponen" ini tidak bisa diakses
            produkDropdown.setEnabled(false); 
            namaDokterInputTextField.setEnabled(false);
            aturanPakaiInputTextField.setEnabled(false);
            tambahItemResepButton.setEnabled(false);
        }
        updateTanggalResepOtomatis(); // Update tanggal juga jika pelanggan direset/null
    }//GEN-LAST:event_namaPelangganDropdownActionPerformed

    private void aturanPakaiInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aturanPakaiInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aturanPakaiInputTextFieldActionPerformed

    private void buatResepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buatResepButtonActionPerformed
        if (actionPembuatanResep == null) return;
        
        try {
            daftarResepMasihKosongException();
            
            int dialog = JOptionPane.showConfirmDialog(rootPane, "Sudah Yakin ingin Membuat Resep?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (dialog != JOptionPane.YES_OPTION) {
                return;
            }
            
            LocalDate tglResep = resepDate; 
            
            Pelanggan selectedPelanggan = (Pelanggan) namaPelangganDropdown.getSelectedItem();
            Apoteker selectedApoteker = loggedInApoteker; // Transaksi akan ditangani oleh Apoteker yang login saat itu

            if ("add".equals(actionPembuatanResep)) { // Jika mau menambahkan resep tapi sudah ada sebelumnya
                for (DetailPembuatanResep detailItem : currentDetailItems) {

                    if (rc.isResepExistForPelangganProduk(selectedPelanggan.getId_pelanggan(), detailItem.getProduk().getId_produk())) {
                        JOptionPane.showMessageDialog(rootPane, 
                            "Resep untuk pelanggan '" + selectedPelanggan.getNama() + 
                            "' dan produk '" + detailItem.getProduk().getNama_produk() + 
                            "' sudah ada di database. Tidak dapat membuat resep duplikat.", 
                            "Resep Duplikat", JOptionPane.WARNING_MESSAGE);
                        return; // Batalkan operasi jika duplikat ditemukan
                    }
                }
            }

            // Buat objek Transaksi header
            PembuatanResep resepHeader = new PembuatanResep(
                selectedPelanggan,
                selectedApoteker,
                namaDokterInputTextField.getText(),
                tglResep,
                currentDetailItems 
            );

            switch (actionPembuatanResep) {
                case "add":
                    rc.insertDataPembuatanResep(resepHeader);
                    JOptionPane.showMessageDialog(rootPane, "Resep berhasil Dibuat!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "update":
                    int idToUpdate = Integer.parseInt(idResepTextField.getText());
                    resepHeader.setId_resep(idToUpdate);
                    rc.updatePembuatanResep(resepHeader, idToUpdate);
                    JOptionPane.showMessageDialog(rootPane, "Resep berhasil Diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }

            namaPelangganDropdown.setEnabled(true);
            clearText();
            setComponents(false);
            setEditDeleteButton(false);
            tablePembuatanResep.setEnabled(true);
            showTableBySearch(null); // Refresh tabel utama
            actionPembuatanResep = null;
            currentDetailItems.clear(); // Bersihkan list detail tak terlihat
            
            jumlah_resep = 0; // Reset jumlah Resep
            jumlahLabel.setText(Integer.toString(jumlah_resep));
        }catch(KeranjangKosongException e){
            JOptionPane.showConfirmDialog(rootPane, e.toString());
        }catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_buatResepButtonActionPerformed

    private void tambahItemResepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahItemResepButtonActionPerformed
        try {
            inputKosongPembuatanResepException();

            Produk selectedProduk = (Produk) produkDropdown.getSelectedItem();

            // Validasi duplikasi item di daftar Pembuatan Resep
            for (DetailPembuatanResep detail : currentDetailItems) {
                if (detail.getProduk().getId_produk().equals(selectedProduk.getId_produk())) {
                    JOptionPane.showMessageDialog(rootPane, "Produk ini sudah masuk ke List Pembuatan Resep.", "Item Duplikat", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            jumlah_resep++;
            jumlahLabel.setText(Integer.toString(jumlah_resep));

            // Buat objek DetailTransaksi sementara
            DetailPembuatanResep detail = new DetailPembuatanResep(null, selectedProduk, jumlah_resep, aturanPakaiInputTextField.getText());

            currentDetailItems.add(detail); // Tambahkan ke list keranjang sementara
            JOptionPane.showMessageDialog(rootPane, "Item Berhasil ditambahkan ke Daftar Pembuatan Resep");
            namaPelangganDropdown.setEnabled(false);

        } catch (InputKosongException e) {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan sistem: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tambahItemResepButtonActionPerformed

    private void batalkanResepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalkanResepButtonActionPerformed
        actionPembuatanResep = null;
        clearText();
        setComponents(false);
        setEditDeleteButton(false);
        tablePembuatanResep.setEnabled(true);
        namaPelangganDropdown.setEnabled(true);
        tambahResepButton.setEnabled(true);
        currentDetailItems.clear();
    }//GEN-LAST:event_batalkanResepButtonActionPerformed

    private void listResepButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listResepButtonMouseClicked
        Pelanggan selectedPelanggan = (Pelanggan) namaPelangganDropdown.getSelectedItem();

        if ("add".equals(actionPembuatanResep)) {
            if (currentDetailItems.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "List Pembuatan Resep masih Kosong.", "Keranjang Kosong", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder resepDetail = new StringBuilder();
            resepDetail.append("--- Keranjang Belanja " + selectedPelanggan.getNama() + " ---\n");
            int itemNum = 1;
            for (DetailPembuatanResep detail : currentDetailItems) {
                resepDetail.append(itemNum++).append(". ");
                resepDetail.append(detail.getProduk().getNama_produk());
                resepDetail.append(" (").append(detail.getJumlah_resep()).append("x)");
                resepDetail.append(" - Aturan Pakai: ").append(detail.getAturan_pakai());
                resepDetail.append("\n"); // Baris baru untuk setiap item
            }
            JOptionPane.showMessageDialog(rootPane, resepDetail.toString(), "List Pembuatan Resep", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_listResepButtonMouseClicked

    private void listResepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listResepButtonActionPerformed

    }//GEN-LAST:event_listResepButtonActionPerformed

    private void tambahResepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahResepButtonActionPerformed
        actionPembuatanResep = "add";
        clearText();
        setComponents(true);
        tablePembuatanResep.setEnabled(false);
        idResepTextField.setText("Auto Gen");
        idResepTextField.setEnabled(false);
    }//GEN-LAST:event_tambahResepButtonActionPerformed

    private void hapusResepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusResepButtonActionPerformed
        int opsi = JOptionPane.showConfirmDialog(rootPane, "Yakin Ingin Menghapus Data?", "Hapus Data", JOptionPane.YES_NO_OPTION);
        if( opsi == JOptionPane.NO_OPTION || opsi == JOptionPane.CLOSED_OPTION) return;

        actionPembuatanResep = "delete";
        rc.deletePembuatanResep(Integer.parseInt(idResepTextField.getText()));

        clearText();
        setEditDeleteButton(false);
        setComponents(false);
        showTableBySearch("");
        actionPembuatanResep = null;
        currentDetailItems.clear();
    }//GEN-LAST:event_hapusResepButtonActionPerformed

    private void tablePembuatanResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePembuatanResepMouseClicked
        int clickedRow = tablePembuatanResep.getSelectedRow();
        if (clickedRow == -1) return;

        TablePembuatanResep model = (TablePembuatanResep) tablePembuatanResep.getModel();
        PembuatanResep selectedResep = model.getList().get(clickedRow);
        
        //        setComponents(true);
        setEditDeleteButton(true);
        buatResepButton.setEnabled(false);
        batalkanResepButton.setEnabled(true);
        namaDokterInputTextField.setEnabled(false);

        idResepTextField.setText(String.valueOf(selectedResep.getId_resep()));
        idResepTextField.setEnabled(false);

        int pelangganIndex = -1;
        for (int i = 0; i < listPelanggan.size(); i++) {
            if (listPelanggan.get(i).getId_pelanggan() == selectedResep.getPelanggan().getId_pelanggan()) {
                pelangganIndex = i;
                break;
            }
        }
        if (pelangganIndex != -1) namaPelangganDropdown.setSelectedIndex(pelangganIndex);

        namaDokterInputTextField.setText(selectedResep.getNama_dokter());
        
        if (selectedResep.getTanggal_resep() != null){
            resepDate = selectedResep.getTanggal_resep();
        }else{
            resepDate = null;
        }

        currentDetailItems.clear();
        currentDetailItems.addAll(selectedResep.getDetailResep());

        tablePembuatanResepDetailClicked(evt);
        tambahItemResepButton.setEnabled(false);
    }//GEN-LAST:event_tablePembuatanResepMouseClicked

    // Menampilkan Detail Transaksi melalui JOptionPane ketika di klik pada tabelnya
    private void tablePembuatanResepDetailClicked(java.awt.event.MouseEvent evt) {
        int clickedRow = tablePembuatanResep.getSelectedRow();
        int clickedColumn = tablePembuatanResep.getSelectedColumn(); 

        if (clickedRow == -1) return;

        // Dapatkan objek Pembuatan Resep dari baris yang diklik
        TablePembuatanResep model = (TablePembuatanResep) tablePembuatanResep.getModel();
        PembuatanResep selectedResep = model.getList().get(clickedRow);

        if (clickedColumn == 5) { // Jika kolom Item Produk (index 5) yang diklik
            List<DetailPembuatanResep> details = selectedResep.getDetailResep();
            if (details == null || details.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Tidak ada item untuk List Resep ini", "Detail Pembuatan Resep", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder detailMessage = new StringBuilder();
            detailMessage.append("--- Detail Pembuatan Resep ID: ").append(selectedResep.getId_resep()).append(" ---\n");
            detailMessage.append("Pelanggan: ").append(selectedResep.getPelanggan().getNama()).append("\n");
            detailMessage.append("Apoteker: ").append(selectedResep.getApoteker().getNama_apoteker()).append("\n");
            detailMessage.append("Tanggal: ").append(selectedResep.getTanggal_resep()).append("\n");

            detailMessage.append("\n--- Item-item Pembuatan Resep ---\n");
            int itemNum = 1;
            for (int i = 0; i < details.size(); i++) {
                detailMessage.append(itemNum++).append(". ");
                detailMessage.append(details.get(i).getProduk().getNama_produk());
                detailMessage.append(" (Jenis: ").append(details.get(i).getProduk().getJenis()).append(")");
                detailMessage.append("\n    Aturan Pakai: ").append(details.get(i).getAturan_pakai());
                detailMessage.append("\n"); // Baris baru untuk item berikutnya
            }

            JOptionPane.showMessageDialog(rootPane, detailMessage.toString(), "Detail Pembuatan Resep Lengkap", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void idResepTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idResepTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idResepTextFieldActionPerformed

    private void apotekerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apotekerTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apotekerTextFieldActionPerformed

    private void namaDokterInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaDokterInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaDokterInputTextFieldActionPerformed

    private void produkDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produkDropdownActionPerformed
        if (isInitializing){
            return;
        }else{
            Pelanggan selectedPelanggan = (Pelanggan) namaPelangganDropdown.getSelectedItem(); // Ambil pelanggan saat ini

            if (selectedPelanggan == null) {
                // Jika pelanggan belum dipilih saat mencoba memilih produk
                produkDropdown.setSelectedIndex(-1); // Reset pilihan produk
                produkDropdown.setEnabled(false); // Nonaktifkan produkDropdown lagi
                JOptionPane.showMessageDialog(rootPane, "Silakan pilih Pelanggan terlebih dahulu untuk mengakses produk.", "Pelanggan Belum Terpilih", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            // Jika pelanggan sudah terpilih, lanjutkan dengan logika update tanggal
            updateTanggalResepOtomatis();
        }
    }//GEN-LAST:event_produkDropdownActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apotekerLabel;
    private javax.swing.JTextField apotekerTextField;
    private javax.swing.JLabel aturanPakaiInputLabel;
    private javax.swing.JTextField aturanPakaiInputTextField;
    private javax.swing.JButton batalkanResepButton;
    private javax.swing.JButton buatResepButton;
    private javax.swing.JPanel coverPanel;
    private javax.swing.JButton hapusResepButton;
    private javax.swing.JLabel idResepLabel;
    private javax.swing.JTextField idResepTextField;
    private javax.swing.JLabel jumlahLabel;
    private javax.swing.JLabel jumlahResepLabel;
    private javax.swing.JLabel ketentuanLabel;
    private javax.swing.JButton listResepButton;
    private javax.swing.JTextField namaDokterInputTextField;
    private javax.swing.JPanel namaDokterPanel;
    private javax.swing.JComboBox<Pelanggan> namaPelangganDropdown;
    private javax.swing.JLabel namaPelangganInputLabel;
    private javax.swing.JPanel namaPelangganPanel;
    private javax.swing.JComboBox<Produk> produkDropdown;
    private javax.swing.JLabel produkInputLabel;
    private javax.swing.JLabel produkInputLabel1;
    private javax.swing.JPanel produkPanel;
    private javax.swing.JPanel resepButtonPanel;
    private javax.swing.JPanel resepFormPanel;
    private javax.swing.JLabel resepLabel;
    private javax.swing.JButton searchResepInputButton;
    private javax.swing.JLabel searchResepInputLabel;
    private javax.swing.JTextField searchResepInputTextField;
    private javax.swing.JPanel searchTransaksiInputPanel;
    private javax.swing.JPanel specialAttributePanel;
    private javax.swing.JScrollPane tabelResepScrollPane;
    private javax.swing.JTable tablePembuatanResep;
    private javax.swing.JButton tambahItemResepButton;
    private javax.swing.JButton tambahResepButton;
    // End of variables declaration//GEN-END:variables
}
