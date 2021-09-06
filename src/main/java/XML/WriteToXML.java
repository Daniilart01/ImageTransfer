package XML;

import java.awt.*;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WriteToXML {

    @SneakyThrows
    public WriteToXML(Color colorLine, Color colorFill){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement =
                    doc.createElementNS("https://https://bitbucket.org/DmitriyKinoshenko/nure-pzos/src/main/pzos/2-danylo.artomov/LB4", "Colors");
            doc.appendChild(rootElement);
            rootElement.appendChild(getColors(doc, colorLine, colorFill));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File("Settings.xml"));

            transformer.transform(source, file);

    }
    private static Node getColors(Document doc, Color colorLine, Color colorFill) {
        Element color = doc.createElement("Color");
        int str = colorLine.getRGB();
        color.appendChild(getColorsElements(doc, color, "ColorLine", Integer.toString(str)));
        if(colorFill != null) {
            str = colorFill.getRGB();
        }
        else{
            str = 0;
        }
        color.appendChild(getColorsElements(doc, color, "ColorFill", Integer.toString(str)));
        return color;
    }


    private static Node getColorsElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

}