package cz.upce.fei.boop.u02klonovani.hluboce;

import cz.upce.fei.boop.u02klonovani.Vana;
import cz.upce.fei.boop.u02klonovani.Rozmer;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        Vana vana = new Vana("Ravak Classic", new Rozmer(2.5, 1.2, 0.5));
        KoupelnaHluboce koupelna1 = new KoupelnaHluboce("K1", new Rozmer(10, 10, 12), vana);
        KoupelnaHluboce koupelna2 = (KoupelnaHluboce) koupelna1.clone();
        System.out.println("" + koupelna1);
        System.out.println("" + koupelna2);

    }
}
