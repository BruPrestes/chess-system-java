package application;

import chess.ChessMatch;

/**
 *
 * @author Bruno Prestes
 */
public class Program {
    public static void main(String[] args) {
        
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
   
        /*
        chessMatch.getPieces vai ser o i e j do printBoard
        8x8
        e o método print Board vai inicializar as variáveis 
        ou - ou a peça
        */
       

        
    }
}
