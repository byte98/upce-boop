/*
 * Copyright (C) 2022 Jiri Skoda <jiri.skoda@student.upce.cz>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.upce.fei.boop.u02inicializacnibloky;

/**
 * Nova trida Main vychazejici z druheho to do
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class Main
{
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
