package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Relation {
    private String nom;
    private final Map<String, String> roles = new HashMap<>();

    private String type;

    private final ArrayList<Classe> classes = new ArrayList<>();

    private Coordinates coordinates;

    private RelationAttributes relationAttributes;

    public Relation() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashMap<String, String> getRoles() {
        return (HashMap<String, String>) roles;
    }

    public void addRoles(String key, String value){
        roles.put(key, value);
    }

    public void removeRole(String key){
        roles.remove(key);
    }


    public ArrayList<Classe> getClasses() {
        return classes;
    }

    public void addClasse(Classe classe){
        classes.add(classe);
    }

    public void removeClasse(Classe classe){
        classes.remove(classe);

    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RelationAttributes getRelationAttributes() {
        return relationAttributes;
    }

    public void setRelationAttributes(RelationAttributes relationAttributes) {
        this.relationAttributes = relationAttributes;
    }
}
