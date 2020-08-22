package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 *
 * @author Bruno Prestes
 */
public class ChessMatch {
    
    private Board board;
    
    public ChessMatch(){
        board = new Board(8, 8);
        initialSetup();
    }
    
    /*
    Retornar uma matriz de peça de xadrez 
    correspondente a essa partida
    */
    
    /*
    Criei uma peça de Xadrez que é independente da camada do tabuleiro percorri a matriz de posições e fiz um
    downcasting a peça de xadrez
    
    EUREKA
    
    a classe ChessPiece é publica então 
    posso fazer modificações nela nas classes do mesmo pacote
    
    como ela é do mesmo pacote eu não preciso importar
    ela apenas está sendo instanciada em outra classe do pacote 
    e está sendo instanciada como matriz 
    pois em uma partida de xadrez existem varias peças
    
    */
    /*
    getPieces é um método de ChessMatch
    chamei ele no programa principal e tenho que obter o retorno
    apesar do método chessPiece estar em ChessMatch ele é um método da classe 
    ChessPiece apenas declarei ele como um método em matriz 
    */
  
  
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i <board.getRows(); i++) {
            for (int j = 0; j <board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        //isso porque toPosition é uma posição de matriz
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece)capturedPiece;        
    }
    
    private Piece makeMove(Position source, Position target){
        //removi a peça comedora de seu local de origem
        Piece p = board.removePiece(source);
        //peça comida foi removida
        
        Piece capturedPiece = board.removePiece(target);
        //peça comedora está no local que estava a comida
        board.placePiece(p, target);
        //como o placePiece já foi instanciado 
        //capturePiece é uma peça e ela será retornada, peças tem posições 
        //ele retorna um capturedPiece porque ela é chamada assim que uma peça é capturada no Piece capturedPiece...
        
        //:::::::: esse return é apenas um parametro que nada mais do que 
        //:::::::: vai retornar o que já aconteceu 
        //:::::::: a peça comida ou n já era 
       
        return capturedPiece;
    }
    
    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
    }
    
    private void initialSetup(){
        /*
        Eu atribui a variável Board então chamei o método placePiece que será usado na camada de xadrez de acordo 
        com o ChessPiece
        Como é usado herança em ChessPiece então o ChessMatch acessa somente o que é usado no ChessPiece
        O position chamei direto da classe position
        EUREKA
        ::::: Abaixo estou inicializando o construtor de position que será o row e column do set e get position
        */
        placeNewPiece('c', 1, new Rook(Color.WHITE, board));
        placeNewPiece('c', 2, new Rook(Color.WHITE, board));
        placeNewPiece('d', 2, new Rook(Color.WHITE, board));
        placeNewPiece('e', 2, new Rook(Color.WHITE, board));
        placeNewPiece('e', 1, new Rook(Color.WHITE, board));
        placeNewPiece('d', 1, new King(Color.WHITE, board));

        placeNewPiece('c', 7, new Rook(Color.BLACK, board));
        placeNewPiece('c', 8, new Rook(Color.BLACK, board));
        placeNewPiece('d', 7, new Rook(Color.BLACK, board));
        placeNewPiece('e', 7, new Rook(Color.BLACK, board));
        placeNewPiece('e', 8, new Rook(Color.BLACK, board));
        placeNewPiece('d', 8, new King(Color.BLACK, board));
    }
}
