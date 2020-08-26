package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import java.security.InvalidParameterException;
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
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    public ChessPiece getPromoted() {
        return promoted;
    }
    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

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
        
        ChessPiece movedPiece = (ChessPiece)board.piece(target);
        
        //colocar antes do Check, pois pode ser que 
        //de acordo com a peça escolhida o adversário fique em Check
        
        // #specialmove promotion
        
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
               promoted = (ChessPiece)board.piece(target);
               //fiz um downcasting pois o Piece que chama o board e setei no tabuleiro a posição de destion como promoted
               promoted = replacePromotedPiece("Q");
            }
        }
        
        check = (testCheck(opponent(currentPlayer))) ? true : false;
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        }
        else {
        nextTurn();
        }
        
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }
        
        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type){
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }
        if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
            throw new InvalidParameterException("Invalid type for promotion");
        }
        
        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnTheBoard.remove(p);
        
        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, pos);
        //coloquei a new piece na posição da peça promovida que 
        //continua sendo o fim do tabuleiro
        piecesOnTheBoard.add(newPiece);
        
        return newPiece;
    }
    
    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("B")) return new Bishop(color, board);
        if (type.equals("N")) return new Knight(color, board);
        if (type.equals("Q")) return new Queen(color, board);
        return new Rook(color, board);
    }
    
    private Piece makeMove(Position source, Position target) {
        //removi a peça comedora de seu local de origem
        ChessPiece p = (ChessPiece)board.removePiece(source);
        p.increaseMoveCount();
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
        
        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
             Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
             Position targetT = new Position(source.getRow(), source.getColumn() + 1);
             ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
             board.placePiece(rook, targetT);
             rook.increaseMoveCount();
        }
        
        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
             Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
             Position targetT = new Position(source.getRow(), source.getColumn() - 1);
             ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
             board.placePiece(rook, targetT);
             rook.increaseMoveCount();
        }
        
        //#specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }
        
        return capturedPiece;
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece)board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
        
        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
             Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
             Position targetT = new Position(source.getRow(), source.getColumn() + 1);
             ChessPiece rook = (ChessPiece)board.removePiece(targetT);
             board.placePiece(rook, sourceT);
             rook.decreaseMoveCount();
        }
        
        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
             Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
             Position targetT = new Position(source.getRow(), source.getColumn() - 1);
             ChessPiece rook = (ChessPiece)board.removePiece(targetT);
             board.placePiece(rook, sourceT);
             rook.decreaseMoveCount();
        }
        
        //#specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                }
                else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn , pawnPosition);
                
                capturedPiece = board.removePiece(pawnPosition);
                
            }
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
        placeNewPiece('a', 1, new Rook(Color.WHITE, board));
        placeNewPiece('b', 1, new Knight(Color.WHITE, board));
        placeNewPiece('c', 1, new Bishop(Color.WHITE, board));
        placeNewPiece('d', 1, new Queen(Color.WHITE, board));
        placeNewPiece('e', 1, new King(Color.WHITE, board, this));
        placeNewPiece('f', 1, new Bishop(Color.WHITE, board));
        placeNewPiece('g', 1, new Knight(Color.WHITE, board));
        placeNewPiece('h', 1, new Rook(Color.WHITE, board));
        placeNewPiece('a', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('b', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('c', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('d', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('e', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('f', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('g', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('h', 2, new Pawn(Color.WHITE, board, this));

        placeNewPiece('a', 8, new Rook(Color.BLACK, board));
        placeNewPiece('b', 8, new Knight(Color.BLACK, board));
        placeNewPiece('c', 8, new Bishop(Color.BLACK, board));
        placeNewPiece('d', 8, new Queen(Color.BLACK, board));
        placeNewPiece('e', 8, new King(Color.BLACK, board, this));
        placeNewPiece('f', 8, new Bishop(Color.BLACK, board));
        placeNewPiece('g', 8, new Knight(Color.BLACK, board));
        placeNewPiece('h', 8, new Rook(Color.BLACK, board));
        placeNewPiece('a', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('b', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('c', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('d', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('e', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('f', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('g', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('h', 7, new Pawn(Color.BLACK, board, this));
    }
}
