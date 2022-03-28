package cz.upce.fei.boop.lexikalnianalyzator.collection;

import cz.upce.fei.boop.lexikalnianalyzator.token.Token;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author karel@simerda.cz
 */
public class TokenList implements Iterable<Token>{

    private Token[] list;
    private int kapacita;
    private int index;

    public TokenList() {
        nastav();
    }

    private void nastav() {
        kapacita = 10;
        this.list = new Token[kapacita];
        index = 0;
    }


    public void add(Token token) {
        Objects.requireNonNull(token);
        if (index >= kapacita) {
            kapacita = 2 * kapacita;
            Token[] newList = new Token[kapacita];
            System.arraycopy(list, 0, newList, 0, index);
            list = newList;
        }
        list[index++] = token;
    }


    public void clear() {
        nastav();
    }


    @Override
    public Iterator<Token> iterator() {
        return new Iterator() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < index;
            }

            @Override
            public Object next() {
                if (hasNext()) {
                    return list[i++];
                } else {
                    throw new NoSuchElementException();
                }
            }

        };
    }


    public Token getToken(int position) {
        if (position < index) {
            return list[position];
        }
        throw new ArrayIndexOutOfBoundsException();
    }


    public int getCapacity() {
        return kapacita;
    }


    public int getSize() {
        return index;
    }

}
