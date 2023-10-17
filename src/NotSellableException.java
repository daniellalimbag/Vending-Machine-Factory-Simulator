public class NotSellableException extends Exception {
    public NotSellableException(){
        super("Item cannot be sold individually.");
    }
}