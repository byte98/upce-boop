package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

/**
 *
 * @author karel@simerda.cz
 */
public enum TokenType {
    KEY("klíčové slovo"),
    LONG_NUMBER("celé číslo"),
    DOUBLE_NUMBER("reálné číslo"),
    IDENTIFIER("identifikátor"),
    SEPARATOR("oddělovač"),
    OPERATOR("operátor"),
    NONE("");

    private final String nazev;

    private TokenType(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return "Token{" + nazev + '}';
    }

}
