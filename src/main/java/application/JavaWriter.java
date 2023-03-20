package application;

import controller.MainController;
import model.Attribut;
import model.Classe;
import model.Method;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JavaWriter {

    public static void writeJava(String path, ArrayList<Classe> elements){

        for (Classe element : elements){
            try {
                File f = new File(path+ "/" + element.getName() + ".java");


                f.createNewFile();

                PrintWriter writer = new PrintWriter(f, StandardCharsets.UTF_8);
//                writer.println("package " + Paths.get(path).getFileName().toString());
                writer.println("");
                writer.println("");
                if (element.getStereotype().equals("interface")){
                    writer.println("public " + element.getStereotype() + " " + element.getName() + (element.getExtend() != null ? " extends " + element.getExtend() : "") +" {");
                }else {
                    writer.println("public " + element.getStereotype() + " " + "class" + " " + element.getName() + (element.getExtend() != null ? " extends " + element.getExtend() : " ") + (element.getImplement() != null ? " implements " + element.getImplement() : "")+ "{");
                }
                writer.println("");
                for (Attribut attribut : element.getAttributs()){
                    writer.println(attribut.getVisibility() + " " + attribut.getType() + " " + attribut.getNom()+";");
                    writer.println("");
                }

                for (Method method : element.getMethods()){
                    writer.println(method.getVisibility() + " " + (method.getStatic() ? "static" : "")+ " " + method.getTypeReturn() + " " + method.getNom()+ "() {") ;
                    writer.println("");
                    writer.println("//TODO");
                    writer.println("");
                    writer.println("}");
                    writer.println("");
                }

                if(element.getConstructor().size() > 0){
                    writer.println("public " + element.getName() + " () {");
                    writer.println("");
                    for (Attribut attribut : element.getConstructor()){
                        writer.println(attribut.getNom().toLowerCase() + " = new " + attribut.getType() + "();" );
                        writer.println("");
                    }
                    writer.println("}");
                }

                if (MainController.constructor){
                    writer.print("public " + element.getName() + " (");
                    for(int i = 0; i<element.getAttributs().size(); i++){
                        writer.print(element.getAttributs().get(i).getType() + " " +element.getAttributs().get(i).getNom() +  (i== element.getAttributs().size() - 1 ?  " " : ", "));
                    }
                    writer.println(") {");
                    for (Attribut attribut : element.getAttributs()){
                        writer.println("this." + attribut.getNom() + " = " + attribut.getNom() + ";");
                    }

                    writer.println(" }");
                }

                writer.println("");
                writer.println("}");

                writer.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public static String capitalize(String str){
        if (str == null || str.length() == 0) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
