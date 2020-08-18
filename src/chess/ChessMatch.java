package chess;

import boardgame.Board;

/**
 *
 * @author Bruno Prestes
 */
public class ChessMatch {
    
    private Board board;
    
    public ChessMatch(){
        board = new Board(8, 8);
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
}
