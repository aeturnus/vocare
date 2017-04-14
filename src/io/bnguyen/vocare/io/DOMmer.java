package io.bnguyen.vocare.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMmer
{
    public static String getElementTagValue(Element ele, String tagName)
    {
        return ele.getElementsByTagName(tagName).item(0).getNodeValue();
    }
    public static String[] getElementTagValues(Element ele, String tagName)
    {
        NodeList nodes = ele.getElementsByTagName(tagName);
        int length = nodes.getLength();
        String[] values = new String[length];
        for(int i = 0; i < length; i++)
        {
            values[i] = nodes.item(i).getNodeValue();
        }
        return values;
    }
    public static void addChildElementValue(Document doc, Element parent, String tagName, String value)
    {
        Element child = doc.createElement(tagName);
        child.setNodeValue(value);
        parent.appendChild(child);   
    }
    public static Element generateParentWithValue(Document doc, String tagName, String attrName, String attrValue)
    {
        Element parent = doc.createElement(tagName);
        parent.setAttribute(attrName, attrValue);
        return parent;
    }

}
