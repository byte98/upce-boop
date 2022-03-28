package cz.upce.fei.boop.lexikalnianalyzator.token.enums;

/**
 *
 * @author karel@simerda.cz
 */
public enum SeparatorEnum {
    WHITE_CHAR("bílý znak"),
    EQUALS("rovná se"),
    COMMA("čárka"),
    COLON("dvojtečka"),
    SEMICOLON("středník"),
    NONE("není");

    private final String nazev;

    private SeparatorEnum(String nazev) {
        this.nazev = nazev;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
        return "Separator{" + nazev + '}';
    }

}
