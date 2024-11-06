import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Escenario {
    private String nombre;
    private int N;
    private Elemento[][] campoDeBatalla;

    public Escenario(String nombre, int N){
        campoDeBatalla = new Elemento[N][N];
        this.nombre = nombre;
        this.N = N;
    }

    public void agregarElemento(Elemento e){
        campoDeBatalla[e.getPosicion().getColumna()][e.getPosicion().getRenglon()] = e;
    }

    public void destruirElementos(Posicion p, int radio) {
        for (int i = Math.max(0, p.getRenglon() - radio); i <= Math.min(N - 1, p.getRenglon() + radio); i++) {
            for (int j = Math.max(0, p.getColumna() - radio); j <= Math.min(N - 1, p.getColumna() + radio); j++) {
                Elemento elemento = campoDeBatalla[i][j];
                if (elemento instanceof Destruible) {
                    ((Destruible) elemento).destruir();
                    campoDeBatalla[i][j] = null; // Eliminar el elemento del campo
                }
            }
        }
    }
    

    @Override 
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (campoDeBatalla[i][j] == null) {
                    sb.append(" . ");  
                } else if(campoDeBatalla[i][j] instanceof Bomba){
                    sb.append(" B ");
                } else if(campoDeBatalla[i][j] instanceof Roca){
                    sb.append(" R ");
                } else if(campoDeBatalla[i][j] instanceof Terricola){
                    sb.append(" T ");
                } else if(campoDeBatalla[i][j] instanceof Extraterrestre){
                    sb.append(" E ");
                }
            }
            sb.append('\n'); 
        }
        return sb.toString();
    }

    public void leerArchivoEscenarioNuevo(){
        try{
            String linea;
            BufferedReader leer = new BufferedReader(new FileReader("C:\\Users\\bombo\\Desktop\\POO\\Practica10\\Escenario1.txt"));
            while((linea = leer.readLine()) != null){
                String [] contenido = linea.split(" ");
                if(contenido[0].equals("Roca")){
                    agregarElemento(new Roca(new Escenario("Nuevo", 10) , new Posicion(Integer.parseInt(contenido[1]),Integer.parseInt(contenido[2]))));
                } else if(contenido[0].equals("Extraterrestre")){
                    agregarElemento(new Extraterrestre("Nuevo extraterrestre" ,new Escenario("Nuevo", 10) , new Posicion(Integer.parseInt(contenido[1]),Integer.parseInt(contenido[2]))));
                } else if(contenido[0].equals("Bomba")){
                    agregarElemento(new Bomba(new Escenario("Nuevo", 10), new Posicion(Integer.parseInt(contenido[1]),Integer.parseInt(contenido[2])), Integer.parseInt(contenido[3])));
                }
            }
        } catch(FileNotFoundException ex){
            System.out.println("No se pudo encontrar el archivo");
        } catch(IOException ex){
            System.out.println("No se pudo leer");
        }
    }

    public void guardarEscenarioNuevo() {
        try (BufferedWriter escribir = new BufferedWriter(new FileWriter("C:\\Users\\bombo\\Desktop\\POO\\Practica10\\Escenario1.txt"))) {
            for (int i = 0; i < campoDeBatalla.length; i++) {
                for (int j = 0; j < campoDeBatalla[i].length; j++) {
                    Elemento elemento = campoDeBatalla[i][j];
                    if (elemento != null) {
                        String linea = "";
                        if (elemento instanceof Roca) {
                            linea = "Roca " + i + " " + j;
                        } else if (elemento instanceof Extraterrestre) {
                            linea = "Extraterrestre " + i + " " + j;
                        } else if (elemento instanceof Bomba) {
                            Bomba bomba = (Bomba) elemento;
                            linea = "Bomba " + i + " " + j + " " + bomba.getRadio();
                        }
                        escribir.write(linea);
                        escribir.newLine();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("No se pudo guardar el archivo");
        }
    }
    

}
