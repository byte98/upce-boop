package cz.upce.fei.boop.lexikalnianalyzator.token;

import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenLongNumber extends Token {

    private final long value;

    public TokenLongNumber(long value) {
        super(TokenType.LONG_NUMBER);
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "LongNumberToken{" + "value=" + value + '}';
    }

}
