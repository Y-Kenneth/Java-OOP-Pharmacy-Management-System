package exception;

public class NoTeleponException extends Exception{
    public NoTeleponException() {
        super("No Telepon hanya boleh terdiri dari Angka / Diawali '+'");
    }
}
