package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
        
        Position p = new Position(0, 0);
        
        // above
        /*
        as posições iniciais são 0 portanto vou setar um valor para a variável p
        
        Reconhece que possible moves é um método das peças 
        portanto quando ele pedir no p(auxiliar) o p.getRow
        ele vai trazer o getRow da peça, pois em uma peça 
        quando instanciado o método ele irá trazer o getRow e getColumn da peça oras bolas 
        e o position é apenas um auxiliar para fazer a lógica
        */
        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow()-1);            
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // left
        
        p.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);            
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // right
        
        p.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);            
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // below
        
        p.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow()+1);            
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        
        
        return mat;
    }
}
