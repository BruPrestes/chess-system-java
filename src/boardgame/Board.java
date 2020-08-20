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
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }
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

    

    public int getColumns() {
        return columns;
    }

    
   
    /*
    EUREKA
    vai retornar diretamente do que for definido como linha e coluna independente da posição
    */
    
    public Piece piece(int row, int column){
        if(!positionExists(row,column)){
            throw new BoardException("Position not on the board");
        }
        return pieces[row][column];
    }
    
    //vai chamar o método acima
    public Piece piece(Position position){
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        return pieces[position.getRow()][position.getColumn()];
    }
    
    
    
    public void placePiece(Piece piece, Position position){
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }
        /*
        Dado a matriz de peças, será atribuida suas posições
        */
        pieces[position.getRow()][position.getColumn()] = piece;
        //isso irá retornar o que vou setar na classe ChessMatch com as peças inicializando o construtor
        
        piece.position = position;
/*piece.position irá dar qual a posição da peça de xadrez
        que foi atribuido no calculo acima.
*/
       }
    /*Quando uma posição em uma dada linha e coluna existe
      É quando essa posição está dentro do tabuleiro
      Essa row e column vai ser o usuário que vai 
      solicitar ao sistema*/    
        private boolean positionExists(int row, int column){
          return row >= 0 && row < rows && column >= 0 && column < columns;
        }
    
        public boolean positionExists(Position position){
            return positionExists(position.getRow(), position.getColumn());
        }
    
        public boolean thereIsAPiece(Position position){
            if(!positionExists(position)){
            throw new BoardException("Position not on the board");
            }
            return piece(position) != null;
        }
}
