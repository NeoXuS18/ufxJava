package application;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UFXReader {


    public static ArrayList<Classe> read() {
        ArrayList<Classe> elements = new ArrayList<>();
        ArrayList<Relation> relations = new ArrayList<>();


        try {

            File f = new File("C:\\Users\\17010-27-09\\Documents\\ProjetCDA\\Diagramme AfpaMazon.uxf");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();

            Document doc = dbBuilder.parse(f);

            doc.getDocumentElement().normalize();

            NodeList umlClass = doc.getElementsByTagName("element");

            for (int i = 0; i < umlClass.getLength(); i++) {
                Node node = umlClass.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
//                  Type of Element (Class, Relation ...)
                    String id = element.getElementsByTagName("id").item(0).getTextContent();

//                  Get all Information about Class or Relation
                    String panelAttribute = element.getElementsByTagName("panel_attributes").item(0).getTextContent();
//                  Delete all white space into information about Class or Relation
                    String panelWhitoutSpace = panelAttribute.replaceAll("\\s+", "");

//                   Create an array to get Title, Attribut , Methods
                    String[] panelSplit = panelWhitoutSpace.split("--");

                    switch (id) {
                        case "UMLClass" -> {
//                            Create a class
                            Classe classe = new Classe();
//                            Check if this class is abstract
                            if (panelSplit[0].contains("/")) {
//                              Replace "/" to ""
                                panelSplit[0] = panelSplit[0].replaceAll("/", "");
//                              Apply Abstract to stereotype at this class
                                classe.setStereotype("abstract");
                            } else {
                                classe.setStereotype("");
                            }
//                            Give name to this class
                            classe.setNom(panelSplit[0]);

//                            Check if class have attribut
                            if (panelSplit.length > 1) {
                                boolean isFinish = false;

                                while (!isFinish) {
                                    if (panelSplit[1].contains("-")) {
                                        String att;
//                                        att = [name, type of attribut]
                                        Attribut attribut = new Attribut();
                                        panelSplit[1] = panelSplit[1].substring(1);
                                        int index = panelSplit[1].indexOf("-");
                                        if (index != -1) {
                                            att = panelSplit[1].substring(0, index);
                                            panelSplit[1] = panelSplit[1].substring(index);
                                        } else {
                                            att = panelSplit[1];
                                        }

                                        String[] info = att.split(":");
                                        attribut.setVisibility("private");
                                        attribut.setNom(info[0]);
                                        attribut.setType(info[1]);

                                        classe.addAttribut(attribut);
                                    } else {
                                        isFinish = true;
                                    }
                                }
                                if (panelSplit.length > 2) {
                                    boolean isClose = false;
                                    while (!isClose) {
                                        if (panelSplit[2].contains("+")) {
                                            String meth;
//                                        meth = [name, return type of method]
                                            Method method = new Method();
                                            panelSplit[2] = panelSplit[2].substring(1);
                                            int index = panelSplit[2].indexOf("+");
                                            if (index != -1) {
                                                meth = panelSplit[2].substring(0, index);
                                                panelSplit[2] = panelSplit[2].substring(index);
                                            } else {
                                                meth = panelSplit[2];
                                            }


                                            String[] info = meth.split(":");
                                            method.setVisibility("public");
                                            method.setNom(info[0]);
                                            method.setTypeReturn(info[1]);
                                            method.setStatic(true);
                                            classe.addMethod(method);
                                        } else {
                                            isClose = true;
                                        }
                                    }
                                }

                            }
                            Coordinates coordinates = new Coordinates(Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("w").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("h").item(0).getTextContent()));
                            classe.setCoordinates(coordinates);
                            elements.add(classe);
                        }
                        case "Relation" -> {
                            Relation relation = new Relation();
                            Coordinates coordinate = new Coordinates(Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("w").item(0).getTextContent()), Double.parseDouble(element.getElementsByTagName("h").item(0).getTextContent()));
                            relation.setCoordinates(coordinate);
                            String panelAttributes = element.getElementsByTagName("panel_attributes").item(0).getTextContent().replaceAll("\\s+", "");
                            if (panelAttributes.contains("=<<-")) {
                                relation.setType("heritage");
                            } else if (panelAttributes.contains("=<<<<<-")) {
                                relation.setType("composition");
                            } else if (panelAttributes.contains("lt=<<<<-")) {
                                relation.setType("aggregation");
                            } else if (panelAttributes.contains("lt=-")) {
                                relation.setType("association");
                            }
                            if (panelAttributes.contains("m1") || panelAttributes.contains("m2")) {
                                System.out.println(panelAttributes);
                            }
                            relations.add(relation);
                        }
                    }
                }
            }
            checkAssociation(elements, relations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elements;

    }

    public static void checkAssociation(ArrayList<Classe> classes, ArrayList<Relation> relations){

        for (Relation relation : relations){
            System.out.println("Type : " + relation.getType());
        }
    }
}
