package panelView;

import model.Produk;
import model.Obat;
import model.Vitamin;
import controller.ProdukControl;
import controller.ObatControl;
import controller.VitaminControl;
import exception.InputKosongException;
import exception.InvalidHargaException;
import exception.JenisResepException;
import exception.StokKurangException;
import java.awt.Component;
import javax.swing.JOptionPane;

public class PanelProduk extends javax.swing.JPanel {
    private ProdukControl pc = new ProdukControl();
    private ObatControl oc = new ObatControl();
    private VitaminControl vc = new VitaminControl();
    private Produk p = null;
    private Obat o = null;
    private Vitamin v = null;
    String actionProduk = null;
    private Component rootPane;
    
    public PanelProduk() {
        initComponents();
        setOpaque(false);
        showProduk();
        setComponentsProduk(false);
        setProdukEditDeleteButton(false);
        clearText();   
    }

    public void inputKosongProdukException() throws InputKosongException{
        if (namaProdukInputTextField.getText().isEmpty() || stokInputTextField.getText().isEmpty() || 
                hargaInputTextField.getText().isEmpty()){
            throw new InputKosongException();
        }
    }
    
    public void invalidStokProdukException() throws StokKurangException{
        if (Integer.parseInt(stokInputTextField.getText()) < 1){
            throw new StokKurangException("Minimal Input Stok Produk adalah 1");
        }
    }
    
    public void invalidHargaProdukException() throws InvalidHargaException{
        if (Integer.parseInt(hargaInputTextField.getText()) < 1000){
            throw new InvalidHargaException();
        }
    }
    
    public void invalidJenisResepException() throws JenisResepException{
        if (jenisProdukButton.getText().equals("Obat")){
            if (!specialAttributeInputTextField.getText().equalsIgnoreCase("Wajib")  
                && !specialAttributeInputTextField.getText().equalsIgnoreCase("Bebas"))
            {
                // Jenis resep hanya boleh "Wajib" dan "Bebas"
                throw new JenisResepException();
            }
        }
    }
    
    public void isNotIntegerException() throws NumberFormatException{
        if(!isInteger(stokInputTextField.getText()) || !isInteger(hargaInputTextField.getText())){
            throw new NumberFormatException();
        }
    }
    
    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void setComponentsProduk(boolean value){
        idProdukTextField.setEnabled(value);
        namaProdukInputTextField.setEnabled(value);
        stokInputTextField.setEnabled(value);
        hargaInputTextField.setEnabled(value);
        specialAttributeInputTextField.setEnabled(value);
        jenisProdukButton.setEnabled(value);
        tglKadaluarsaDateChooser.setEnabled(value);
    }
    
    public void setProdukEditDeleteButton(boolean value){
        barukanProdukButton.setEnabled(value);
        hapusProdukButton.setEnabled(value);
    }
    
    public void clearText(){
        idProdukTextField.setText("");
        namaProdukInputTextField.setText("");
        stokInputTextField.setText("");
        hargaInputTextField.setText("");
        specialAttributeInputTextField.setText("");
        searchProdukInputTextField.setText("");
        jenisProdukButton.setText("");
    }
    
    public void showProduk(){
        vitaminTextArea.setText("List Vitamin : \n==========================\n" + vc.showStringVitamin());
        obatTextArea.setText("List Obat : \n==========================\n" + oc.showStringObat());
    }
    
    private boolean obatIsSelected(){
        return jenisProdukButton.getText().equals("Obat");
    }
    
    private void doSearchProduk(){
        if(searchProdukInputTextField.getText().isEmpty()){
            return;
        }
            
        p = pc.searchDataProdukByAll(searchProdukInputTextField.getText()); 
            
        if(p != null){
            setProdukEditDeleteButton(true);
            idProdukTextField.setText(p.getId_produk());
            namaProdukInputTextField.setText(p.getNama_produk());
            stokInputTextField.setText(Integer.toString(p.getStok()));
            hargaInputTextField.setText(Integer.toString(p.getHarga()));
            jenisProdukButton.setText(p.getJenis());
            setSpecialAttributeLabel();
            specialAttributeInputTextField.setText(p.getSpecial());
            
            // Untuk menampilkan Tanggal di Date Chooser, perlu diubah dulu ke format Date (Sebelumnya LocalDate)
            java.sql.Date sqlDate = java.sql.Date.valueOf(p.getTgl_kadaluarsa());
            java.util.Date utilDate = sqlDate;      
            tglKadaluarsaDateChooser.setDate(utilDate);
        }else{
            JOptionPane.showMessageDialog(rootPane, "PRODUK NOT FOUND !!!");
        }
    }
    
    private void setSpecialAttributeLabel(){
        if(jenisProdukButton.getText().equals("Obat")){
            specialAttributeInputLabel.setText("Jenis Resep");
        }else{
            specialAttributeInputLabel.setText("Cara Konsumsi");
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
        searchProdukInputPanel = new javax.swing.JPanel();
        searchProdukInputLabel = new javax.swing.JLabel();
        searchProdukInputTextField = new javax.swing.JTextField();
        searchProdukInputButton = new javax.swing.JButton();
        produkFormPanel = new javax.swing.JPanel();
        namaProdukPanel = new javax.swing.JPanel();
        namaProdukInputLabel = new javax.swing.JLabel();
        namaProdukInputTextField = new javax.swing.JTextField();
        stokInputPanel = new javax.swing.JPanel();
        stokInputLabel = new javax.swing.JLabel();
        stokInputTextField = new javax.swing.JTextField();
        hargaInputPanel = new javax.swing.JPanel();
        hargaInputLabel = new javax.swing.JLabel();
        hargaInputTextField = new javax.swing.JTextField();
        idProdukLabel = new javax.swing.JLabel();
        idProdukTextField = new javax.swing.JTextField();
        jenisProdukLabel = new javax.swing.JLabel();
        jenisProdukButton = new javax.swing.JButton();
        tglKadaluarsaDateChooser = new com.toedter.calendar.JDateChooser();
        tglKadaluarsaInputLabel = new javax.swing.JLabel();
        specialAttributeInputLabel = new javax.swing.JLabel();
        specialAttributeInputTextField = new javax.swing.JTextField();
        ketentuanResepLabel = new javax.swing.JLabel();
        simpanProdukButton = new javax.swing.JButton();
        produkButtonPanel = new javax.swing.JPanel();
        tambahProdukButton = new javax.swing.JButton();
        barukanProdukButton = new javax.swing.JButton();
        hapusProdukButton = new javax.swing.JButton();
        produkIcon = new javax.swing.JLabel();
        obatScrollPane = new javax.swing.JScrollPane();
        obatTextArea = new javax.swing.JTextArea();
        vitaminScrollPane = new javax.swing.JScrollPane();
        vitaminTextArea = new javax.swing.JTextArea();
        produkLabel = new javax.swing.JLabel();

        produkPanel.setBackground(new java.awt.Color(134, 196, 66));

        searchProdukInputPanel.setBackground(new java.awt.Color(134, 196, 66));

        searchProdukInputLabel.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        searchProdukInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        searchProdukInputLabel.setText("Cari Produk :");

        searchProdukInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        searchProdukInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        searchProdukInputTextField.setForeground(new java.awt.Color(0, 0, 0));
        searchProdukInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchProdukInputTextFieldActionPerformed(evt);
            }
        });
        searchProdukInputTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchProdukInputTextFieldKeyTyped(evt);
            }
        });

        searchProdukInputButton.setBackground(new java.awt.Color(24, 170, 176));
        searchProdukInputButton.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 14)); // NOI18N
        searchProdukInputButton.setForeground(new java.awt.Color(255, 255, 255));
        searchProdukInputButton.setText("Cari");
        searchProdukInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchProdukInputButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchProdukInputPanelLayout = new javax.swing.GroupLayout(searchProdukInputPanel);
        searchProdukInputPanel.setLayout(searchProdukInputPanelLayout);
        searchProdukInputPanelLayout.setHorizontalGroup(
            searchProdukInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProdukInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchProdukInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchProdukInputPanelLayout.createSequentialGroup()
                        .addComponent(searchProdukInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchProdukInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 44, Short.MAX_VALUE))
                    .addComponent(searchProdukInputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        searchProdukInputPanelLayout.setVerticalGroup(
            searchProdukInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProdukInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchProdukInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchProdukInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchProdukInputTextField)
                    .addComponent(searchProdukInputButton, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        produkFormPanel.setBackground(new java.awt.Color(134, 196, 66));
        produkFormPanel.setForeground(new java.awt.Color(255, 255, 255));

        namaProdukPanel.setBackground(new java.awt.Color(134, 196, 66));
        namaProdukPanel.setForeground(new java.awt.Color(255, 255, 255));

        namaProdukInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        namaProdukInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        namaProdukInputLabel.setText("Nama Produk");

        namaProdukInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        namaProdukInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        namaProdukInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaProdukInputTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout namaProdukPanelLayout = new javax.swing.GroupLayout(namaProdukPanel);
        namaProdukPanel.setLayout(namaProdukPanelLayout);
        namaProdukPanelLayout.setHorizontalGroup(
            namaProdukPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaProdukPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(namaProdukPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaProdukInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namaProdukInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        namaProdukPanelLayout.setVerticalGroup(
            namaProdukPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namaProdukPanelLayout.createSequentialGroup()
                .addComponent(namaProdukInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaProdukInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        stokInputPanel.setBackground(new java.awt.Color(134, 196, 66));
        stokInputPanel.setForeground(new java.awt.Color(255, 255, 255));

        stokInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        stokInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        stokInputLabel.setText("Stok");

        stokInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        stokInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout stokInputPanelLayout = new javax.swing.GroupLayout(stokInputPanel);
        stokInputPanel.setLayout(stokInputPanelLayout);
        stokInputPanelLayout.setHorizontalGroup(
            stokInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stokInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(stokInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stokInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stokInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        stokInputPanelLayout.setVerticalGroup(
            stokInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stokInputPanelLayout.createSequentialGroup()
                .addComponent(stokInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stokInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        hargaInputPanel.setBackground(new java.awt.Color(134, 196, 66));
        hargaInputPanel.setForeground(new java.awt.Color(255, 255, 255));

        hargaInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        hargaInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        hargaInputLabel.setText("Harga");

        hargaInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        hargaInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        hargaInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hargaInputTextFieldActionPerformed(evt);
            }
        });
        hargaInputTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hargaInputTextFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout hargaInputPanelLayout = new javax.swing.GroupLayout(hargaInputPanel);
        hargaInputPanel.setLayout(hargaInputPanelLayout);
        hargaInputPanelLayout.setHorizontalGroup(
            hargaInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hargaInputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hargaInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hargaInputPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hargaInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        hargaInputPanelLayout.setVerticalGroup(
            hargaInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hargaInputPanelLayout.createSequentialGroup()
                .addComponent(hargaInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hargaInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        idProdukLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        idProdukLabel.setForeground(new java.awt.Color(255, 255, 255));
        idProdukLabel.setText("ID Produk :");

        idProdukTextField.setBackground(new java.awt.Color(255, 255, 255));
        idProdukTextField.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 12)); // NOI18N
        idProdukTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idProdukTextFieldActionPerformed(evt);
            }
        });
        idProdukTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idProdukTextFieldKeyTyped(evt);
            }
        });

        jenisProdukLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jenisProdukLabel.setForeground(new java.awt.Color(255, 255, 255));
        jenisProdukLabel.setText("Jenis Produk :");

        jenisProdukButton.setBackground(new java.awt.Color(24, 170, 176));
        jenisProdukButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        jenisProdukButton.setForeground(new java.awt.Color(255, 255, 255));
        jenisProdukButton.setText("Jenis");
        jenisProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jenisProdukButtonActionPerformed(evt);
            }
        });

        tglKadaluarsaDateChooser.setBackground(new java.awt.Color(255, 255, 255));
        tglKadaluarsaDateChooser.setForeground(new java.awt.Color(0, 0, 0));
        tglKadaluarsaDateChooser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tglKadaluarsaDateChooserMouseClicked(evt);
            }
        });

        tglKadaluarsaInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        tglKadaluarsaInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        tglKadaluarsaInputLabel.setText("Tanggal Kadaluarsa");

        specialAttributeInputLabel.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        specialAttributeInputLabel.setForeground(new java.awt.Color(255, 255, 255));
        specialAttributeInputLabel.setText("Special Attribute");

        specialAttributeInputTextField.setBackground(new java.awt.Color(255, 255, 255));
        specialAttributeInputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        specialAttributeInputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specialAttributeInputTextFieldActionPerformed(evt);
            }
        });

        ketentuanResepLabel.setFont(new java.awt.Font("Arial Black", 2, 12)); // NOI18N
        ketentuanResepLabel.setForeground(new java.awt.Color(255, 255, 255));
        ketentuanResepLabel.setText("* Jenis Resep untuk Obat hanya bisa \"Wajib\" atau \"Bebas\"");

        simpanProdukButton.setBackground(new java.awt.Color(24, 170, 176));
        simpanProdukButton.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        simpanProdukButton.setForeground(new java.awt.Color(255, 255, 255));
        simpanProdukButton.setText("Simpan");
        simpanProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanProdukButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout produkFormPanelLayout = new javax.swing.GroupLayout(produkFormPanel);
        produkFormPanel.setLayout(produkFormPanelLayout);
        produkFormPanelLayout.setHorizontalGroup(
            produkFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produkFormPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(idProdukLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idProdukTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jenisProdukLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jenisProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(produkFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(simpanProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglKadaluarsaInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tglKadaluarsaDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specialAttributeInputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specialAttributeInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ketentuanResepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(475, 475, 475))
            .addGroup(produkFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(produkFormPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(produkFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(stokInputPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(namaProdukPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hargaInputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(918, Short.MAX_VALUE)))
        );
        produkFormPanelLayout.setVerticalGroup(
            produkFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produkFormPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(specialAttributeInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specialAttributeInputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ketentuanResepLabel)
                .addGap(33, 33, 33)
                .addComponent(tglKadaluarsaInputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tglKadaluarsaDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(produkFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idProdukLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idProdukTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jenisProdukLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jenisProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simpanProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(produkFormPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(produkFormPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(namaProdukPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(stokInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(hargaInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(58, Short.MAX_VALUE)))
        );

        produkButtonPanel.setBackground(new java.awt.Color(134, 196, 66));

        tambahProdukButton.setBackground(new java.awt.Color(24, 170, 176));
        tambahProdukButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        tambahProdukButton.setForeground(new java.awt.Color(255, 255, 255));
        tambahProdukButton.setText("Tambah");
        tambahProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahProdukButtonActionPerformed(evt);
            }
        });

        barukanProdukButton.setBackground(new java.awt.Color(24, 170, 176));
        barukanProdukButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        barukanProdukButton.setForeground(new java.awt.Color(255, 255, 255));
        barukanProdukButton.setText("Edit");
        barukanProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barukanProdukButtonActionPerformed(evt);
            }
        });

        hapusProdukButton.setBackground(new java.awt.Color(24, 170, 176));
        hapusProdukButton.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        hapusProdukButton.setForeground(new java.awt.Color(255, 255, 255));
        hapusProdukButton.setText("Hapus");
        hapusProdukButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusProdukButtonActionPerformed(evt);
            }
        });

        produkIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Produk.png"))); // NOI18N

        javax.swing.GroupLayout produkButtonPanelLayout = new javax.swing.GroupLayout(produkButtonPanel);
        produkButtonPanel.setLayout(produkButtonPanelLayout);
        produkButtonPanelLayout.setHorizontalGroup(
            produkButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkButtonPanelLayout.createSequentialGroup()
                .addComponent(tambahProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(barukanProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hapusProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(produkIcon)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        produkButtonPanelLayout.setVerticalGroup(
            produkButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(produkButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(barukanProdukButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahProdukButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hapusProdukButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(produkButtonPanelLayout.createSequentialGroup()
                .addComponent(produkIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        obatScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        obatTextArea.setEditable(false);
        obatTextArea.setBackground(new java.awt.Color(255, 255, 255));
        obatTextArea.setColumns(20);
        obatTextArea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        obatTextArea.setForeground(new java.awt.Color(0, 0, 0));
        obatTextArea.setRows(5);
        obatTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        obatScrollPane.setViewportView(obatTextArea);

        vitaminScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        vitaminScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        vitaminTextArea.setBackground(new java.awt.Color(255, 255, 255));
        vitaminTextArea.setColumns(20);
        vitaminTextArea.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        vitaminTextArea.setForeground(new java.awt.Color(0, 0, 0));
        vitaminTextArea.setRows(5);
        vitaminScrollPane.setViewportView(vitaminTextArea);

        produkLabel.setFont(new java.awt.Font("Arial Black", 1, 80)); // NOI18N
        produkLabel.setForeground(new java.awt.Color(255, 255, 255));
        produkLabel.setText("PRODUK");

        javax.swing.GroupLayout produkPanelLayout = new javax.swing.GroupLayout(produkPanel);
        produkPanel.setLayout(produkPanelLayout);
        produkPanelLayout.setHorizontalGroup(
            produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkPanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addComponent(obatScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(vitaminScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(produkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(produkButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchProdukInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(produkFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        produkPanelLayout.setVerticalGroup(
            produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produkPanelLayout.createSequentialGroup()
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produkPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(produkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(produkPanelLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(searchProdukInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(produkButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(produkFormPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(produkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(obatScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vitaminScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(181, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout coverPanelLayout = new javax.swing.GroupLayout(coverPanel);
        coverPanel.setLayout(coverPanelLayout);
        coverPanelLayout.setHorizontalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1513, Short.MAX_VALUE)
            .addGroup(coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(produkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        coverPanelLayout.setVerticalGroup(
            coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
            .addGroup(coverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(produkPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1513, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(coverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 945, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(coverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchProdukInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchProdukInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchProdukInputTextFieldActionPerformed

    private void searchProdukInputTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchProdukInputTextFieldKeyTyped

    }//GEN-LAST:event_searchProdukInputTextFieldKeyTyped

    private void searchProdukInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchProdukInputButtonActionPerformed
        doSearchProduk();
    }//GEN-LAST:event_searchProdukInputButtonActionPerformed

    private void namaProdukInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaProdukInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaProdukInputTextFieldActionPerformed

    private void hargaInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hargaInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hargaInputTextFieldActionPerformed

    private void hargaInputTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaInputTextFieldKeyTyped

    }//GEN-LAST:event_hargaInputTextFieldKeyTyped

    private void simpanProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanProdukButtonActionPerformed
        if(actionProduk == null)
            return;
        try{
            inputKosongProdukException();
            invalidHargaProdukException();
            invalidStokProdukException();
            invalidJenisResepException();
            isNotIntegerException();
            
            int dialog = JOptionPane.showConfirmDialog(rootPane, "yakin ingin melakukan " + actionProduk + "?");
            if(dialog != JOptionPane.YES_OPTION){
                return;
            }
            
            java.util.Date utilDate = tglKadaluarsaDateChooser.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            java.time.LocalDate tglKadaluarsa = sqlDate.toLocalDate();
                
            switch (actionProduk){
                case "add":
                    String newIdProduk = pc.generateIdProduk(); // Generate ID di sini
                    if(obatIsSelected()){
                        o = new Obat(newIdProduk, // <<< ID PRODUK SEKARANG DITERUSKAN DI KONSTRUKTOR
                                     namaProdukInputTextField.getText(),
                                     jenisProdukButton.getText(), // "Obat"
                                     Integer.parseInt(stokInputTextField.getText()),
                                     Integer.parseInt(hargaInputTextField.getText()),
                                     tglKadaluarsa,
                                     specialAttributeInputTextField.getText()); // jenis_resep
                        oc.insertDataObat(o);
                    }else{ // Vitamin
                        v = new Vitamin(newIdProduk, // <<< ID PRODUK SEKARANG DITERUSKAN DI KONSTRUKTOR
                                      namaProdukInputTextField.getText(),
                                      jenisProdukButton.getText(), // "Vitamin"
                                      Integer.parseInt(stokInputTextField.getText()),
                                      Integer.parseInt(hargaInputTextField.getText()),
                                      tglKadaluarsa,
                                      specialAttributeInputTextField.getText()); // cara_konsumsi
                        vc.insertDataVitamin(v);
                    }
                    break;
                case "update":
                    String idProdukToUpdate = idProdukTextField.getText();
                    
                    if(obatIsSelected()){
                        o = new Obat(idProdukToUpdate, // <<< ID PRODUK DARI TEXT FIELD UNTUK UPDATE
                                     namaProdukInputTextField.getText(),
                                     jenisProdukButton.getText(), // "Obat"
                                     Integer.parseInt(stokInputTextField.getText()),
                                     Integer.parseInt(hargaInputTextField.getText()),
                                     tglKadaluarsa,
                                     specialAttributeInputTextField.getText()); // jenis_resep
                        oc.updateDataObat(o, idProdukToUpdate, specialAttributeInputTextField.getText()); // Meneruskan idProdukToUpdate dan jenis_resep_baru
                    }else{ // Vitamin
                        v = new Vitamin(idProdukToUpdate, // <<< ID PRODUK DARI TEXT FIELD UNTUK UPDATE
                                      namaProdukInputTextField.getText(),
                                      jenisProdukButton.getText(), // "Vitamin"
                                      Integer.parseInt(stokInputTextField.getText()),
                                      Integer.parseInt(hargaInputTextField.getText()),
                                      tglKadaluarsa,
                                      specialAttributeInputTextField.getText()); // cara_konsumsi
                        vc.updateDataVitamin(v, idProdukToUpdate, specialAttributeInputTextField.getText()); // Meneruskan idProdukToUpdate dan cara_konsumsi_baru
                    }
                    break;
                default:
                    break;
            }
            clearText();
            setProdukEditDeleteButton(false);
            setComponentsProduk(false);
            showProduk(); // Refresh tampilan setelah update
            actionProduk = null;
        }catch(InputKosongException e1){
            JOptionPane.showMessageDialog(rootPane, e1.toString());
        }catch(InvalidHargaException e2){
            JOptionPane.showMessageDialog(rootPane, e2.toString());
        }catch(StokKurangException e3){
            JOptionPane.showMessageDialog(rootPane, e3.toString());
        }catch(JenisResepException e4){
            JOptionPane.showMessageDialog(rootPane, e4.toString());
        }catch(NumberFormatException e5){
            JOptionPane.showMessageDialog(rootPane, "Input Stok dan Harga, Harus Berupa Angka!");
        }
    }//GEN-LAST:event_simpanProdukButtonActionPerformed

    private void tambahProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahProdukButtonActionPerformed
        actionProduk = "add";
        clearText();
        setProdukEditDeleteButton(false);
        setComponentsProduk(true);
        setProdukEditDeleteButton(false);
        
        idProdukTextField.setEnabled(false);
        idProdukTextField.setText(pc.generateIdProduk());
        jenisProdukButton.setText("Obat");
        setSpecialAttributeLabel();
    }//GEN-LAST:event_tambahProdukButtonActionPerformed

    private void barukanProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barukanProdukButtonActionPerformed
        actionProduk = "update";
        setComponentsProduk(true);
        idProdukTextField.setEnabled(false);
    }//GEN-LAST:event_barukanProdukButtonActionPerformed

    private void hapusProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusProdukButtonActionPerformed
        int opsi = JOptionPane.showConfirmDialog(rootPane, "Yakin Ingin Hapus ?", "Hapus Data", JOptionPane.YES_NO_OPTION);
        if( opsi == JOptionPane.NO_OPTION || opsi == JOptionPane.CLOSED_OPTION)
            return;
        
        pc.deleteDataProduk(idProdukTextField.getText());
        clearText();
        setProdukEditDeleteButton(false);
        setComponentsProduk(false);
        showProduk();
    }//GEN-LAST:event_hapusProdukButtonActionPerformed

    private void idProdukTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idProdukTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idProdukTextFieldActionPerformed

    private void idProdukTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idProdukTextFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_idProdukTextFieldKeyTyped

    private void jenisProdukButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisProdukButtonActionPerformed
        switch(jenisProdukButton.getText()){
            case "Obat": jenisProdukButton.setText("Vitamin"); break;
            case "Vitamin": jenisProdukButton.setText("Obat"); break;
        }
        setSpecialAttributeLabel();
    }//GEN-LAST:event_jenisProdukButtonActionPerformed

    private void tglKadaluarsaDateChooserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tglKadaluarsaDateChooserMouseClicked

    }//GEN-LAST:event_tglKadaluarsaDateChooserMouseClicked

    private void specialAttributeInputTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specialAttributeInputTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_specialAttributeInputTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton barukanProdukButton;
    private javax.swing.JPanel coverPanel;
    private javax.swing.JButton hapusProdukButton;
    private javax.swing.JLabel hargaInputLabel;
    private javax.swing.JPanel hargaInputPanel;
    private javax.swing.JTextField hargaInputTextField;
    private javax.swing.JLabel idProdukLabel;
    private javax.swing.JTextField idProdukTextField;
    private javax.swing.JButton jenisProdukButton;
    private javax.swing.JLabel jenisProdukLabel;
    private javax.swing.JLabel ketentuanResepLabel;
    private javax.swing.JLabel namaProdukInputLabel;
    private javax.swing.JTextField namaProdukInputTextField;
    private javax.swing.JPanel namaProdukPanel;
    private javax.swing.JScrollPane obatScrollPane;
    private javax.swing.JTextArea obatTextArea;
    private javax.swing.JPanel produkButtonPanel;
    private javax.swing.JPanel produkFormPanel;
    private javax.swing.JLabel produkIcon;
    private javax.swing.JLabel produkLabel;
    private javax.swing.JPanel produkPanel;
    private javax.swing.JButton searchProdukInputButton;
    private javax.swing.JLabel searchProdukInputLabel;
    private javax.swing.JPanel searchProdukInputPanel;
    private javax.swing.JTextField searchProdukInputTextField;
    private javax.swing.JButton simpanProdukButton;
    private javax.swing.JLabel specialAttributeInputLabel;
    private javax.swing.JTextField specialAttributeInputTextField;
    private javax.swing.JLabel stokInputLabel;
    private javax.swing.JPanel stokInputPanel;
    private javax.swing.JTextField stokInputTextField;
    private javax.swing.JButton tambahProdukButton;
    private com.toedter.calendar.JDateChooser tglKadaluarsaDateChooser;
    private javax.swing.JLabel tglKadaluarsaInputLabel;
    private javax.swing.JScrollPane vitaminScrollPane;
    private javax.swing.JTextArea vitaminTextArea;
    // End of variables declaration//GEN-END:variables
}
