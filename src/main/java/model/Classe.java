package model;

import java.util.ArrayList;

public class Classe extends Element {
    private String nom;
    private String stereotype;
    private Coordinates coordinates;
    private String extend;

    private String implement;
    private final ArrayList<Attribut> attributs = new ArrayList<>();
    private final ArrayList<Method> methods = new ArrayList<>();
    private final ArrayList<Attribut> constructor = new ArrayList<>();

    public Classe() {
    }

    public Classe(String nom, String stereotype, Coordinates coordinates) {
        this.nom = nom;
        this.stereotype = stereotype;
        this.coordinates = coordinates;
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

    public void addAttribut(Attribut attribut) {
        attributs.add(attribut);
    }

    public void removeAttribut(Attribut attribut) {
        attributs.remove(attribut);
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        methods.add(method);
    }

    public void removeMethod(Method method) {
        methods.remove(method);
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getExtend() {
        return extend;
    }
    public void setExtend(String extend) {
        this.extend = extend;
    }

    public void addConstructor(Attribut string){
        constructor.add(string);
    }

    public ArrayList<Attribut> getConstructor(){
        return constructor;
    }

    public void removeConstructor(Attribut string){
        constructor.remove(string);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("public " + getStereotype() + " " + getNom() + " , ");
        for (Attribut attribut : attributs) {
            sb.append(attribut.getVisibility() + " " + attribut.getType() + " " + attribut.getNom());
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return this.getNom();
    }

    public String getImplement() {
        return implement;
    }

    public void setImplement(String implement) {
        this.implement = implement;
    }
}
