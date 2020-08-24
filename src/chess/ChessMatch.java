package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Bruno Prestes
 */
public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();
    
    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean getCheck() {
        return check;
    }

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        check = false;
        //pois todo boolean que não foi atribuido nada sempre é 
        //falso
        initialSetup();
    }

    /*
    Retornar uma matriz de peça de xadrez 
    correspondente a essa partida
     */
 /*
    Criei uma peça de Xadrez que é independente da camada do tabuleiro percorri a matriz de posições e fiz um
    downcasting a peça de xadrez
    
    EUREKA
    
    a classe ChessPiece é publica então 
    posso fazer modificações nela nas classes do mesmo pacote
    
    como ela é do mesmo pacote eu não preciso importar
    ela apenas está sendo instanciada em outra classe do pacote 
    e está sendo instanciada como matriz 
    pois em uma partida de xadrez existem varias peças
    
     */
 /*
    getPieces é um método de ChessMatch
    chamei ele no programa principal e tenho que obter o retorno
    apesar do método chessPiece estar em ChessMatch ele é um método da classe 
    ChessPiece apenas declarei ele como um método em matriz 
     */
    
    public boolean getCheckMate() {
        return checkMate;
    }
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }
    
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        //isso porque toPosition é uma posição de matriz
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
            
        }
        
        check = (testCheck(opponent(currentPlayer))) ? true : false;
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        }
        else {
        nextTurn();
        }
        
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        //removi a peça comedora de seu local de origem
        Piece p = board.removePiece(source);
        //peça comida foi removida

        Piece capturedPiece = board.removePiece(target);
        //peça comedora está no local que estava a comida
        board.placePiece(p, target);
        
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        //como o placePiece já foi instanciado 
        //capturePiece é uma peça e ela será retornada, peças tem posições 
        //ele retorna um capturedPiece porque ela é chamada assim que uma peça é capturada no Piece capturedPiece...

        //:::::::: esse return é apenas um parametro que nada mais do que 
        //:::::::: vai retornar o que já aconteceu 
        //:::::::: a peça comida ou n já era 
        return capturedPiece;
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece p = board.removePiece(target);
        board.placePiece(p, source);
        
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }
    

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private void nextTurn() {
        turn++;
        //se o jogador atual for Color.WHITE então agora será Color.BLACK, caso contrário será o Color.WHITE
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    private ChessPiece king(Color color) {
    
    List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece)p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }
    
    
    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
       
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void initialSetup() {
        /*
        Eu atribui a variável Board então chamei o método placePiece que será usado na camada de xadrez de acordo 
        com o ChessPiece
        Como é usado herança em ChessPiece então o ChessMatch acessa somente o que é usado no ChessPiece
        O position chamei direto da classe position
        EUREKA
        ::::: Abaixo estou inicializando o construtor de position que será o row e column do set e get position
         */
        placeNewPiece('h', 7, new Rook(Color.WHITE, board));
        placeNewPiece('d', 1, new Rook(Color.WHITE, board));
        placeNewPiece('e', 1, new King(Color.WHITE, board));

        placeNewPiece('b', 8, new Rook(Color.BLACK, board));
        placeNewPiece('a', 8, new King(Color.BLACK, board));
    }
}
