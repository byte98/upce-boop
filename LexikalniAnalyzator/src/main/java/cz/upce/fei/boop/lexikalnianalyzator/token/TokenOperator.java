package cz.upce.fei.boop.lexikalnianalyzator.token;

import cz.upce.fei.boop.lexikalnianalyzator.token.enums.OperatorEnum;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.TokenType;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenOperator extends Token {

    private final OperatorEnum operator;

    public TokenOperator(OperatorEnum operator) {
        super(TokenType.OPERATOR);
        this.operator = operator;
    }

    public OperatorEnum getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "OperatorToken{" + "operator=" + operator.getName()+ '}';
    }

}
