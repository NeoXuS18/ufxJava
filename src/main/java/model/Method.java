package model;

public class Method {

    private String visibility;

    private String typeReturn;

    private Boolean isStatic;

    private String nom;

    public Method(){}
    public Method(String visibility, String typeReturn, Boolean isStatic) {
        this.visibility = visibility;
        this.typeReturn = typeReturn;
        this.isStatic = isStatic;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getTypeReturn() {
        return typeReturn;
    }

    public void setTypeReturn(String typeReturn) {
        this.typeReturn = typeReturn;
    }

    public Boolean getStatic() {
        return isStatic;
    }

    public void setStatic(Boolean aStatic) {
        isStatic = aStatic;
    }

    public String toString(){
        return getVisibility() + " " + (getStatic() ? "static" : "") + " " + getTypeReturn() + " " + getNom()+"(){//TODO}";
    }
}
