public class InsufficientChangeException extends Exception {
    public InsufficientChangeException(){
        super("Insufficient money in machine for change.");
    }
}