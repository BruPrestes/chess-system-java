package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

/**
 *
 * @author Bruno Prestes
 */
public abstract class ChessPiece extends Piece {

    private Board board;
    private Color color;

    public ChessPiece(Color color, Board board) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }
    
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}
