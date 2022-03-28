package cz.upce.fei.boop.u02inicializacnibloky;

/**
 * Demonstrační úloha se statickými a instančními inicializačními bloky a v
 * kombinaci s konstruktory.
 *
 * Prostudujte výpis programu a přesvedčte se, jak probíhá řazení
 * inicializačních bloků a kostruktorů.
 *
 */
public class StaticDemoChild extends StaticDemo {

    private static int a;

    static { // Main::main (2)
        System.out.println("3. Zahajeni statickeho inicializacniho bloku 1 potomka\n"
                + "    a vlastne zahajeni statickeho konstruktoru potomka");

        /*  Při pokusu o výpis ještě nedeklarované proměnné b to překladač odhalí*/
        System.out.println("   Hodnota staticke promenne a pred nastavenim "
                + " a=" + a
        // +" b="  + b   // 
        );
        a = 1;
        /*  Následující nastavení proměnně b překladač neodhalí, 
            že je deklarována níže a dokonce provede přiřazení   */
        b = 2;
        System.out.println("    po nastaveni "
                + "a=" + a
        // + " b=" + b
        );
        System.out.println("4. Konec statickeho inicializacniho bloku 1 potomka");

    }

    private static int b;

    static { //Main::main (3)
        System.out.println("5. Zahajeni statickeho inicializacniho bloku 2 potomka ");
        System.out.println("    hodnota staticke promenne a pred nastavenim "
                + " a=" + a
                + " b=" + b);
        b = 3;
        System.out.println("    po nastaveni " + " a=" + a + " b=" + b);
        System.out.println("6. Konec statickeho inicializacniho bloku potomka");
        System.out.println("    a vlastne ukonceni statickeho kontruktoru.");
    }

    private String name;

    { //StaticDemoChild::main (3) Main::main (6)
        System.out.println("3. Zacatek instancniho inicializacniho bloku potomka");
        name = "aaa";
        System.out.println("   jmeno " + name);
        System.out.println("4. Konec instancniho inicializacniho bloku potomka");
    }

    /**
     * Konstruktor potomka se spustí až poslední. Nejdříve se vykonají postupně
     * instanční inicializační bloky předků, po nich jejich konstruktory a
     * teprve potom se vykonají všechny instačních bloky potomka a na konec
     * příjde na řadu provedení konstruktoru potomka. Konstruktory se sice
     * zahájí od potomka ke jeho předkům, ale vykonávají se v opačném pořadí. To
     * je zajištěno tím, že na začátku každého konstruktoru je vyvolán
     * konstruktor jeho předka. Každé vyvolání konstuktoru předka způsobí
     * uložení místa návratu do zásobníku a při ukončení vykonávání konstruktoru
     * předka se podle vrcholu zásobníku vrací řízení na další příkazy
     * konstruktoru potomka a to se opakuje rekurzivně podle toho, jak je
     * hluboký strom dědičnosti.
     *
     * @param name
     */
    public StaticDemoChild(String name) {//StaticDemoChild::main (5) Main::main(8)

        System.out.println("5. Instancni konstruktor potomka");
        this.name = name;
    }

    /* TODO Do tohoto místa vložte další instanční inicializační blok 
       podle vašeho výběru s kontrolním výpisem.
     */
    { //StaticDemoChild::main (4) Main::main(7)
        System.out.println(">> Dalsi instancni inicializacni blok");
    }
    public void display() {//StaticDemoChild::main (6) Main::main(9)
        System.out.println("   Metoda potomka " + name);
    }

    /**
     * TODO Tuto metodu main překopírujte do nově vytvořené třídy Main
     * Porovnejte po spuštění oba dva výpisy a zdůvodněte proč jsou rozdílné.
     * Odpověď můžete vyčíst z přednášek. Projekt se zdůvodněním a úpravami
     * uložte do své složky na SVN repozitory
     *
     * Zdrůvodnění rozdílů:
     * Funkce main v Main.java natažením třídy StaticDemoChild, která je potomkem
     * StaticDemo, tudíž se natáhne automaticky i tato třída, zavolá při vytváření
     * nové instance objektu statické inicializační bloky, a to v pořadí nejprve
     * předek a následně potomek.
     * Jelikož funkce main v StaticDemoChild.java žádnou třídu neimportuje,
     * žádné statické inicializační bloky nevolá.
     * Funkce main v StaticDemoChild.java voláním new vytváří instanci objektu
     * StaticDemoChild. To způsobí nejprve zavolání instančních inicializačních
     * bloků v předkovi, následuje konstruktor předka a poté obdobným způsobem
     * pokračuje v rámci své třídy (potomka).
     * Funkce main v Main.java postupuje obdobně, ale jelikož statické inicializační
     * bloky již byly zavolány, znovu je nevolá.
     * 
     */
    public static void main(String args[]) {
        System.out.println("Zahajeni metody main ");
        System.out.println("==================== ");
        System.out.println("Vytvoreni prvniho objektu obj1");
        StaticDemoChild obj1 = new StaticDemoChild("Objekt 1");
        obj1.display();
        System.out.println("\nVytvoreni druheho objektu obj2");
        StaticDemoChild obj2 = new StaticDemoChild("Objekt 2");
        obj2.display();
        System.out.println("Konec metody main");
        System.out.println("=================");

    }
}
