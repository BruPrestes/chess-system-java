package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author Bruno Prestes
 */
public class Rook extends ChessPiece{
    
    public Rook(Color color, Board board) {
        super(color, board);
    }

 

    @Override
    public String toString() {
        return "R";
    }

    //Ainda não tem linhas e colunas específicas no getBoard portanto vai dar tudo false no método
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
