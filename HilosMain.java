import java.util.Scanner;

public class HilosMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("   PROGRAMA DE HILOS - JAVA THREADS     ");
        System.out.println("========================================");

        int numero = 0;
        boolean numeroValido = false;

        while (!numeroValido) {
            System.out.print("\nIngresa el numero de inicio para la cuenta regresiva: ");
            String inputNumero = scanner.nextLine().trim();
            try {
                numero = Integer.parseInt(inputNumero);
                if (numero <= 0) {
                    System.out.println("ERROR: El numero debe ser mayor que 0.");
                } else {
                    numeroValido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Ingresa un numero entero valido.");
            }
        }

        char letraFin = ' ';
        boolean letraValida = false;

        while (!letraValida) {
            System.out.print("Ingresa la letra final del alfabeto (B - Z): ");
            String inputLetra = scanner.nextLine().trim().toUpperCase();
            if (inputLetra.length() != 1) {
                System.out.println("ERROR: Ingresa solo UNA letra.");
            } else {
                char letra = inputLetra.charAt(0);
                if (letra >= 'B' && letra <= 'Z') {
                    letraFin = letra;
                    letraValida = true;
                } else if (letra == 'A') {
                    System.out.println("ERROR: La letra debe ser mayor que A.");
                } else {
                    System.out.println("ERROR: Caracter no valido. Ingresa una letra entre B y Z.");
                }
            }
        }

        System.out.println("\n========================================");
        System.out.println("        INICIANDO LOS DOS HILOS...      ");
        System.out.println("========================================\n");

        HiloCuentaRegresiva hiloCuenta = new HiloCuentaRegresiva(numero);
        HiloAlfabeto hiloAlfabeto = new HiloAlfabeto(letraFin);

        hiloCuenta.start();
        hiloAlfabeto.start();

        try {
            hiloCuenta.join();
            hiloAlfabeto.join();
        } catch (InterruptedException e) {
            System.out.println("El programa fue interrumpido.");
        }

        System.out.println("========================================");
        System.out.println("       AMBOS HILOS TERMINARON           ");
        System.out.println("========================================");

        scanner.close();
    }
}

class HiloCuentaRegresiva extends Thread {

    private int numeroInicio;

    public HiloCuentaRegresiva(int numeroInicio) {
        super("HiloCuentaRegresiva");
        this.numeroInicio = numeroInicio;
    }

    @Override
    public void run() {
        System.out.println("[" + getName() + "] Iniciando desde " + numeroInicio);
        for (int i = numeroInicio; i >= 0; i--) {
            System.out.println("  [Cuenta Regresiva] --> " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("[" + getName() + "] Hilo interrumpido.");
                return;
            }
        }
        System.out.println("\n  Trabajo del hilo " + getName() + " terminado.\n");
    }
}

class HiloAlfabeto extends Thread {

    private char letraFin;

    public HiloAlfabeto(char letraFin) {
        super("HiloAlfabeto");
        this.letraFin = letraFin;
    }

    @Override
    public void run() {
        System.out.println("[" + getName() + "] Mostrando desde A hasta " + letraFin);
        for (char letra = 'A'; letra <= letraFin; letra++) {
            System.out.println("        [Alfabeto] --> " + letra);
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                System.out.println("[" + getName() + "] Hilo interrumpido.");
                return;
            }
        }
        System.out.println("\n        Trabajo del hilo " + getName() + " terminado.\n");
    }
}
