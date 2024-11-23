package gerardo.sn.demoCrudBasico.console;

//public class ConsoleApplication {
//}
import gerardo.sn.demoCrudBasico.model.Socio;
import gerardo.sn.demoCrudBasico.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApplication implements CommandLineRunner {

    @Autowired
    private SocioService socioService;
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== MENÚ SOCIOS DE PILETA ===");
            System.out.println("1. Ingresar nuevo socio");
            System.out.println("2. Ver todos los socios");
            System.out.println("3. Eliminar socio");
            System.out.println("4. Editar socio");

            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();


            switch (opcion) {
                case "1":
                    ingresarNuevoSocio();
                    break;
                case "2":
                    mostrarTodosSocios();
                    break;
                case "3":
                    eliminarSocio();
                    break;
                case "4":
                    editarSocio();
                    break;
                case "5":
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        scanner.close();
    }

    private void editarSocio() {
        System.out.print("\nIngrese el ID del socio a editar (0 para cancelar): ");
        String idStr = scanner.nextLine();

        try {
            Long id = Long.parseLong(idStr);

            if (id == 0) {
                System.out.println("Operación cancelada");
                return;
            }

            // Verificar si el socio existe
            Socio socio = socioService.obtenerSocioPorId(id);
            if (socio != null) {
                // Mostrar datos actuales
                System.out.println("\n=== Datos actuales del socio ===");
                System.out.println("Nombre: " + socio.getNombre());
                System.out.println("Habilitado: " + (socio.isHabilitadoParaIngresar() ? "Sí" : "No"));
                System.out.println("Fecha de habilitación: " +
                        socio.getHabilitadoHastaLaFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                // Solicitar nuevos datos
                System.out.println("\nIngrese los nuevos datos (presione Enter para mantener el [valor actual]):");

                // Editar nombre
                System.out.print("Nuevo nombre [" + socio.getNombre() + "]: ");
                String nuevoNombre = scanner.nextLine();
                if (!nuevoNombre.isEmpty()) {
                    socio.setNombre(nuevoNombre);
                }

                // Editar habilitación
                System.out.print("¿Está habilitado? (si/no) [" +
                        (socio.isHabilitadoParaIngresar() ? "si" : "no") + "]: ");
                String habilitadoStr = scanner.nextLine().trim().toLowerCase();
                if (!habilitadoStr.isEmpty()) {
                    socio.setHabilitadoParaIngresar(habilitadoStr.equals("si"));
                }

                // Editar fecha
                System.out.print("Nueva fecha de habilitación (dd/MM/yyyy) [" +
                        socio.getHabilitadoHastaLaFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "]: ");
                String fechaStr = scanner.nextLine();
                if (!fechaStr.isEmpty()) {
                    try {
                        LocalDate nuevaFecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        socio.setHabilitadoHastaLaFecha(nuevaFecha);
                    } catch (Exception e) {
                        System.out.println("Formato de fecha inválido. Se mantendrá la fecha actual.");
                    }
                }

                // Guardar cambios
                socioService.guardarSocio(socio);
                System.out.println("Socio actualizado exitosamente");

                // Mostrar la lista actualizada
                System.out.println("\nLista actualizada:");
                mostrarTodosSocios();
            } else {
                System.out.println("No se encontró un socio con el ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido");
        }
    }

    private void eliminarSocio() {
        mostrarTodosSocios();//no es muy dificil entender que hace esta liena

        System.out.print("\nIngrese el ID del socio a eliminar (0 para cancelar): ");
        String idStr = scanner.nextLine();

        try {
            Long id = Long.parseLong(idStr);

            if (id == 0) {
                System.out.println("Operación cancelada");
                return;
            }

            // Verificar si el socio existe
            Socio socio = socioService.obtenerSocioPorId(id);
            if (socio != null) {
                socioService.eliminarSocio(id);
                System.out.println("Socio eliminado exitosamente");
                // Mostrar la lista actualizada
                System.out.println("\nLista actualizada:");
                mostrarTodosSocios();
            } else {
                System.out.println("No se encontró un socio con el ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido");
        }
    }

    private void ingresarNuevoSocio() {
        System.out.print("Ingrese nombre del socio: ");
        String nombre = scanner.nextLine();

        System.out.print("¿Está habilitado para ingresar? (true/false): ");
        String habilitadoStr = scanner.nextLine().trim().toLowerCase();
        boolean habilitado = habilitadoStr.equals("si");

//        boolean habilitado = scanner.nextBoolean();
//        scanner.nextLine(); // Consumir el salto de línea

        System.out.print("Ingrese fecha de habilitación (dd/MM/yyyy): ");
        String fechaStr = scanner.nextLine();
        LocalDate fecha;
        if (fechaStr == null || fechaStr.isEmpty()) {
            fecha = LocalDate.now(); // Fecha 0 como valor por defecto
        } else {
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
//        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Socio socio = new Socio(nombre, habilitado, fecha);
        socioService.guardarSocio(socio);
        System.out.println("Socio guardado exitosamente");
    }

    private void mostrarTodosSocios() {
        List<Socio> socios = socioService.obtenerTodosSocios();
        if (socios.isEmpty()) {
            System.out.println("No hay socios registrados");
            return;
        }

        System.out.println("\n=== LISTA DE SOCIOS ===");
        System.out.println("ID | Nombre | Habilitado | Fecha Hasta");
        System.out.println("----------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Socio socio : socios) {
            System.out.printf("%d | %s | %s | %s%n",
                    socio.getId(),
                    socio.getNombre(),
                    socio.isHabilitadoParaIngresar() ? "Sí" : "No",
                    socio.getHabilitadoHastaLaFecha().format(formatter));
        }
    }
}