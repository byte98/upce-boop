package cz.upce.fei.boop.lexikalnianalyzator.token;

import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenIdentifier extends Token {

    private final String nazev;

    public TokenIdentifier(String name) {
        super(TokenType.IDENTIFIER);
        nazev = name;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
        return "IdentifierToken{" + nazev + '}';
    }

}
