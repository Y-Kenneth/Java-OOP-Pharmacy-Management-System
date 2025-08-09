package exception;

public class InputKosongException extends Exception{
    public InputKosongException() {
        super("FIELD INPUT TIDAK BOLEH KOSONG!");
    }
}
