public enum Tile {
    BLANK('_'), SOLID('#'), X('X'), O('O')
    ;
    private final char s;
    Tile(char s) {
        this.s = s;
    }
    public char toSting(){
        return s;
    }
    public String toString(){
        return String.valueOf(s);
    }
}
