public class Horizontal extends Reason {
    private int x, y;
    char aChar;
    public Horizontal(char c, int x, int y) {
        this.aChar = c;
        this.x = x + 1;
        this.y = y + 1;
    }
    @Override
    public String toString() {
        String reason = new String();
        reason = "Horizontal with a " + this.aChar + " at position " + this.y + " " + this.x;

        return reason;
    }
}
