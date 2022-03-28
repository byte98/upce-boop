package cz.upce.fei.boop.lexikalnianalyzator;

import cz.upce.fei.boop.lexikalnianalyzator.collection.TokenList;
import cz.upce.fei.boop.lexikalnianalyzator.io.FileBufferedReader;
import cz.upce.fei.boop.lexikalnianalyzator.statemachine.AnalyzerException;
import cz.upce.fei.boop.lexikalnianalyzator.statemachine.Machine;
import cz.upce.fei.boop.lexikalnianalyzator.statemachine.StateMachine;
import cz.upce.fei.boop.lexikalnianalyzator.token.Token;
import java.io.IOException;

/**
 *
 * @author karel@simerda.cz
 */
public class LexikalniAnalyzatorMain {

   

    public static void main(String... args) throws IOException {

        if (args.length == 0) {
            System.out.println("Chybí vstupní argument se jménem souboru");
            return;
        }
        String fileName = args[0];

        var reader = FileBufferedReader.create(fileName);
        var tokenList = new TokenList();
        var stateMachine = new StateMachine(tokenList);
        while (!reader.isEOF()) {
            try {
                stateMachine.execute((char) reader.read());
            } catch (AnalyzerException ex) {
                System.out.println(ex.getMessage());
            }
        }
        for (Token token : tokenList) {
            System.out.println(token);
        }

    }
}
