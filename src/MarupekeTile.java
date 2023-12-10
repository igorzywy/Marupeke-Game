public class MarupekeTile {
    private boolean editable;
    private Tile tile;

    public boolean getEditable(){
        return this.editable;
    }

    public char getTile(){
        return this.tile.toSting();
    }

    public String getTileString(){
        return this.tile.toString();
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
