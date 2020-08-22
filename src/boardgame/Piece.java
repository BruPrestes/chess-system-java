package boardgame;

/**
 *
 * @author BrunoPrestes
 */
public abstract class Piece {

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
    public abstract boolean[][] possibleMoves();

    //método concreto utilizando método abstrato
    //hook methods
    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }

        }
        return false;
    }
}
