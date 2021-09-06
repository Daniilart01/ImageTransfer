package XML;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lombok.Getter;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadFromXML {

    @Getter
    List<Color> colors;

    @SneakyThrows
    public ReadFromXML(){
        String filepath = "Settings.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("Color");


        colors = getColors(nodeList.item(0));
    }

    private static List<Color> getColors(Node node) {
        List<Color> colors = new ArrayList<>();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            colors.add(new Color(Integer.parseInt(getTagValue("ColorLine", element))));
            if(Integer.parseInt(getTagValue("ColorFill", element))==0){
                colors.add(null);
            }
            else {
                colors.add(new Color(Integer.parseInt(getTagValue("ColorFill", element))));
            }
        }
        return colors;
    }


    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

}