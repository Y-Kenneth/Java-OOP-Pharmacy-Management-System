package panelView;

import controller.TransaksiControl;
import controller.PelangganControl;
import controller.ApotekerControl;
import controller.ProdukControl;
import exception.InputKosongException;
import exception.StokKurangException;
import exception.KeranjangKosongException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import java.awt.Component;

import model.Transaksi;
import model.Pelanggan;
import model.Apoteker;
import model.Produk;
import model.Obat;
import model.Vitamin;
import model.DetailTransaksi;
import table.TableTransaksi;

public class PanelTransaksi extends javax.swing.JPanel {
    
    // --- Kontroler ---
    private TransaksiControl tc;
    private PelangganControl pc;
    private ApotekerControl ac;
    private ProdukControl prodC;
    
    // --- Variabel State Panel ---
    private Transaksi T = null; 
    private String actionTransaksi = null;
    private int selectedIdTransaksi = 0;
    private Apoteker loggedInApoteker;
    private int tempTotalHarga = 0;
    
    // List untuk dropdown dan tabel
    private List<Pelanggan> listPelanggan;
    private List<Apoteker> listApoteker;
    private List<Produk> listProduk;
    private List<DetailTransaksi> currentDetailItems; 
    
    private Component rootPane;
    
    public PanelTransaksi(Apoteker loginApoteker) {
        initComponents();
        setOpaque(false);

        this.loggedInApoteker = loginApoteker;
        apotekerTextField.setText(loginApoteker.getNama_apoteker());
        
        tc = new TransaksiControl();
        pc = new PelangganControl();
        ac = new ApotekerControl();
        prodC = new ProdukControl();
        
        currentDetailItems = new ArrayList<>();
        
        populateDropdowns();
        showTableBySearch(null);
        
        clearText();
        setComponents(false);
        setEditDeleteButton(false);
    }
    
    public void clearText(){
        namaPelangganDropdown.setSelectedIndex(-1);
        produkDropdown.setSelectedIndex(-1);
        
        idTransaksiTextField.setText("");
        jumlahInputTextField.setText("");
        totalBiayaOutputTextField.setText("");
        
        searchTransaksiInputTextField.setText("");
    }
    
    public void setEditDeleteButton(boolean value){
//        barukanTransaksiButton.setEnabled(value);
        hapusTransaksiButton.setEnabled(value);
    }
    
    public void setComponents(boolean value){
        namaPelangganDropdown.setEnabled(value);
        tanggalTransaksiDateChooser.setEnabled(value);
        
        produkDropdown.setEnabled(value);
        totalBiayaOutputTextField.setEnabled(false);
        
        tambahItemTransaksiButton.setEnabled(value);
        bayarTransaksiButton.setEnabled(value);
        batalkanTransaksiButton.setEnabled(value);
        keranjangButton.setEnabled(value);
    }
    
    // --- Method untuk Mengeset Semua Dropdowns ---
    public void populateDropdowns() {
        
        // Mengisi dropdown Pelanggan
        listPelanggan = pc.showListPelanggan();
        if (listPelanggan != null) {
            for (int i = 0; i < listPelanggan.size(); i++) {
                namaPelangganDropdown.addItem(listPelanggan.get(i)); // Tambahkan objek Pelanggan langsung
            }
        }

        // Mengisi dropdown Produk
        listProduk = prodC.showListProduk(); // ProdukControl.showListProduk() mengembalikan List<Produk>
        if (listProduk != null) {
            for (int i = 0; i < listProduk.size(); i++) {
                produkDropdown.addItem(listProduk.get(i)); // Tambahkan objek Produk langsung
            }
        }
    }   
    
    public void showTableBySearch(String target) {
        List<Transaksi> transaksiHeaders;
        if (target == null || target.isEmpty()) {
            transaksiHeaders = tc.showTable(null).getList(); // Mengambil semua data
        } else {
            Transaksi singleTransaksi = tc.searchTransaksiByAll(target);
            transaksiHeaders = new ArrayList<>();
            if (singleTransaksi != null) {
                transaksiHeaders.add(singleTransaksi);
            }
        }
        tableTransaksi.setModel(new TableTransaksi(transaksiHeaders));
    }
    
    // Exception
    public void inputKosongTransaksiException() throws InputKosongException{
        if (namaPelangganDropdown.getSelectedIndex() == -1 || produkDropdown.getSelectedIndex() == -1 
                || jumlahInputTextField.getText().isEmpty()){
            throw new InputKosongException();
        }
    }
    public void jumlahMelebihiStokException() throws StokKurangException{
        Produk selectedProduk = (Produk) produkDropdown.getSelectedItem();
        int jumlah = Integer.parseInt(jumlahInputTextField.getText());
        
        if (jumlah > selectedProduk.getStok()){
            throw new StokKurangException("Stok Produk " + selectedProduk.getNama_produk() + " Tersisa " + selectedProduk.getStok());
        }
    }
    public void keranjangMasihKosongException() throws KeranjangKosongException{
        if (currentDetailItems.isEmpty()){
            throw new KeranjangKosongException("Belum ada Item yang ditambahkan di Keranjang");
        }
    }
    
    public void calculateTotalHarga(){
        int total = 0;
        for (DetailTransaksi detail : currentDetailItems){
            total += detail.getSub_total();
        }
        totalBiayaOutputTextField.setText(String.valueOf(total));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        coverPanel = new javax.swing.JPanel();
        produkPanel = new javax.swing.JPanel();
        searchTransaksiInputPanel = new javax.swing.JPanel();
        searchProdukInputLabel = new javax.swing.JLabel();
        searchTransaksiInputTextField = new javax.swing.JTextField();
        searchTransaksiInputButton = new javax.swing.JButton();
        transaksiFormPanel = new javax.swing.JPanel();
        namaPelangganPanel = new javax.swing.JPanel();
        namaPelangganInputLabel = new javax.swing.JLabel();
        namaPelangganDropdown = new javax.swing.JComboBox<>();
        namaApotekerPanel = new javax.swing.JPanel();
        produkDropdown = new javax.swing.JComboBox<>();
        produkInputLabel = new javax.swing.JLabel();
        tanggalTransaksiPanel = new javax.swing.JPanel();
        tanggalTransaksiInputLabel = new javax.swing.JLabel();
        tanggalTransaksiDateChooser = new com.toedter.calendar.JDateChooser();
        totalBiayaOutputLabel = new javax.swing.JLabel();
        totalBiayaOutputTextField = new javax.swing.JTextField();
        jumlahInputLabel = new javax.swing.JLabel();
        jumlahInputTextField = new javax.swing.JTextField();
        bayarTransaksiButton = new javax.swing.JButton();
        tambahItemTransaksiButton = new javax.swing.JButton();
        batalkanTransaksiButton = new javax.swing.JButton();
        keranjangButton = new javax.swing.JButton();
        transaksiButtonPanel = new javax.swing.JPanel();
        tambahTransaksiButton = new javax.swing.JButton();
        hapusTransaksiButton = new javax.swing.JButton();
        transaksiLabel = new javax.swing.JLabel();
        tabelTransaksiScrollPane = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        specialAttributePanel = new javax.swing.JPanel();
        idTransaksiLabel = new javax.swing.JLabel();
        idTransaksiTextField = new javax.swing.JTextField();
        apotekerLabel = new javax.swing.JLabel();
        apotekerTextField = new javax.swing.JTextField();
        ketentuanLabel = new javax.swing.JLabel();

        produkPanel.setBackground(new java.awt.Color(134, 196, 66));

        searchTransaksiInputPanel.setBackground(new java.awt.Color(134, 196, 66));

        searchProdukInputLabel.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        searchProdukInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchProdukInputLabel.setText("Cari Transaksi di Apotek ASEAN");

        searchTransaksiInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        searchTransaksiInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        searchTransaksiInputTextField.setForeground(new java.awt.Color(0, 0, 0));
        searchTransaksiInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTransaksiInputTextFieldActionPerformed(evt);
            }
        });
        searchTransaksiInputTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTransaksiInputTextFieldKeyTyped(evt);
            }
        });

        searchTransaksiInputButton.setBackground(new java.awt.Color(24, 170, 176));
        searchTransaksiInputButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        searchTransaksiInputButton.setForeground(new java.awt.Color(255, 255, 255));
        searchTransaksiInputButton.setText("Cari");
        searchTransaksiInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTransaksiInputButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchTransaksiInputPanelLayout = new javax.swing.GroupLayout(searchTransaksiInputPanel);
        searchTransaksiInputPanel.setLayout(searchTransaksiInputPanelLayout);
        searchTransaksiInputPanelLayout.setHorizontalGroup(
            searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchTransaksiInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchProdukInputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(searchTransaksiInputPanelLayout.createSequentialGroup()
                        .addComponent(searchTransaksiInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(searchTransaksiInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        searchTransaksiInputPanelLayout.setVerticalGroup(
            searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchTransaksiInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchProdukInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchTransaksiInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTransaksiInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchTransaksiInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transaksiFormPanel.setBackground(new java.awt.Color(134, 196, 66));
        transaksiFormPanel.setForeground(new java.awt.Color(255, 255, 255));

        namaPelangganPanel.setBackground(new java.awt.Color(134, 196, 66));
        namaPelangganPanel.setForeground(new java.awt.Color(255, 255, 255));

        namaPelangganInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        namaPelangganInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        namaPelangganInputLabel.setText("Nama Pelanggan");

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
                .addContainerGap()
                .addGroup(namaPelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaPelangganInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaPelangganDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        namaPelangganPanelLayout.setVerticalGroup(
            namaPelangganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaPelangganPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(namaPelangganInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaPelangganDropdown, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        namaApotekerPanel.setBackground(new java.awt.Color(134, 196, 66));
        namaApotekerPanel.setForeground(new java.awt.Color(255, 255, 255));

        produkDropdown.setBackground(new java.awt.Color(255, 255, 255));
        produkDropdown.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        produkDropdown.setForeground(new java.awt.Color(0, 0, 0));

        produkInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        produkInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        produkInputLabel.setText("Produk yang Dibeli");

        javax.swing.GroupLayout namaApotekerPanelLayout = new javax.swing.GroupLayout(namaApotekerPanel);
        namaApotekerPanel.setLayout(namaApotekerPanelLayout);
        namaApotekerPanelLayout.setHorizontalGroup(
            namaApotekerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaApotekerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(namaApotekerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(produkDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(produkInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        namaApotekerPanelLayout.setVerticalGroup(
            namaApotekerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaApotekerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(produkInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(produkDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tanggalTransaksiPanel.setBackground(new java.awt.Color(134, 196, 66));
        tanggalTransaksiPanel.setForeground(new java.awt.Color(255, 255, 255));

        tanggalTransaksiInputLabel.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 14)); // NOI18N
        tanggalTransaksiInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        tanggalTransaksiInputLabel.setText("Tanggal Transaksi");

        tanggalTransaksiDateChooser.setBackground(new java.awt.Color(255, 255, 255));
        tanggalTransaksiDateChooser.setForeground(new java.awt.Color(0, 0, 0));
        tanggalTransaksiDateChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tanggalTransaksiDateChooserMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tanggalTransaksiPanelLayout = new javax.swing.GroupLayout(tanggalTransaksiPanel);
        tanggalTransaksiPanel.setLayout(tanggalTransaksiPanelLayout);
        tanggalTransaksiPanelLayout.setHorizontalGroup(
            tanggalTransaksiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tanggalTransaksiPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tanggalTransaksiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalTransaksiDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tanggalTransaksiPanelLayout.createSequentialGroup()
                        .addComponent(tanggalTransaksiInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(84, Short.MAX_VALUE))))
        );
        tanggalTransaksiPanelLayout.setVerticalGroup(
            tanggalTransaksiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tanggalTransaksiPanelLayout.createSequentialGroup()
                .addComponent(tanggalTransaksiInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tanggalTransaksiDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        totalBiayaOutputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        totalBiayaOutputLabel.setForeground(new java.awt.Color(255, 255, 255));
        totalBiayaOutputLabel.setText("Total Biaya Transaksi");

        totalBiayaOutputTextField.setBackground(new java.awt.Color(255, 255, 255));
        totalBiayaOutputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        totalBiayaOutputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalBiayaOutputTextFieldActionPerformed(evt);
            }
        });

        jumlahInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jumlahInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        jumlahInputLabel.setText("Jumlah");

        jumlahInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        jumlahInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jumlahInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahInputTextFieldActionPerformed(evt);
            }
        });

        bayarTransaksiButton.setBackground(new java.awt.Color(247, 210, 84));
        bayarTransaksiButton.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        bayarTransaksiButton.setForeground(new java.awt.Color(0, 0, 0));
        bayarTransaksiButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Transaksi.png"))); // NOI18N
        bayarTransaksiButton.setText("Bayar");
        bayarTransaksiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarTransaksiButtonActionPerformed(evt);
            }
        });

        tambahItemTransaksiButton.setBackground(new java.awt.Color(24, 170, 176));
        tambahItemTransaksiButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        tambahItemTransaksiButton.setForeground(new java.awt.Color(255, 255, 255));
        tambahItemTransaksiButton.setText("Tambah Item ke Keranjang");
        tambahItemTransaksiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahItemTransaksiButtonActionPerformed(evt);
            }
        });

        batalkanTransaksiButton.setBackground(new java.awt.Color(237, 8, 0));
        batalkanTransaksiButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        batalkanTransaksiButton.setForeground(new java.awt.Color(255, 255, 255));
        batalkanTransaksiButton.setText("Batalkan");
        batalkanTransaksiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalkanTransaksiButtonActionPerformed(evt);
            }
        });

        keranjangButton.setBackground(new java.awt.Color(247, 210, 84));
        keranjangButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Keranjang.png"))); // NOI18N
        keranjangButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keranjangButtonMouseClicked(evt);
            }
        });
        keranjangButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keranjangButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout transaksiFormPanelLayout = new javax.swing.GroupLayout(transaksiFormPanel);
        transaksiFormPanel.setLayout(transaksiFormPanelLayout);
        transaksiFormPanelLayout.setHorizontalGroup(
            transaksiFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transaksiFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(transaksiFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(namaApotekerPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(namaPelangganPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tanggalTransaksiPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(transaksiFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jumlahInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jumlahInputTextField)
                    .addComponent(totalBiayaOutputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalBiayaOutputTextField)
                    .addGroup(transaksiFormPanelLayout.createSequentialGroup()
                        .addComponent(tambahItemTransaksiButton, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(keranjangButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, Short.MAX_VALUE)))
                .addGap(60, 60, 60)
                .addGroup(transaksiFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bayarTransaksiButton, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(batalkanTransaksiButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(511, 511, 511))
        );
        transaksiFormPanelLayout.setVerticalGroup(
            transaksiFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiFormPanelLayout.createSequentialGroup()
                .addGroup(transaksiFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transaksiFormPanelLayout.createSequentialGroup()
                        .addComponent(namaPelangganPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(namaApotekerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(tanggalTransaksiPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transaksiFormPanelLayout.createSequentialGroup()
                        .addComponent(bayarTransaksiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(batalkanTransaksiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 12, Short.MAX_VALUE))
            .addGroup(transaksiFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jumlahInputLabel)
                .addGap(8, 8, 8)
                .addComponent(jumlahInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(totalBiayaOutputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalBiayaOutputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(transaksiFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(keranjangButton, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(tambahItemTransaksiButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        transaksiButtonPanel.setBackground(new java.awt.Color(134, 196, 66));

        tambahTransaksiButton.setBackground(new java.awt.Color(24, 170, 176));
        tambahTransaksiButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        tambahTransaksiButton.setForeground(new java.awt.Color(255, 255, 255));
        tambahTransaksiButton.setText("Tambah");
        tambahTransaksiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahTransaksiButtonActionPerformed(evt);
            }
        });

        hapusTransaksiButton.setBackground(new java.awt.Color(24, 170, 176));
        hapusTransaksiButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        hapusTransaksiButton.setForeground(new java.awt.Color(255, 255, 255));
        hapusTransaksiButton.setText("Hapus");
        hapusTransaksiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusTransaksiButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout transaksiButtonPanelLayout = new javax.swing.GroupLayout(transaksiButtonPanel);
        transaksiButtonPanel.setLayout(transaksiButtonPanelLayout);
        transaksiButtonPanelLayout.setHorizontalGroup(
            transaksiButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiButtonPanelLayout.createSequentialGroup()
                .addComponent(tambahTransaksiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(hapusTransaksiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        transaksiButtonPanelLayout.setVerticalGroup(
            transaksiButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(transaksiButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tambahTransaksiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusTransaksiButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        transaksiLabel.setFont(new java.awt.Font("Arial Black", 1, 80)); // NOI18N
        transaksiLabel.setForeground(new java.awt.Color(255, 255, 255));
        transaksiLabel.setText("TRANSAKSI");

        tableTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        tableTransaksi.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tableTransaksi.setForeground(new java.awt.Color(0, 0, 0));
        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiMouseClicked(evt);
            }
        });
        tabelTransaksiScrollPane.setViewportView(tableTransaksi);

        specialAttributePanel.setBackground(new java.awt.Color(134, 196, 66));

        idTransaksiLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        idTransaksiLabel.setForeground(new java.awt.Color(255, 255, 255));
        idTransaksiLabel.setText("ID Transaksi : ");

        idTransaksiTextField.setBackground(new java.awt.Color(255, 255, 255));
        idTransaksiTextField.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        idTransaksiTextField.setForeground(new java.awt.Color(0, 0, 0));
        idTransaksiTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTransaksiTextFieldActionPerformed(evt);
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
                .addComponent(idTransaksiLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idTransaksiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(idTransaksiLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idTransaksiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apotekerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(apotekerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        ketentuanLabel.setFont(new java.awt.Font("Arial Black", 2, 12)); // NOI18N
        ketentuanLabel.setForeground(new java.awt.Color(255, 255, 255));
        ketentuanLabel.setText("* Klik Item Produk, untuk melihat Detail Transaksi");

        javax.swing.GroupLayout produkPanelLayout = new javax.swing.GroupLayout(produkPanel);
        produkPanel.setLayout(produkPanelLayout);
        produkPanelLayout.setHorizontalGroup(
            produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addComponent(searchTransaksiInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(transaksiLabel))
                    .addComponent(transaksiFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(transaksiButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(specialAttributePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ketentuanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tabelTransaksiScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1001, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        produkPanelLayout.setVerticalGroup(
            produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addComponent(transaksiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produkPanelLayout.createSequentialGroup()
                        .addComponent(searchTransaksiInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transaksiButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specialAttributePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(transaksiFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(ketentuanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabelTransaksiScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(191, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout coverPanelLayout = new javax.swing.GroupLayout(coverPanel);
        coverPanel.setLayout(coverPanelLayout);
        coverPanelLayout.setHorizontalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1304, Short.MAX_VALUE)
            .addGroup(coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(coverPanelLayout.createSequentialGroup()
                    .addComponent(produkPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        coverPanelLayout.setVerticalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 962, Short.MAX_VALUE)
            .addGroup(coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(produkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1304, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(coverPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 966, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(coverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1304, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 966, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchTransaksiInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTransaksiInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTransaksiInputTextFieldActionPerformed

    private void searchTransaksiInputTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTransaksiInputTextFieldKeyTyped

    }//GEN-LAST:event_searchTransaksiInputTextFieldKeyTyped
    
    private void searchTransaksiInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTransaksiInputButtonActionPerformed
        String query = searchTransaksiInputTextField.getText();
        if (query.isEmpty()) {
            showTableBySearch(null); 
        } else {
            try {
                Integer.parseInt(query); 
                showTableBySearch(query); 
                if (tableTransaksi.getModel().getRowCount() == 0) {
                    JOptionPane.showMessageDialog(rootPane, "Transaksi dengan ID '" + query + "' tidak ditemukan!", "Pencarian", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                showTableBySearch(null); 
                JOptionPane.showMessageDialog(rootPane, "Pencarian hanya dapat dilakukan berdasarkan ID Transaksi.", "Pencarian", JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan saat pencarian: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_searchTransaksiInputButtonActionPerformed

    private void tanggalTransaksiDateChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tanggalTransaksiDateChooserMouseClicked

    }//GEN-LAST:event_tanggalTransaksiDateChooserMouseClicked

    private void tambahTransaksiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahTransaksiButtonActionPerformed
        actionTransaksi = "add";
        clearText();
        setComponents(true);
        tableTransaksi.setEnabled(false);
        
        idTransaksiTextField.setText("Auto Gen");
        idTransaksiTextField.setEnabled(false);
        jumlahInputTextField.setEnabled(true);
        calculateTotalHarga();
    }//GEN-LAST:event_tambahTransaksiButtonActionPerformed

    private void hapusTransaksiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusTransaksiButtonActionPerformed
        int opsi = JOptionPane.showConfirmDialog(rootPane, "Yakin Ingin Hapus ?", "Hapus Data", JOptionPane.YES_NO_OPTION);
        if( opsi == JOptionPane.NO_OPTION || opsi == JOptionPane.CLOSED_OPTION) return;
        
        actionTransaksi = "delete";
        tc.deleteTransaksi(Integer.parseInt(idTransaksiTextField.getText()));
        
        clearText();
        setEditDeleteButton(false);
        setComponents(false);
        showTableBySearch("");
        actionTransaksi = null;
        currentDetailItems.clear();
        calculateTotalHarga();
    }//GEN-LAST:event_hapusTransaksiButtonActionPerformed

    private void bayarTransaksiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarTransaksiButtonActionPerformed
        if (actionTransaksi == null) return;
        try {
            keranjangMasihKosongException();

            int dialog = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin melakukan Pembayaran?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (dialog != JOptionPane.YES_OPTION) {
                return;
            }

            // Dapatkan tanggal transaksi dari JDateChooser
            java.util.Date utilDate = tanggalTransaksiDateChooser.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            java.time.LocalDate tglTransaksi = sqlDate.toLocalDate();
            
            Pelanggan selectedPelanggan = (Pelanggan) namaPelangganDropdown.getSelectedItem();
            Apoteker selectedApoteker = loggedInApoteker; // Transaksi akan ditangani oleh Apoteker yang login saat itu

            // Hitung total harga dari detail item
            int totalHarga = 0; // Menggunakan int untuk total harga
            for (DetailTransaksi detail : currentDetailItems) {
                totalHarga += detail.getSub_total(); // Subtotal sudah int
                // Kurangi stok produk
                Produk p = detail.getProduk();
                int jumlah = detail.getJumlah();
                p.setStok(p.getStok() - jumlah);
                prodC.updateDataProduk(p, p.getId_produk());
            }

            // Buat objek Transaksi header
            Transaksi transaksiHeader = new Transaksi(
                    selectedPelanggan,
                    selectedApoteker,
                    tglTransaksi,
                    totalHarga, // Total harga sudah int
                    currentDetailItems // Teruskan list detail item
            );

            switch (actionTransaksi) {
                case "add":
                    tc.insertDataTransaksi(transaksiHeader);
                    JOptionPane.showMessageDialog(rootPane, "Transaksi berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "update":
                    // Untuk update, ID transaksi diambil dari idTransaksiTextField
                    int idToUpdate = Integer.parseInt(idTransaksiTextField.getText());
                    transaksiHeader.setId_transaksi(idToUpdate); // Pastikan objek memiliki ID yang benar
                    tc.updateTransaksi(transaksiHeader, idToUpdate);
                    JOptionPane.showMessageDialog(rootPane, "Transaksi berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }

            namaPelangganDropdown.setEnabled(true);
            clearText();
            setComponents(false);
            setEditDeleteButton(false);
            tableTransaksi.setEnabled(true);
            showTableBySearch(null); // Refresh tabel utama
            actionTransaksi = null;
            currentDetailItems.clear(); // Bersihkan list detail tak terlihat
            calculateTotalHarga(); // Membersihkan tampilan total harga
            tempTotalHarga = 0; // reset tampilan total harga sementara
            totalBiayaOutputTextField.setText(Integer.toString(tempTotalHarga));
        }catch(KeranjangKosongException e1){
            JOptionPane.showMessageDialog(rootPane, e1.toString());
        }catch (Exception e){ 
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_bayarTransaksiButtonActionPerformed

    private void totalBiayaOutputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalBiayaOutputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalBiayaOutputTextFieldActionPerformed

    private void jumlahInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahInputTextFieldActionPerformed

    private void namaPelangganDropdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaPelangganDropdownActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaPelangganDropdownActionPerformed

    private void idTransaksiTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTransaksiTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTransaksiTextFieldActionPerformed

    private void tambahItemTransaksiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahItemTransaksiButtonActionPerformed
        try {
            inputKosongTransaksiException();
            jumlahMelebihiStokException();

            Produk selectedProduk = (Produk) produkDropdown.getSelectedItem();
            int jumlah = Integer.parseInt(jumlahInputTextField.getText());

            // Validasi duplikasi item di keranjang
            for (DetailTransaksi detail : currentDetailItems) {
                if (detail.getProduk().getId_produk().equals(selectedProduk.getId_produk())) {
                    JOptionPane.showMessageDialog(rootPane, "Produk ini sudah ada di keranjang", "Item Duplikat", JOptionPane.WARNING_MESSAGE);
                    return;
                }
//                tempTotalHarga += (jumlah * detail.getSub_total()); 
//                totalBiayaOutputTextField.setText(Integer.toString(tempTotalHarga));
            }
            
            int subTotal = selectedProduk.getHarga() * jumlah; // Menggunakan int untuk harga

            // Buat objek DetailTransaksi sementara
            DetailTransaksi detail = new DetailTransaksi(null, selectedProduk, jumlah, subTotal); 

            currentDetailItems.add(detail); // Tambahkan ke list keranjang sementara
            JOptionPane.showMessageDialog(rootPane, "Item Berhasil ditambahkan ke Keranjang");
            calculateTotalHarga();
            
            namaPelangganDropdown.setEnabled(false); // Ketika sebuah user telah dipilih, maka tidak bisa diganti
            produkDropdown.setSelectedIndex(-1); // Reset input field detail
            jumlahInputTextField.setText("");

        } catch (InputKosongException | StokKurangException e) {
            JOptionPane.showMessageDialog(rootPane, e.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan sistem: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tambahItemTransaksiButtonActionPerformed

    private void batalkanTransaksiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalkanTransaksiButtonActionPerformed
        actionTransaksi = null;
        clearText();
        setComponents(false);
        setEditDeleteButton(false);
        tableTransaksi.setEnabled(true);
        tambahTransaksiButton.setEnabled(true); 
        tanggalTransaksiDateChooser.setEnabled(true);
        namaPelangganDropdown.setEnabled(true);
        currentDetailItems.clear();
        calculateTotalHarga();
    }//GEN-LAST:event_batalkanTransaksiButtonActionPerformed

    // Menampilkan Detail Transaksi melalui JOptionPane ketika di klik pada tabelnya
    private void tableTransaksiDetailClicked(java.awt.event.MouseEvent evt) {
        int clickedRow = tableTransaksi.getSelectedRow();
        int clickedColumn = tableTransaksi.getSelectedColumn(); // Dapatkan kolom yang diklik

        if (clickedRow == -1) return; // Pastikan ada baris yang dipilih

        // Dapatkan objek Transaksi dari baris yang diklik
        TableTransaksi model = (TableTransaksi) tableTransaksi.getModel();
        Transaksi selectedTransaksi = model.getList().get(clickedRow);

        if (clickedColumn == 5) { // Jika kolom Item Produk (index 5) yang diklik
            List<DetailTransaksi> details = selectedTransaksi.getDetailTransaksi();
            if (details == null || details.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Tidak ada item detail untuk transaksi ini.", "Detail Transaksi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder detailMessage = new StringBuilder();
            detailMessage.append("--- Detail Transaksi ID: ").append(selectedTransaksi.getId_transaksi()).append(" ---\n");
            detailMessage.append("Pelanggan: ").append(selectedTransaksi.getPelanggan().getNama()).append("\n");
            detailMessage.append("Apoteker: ").append(selectedTransaksi.getApoteker().getNama_apoteker()).append("\n");
            detailMessage.append("Tanggal: ").append(selectedTransaksi.getTanggal()).append("\n");
            detailMessage.append("Total Harga: ").append(selectedTransaksi.getTotal_harga()).append("\n\n");

            detailMessage.append("--- Item-item Pembelian ---\n");
            int itemNum = 1;
            for (DetailTransaksi detail : details) {
                detailMessage.append(itemNum++).append(". ");
                detailMessage.append(detail.getProduk().getNama_produk());
                detailMessage.append(" (").append(detail.getProduk().getJenis()).append(")");
                detailMessage.append("\n   Jumlah: ").append(detail.getJumlah());
                detailMessage.append("\n   Subtotal: ").append(detail.getSub_total());
                
                if ("Obat".equals(detail.getProduk().getJenis())) {
                    detailMessage.append("\n   Resep: ").append(((Obat) detail.getProduk()).getJenis_resep());
                } else if ("Vitamin".equals(detail.getProduk().getJenis())) {
                    detailMessage.append("\n   Konsumsi: ").append(((Vitamin) detail.getProduk()).getCara_konsumsi());
                }
                 
                detailMessage.append("\n"); // Baris baru untuk item berikutnya
            }

            JOptionPane.showMessageDialog(rootPane, detailMessage.toString(), "Detail Transaksi Lengkap", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
        int clickedRow = tableTransaksi.getSelectedRow();
        if (clickedRow == -1) return; 

        TableTransaksi model = (TableTransaksi) tableTransaksi.getModel();
        Transaksi selectedTransaksi = model.getList().get(clickedRow); 
        
        //        setComponents(true);
        setEditDeleteButton(true);
        bayarTransaksiButton.setEnabled(false);
        batalkanTransaksiButton.setEnabled(true);
        namaPelangganDropdown.setEnabled(false);
        jumlahInputTextField.setEnabled(false);
        totalBiayaOutputTextField.setEnabled(false);
        tanggalTransaksiDateChooser.setEnabled(false);

        idTransaksiTextField.setText(String.valueOf(selectedTransaksi.getId_transaksi()));
        idTransaksiTextField.setEnabled(false);
        
        int pelangganIndex = -1;
        for (int i = 0; i < listPelanggan.size(); i++) {
            if (listPelanggan.get(i).getId_pelanggan() == selectedTransaksi.getPelanggan().getId_pelanggan()) {
                pelangganIndex = i;
                break;
            }
        }
        if (pelangganIndex != -1) namaPelangganDropdown.setSelectedIndex(pelangganIndex);

        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedTransaksi.getTanggal());
        tanggalTransaksiDateChooser.setDate(sqlDate);
        totalBiayaOutputTextField.setText(String.valueOf(selectedTransaksi.getTotal_harga()));
        
        // --- Isi list currentDetailItems dari objek Transaksi yang terpilih ---
        currentDetailItems.clear(); 
        currentDetailItems.addAll(selectedTransaksi.getDetailTransaksi()); 
        calculateTotalHarga(); 
        
        tableTransaksiDetailClicked(evt);
        tambahItemTransaksiButton.setEnabled(false);
    }//GEN-LAST:event_tableTransaksiMouseClicked

    private void apotekerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apotekerTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apotekerTextFieldActionPerformed

    private void keranjangButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keranjangButtonActionPerformed
        
    }//GEN-LAST:event_keranjangButtonActionPerformed

    private void keranjangButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangButtonMouseClicked
        Pelanggan selectedPelanggan = (Pelanggan) namaPelangganDropdown.getSelectedItem();
        
        if ("add".equals(actionTransaksi)) {
            if (currentDetailItems.isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Keranjang belanja saat ini kosong. Silakan tambahkan item.", "Keranjang Kosong", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder keranjangDetail = new StringBuilder();
            keranjangDetail.append("--- Keranjang Belanja " + selectedPelanggan.getNama() + " ---\n");
            int itemNum = 1;
            for (model.DetailTransaksi detail : currentDetailItems) {
                keranjangDetail.append(itemNum++).append(". ");
                keranjangDetail.append(detail.getProduk().getNama_produk());
                keranjangDetail.append(" (").append(detail.getJumlah()).append("x)");
                keranjangDetail.append(" - Subtotal: ").append(detail.getSub_total());
                keranjangDetail.append("\n"); // Baris baru untuk setiap item
            }
            JOptionPane.showMessageDialog(rootPane, keranjangDetail.toString(), "Isi Keranjang Belanja", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_keranjangButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apotekerLabel;
    private javax.swing.JTextField apotekerTextField;
    private javax.swing.JButton batalkanTransaksiButton;
    private javax.swing.JButton bayarTransaksiButton;
    private javax.swing.JPanel coverPanel;
    private javax.swing.JButton hapusTransaksiButton;
    private javax.swing.JLabel idTransaksiLabel;
    private javax.swing.JTextField idTransaksiTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jumlahInputLabel;
    private javax.swing.JTextField jumlahInputTextField;
    private javax.swing.JButton keranjangButton;
    private javax.swing.JLabel ketentuanLabel;
    private javax.swing.JPanel namaApotekerPanel;
    private javax.swing.JComboBox<Pelanggan> namaPelangganDropdown;
    private javax.swing.JLabel namaPelangganInputLabel;
    private javax.swing.JPanel namaPelangganPanel;
    private javax.swing.JComboBox<Produk> produkDropdown;
    private javax.swing.JLabel produkInputLabel;
    private javax.swing.JPanel produkPanel;
    private javax.swing.JLabel searchProdukInputLabel;
    private javax.swing.JButton searchTransaksiInputButton;
    private javax.swing.JPanel searchTransaksiInputPanel;
    private javax.swing.JTextField searchTransaksiInputTextField;
    private javax.swing.JPanel specialAttributePanel;
    private javax.swing.JScrollPane tabelTransaksiScrollPane;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JButton tambahItemTransaksiButton;
    private javax.swing.JButton tambahTransaksiButton;
    private com.toedter.calendar.JDateChooser tanggalTransaksiDateChooser;
    private javax.swing.JLabel tanggalTransaksiInputLabel;
    private javax.swing.JPanel tanggalTransaksiPanel;
    private javax.swing.JLabel totalBiayaOutputLabel;
    private javax.swing.JTextField totalBiayaOutputTextField;
    private javax.swing.JPanel transaksiButtonPanel;
    private javax.swing.JPanel transaksiFormPanel;
    private javax.swing.JLabel transaksiLabel;
    // End of variables declaration//GEN-END:variables
}
