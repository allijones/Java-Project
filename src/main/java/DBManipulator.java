/**
 * The DBManipulator class ...
 *
 * @author Alli Jones and Aidan Edwards
 *
 */

public class DBManipulator {
    protected DBInterface dbInterface;

    public DBManipulator(DBInterface dbInterface) {
        this.dbInterface = dbInterface;
    }

    public void setDBInterface(DBInterface dbInterface) {
        this.dbInterface = dbInterface;
    }
}
