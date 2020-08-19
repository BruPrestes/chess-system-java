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
   
    /*
    EUREKA
    vai retornar diretamente do que for definido como linha e coluna independente da posição
    */
    
    public Piece piece(int row, int column){
        return pieces[row][column];
    }
    
    //vai retornar a peça pela posição da matriz diretamente do position
    public Piece piece(Position position){
        return pieces[position.getRow()][position.getColumn()];
    }
    
    
    
    public void placePiece(Piece piece, Position position){
        /*
        Dado a matriz de peças, será atribuida suas posições
        */
        pieces[position.getRow()][position.getColumn()] = piece;
        
        piece.position = position;
/*piece.position irá dar qual a posição da peça de xadrez
        que foi atribuido no calculo acima.
*/
}
}
