package model;

import java.util.ArrayList;

public class Relation {
    private String nom;
    private final ArrayList<String> roles = new ArrayList<>();

    private String type;

    private final ArrayList<Classe> classes = new ArrayList<>();

    private Coordinates coordinates;

    public Relation() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void addRoles(String role){
        roles.add(role);
    }

    public void removeRole(String role){
        roles.remove(role);
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
}
