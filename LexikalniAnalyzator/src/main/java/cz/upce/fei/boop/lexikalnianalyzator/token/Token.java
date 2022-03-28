package cz.upce.fei.boop.lexikalnianalyzator.token;

import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;

/**
 *
 * @author karel@simerda.cz
 */
public abstract class Token {

    private final TokenType type;

    public Token(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    

}
