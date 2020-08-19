package chess;

import boardgame.Board;
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
    
    private void initialSetup(){
        /*
        Eu atribui a variável Board então chamei o método placePiece que será usado na camada de xadrez de acordo 
        com o ChessPiece
        Como é usado herança em ChessPiece então o ChessMatch acessa somente o que é usado no ChessPiece
        O position chamei direto da classe position
        */
        board.placePiece(new Rook(Color.WHITE, board), new Position(2, 1));
        board.placePiece(new King(Color.BLACK, board), new Position(0, 4));
        board.placePiece(new King(Color.WHITE, board), new Position(7, 4));
    }
}
