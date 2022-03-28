package cz.upce.fei.boop.lexikalnianalyzator.token;

import cz.upce.fei.boop.lexikalnianalyzator.token.enums.SeparatorEnum;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenSeparator extends Token {

    private final SeparatorEnum sep;

    public TokenSeparator(SeparatorEnum separator) {
        super(TokenType.SEPARATOR);
        sep = separator;
    }

    public SeparatorEnum getSep() {
        return sep;
    }

    @Override
    public String toString() {
        return "SeparatorToken{" + sep.getNazev() + '}';
    }

}
