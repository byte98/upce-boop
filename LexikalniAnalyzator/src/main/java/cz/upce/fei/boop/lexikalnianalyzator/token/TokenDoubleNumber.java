package cz.upce.fei.boop.lexikalnianalyzator.token;

import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenDoubleNumber extends Token {

    private final double value;

    public TokenDoubleNumber(double value) {
        super(TokenType.LONG_NUMBER);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DoubleNumberToken{" + "value=" + value + '}';
    }

}
