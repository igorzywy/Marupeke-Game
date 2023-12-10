public class Diagonal extends Reason{
    private int x, y;
    char aChar;
    public Diagonal(char c, int x, int y) {
        this.aChar = c;
        this.x = x + 1;
        this.y = y + 1;
    }
    @Override
    public String toString() {
        String reason = new String();
        reason = "Diagonal with a " + this.aChar + " at position " + this.y + " " + this.x;

        return reason;
    }
}
