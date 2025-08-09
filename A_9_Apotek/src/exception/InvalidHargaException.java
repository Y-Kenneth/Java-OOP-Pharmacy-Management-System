package exception;

public class InvalidHargaException extends Exception{
    public InvalidHargaException(){
        super("Minimal Harga Produk adalah Rp 1.000");
    }
}

