package application;

import model.Attribut;
import model.Classe;
import model.Element;
import model.Method;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

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
                writer.println("public " + element.getStereotype() + " " + "class" + " " + element.getName() + (element.getExtend() != null ? " extends " + element.getExtend() : "") +" {");
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

                writer.println("");
                writer.println("}");

                writer.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
