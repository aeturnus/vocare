package io.bnguyen.vocare.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.bnguyen.vocare.server.Database;

public interface DOMable
{
    public Element generateElement(Document doc);
    public void fromElement(Element ele, Database db);
}
