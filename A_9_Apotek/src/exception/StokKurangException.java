package exception;

public class StokKurangException extends Exception{
    public StokKurangException(String message) {
        super(message);
    }
    
    // Exception ini digunakan untuk 2 kepentingan :
    // - Jika stok produk yang diinputkan < 1
    // - Jika pembelian produk melebihi sisa stok yang tersedia
}
