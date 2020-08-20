package chess;

/**
 *
 * @author Bruno Prestes
 */
public class ChessException extends RuntimeException {    
    private static final long serialVersion = 1L;
    
    public ChessException(String msg){
        super(msg);
    }
}
