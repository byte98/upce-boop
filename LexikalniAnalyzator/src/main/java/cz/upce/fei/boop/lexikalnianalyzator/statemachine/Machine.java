package cz.upce.fei.boop.lexikalnianalyzator.statemachine;

/**
 *
 * @author karel@simerda.cz
 */
public interface Machine {

    void execute(char character);

    default void execute(char... list) {
        for (char character : list) {
            execute(character);
        }
    }

    default void execute(String str) {
        execute(str.toCharArray());
    }

    State getState();

}
