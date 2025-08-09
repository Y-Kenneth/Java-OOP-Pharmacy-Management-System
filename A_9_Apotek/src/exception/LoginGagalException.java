package exception;

public class LoginGagalException extends Exception{
    public LoginGagalException() {
        super("Username atau Password tidak sesuai. Silakan coba lagi.");
    }
}
