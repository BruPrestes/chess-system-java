package application;


import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.Scanner;

/**
 *
 * @author Bruno Prestes
 */
public class Program {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        
        while (true) {            
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.println("Source: ");
            ChessPosition source = UI.readChessPosition(sc);
            
            System.out.println();
            System.out.println("Target: ");
            ChessPosition target = UI.readChessPosition(sc);
            
            //ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }
        
   
        /*
        chessMatch.getPieces vai ser o i e j do printBoard
        8x8
        e o método print Board vai inicializar as variáveis 
        ou - ou a peça
        */
       

        
    }
}
