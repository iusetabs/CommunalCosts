package customException;

/**
 * Created by C5228122 on 07/03/2018.
 */

public class CollectiveNotFoundException extends Exception {
    public CollectiveNotFoundException() {}

    public CollectiveNotFoundException(String message){
        super(message);
    }
    public CollectiveNotFoundException (Throwable cause) {
        super (cause);
    }

    public CollectiveNotFoundException (String message, Throwable cause) {
        super (message, cause);
    }

}
