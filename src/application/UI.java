package application;

import chess.ChessPiece;

/**
 *
 * @author Bruno Prestes
 */
public class UI {
    
    /*
    Aqui eu importei o ChessPiece
    
    
    */
    //ChessPiece[][] em matriz já caiu na memória na outra classe, só ficar relax
    
    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i <pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }
 
    
    
    private static void printPiece(ChessPiece piece){
     //ele só vai saber se é nulo ou não chamando o chessMatch.getPieces
     //na inicialização da variável
        if (piece == null) {
            System.out.print("-");
        }
        else{
            System.out.print(piece);
        }
        System.out.print(" ");
    }
}
