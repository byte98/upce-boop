package cz.upce.fei.boop.lexikalnianalyzator.statemachine;

import cz.upce.fei.boop.lexikalnianalyzator.collection.TokenList;
import static cz.upce.fei.boop.lexikalnianalyzator.statemachine.State.*;
import cz.upce.fei.boop.lexikalnianalyzator.token.*;
import cz.upce.fei.boop.lexikalnianalyzator.token.enums.*;

/**
 *
 * @author karel@simerda.cz
 */
public class StateMachine implements Machine {

    private State state;
    private final StringBuffer identifier;
    private long integerPart;
    private long fractionPart;
    private boolean fraction;
    private long exponent;
    private long oder;
    private final TokenList tokenList;
    private int exponentSignum;
    private int counter;

    public StateMachine(TokenList list) {
        this.tokenList = list;
        this.state = STATE1;
        this.identifier = new StringBuffer();
    }

    @Override
    public void execute(char character) {

        switch (state) {

            case STATE1 -> {
                clear();

                if (isLetter(character)) {
                    execute(STATE2, character);
                } else if (character == '0') {
                    state = STATE3;
                } else if ('1' <= character && character <= '9') {
                    execute(STATE6, character);
                } else if (isSeparator(character)) {
                    execute(STATE13, character);
                } else if (isOperator(character)) {
                    execute(STATE14, character);
                } else if (character == '{') {
                    state = STATE15;
                } else {
                    throw new AnalyzerException(STATE1.name() + ": Nepovoleny znak '"
                            + character + "'/" + ((int) character));
                }
            }

            case STATE2 -> {
                if (isDigit(character) || isLetter(character)) {
                    identifier.append(character);
                } else if (isEndElement(character)) {
                    var ident = identifier.toString();
                    var key = findKey(ident);
                    if (key != null) {
                        tokenList.add(new TokenKey(key));
                    } else {
                        tokenList.add(new TokenIdentifier(ident));
                    }
                    execute(STATE10, character);
                } else { 
                    state = STATE1;
                    throw new AnalyzerException(STATE2.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE3 -> {
                if (isOctalDigit(character)) {
                    execute(State.STATE5, character);
                } else if (character == 'x') {
                    state = State.STATE4;
                } else if (character == '.') {
                    //fractionPart = 0;
                    fraction = true;
                    oder = 1;
                    counter++;
                    state = STATE7;
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE3.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE4 -> {
                if (isDigit(character) || isHexLetter(character)) {
                    character = Character.toLowerCase(character);
                    integerPart = 16L * integerPart;
                    if (isDigit(character)) {
                        integerPart += character - '0';
                    } else {
                        integerPart += character - 'a' + 10;
                    }
                } else if (isEndElement(character)) {
                    tokenList.add(new TokenLongNumber(integerPart));
                    execute(STATE10, character);
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE4.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE5 -> {
                if (isOctalDigit(character)) {
                    integerPart = 8L * integerPart + character - '0';
                } else if (isEndElement(character)) {
                    tokenList.add(new TokenLongNumber(integerPart));
                    execute(STATE10, character);
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE5.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE6 -> {
                if (isDigit(character)) {
                    integerPart = 10L * integerPart + character - '0';
                    counter++;
                } else if (character == '.') {
                    //fractionPart = 0;
                    fraction = true;
                    oder = 1;
                    state = STATE7;
                } else if (isExponentLetter(character)) {
                    oder = 1;
                    state = STATE9;
                } else if (isEndElement(character)) {
                    tokenList.add(new TokenLongNumber(integerPart));
                    execute(STATE10, character);
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE6.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE7 -> {
                if (isDigit(character)) {
                    fractionPart = 0;
                    oder = 1;
                    execute(State.STATE8, character);
                } else if (isExponentLetter(character)) {
                    oder = 1;
                    state = State.STATE9;
                } else if (isEndElement(character) && counter != 0) {
                    tokenList.add(new TokenDoubleNumber(integerPart));
                    execute(STATE10, character);
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE7.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE8 -> {
                if (isDigit(character)) {
                    fractionPart = 10L * fractionPart + character - '0';
                    oder *= 10L;
                } else if (isExponentLetter(character)) {
                    state = State.STATE9;
                } else if (isEndElement(character)) {
                    tokenList.add(new TokenDoubleNumber(integerPart + ((double) fractionPart) / oder));
                    execute(STATE10, character);
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE8.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE9 -> {
                exponentSignum = 1;
                if (isDigit(character)) {
                    execute(STATE11, character);
                } else if (character == '+') {
                    state = State.STATE11;
                } else if (character == '-') {
                    exponentSignum = -1;
                    state = State.STATE11;
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE9.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE10 -> {
                if (isSeparator(character)) {
                    execute(STATE13, character);
                } else if (isOperator(character)) {
                    execute(STATE14, character);
                } else { // nedosažitelný kód ?
                    state = STATE1;
                    throw new AnalyzerException(STATE10.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE11 -> {
                if (isDigit(character)) {
                    execute(STATE12, character);
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE11.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));

                }
            }

            case STATE12 -> {
                if (isDigit(character)) {
                    exponent = 10L * exponent + character - '0';

                } else if (isEndElement(character)) {
                    var exp = Math.pow(10, exponent);
                    var number = (integerPart + ((double) fractionPart) / oder);
                    number = (exponentSignum == 1) ? number * exp : number / exp;
                    tokenList.add(new TokenDoubleNumber(number));
                    execute(STATE10, character);
                } else {
                    state = STATE1;
                    throw new AnalyzerException(STATE12.name() + ": Nepovoleny znak "
                            + character + "'/" + ((int) character));
                }
            }

            case STATE13 -> {
                switch (character) {
                    case ' ', '\t', '\n', '\r' -> tokenList.add(new TokenSeparator(SeparatorEnum.WHITE_CHAR));
                    case ',' -> tokenList.add(new TokenSeparator(SeparatorEnum.COMMA));
                    case ':' -> tokenList.add(new TokenSeparator(SeparatorEnum.COLON));
                    case '=' -> tokenList.add(new TokenSeparator(SeparatorEnum.EQUALS));
                    case ';' -> tokenList.add(new TokenSeparator(SeparatorEnum.SEMICOLON));
                    default -> {
                        state = STATE1;
                        throw new AnalyzerException(STATE13.name() + ": Nepovoleny znak "
                                + character + "'/" + ((int) character));
                    }
                }
                state = STATE1;
            }

            case STATE14 -> {
                switch (character) {
                    case '+' -> tokenList.add(new TokenOperator(OperatorEnum.PLUS));
                    case '-' -> tokenList.add(new TokenOperator(OperatorEnum.MINUS));
                    case '*' -> tokenList.add(new TokenOperator(OperatorEnum.MULTIPLY));
                    case '/' -> tokenList.add(new TokenOperator(OperatorEnum.DIVIDE));
                    default -> {
                        state = STATE1;
                        throw new AnalyzerException(STATE14.name() + ": Nepovoleny znak "
                                + character + "'/" + ((int) character));
                    }
                }
                state = STATE1;
            }

            case STATE15 -> {
                if (character == '}') {
                    state = State.STATE1;
                }
            }
        }
    }

    @Override
    public State getState() {
        return state;
    }

    // =========================================================================
    private void clear() {
        identifier.setLength(0);
        integerPart = 0;
        exponent = 0;
        counter = 0;
        fraction = false;
    }

    private boolean isEndElement(char character) {
        return isSeparator(character) || isOperator(character);
    }

    private void execute(State state, char character) {
        this.state = state;
        execute(character);
    }

    private KeyWord findKey(String identifier) {
        for (KeyWord key : KeyWord.values()) {
            if (identifier.equals(key.getName())) {
                return key;
            }
        }
        return null;
    }

    private static boolean isLetter(char character) {
        return 'a' <= character && character <= 'z'
                || 'A' <= character && character <= 'Z';
    }

    private static boolean isHexLetter(char character) {
        return 'a' <= character && character <= 'f'
                || 'A' <= character && character <= 'F';
    }

    private static boolean isDigit(char character) {
        return '0' <= character && character <= '9';
    }

    private static boolean isOctalDigit(char character) {
        return '0' <= character && character <= '7';
    }

    private boolean isSeparator(char character) {
        return switch (character) {
            case ' ', '=', ',', ':', ';', '\t', '\n', '\r' ->  true;
            default -> false;
        };
    }

    private boolean isOperator(char character) {
        return switch (character) {
            case '+', '-', '*', '/' -> true;
            default -> false;
        };
    }

    private boolean isExponentLetter(char character) {
        return character == 'e' || character == 'E';
    }
}
