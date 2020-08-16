package boardgame;

/**
 *
 * @author BrunoPrestes
 */
public class Piece {
    
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
        /*Só gerei o construtor do tabuleiro e não 
        porque a posição de uma peça recém criada
        é nula pois ela ainda não foi colocada no
        tabuleiro
        */
    }

    
    /*
    Somente classes dentro do mesmo pacote e
    subclasses poderão acessar o GetBoard
    */
    protected Board getBoard() {
        return board;
    }

    /*
    Tirei o get board porque não quero permitir que
    o tabuleiro seja alterado
    */
    
    
}
