package parcial1;

import java.util.ArrayList;
import java.util.Scanner;

public class Parcial1 {

    public static void main(String[] args) {
        
        Scanner scanner=new Scanner(System.in);
        System.out.println("=== CONFIGURACION DE ECOSISTEMA ===");
        System.out.println("Cantidad inicial de plantas (5-30):");
        int numPlanta=Integer.parseInt(scanner.nextLine());
        System.out.println("Cantidad inicial de conejos (2-15):");
        int numLobo=Integer.parseInt(scanner.nextLine());
        System.out.println("Cantidad inicial de lobos (1-5):");
        int numConejo=Integer.parseInt(scanner.nextLine());
        
        System.out.println("Seleccionar el Clima");
        System.out.println("1. Soleado\n2. Lluvioso\n3. Sequia\n4. Invierno");
        System.out.println("Eliga entre 1 y 4");
        int climaOpcion=Integer.parseInt(scanner.nextLine());
        Clima climaInicial=Clima.values()[climaOpcion-1];
        System.out.println("Cantidad de turnos de la simulación (10-50):");
        int totalTurnos=Integer.parseInt(scanner.nextLine());
        
        ArrayList<Planta> plantasIniciales = new ArrayList<>();
        ArrayList<Conejo> conejosIniciales = new ArrayList<>();
        ArrayList<Lobo> lobosIniciales = new ArrayList<>();
        /*
        for (int i = 0; i < numPlantas; i++)
            plantasIniciales.add(new Planta(3, "Planta_I" + i, 50, 0, true));
        for (int i = 0; i < numConejos; i++)
            conejosIniciales.add(new Conejo("Conejo_I" + i, 100, 0, true, 10, 5));
        for (int i = 0; i < numLobos; i++)
            lobosIniciales.add(new Lobo("Lobo_I" + i, 150, 0, true, 15, 30));
        */
        
        
        
    }
    
}
