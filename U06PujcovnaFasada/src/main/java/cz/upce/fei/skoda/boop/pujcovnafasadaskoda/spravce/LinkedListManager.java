//<editor-fold defaultstate="collapsed" desc="License">
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
//</editor-fold>
package cz.upce.fei.skoda.boop.pujcovnafasadaskoda.spravce;

import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.generator.BusGenerator;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.generator.Generator;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.generator.IGenerator;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.generator.TrainGenerator;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.kolekce.KolekceException;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.kolekce.LinkSeznam;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.kolekce.SpojovySeznam;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.BinaryReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.BinaryWriter;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.Mappable;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.TextReader;
import cz.upce.fei.skoda.boop.pujcovnafasadaskoda.perzistence.TextWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Class which can manage data stored in linked list
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class LinkedListManager implements IManager
{
    /**
     * List with data which will be managed
     */
    private SpojovySeznam<Mappable> data;
    
    /**
     * List with data selected by user
     */
    private List<Mappable> selectedData;
    
    /**
     * Array with all available generators
     */
    private static final IGenerator[] GENERATORS =
    {
        new Generator(),
        new BusGenerator(),
        new TrainGenerator()
    };

    /**
     * Reference to provider of random data
     */
    private final Random random;
    
    /**
     * Creates new manager of data stored in linked list
     */
    public LinkedListManager()
    {
        this.random = ThreadLocalRandom.current();
        this.selectedData = new ArrayList<>();
        this.data = new LinkSeznam<>();
    }
        
    //<editor-fold defaultstate="collapsed" desc="Implementation of interface IManager">
    @Override
    public int countElements()
    {
        return this.data.size();
    }

    @Override
    public int countSelectedElements()
    {
        return this.selectedData.size();
    }

    @Override
    public Mappable createNew()
    {
        Mappable reti = this.getGenerator().next();
        this.data.vlozPosledni(reti);
        return reti;
    }

    @Override
    public Mappable createNew(String dataType) throws IllegalArgumentException
    {
        Mappable reti = null;
        IGenerator gen = this.getGenerator(dataType);
        if (gen != null)
        {
            reti = gen.next();
            this.data.vlozPosledni(reti);
        }
        else
        {
            throw new IllegalArgumentException();
        }
        return reti;
    }

    @Override
    public Mappable createNew(String dataType, Consumer<Mappable> editor) throws IllegalArgumentException
    {
        Mappable reti = null;
        try
        {
            reti = this.createNew(dataType);
            editor.accept(reti);
        }
        catch(IllegalArgumentException ex)
        {
            throw ex;
        }
        return reti;
    }

    @Override
    public int deleteList()
    {
        int reti = this.countElements();
        this.selectedData.clear();
        this.data.zrus();
        return reti;
    }

    @Override
    public int deleteSelectedElements()
    {
        int reti = 0;
        if (this.data.jePrazdny() == false)
        {
            Mappable currentActual = null;
            try
            {
                currentActual = this.data.dejAktualni();
            } catch (KolekceException ex) {}
            try
            {
                this.data.nastavPrvni();
                boolean del = false;
                if (this.data.jeDalsi() == false && this.selectedData.contains(this.data.dejAktualni()))
                {
                    this.data.odeberAktualni();
                    reti++;
                }
                else
                {
                    while(this.data.jeDalsi())
                    {
                        if (del == true)
                        {
                            this.data.odeberPredAktualnim();
                            reti++;
                            del = false;
                        }
                        if (this.selectedData.contains(this.data.dejAktualni()))
                        {
                            del = true;
                        }
                        this.data.dalsi();
                    }
                }
                if (del == true)
                {
                    this.data.odeberPredAktualnim();
                    reti++;
                }
                if (currentActual != null)
                {
                    this.setAcutal(currentActual);
                }
            } catch (KolekceException ex) {}
        }
        this.selectedData.clear();
        return reti;
    }

    @Override
    public int editSelectedElements(Consumer<Mappable> editor)
    {
        int reti = 0;
        for (Mappable item: this.selectedData)
        {
            editor.accept(item);
            reti++;
        }
        return reti;
    }

    @Override
    public boolean first()
    {
        boolean reti = true;
        try
        {
            this.data.nastavPrvni();
        }
        catch (KolekceException ex)
        {
            reti = false;
        }
        return reti;
    }

    @Override
    public Mappable getActual()
    {
        Mappable reti = null;
        try
        {
            reti = this.data.dejAktualni();
        }
        catch(KolekceException ex)
        {
            reti = null;
        }
        return reti;
    }
    
    @Override
    public Mappable[] getAllData()
    {
        Mappable[] reti = new Mappable[this.data.size()];
        int idx = 0;
        Iterator<Mappable> it = this.data.iterator();
        while(it.hasNext())
        {
            reti[idx] = it.next();
            idx++;
        }
        return reti;
    }
    
    @Override
    public Mappable[] getSelectedData()
    {
        Mappable[] reti = new Mappable[this.selectedData.size()];
        int idx = 0;
        for(Mappable m: this.selectedData)
        {
            reti[idx] = m;
            idx++;
        }
        return reti;
        
    }
    
    @Override
    public boolean hasNext()
    {
        return this.data.jeDalsi();
    }
    
    @Override
    public boolean last()
    {
        boolean reti = true;
        try
        {
            this.data.nastavPosledni();
        }
        catch (KolekceException ex)
        {
            reti = false;
        }
        return reti;
    }

    @Override
    public void loadBinary(BinaryReader reader)
    {
        Function<SpojovySeznam<Serializable>, SpojovySeznam<Mappable>> retype = (t) ->
        {
            SpojovySeznam<Mappable> reti = new LinkSeznam<>();
            Iterator<Serializable> it = t.iterator();
            while (it.hasNext())
            {
                reti.vlozPosledni((Mappable) it.next());
            }
            return reti;
        };
        this.data = retype.apply(reader.loadData());
    }

    @Override
    public void loadText(TextReader reader)
    {
        this.data = reader.loadData();
    }

    @Override
    public boolean next()
    {
        boolean reti = true;
        try
        {
            this.data.dalsi();
        }
        catch(KolekceException ex)
        {
            reti = false;
        }
        return reti;
    }
    
    @Override
    public boolean previous()
    {
        boolean reti = true;
        int idx = this.hasPrevious();
        if (idx > 0)
        {
            try
            {
                this.data.nastavPrvni();
                for (int i = 0; i < idx; i++)
                {
                    this.data.dalsi();
                }
            }
            catch (KolekceException ex)
            {
                reti = false;
            }
        }
        return reti;
    }
    
    @Override
    public boolean remove() 
    {
        boolean reti = true;
        try
        {
            this.data.odeberAktualni();
        }
        catch (KolekceException ex)
        {
            reti = false;
        }
        return reti;
    }

    @Override
    public void saveBinary(BinaryWriter writer)
    {
        Function<SpojovySeznam<Mappable>, SpojovySeznam<Serializable>> retype = (t) ->
        {
            SpojovySeznam<Serializable> reti = new LinkSeznam<>();
            Iterator<Mappable> it = t.iterator();
            while (it.hasNext())
            {
                reti.vlozPosledni((Serializable) it.next());
            }
            return reti;
        };
        writer.writeData(retype.apply(this.data));
    }

    @Override
    public void saveText(TextWriter writer)
    {
        writer.writeData(this.data);
    }
    
    @Override
    public int selectElements(Predicate<Mappable> filter)
    {
        int reti = 0;
        this.selectedData.clear();
        Iterator<Mappable> it = this.data.iterator();
        while (it.hasNext())
        {
            Mappable item = it.next();
            if (filter.test(item))
            {
                this.selectedData.add(item);
                reti++;
            }
        }
        return reti;
    }
    //</editor-fold>
    
    /**
     * Gets random generator
     * @return Some random generator or NULL if there are no known generators
     */
    private IGenerator getGenerator()
    {
        IGenerator reti  = null;
        if (LinkedListManager.GENERATORS.length > 0) // Are there any gens?
        {
            reti = LinkedListManager.GENERATORS[
                    this.random.nextInt(0, LinkedListManager.GENERATORS.length)
                    ];
        }
        return reti;
    }
    
    /**
     * Gets generator by name of class which are generated by generator
     * @param className Name of class which instances generator generates
     * @return Generator of instances of defined class name or random
     *         generator if suitable one is not found
     */
    private IGenerator getGenerator(String className)
    {
        IGenerator reti = null;
        List<IGenerator> gens = new ArrayList<>();
        Arrays.stream(LinkedListManager.GENERATORS).filter((t) -> {
            return LinkedListManager.areClassNamesSame(
                    className, t.getGeneratedName()
            );
        }).forEach((t) -> {
                gens.add((IGenerator) t);
                });
        if (gens.size() > 0)
        {
            reti = gens.get(this.random.nextInt(0, gens.size()));
        }
        else
        {
            reti = this.getGenerator();
        }
        return reti;
    }
    
    /**
     * Cleans name of class from package names
     * @param cName Name of class which will be cleaned
     * @return Name of class without packages
     */
    private static String cleanClassName(String cName)
    {
        String reti = cName;
        if (cName.contains("."))
        {
            String parts[] = cName.split(Pattern.quote("."));
            if (parts.length > 1)
            {
                reti = parts[parts.length - 1];
            }
        }
        return reti;
    }
    
    /**
     * Checks, if two class names are same
     * @param cName1 Name of first class which will be compared
     * @param cName2 Name of second class which will be compared
     * @return TRUE if class names are some kind of same, FALSE otherwise
     */
    private static boolean areClassNamesSame(String cName1, String cName2)
    {
        return (LinkedListManager.cleanClassName(cName1).trim().toLowerCase().equals(
                LinkedListManager.cleanClassName(cName2).trim().toLowerCase()));
    }
    
    /**
     * Sets reference to actual by value
     * @param item Item which will be set as actual (if in list)
     */
    private void setAcutal(Mappable item)
    {
        if (this.data.jePrazdny() == false)
        {
            try
            {
                this.data.nastavPrvni();
                if (this.data.dejAktualni().equals(item) == false)
                {
                    while(this.data.jeDalsi())
                    {
                        this.data.dalsi();
                        if (this.data.dejAktualni().equals(item))
                        {
                            break;
                        }
                    }
                }
            }
            catch (KolekceException ex)
            {}
        }        
    }
    
    /**
     * Checks, whether there is some element before actual one
     * @return Negative value if there is no element before actual one
     *         or index of previous element
     */
    private int hasPrevious()
    {
        int reti = -1;
        Iterator<Mappable> it = this.data.iterator();
        boolean returnable = false;
        while (it.hasNext())
        {
            reti++;
            try
            {
                if (it.next().equals(this.data.dejAktualni()))
                {
                    if (reti == 0) // Actual is first one
                    {
                        reti = -1;
                        break;
                    }
                    else
                    {
                        reti--;
                        returnable = true;
                        break;
                    }
                }
            }
            catch (KolekceException ex)
            {
                reti = -1;
                break;
            }
        }
        if (returnable == false)
        {
            reti = -1;
        }
        return reti;
    }
}
