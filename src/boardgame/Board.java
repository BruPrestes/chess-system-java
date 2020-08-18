package boardgame;

/**
 *
 * @author Bruno Prestes
 */
public class Board {
        
   private int rows;
   private int columns;
   private Piece[][] pieces;
   

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
        /*
        Já criei um construtor de matriz de peça 
        que terá sua linha e coluna, pois é assim que 
        ela irá se locomover
        ---------------------------------------
        também cada peça pertence a uma posição 
        do array
        ---------------------------------------
        */
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
   
    public Piece piece(int row, int column){
        return pieces[row][column];
    }
    
    //vai retornar a peça pela posição
    public Piece piece(Position position){
        return pieces[position.getRow()][position.getColumn()];
    }
    
}
