package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

/**
 *
 * @author karel@simerda.cz
 */
public enum KeyWord {
    BEGIN("begin"),
    END("end"),
    FOR("for"),
    WHILE("while"),
    IF("if"),
    THEN("then"),
    ELSE("else"),
    RETURN("return"),
    NO_KEY("no key");

    private final String key;

    private KeyWord(String key) {
        this.key = key;
    }


    public String getName() {
        return key;
    }

    @Override
    public String toString() {
        return "KeyWord{key=" + key + '}';
    }

}
