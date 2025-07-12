package exception;

public class NotHansException extends Exception {
    private final char target;
    public NotHansException(char target) {
        super("The character " + target + "(U+"+Integer.toHexString((int)target)+") is not a Chinese Character!");
        this.target = target;
    }
    public char getTarget() {
        return target;
    }
}
