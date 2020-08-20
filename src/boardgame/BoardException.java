package boardgame;

/**
 *
 * @author Bruno Prestes
 */
public class BoardException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public BoardException(String msg) {
        super(msg);
        //repassei para a superclasse o msg
    }
}
