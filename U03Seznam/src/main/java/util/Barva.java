package util;


import java.awt.Color;

public enum Barva {

    BILA(Color.WHITE, "bílá"),
    CERNA(Color.BLACK, "černá"),
    CERVENA(Color.RED, "červená"),
    ZELENA(Color.GREEN, "zelená"),
    MODRA(Color.BLUE, "modrá");

    private final Color color;
    private final String nazev;

    private Barva(Color color, String cesky) {
        this.color = color;
        this.nazev = cesky;
    }

    public Color getColor() {
        return color;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
       return nazev;
    }
}
