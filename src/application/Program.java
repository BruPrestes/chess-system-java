package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author Bruno Prestes
 */
public class Program {
    public static void main(String[] args) {
        
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
        
       

        
    }
}
