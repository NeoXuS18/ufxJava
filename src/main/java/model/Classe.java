package model;

import java.util.ArrayList;

public class Classe extends Element{
    private String nom;
    private String stereotype;

    private final ArrayList<Attribut> attributs = new ArrayList<>();

    public Classe(String nom, String stereotype) {
        this.nom = nom;
        this.stereotype = stereotype;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStereotype() {
        return stereotype;
    }

    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    public ArrayList<Attribut> getAttributs() {
        return attributs;
    }

    public void addAttribut(Attribut attribut){
        attributs.add(attribut);
    }

    public void removeAttribut(Attribut attribut){
        attributs.remove(attribut);
    }
}
