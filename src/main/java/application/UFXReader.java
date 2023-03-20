package application;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UFXReader {


    public static ArrayList<Classe> read() {
        ArrayList<Classe> elements = new ArrayList<>();
        ArrayList<Relation> relations = new ArrayList<>();


        try {
            File f = new File("C:\\Users\\Antonin\\Desktop\\Code\\CDA\\ufxJava\\src\\main\\resources\\application\\Diagramme Médiathèque sa mère.uxf");
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
                            } else {
                                relation.setType("comment");
                            }
                            if (panelAttributes.contains("m1") || panelAttributes.contains("m2")) {
                                int indexm1 = panelAttributes.indexOf("m1");
                                int indexm2 = panelAttributes.indexOf("m2");

                                if (indexm1 != -1) {
                                    String m1 = panelAttributes.substring(indexm1 + 3, indexm1 + 7);
                                    relation.addRoles("m1", m1);
                                }
                                if (indexm2 != -1) {
                                    String m2 = panelAttributes.substring(indexm2 + 3, indexm2 + 7);
                                    relation.addRoles("m2", m2);
                                }

                            }


                            String getAdditionnalAttributes = element.getElementsByTagName("additional_attributes").item(0).getTextContent();

                            String[] additionnalAttributesToArray = getAdditionnalAttributes.split(";");

                            RelationAttributes relationAttributes = new RelationAttributes(Double.parseDouble(additionnalAttributesToArray[0]), Double.parseDouble(additionnalAttributesToArray[1]), Double.parseDouble(additionnalAttributesToArray[additionnalAttributesToArray.length - 2]), Double.parseDouble(additionnalAttributesToArray[additionnalAttributesToArray.length - 1]));
                            relation.setRelationAttributes(relationAttributes);

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

    public static void checkAssociation(ArrayList<Classe> classes, ArrayList<Relation> relations) {
        ArrayList<Relation> associations = new ArrayList<>();

        for (Relation relation : relations) {
            for (Classe classe : classes) {
                if (classe.getCoordinates().getX1() <= (relation.getCoordinates().getX1() + relation.getRelationAttributes().getX1())) {
                    if ((relation.getCoordinates().getX1() + relation.getRelationAttributes().getX1()) <= (classe.getCoordinates().getX1() + classe.getCoordinates().getWidth())) {
                        if (classe.getCoordinates().getY1() <= (relation.getCoordinates().getY1() + relation.getRelationAttributes().getY1())) {
                            if (relation.getCoordinates().getY1() + relation.getRelationAttributes().getY1() <= classe.getCoordinates().getY1() + classe.getCoordinates().getHeigth()) {
                                switch (relation.getType()) {
                                    case "heritage" ->{
                                      Classe c = getLastRelation(classes, relation);
                                        System.out.println(c);

                                    }
                                    case "aggregation", "association" -> {
                                        relation.addClasse(classe);
                                        Classe c = getLastRelation(classes, relation);
                                        if (c == null) {
                                            Relation relation1 = checkClassAssociation(classes, relation, relations);
                                            assert relation1 != null;
                                            relation1.addClasse(classe);
                                            associations.add(relation1);
                                        } else {
                                            relation.addClasse(c);
                                            if (relation.getRoles().size() > 1) {
                                                if (relation.getRoles().get("m2") != null) {
                                                    if (relation.getRoles().get("m2").contains("*")) {
                                                        classe.addAttribut(new Attribut("arrayListOf" + c.getNom(), "ArrayList<" + c.getName() + ">", "private"));
                                                    } else if (relation.getRoles().get("m2").contains("1")) {
                                                        classe.addAttribut(new Attribut(c.getNom().toLowerCase(), c.getName(), "private"));
                                                    } else {
                                                        classe.addAttribut(new Attribut("arrayOf" + c.getName(), c.getName() + "[" + relation.getRoles().get("m2").charAt(3) + "]", "private"));
                                                    }
                                                }
                                                if (relation.getRoles().get("m1") != null) {
                                                    if (relation.getRoles().get("m1").contains("*")) {
                                                        c.addAttribut(new Attribut("arrayListOf" + classe.getName(), "ArrayList<" + classe.getName() + ">", "private"));
                                                    } else if (relation.getRoles().get("m1").contains("1")) {
                                                        classe.addAttribut(new Attribut(c.getNom().toLowerCase(), c.getName(), "private"));
                                                    } else {
                                                        classe.addAttribut(new Attribut("arrayOf" + c.getName(), c.getName() + "[" + relation.getRoles().get("m1").charAt(3) + "]", "private"));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    case "composition" -> {
                                        Classe c = getLastRelation(classes, relation);
                                        if (relation.getRoles().size() > 0) {
                                            if (relation.getRoles().get("m2").contains("*") && c != null) {
                                                c.addAttribut(new Attribut("arrayListOf" + classe.getNom(), "ArrayList<" + classe.getName() + ">", "private final"));
                                                c.addConstructor(new Attribut("arrayListOf" + classe.getNom(), "ArrayList<" + classe.getName() + ">", "private final"));

                                            } else if (relation.getRoles().get("m2").contains("1") && c != null) {
                                                c.addAttribut(new Attribut(c.getNom().toLowerCase(), c.getName(), "private"));
                                                c.addConstructor(new Attribut("arrayListOf" + classe.getNom(), "ArrayList<" + classe.getName() + ">", "private final"));

                                            } else if (c != null) {
                                                c.addAttribut(new Attribut("arrayOf" + c.getName(), c.getName() + "[" + relation.getRoles().get("m1").charAt(3) + "]", "private"));
                                                c.addConstructor(new Attribut("arrayListOf" + classe.getNom(), "ArrayList<" + classe.getName() + ">", "private final"));
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }

                }
            }
        }

        checkClassAssociationBis(associations, relations);


//        for (Classe classe : classes) {
//            System.out.println("Classe : " + classe.getName());
//            System.out.println("X1 : " + classe.getCoordinates().getX1());
//            System.out.println("Y1 : " + classe.getCoordinates().getY1());
//            System.out.println("Width : " + classe.getCoordinates().getWidth());
//            System.out.println("Height : " + classe.getCoordinates().getHeigth());
//            System.out.println("Extend de : " + classe.getExtend());
//            System.out.println("");
//        }

//        for (Relation relation : relations) {
//            System.out.println("Type de relation : " + relation.getType());
//        }
    }

    public static Classe getLastRelation(ArrayList<Classe> classes, Relation relation) {
        for (Classe classe : classes) {
            if (classe.getCoordinates().getX1() <= (relation.getCoordinates().getX1() + relation.getRelationAttributes().getxLast())) {
                if ((relation.getCoordinates().getX1() + relation.getRelationAttributes().getxLast()) <= (classe.getCoordinates().getX1() + classe.getCoordinates().getWidth())) {
                    if (classe.getCoordinates().getY1() <= (relation.getCoordinates().getY1() + relation.getRelationAttributes().getyLast())) {
                        if (relation.getCoordinates().getY1() + relation.getRelationAttributes().getyLast() <= classe.getCoordinates().getY1() + classe.getCoordinates().getHeigth()) {
                            return classe;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Relation checkClassAssociation(ArrayList<Classe> classes, Relation relation, ArrayList<Relation> relations) {
        for (Relation relation1 : relations) {
            if (relation1.getCoordinates().getX1() <= (relation.getCoordinates().getX1() + relation.getRelationAttributes().getxLast())) {
                if ((relation.getCoordinates().getX1() + relation.getRelationAttributes().getxLast()) <= (relation1.getCoordinates().getX1() + relation1.getCoordinates().getWidth())) {
                    if (relation1.getCoordinates().getY1() <= (relation.getCoordinates().getY1() + relation.getRelationAttributes().getyLast())) {
                        if (relation.getCoordinates().getY1() + relation.getRelationAttributes().getyLast() <= relation1.getCoordinates().getY1() + relation1.getCoordinates().getHeigth()) {
                            return relation1;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void checkClassAssociationBis(ArrayList<Relation> associations, ArrayList<Relation> relations){
        System.out.println(associations.get(0));
    }
}
