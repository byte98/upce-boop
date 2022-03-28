package cz.upce.fei.boop.lexikalnianalyzator.token;

import cz.upce.fei.boop.lexikalnianalyzator.token.enums.KeyWord;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenKey extends Token {

    private final KeyWord klicoveSlovo;

    public TokenKey(KeyWord key) {
        super(TokenType.KEY);
        klicoveSlovo = key;
    }

    public KeyWord getKeyWord() {
        return klicoveSlovo;
    }

    @Override
    public String toString() {
        return "KeyToken{" + "klicoveSlovo=" + klicoveSlovo.getName() + '}';
    }

}
