package exception;

public class JenisResepException extends Exception{
    public JenisResepException(){
        super("Jenis Resep hanya bisa 'Wajib' dan 'Bebas'");
    }
}
