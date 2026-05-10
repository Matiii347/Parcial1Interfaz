package parcial1;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Parcial1 {
public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("=== CONFIGURACION DE ECOSISTEMA ===");
        
        // 1. Plantas
        System.out.println("Cantidad inicial de plantas (5-30):");
        int numPlantas = Integer.parseInt(scanner.nextLine());
        if (numPlantas < 5 || numPlantas > 30) {
            System.out.println("Valor inválido. Se utilizará el valor por defecto: 10 plantas.");
            numPlantas = 10;
        }

        // 2. Conejos
        System.out.println("Cantidad inicial de conejos (2-15):");
        int numConejos = Integer.parseInt(scanner.nextLine());
        if (numConejos < 2 || numConejos > 15) {
            System.out.println("Valor inválido. Se utilizará el valor por defecto: 5 conejos.");
            numConejos = 5;
        }

        // 3. Lobos
        System.out.println("Cantidad inicial de lobos (1-5):");
        int numLobos = Integer.parseInt(scanner.nextLine());
        if (numLobos < 1 || numLobos > 5) {
            System.out.println("Valor inválido. Se utilizará el valor por defecto: 2 lobos.");
            numLobos = 2;
        }
        
        // 4. Clima
        System.out.println("Seleccionar el Clima");
        System.out.println("1. Soleado\n2. Lluvioso\n3. Sequia\n4. Invierno");
        System.out.println("Eliga entre 1 y 4:");
        int climaOpcion = Integer.parseInt(scanner.nextLine());
        
        Clima climaInicial;
        if (climaOpcion >= 1 && climaOpcion <= 4) {
            climaInicial = Clima.values()[climaOpcion - 1];
        } else {
            System.out.println("Opción inválida. Se utilizará SOLEADO por defecto.");
            climaInicial = Clima.SOLEADO;
        }

        // 5. Turnos
        System.out.println("Cantidad de turnos de la simulación (10-50):");
        int totalTurnos = Integer.parseInt(scanner.nextLine());
        if (totalTurnos < 1 || totalTurnos > 50) { // Bajado a 1 provisoriamente para que puedas probar
            System.out.println("Valor inválido. Se utilizará el valor por defecto: 20 turnos.");
            totalTurnos = 20;
        }
        
        System.out.println("\nConfirme para iniciar (Presione Enter):");
        scanner.nextLine();

        // 6. Creación de Listas Iniciales con Energías Aleatorias
        ArrayList<Planta> plantasIniciales = new ArrayList<>();
        ArrayList<Conejo> conejosIniciales = new ArrayList<>();
        ArrayList<Lobo> lobosIniciales = new ArrayList<>();
        
        for (int i = 0; i < numPlantas; i++) {
            // Energía aleatoria entre 30 y 80
            plantasIniciales.add(new Planta(3, "Planta_I" + i, 30 + rand.nextInt(51), 0, true));
        }
        for (int i = 0; i < numConejos; i++) {
            // Energía aleatoria entre 50 y 100
            conejosIniciales.add(new Conejo("Conejo_I" + i, 50 + rand.nextInt(51), 0, true, 10, 5.0));
        }
        for (int i = 0; i < numLobos; i++) {
            // Energía aleatoria entre 80 y 150
            lobosIniciales.add(new Lobo("Lobo_I" + i, 80 + rand.nextInt(71), 0, true, 15, 30.0));
        }
        
        Ecosistema ecosistema = new Ecosistema(plantasIniciales, conejosIniciales, lobosIniciales, climaInicial);
        
        System.out.println("\n=== SIMULACIÓN INICIADA ===");
        
        // LOOP PRINCIPAL
        boolean terminoPorColapso = false;
        
        while (ecosistema.getTurnoActual() <= totalTurnos && !ecosistema.ecosistemaColapsado()) {
            
            ecosistema.procesarTurno();
            
            // Evaluar colapso inmediatamente después del turno
            if (ecosistema.ecosistemaColapsado()) {
                terminoPorColapso = true;
                break;
            }

            // Sistema de Intervención cada 3 turnos
            if (ecosistema.getTurnoActual() <= totalTurnos && ecosistema.getTurnoActual() % 3 == 1 && ecosistema.getTurnoActual() > 1) {
                System.out.println("\n--- INTERVENCIÓN (Turno " + (ecosistema.getTurnoActual() - 1) + ") ---");
                System.out.println("1. Cambiar clima");
                System.out.println("2. Agregar planta");
                System.out.println("3. Agregar conejo");
                System.out.println("4. Agregar lobo");
                System.out.println("5. Solo avanzar turno");
                System.out.print("Elija opción: ");
                
                String opcion = scanner.nextLine();
                switch (opcion) {
                    case "1":
                        System.out.println("Nuevo Clima (1. Soleado 2. Lluvioso 3. Sequia 4. Invierno):");
                        try {
                            int nuevoC = Integer.parseInt(scanner.nextLine());
                            if (nuevoC >= 1 && nuevoC <= 4) ecosistema.cambiarClima(Clima.values()[nuevoC-1]);
                        } catch(Exception e) { System.out.println("Inválido. Se cancela intervención."); }
                        break;
                    case "2": ecosistema.agregarEntidad("planta"); break;
                    case "3": ecosistema.agregarEntidad("conejo"); break;
                    case "4": ecosistema.agregarEntidad("lobo"); break;
                    case "5": default:
                        System.out.println("Avanzando..."); break;
                }
            } else if (ecosistema.getTurnoActual() <= totalTurnos) {
                System.out.println("\n[Presione Enter para avanzar al siguiente turno]");
                scanner.nextLine();
            }
        }
        
        String causa = terminoPorColapso ? "Colapso del ecosistema (una especie llegó a 0)" : "Turnos completados";
        ecosistema.generarReporteFinal(causa);
    }
}
