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
package cz.upce.fei.skoda.boop.pujcovnaguiskoda.perzistence;

import cz.upce.fei.skoda.boop.pujcovnaguiskoda.kolekce.LinkSeznam;
import cz.upce.fei.skoda.boop.pujcovnaguiskoda.kolekce.SpojovySeznam;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class which can read data from XML file
 * @author Jiri Skoda <jiri.skoda@student.upce.cz>
 */
public class XMLReader extends TextReader
{

    /**
     * Creates new object which can read data from XML file
     * @param path Path to XML file
     */
    public XMLReader(String path)
    {
        super(path);
        this.extensions = new String[]{"xml"};
    }

    @Override
    public SpojovySeznam<Mappable> loadData()
    {
        SpojovySeznam<Mappable> reti = new LinkSeznam<>();
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(this.readFile())));
            doc.getDocumentElement().normalize();
            NodeList rootList = doc.getElementsByTagName(XMLWriter.ROOT);
            NodeList list = rootList.item(0).getChildNodes();
            for(int i = 0; i < list.getLength(); i++)
            {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element elem = (Element) node;
                    reti.vlozPosledni(this.createObject(elem));
                }
            }
        }
        catch (ParserConfigurationException | IOException | SAXException ex)
        {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reti;

    }
    
    /**
     * Creates object from XML element
     * @param elem XML element from which object will be created
     * @return Object created from XML element
     */
    private Mappable createObject(Element elem)
    {
        Mappable reti = null;
        Map<String, String> data = new HashMap<>();
        NodeList list = elem.getChildNodes();
        for(int i = 0; i < list.getLength(); i++)
        {
            Node node = list.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element e = (Element) node;
                data.put(e.getNodeName(), e.getTextContent());
            }
        }
        try
        {
            Class<?> cls = Class.forName(elem.getNodeName());
            Constructor<?> ctor = cls.getConstructor(Map.class);
            reti = (Mappable)ctor.newInstance(new Object[]{data});
        }
        catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reti;
    
    }
}
