import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MarupekeGrid {
    private MarupekeTile[][] grid = new MarupekeTile[10][10];
    List<Reason> illigalitiesInGrid;

    public MarupekeGrid(int width, int length) {
        if (width > 10) {
            width = 10;
        }
        if (length > 10) {
            length = 10;
        }
        if (width < 3) {
            width = 3;
        }
        if (length < 3) {
            length = 3;
        }
        this.grid = new MarupekeTile[width][length];

        for (int i = 0; i < width ; i++) {
            for (int j = 0; j < length; j++) {
                this.grid[i][j] = new MarupekeTile();
                this.grid[i][j].setEditable(true);
                this.grid[i][j].setTile(Tile.BLANK);
            }
        }

    }

    public boolean setSolid(int x, int y) {
        if (this.grid[x][y].getEditable() == false) {
            return false;
        } else {
            this.grid[x][y].setTile(Tile.SOLID);
            this.grid[x][y].setEditable(false);
            return true;
        }
    }

    public boolean setX(int x, int y, boolean canEdit) {
        if (this.grid[x][y].getEditable() == true) {
            this.grid[x][y].setTile(Tile.X);
            this.grid[x][y].setEditable(canEdit);
            return true;
        } else {
            return false;
        }
    }

    public boolean setO(int x, int y, boolean canEdit) {
        if (this.grid[x][y].getEditable() == true) {
            this.grid[x][y].setTile(Tile.O);
            this.grid[x][y].setEditable(canEdit);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        String result = new String();
        for (int i = 0; i < getGridW(); i++) {
            for (int j = 0; j < getGridL(); j++) {
                result += this.grid[i][j].getTile();
            }
            result += "\n";
        }
        return result;
    }

    public static MarupekeGrid randomPuzzle(int size, int numFill, int numX, int numO) throws TooManyMarkedSquares {
        if (numFill + numX + numO > (size * size)/2) {
            throw new TooManyMarkedSquares("illegal puzzle");
        }

        MarupekeGrid g = new MarupekeGrid(size, size);

        Random r = new Random();
        //sets Xs randomly in grid
        for (int i = 0; i < numX; i++) {
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            if (g.setX(x, y, false) == true) {
                g.setX(x, y, false);
            }
            //if the tile was was not editable then take one of increment otherwise there wouldn't be enough Xs
            else { i--; }
        }
        //sets Os randomly in grid
        for (int i = 0; i < numO; i++) {
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            if (g.setO(x, y, false) == true) {
                g.setO(x, y, false);
            }
            else { i--; }
        }
        //sets solids randomly in grid
        for (int i = 0; i < numFill; i++) {
            int x = r.nextInt(size);
            int y = r.nextInt(size);
            if (g.setSolid(x, y) == true) {
                g.setSolid(x, y);
            }
            else { i--; }
        }
        if (g.isLegalGrid()) {
            return g;
        } else {
            randomPuzzle(size,numFill,numX,numO);
        }
        return null;
    }

    public void markX(int x, int y) {
        if (this.grid[x][y].getEditable() == true) {
            this.grid[x][y].setTile(Tile.X);
            this.grid[x][y].setEditable(true);
        }

    }
    public void markO(int x, int y) {
        if (this.grid[x][y].getEditable() == true) {
            this.grid[x][y].setTile(Tile.O);
            this.grid[x][y].setEditable(true);
        }

    }

    public boolean unmark(int x, int y) {
        if(this.grid[x][y].getEditable() == true) {
            this.grid[x][y].setTile(Tile.BLANK);
            this.grid[x][y].setEditable(true);
            return true;
        }
        return false;
    }

    public boolean isPuzzleComplete() {
        int tileCount = 0;
        for (int i = 0; i < getGridW(); i++) {
            for (int j = 0; j < getGridL(); j++) {
                if (this.grid[i][j].getTile() != '_') {
                    tileCount++;
                }
            }
        }
        if (getGridW()*getGridL() == tileCount && isLegalGrid() == true) {
            return true;
        }
        else{ return false; }
    }

    public boolean isLegalGrid() {
        List<Reason> illigalitiesInGrid = new ArrayList<Reason>();
        boolean errors = false;
        char[][] c = new char[getGridW()+4][getGridL()+4];
        for (char[] tmp : c) {
            Arrays.fill(tmp, '_');
        }
        for (int i = 2; i < getGridW()+2; i++) {
            for (int j = 2; j < getGridL()+2; j++) {
                c[i][j] = this.grid[i-2][j-2].getTile();
            }
        }
        for (int i = 2; i < getGridW()+2; i++) {
            for (int j = 2; j < getGridL()+2; j++) {

                //check horizontal
                if (c[i][j] != '_' && c[i][j] == c[i][j+1] && c[i][j] == c[i][j+2]) {

                    illigalitiesInGrid.add(new Horizontal(c[i][j],i-2,j-2));

                    errors = true;
                }

                //check vertical

                if (c[i][j] != '_' && c[i][j] == c[i+1][j] && c[i][j] == c[i+2][j]) {
                    illigalitiesInGrid.add(new Vertical(c[i][j],i-2,j-2));

                    errors = true;
                }

                //check diag
                else if (c[i][j] != '_' && c[i][j] == c[i+1][j+1] && c[i][j] == c[i+2][j+2]) {
                    illigalitiesInGrid.add(new Diagonal(c[i][j],i-2,j-2));

                    errors = true;
                }
                //check counter diag


                if (c[i][j] != '_' && c[i][j] == c[i + 1][j - 1] && c[i][j] == c[i + 2][j - 2]) {
                    illigalitiesInGrid.add(new Diagonal(c[i][j],i-2,j-2));

                    errors = true;
                }

            }
        }
        if (errors == true) {
            System.out.println(illigalitiesInGrid.toString());
            return false;
        }
        else{
            return true;
        }

    }

    public String getIlligalitiesInGrid() {
        return illigalitiesInGrid.toString();
    }

    public int getGridW() {
        return this.grid.length;
    }
    public int getGridL() {
        return this.grid[0].length;
    }


    public boolean[][] getEditable() {
        boolean editGrid[][] = new boolean[getGridW()][getGridL()];
        for (int i = 0; i < getGridW(); i++) {
            for (int j = 0; j < getGridL(); j++) {
                editGrid[i][j] = this.grid[i][j].getEditable();

            }
        }
        return editGrid;
    }

    public boolean getEditablePart(int x, int y) {
        return this.grid[x][y].getEditable();
    }

    public char[][] getGrid() {
        char[][] temp = new char[getGridW()][getGridL()];
        for (int i = 0; i < getGridW(); i++) {
            for (int j = 0; j < getGridL(); j++) {
                temp[i][j] = this.grid[i][j].getTile();
            }
        }
        return temp;
    }

}
