package io.bnguyen.vocare.io;

import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMmer
{
    public static String getElementTagValue(Element ele, String tagName)
    {
        //return ele.getElementsByTagName(tagName).item(0).getNodeValue();
        NodeList nl = ele.getElementsByTagName(tagName);
        Node n = nl.item(0);
        return n.getTextContent();
    }
    public static String[] getElementTagValues(Element ele, String tagName)
    {
        NodeList nodes = ele.getElementsByTagName(tagName);
        int length = nodes.getLength();
        String[] values = new String[length];
        for(int i = 0; i < length; i++)
        {
            values[i] = nodes.item(i).getTextContent();
        }
        return values;
    }
    public static void addChildElementValue(Document doc, Element parent, String tagName, String value)
    {
        Element child = doc.createElement(tagName);
        child.setTextContent(value);
        parent.appendChild(child);   
    }
    public static Element generateParentWithValue(Document doc, String tagName, String attrName, String attrValue)
    {
        Element parent = doc.createElement(tagName);
        parent.setAttribute(attrName, attrValue);
        return parent;
    }
    
    public static Element generateParentContainer(Document doc, String tagName, Collection<? extends DOMable> doms)
    {
        Element parent = doc.createElement(tagName);
        for( DOMable dom : doms)
        {
            parent.appendChild(dom.generateElement(doc));
        }
        return parent;
    }
    
    public static Node getNodeByName(Element ele, String name)
    {
        NodeList nl = ele.getChildNodes();
        for(int i = 0, length = nl.getLength(); i < length; i++)
        {
            if(nl.item(i).getNodeName().equals(name))
            {
                return nl.item(i);
            }
        }
        return null;
    }

}
