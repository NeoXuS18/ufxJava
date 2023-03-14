package model;

public class Attribut {
    private String nom;
    private String type;
    private String visibility;
    private Coordinates coordinates;


    public Attribut(String nom, String type, String visibility, Coordinates coordinates) {
        this.nom = nom;
        this.type = type;
        this.visibility = visibility;
        this.coordinates = coordinates;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
