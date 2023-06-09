package application;

import model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class UFXReader {


    public static ArrayList<Classe> read(String path) {
        ArrayList<Classe> elements = new ArrayList<>();
        ArrayList<Relation> relations = new ArrayList<>();


        try {
            File f = new File(path);
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
                            } else if (panelSplit[0].contains("<<")) {
//                                Replace "<<" to ""
                                panelSplit[0] = panelSplit[0].replaceAll("<<", "");
                                panelSplit[0] = panelSplit[0].replaceAll(">>", "");
                                panelSplit[0] = panelSplit[0].replaceAll("interface", "");
                                classe.setStereotype("interface");
                                boolean isClose = false;
                                while (!isClose) {
                                    if (panelSplit[1].contains("+")) {
                                        String meth;
//                                        meth = [name, return type of method]
                                        Method method = new Method();
                                        panelSplit[1] = panelSplit[1].substring(1);

                                        int index = panelSplit[1].indexOf("+");
                                        if (index != -1) {
                                            meth = panelSplit[1].substring(0, index);
                                            panelSplit[1] = panelSplit[1].substring(index);
                                        } else {
                                            meth = panelSplit[1];
                                        }

                                        String[] getParameters = panelAttribute.split("--");
                                        String[] info = meth.split(":");
                                        method.setVisibility("public");
                                        method.setNom(info[0].substring(0, info[0].indexOf("(")) + "(" + getParameters[1].substring(getParameters[1].indexOf("(") + 1, getParameters[1].indexOf(")")) + ")");
                                        method.setTypeReturn(info[1]);
                                        method.setStatic(true);
                                        classe.addMethod(method);
                                    } else {
                                        isClose = true;
                                    }
                                }
                            } else {
                                classe.setStereotype("");
                            }
//                            Give name to this class
                            classe.setNom(panelSplit[0]);

//                            Check if class have attribut
                            if (panelSplit.length > 1) {
                                boolean isFinish = false;

                                while (!isFinish) {
                                    String att = "";
                                    Attribut attribut = new Attribut();
                                    if (Main.checkIfOtherAttribut(panelSplit[1])) {
                                        char firstCharacterIs = panelSplit[1].charAt(0);
                                        if (firstCharacterIs == '_'){
                                            attribut.setStatic(true);
                                            panelSplit[1] = panelSplit[1].substring(1);
                                            panelSplit[1] = panelSplit[1].replaceFirst("_", "");
                                            firstCharacterIs =panelSplit[1].charAt(0);
                                        }
                                        if (firstCharacterIs == '-') {
                                            panelSplit[1] = panelSplit[1].substring(1);
                                            if (Main.checkIfOtherAttribut(panelSplit[1])) {
                                               ArrayList<String> result = getAttributeAndChangeAllLine(panelSplit[1]);
                                               att = result.get(0);
                                               panelSplit[1] =result.get(1);
                                            } else {
                                                att = panelSplit[1];
                                            }
                                            attribut.setVisibility("private");
                                        } else if (firstCharacterIs == '+'){
                                            panelSplit[1] = panelSplit[1].substring(1);
                                            if (Main.checkIfOtherAttribut(panelSplit[1])){
                                                ArrayList<String> result = getAttributeAndChangeAllLine(panelSplit[1]);
                                                att = result.get(0);
                                                panelSplit[1] =result.get(1);
                                            }else {
                                                att = panelSplit[1];
                                            }
                                            attribut.setVisibility("public");
                                        } else if (firstCharacterIs == '#'){
                                            panelSplit[1] = panelSplit[1].substring(1);
                                            if (Main.checkIfOtherAttribut(panelSplit[1])){
                                                ArrayList<String> result = getAttributeAndChangeAllLine(panelSplit[1]);
                                                att = result.get(0);
                                                panelSplit[1] =result.get(1);
                                            }else {
                                                att = panelSplit[1];
                                            }
                                            attribut.setVisibility("protected");
                                        }

                                        String[] info = att.split(":");
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

                                            String[] getParameters = panelAttribute.split("--");
                                            String[] info = meth.split(":");
                                            method.setVisibility("public");
                                            method.setNom(info[0].substring(0, info[0].indexOf("(")) + "(" + getParameters[2].substring(getParameters[2].indexOf("(") + 1, getParameters[2].indexOf(")")) + ")");
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
                            if (panelAttributes.contains("lt=<<-")) {
                                relation.setType("heritage");
                            } else if (panelAttributes.contains("lt=<<<<<-")) {
                                relation.setType("composition");
                            } else if (panelAttributes.contains("lt=<<<<-")) {
                                relation.setType("aggregation");
                            } else if (panelAttributes.contains("lt=-")) {
                                relation.setType("association");
                            } else if (panelAttributes.contains("lt=<.")) {
                                if (panelAttributes.contains("<<realize>>")) {
                                    relation.setType("implements");
                                } else {
                                    relation.setType("comment");
                                }
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
                                    case "heritage" ->
                                            Objects.requireNonNull(getLastRelation(classes, relation)).setExtend(classe.getName());
                                    case "aggregation", "association" -> {
                                        relation.addClasse(classe);
                                        Classe c = getLastRelation(classes, relation);
                                        if (c == null) {
                                            Relation relation1 = checkClassAssociation(relation, relations);
                                            assert relation1 != null;
                                            associations.add(relation);
                                            relation.setAssociation(relation1);
                                        } else {
                                            relation.addClasse(c);
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
                                    case "implements" -> {
                                        Classe c = getLastRelation(classes, relation);
                                        assert c != null;
                                        c.setImplement(classe.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        checkClassAssociationBis(associations, relations);

        addAttribut(relations, classes);
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

    public static Relation checkClassAssociation(Relation relation, ArrayList<Relation> relations) {
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

    public static void checkClassAssociationBis(ArrayList<Relation> associations, ArrayList<Relation> relations) {
        for (Relation association : associations) {
            association.getAssociation().setType("ClasseAssociation");
            Classe associationClass = association.getClasses().get(0);
            Relation associationRelation = association.getAssociation();

            if (associationRelation.getRoles().size() > 1) {
                if (associationRelation.getRoles().get("m2") != null) {
                    if (associationRelation.getRoles().get("m2").contains("*")) {
                        associationClass.addAttribut(new Attribut("arrayListOf" + associationRelation.getClasses().get(1).getName(), "ArrayList<" + associationRelation.getClasses().get(1).getName() + ">", "private"));
                    } else if (associationRelation.getRoles().get("m2").contains("1")) {
                        associationClass.addAttribut(new Attribut(associationRelation.getClasses().get(1).getName().toLowerCase(), associationRelation.getClasses().get(1).getName(), "private"));
                    } else {
                        associationClass.addAttribut(new Attribut("arrayOf" + associationRelation.getClasses().get(1).getName(), associationRelation.getClasses().get(1).getName() + "[" + associationRelation.getRoles().get("m2").charAt(3) + "]", "private"));
                    }
                }
                if (associationRelation.getRoles().get("m1") != null) {
                    if (associationRelation.getRoles().get("m1").contains("*")) {
                        associationClass.addAttribut(new Attribut("arrayListOf" + associationRelation.getClasses().get(0).getName(), "ArrayList<" + associationRelation.getClasses().get(0).getName() + ">", "private"));
                    } else if (associationRelation.getRoles().get("m1").contains("1")) {
                        associationClass.addAttribut(new Attribut(associationRelation.getClasses().get(0).getName().toLowerCase(), associationRelation.getClasses().get(0).getName(), "private"));
                    } else {
                        associationClass.addAttribut(new Attribut("arrayOf" + associationRelation.getClasses().get(0).getName(), associationRelation.getClasses().get(0).getName() + "[" + associationRelation.getRoles().get("m1").charAt(3) + "]", "private"));
                    }
                }
            }
        }
    }

    public static void addAttribut(ArrayList<Relation> relations, ArrayList<Classe> classes) {
        for (Relation relation : relations) {
            if (relation.getType().equals("association")) {
                if (relation.getRoles().size() > 1) {
                    Classe c = relation.getClasses().get(1);
                    Classe classe = relation.getClasses().get(0);
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
    }

    public static int getIndexOfNextAttribute(int indexPrivate, int indexPublic, int indexProtected, int indexStatic) {
        if (indexPrivate != -1) {
            if (indexPublic != -1) {
                if (indexProtected != -1) {
                    if (indexStatic != -1) {
                        if (indexPrivate > indexPublic) {
                            if (indexStatic > indexPublic) {
                                return Math.min(indexPublic, indexProtected);
                            } else {
                                return Math.min(indexStatic, indexProtected);
                            }
                        } else {
                            if (indexStatic > indexPrivate) {
                                return Math.min(indexPrivate, indexProtected);
                            } else {
                                return Math.min(indexStatic, indexProtected);
                            }
                        }
                    } else {
                        if (indexPublic > indexPrivate) {
                            return Math.min(indexProtected, indexPrivate);
                        } else {
                            return Math.min(indexPublic, indexProtected);
                        }
                    }
                } else {
                    if (indexStatic != -1) {
                        if (indexStatic > indexPublic) {
                            return Math.min(indexPublic, indexPrivate);
                        } else {
                            return Math.min(indexStatic, indexPrivate);
                        }
                    } else {
                        return Math.min(indexPublic, indexPrivate);
                    }
                }
            } else if (indexProtected != -1) {
                if (indexStatic != -1) {
                    if (indexStatic > indexProtected) {
                        return Math.min(indexProtected, indexPrivate);
                    } else {
                        return Math.min(indexStatic, indexPrivate);
                    }
                } else {
                    return Math.min(indexProtected, indexPrivate);
                }
            } else {
                if (indexStatic != -1) {
                    return Math.min(indexStatic, indexPrivate);
                } else {
                    return indexPrivate;
                }
            }
        } else if (indexPublic != -1) {
            if (indexProtected != -1) {
                if (indexStatic != -1) {
                    if (indexProtected > indexStatic) {
                        return Math.min(indexStatic, indexPublic);
                    } else {
                        return Math.min(indexProtected, indexPublic);
                    }
                } else {
                    return Math.min(indexProtected, indexPublic);
                }
            } else {
                if (indexStatic != -1) {
                    return Math.min(indexStatic, indexPublic);
                } else {
                    return indexPublic;
                }
            }
        } else {
            if (indexStatic != -1) {
                return Math.min(indexStatic, indexProtected);
            } else {
                return indexProtected;
            }
        }
    }

    public static ArrayList<String> getAttributeAndChangeAllLine(String string) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        int indexPrivate = string.indexOf("-");
        int indexPublic = string.indexOf("+");
        int indexProtected = string.indexOf("#");
        int indexStatic = string.indexOf("_");
        int indexNextAttribute = getIndexOfNextAttribute(indexPrivate, indexPublic, indexProtected, indexStatic);
        stringArrayList.add(string.substring(0, indexNextAttribute));
        stringArrayList.add(string.substring(indexNextAttribute));
        return stringArrayList;
    }

}
