package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

/**
 *
 * @author karel@simerda.cz
 */
public enum OperatorEnum {
    PLUS("plus"),
    MINUS("minus"),
    MULTIPLY("nasobení"),
    DIVIDE("dělení"),
    NONE("není");

    private final String nazev;

    private OperatorEnum(String nazev) {
        this.nazev = nazev;
    }

    public String getName() {
        return nazev;
    }

    @Override
    public String toString() {
        return "Operator{" + nazev + '}';
    }

}
