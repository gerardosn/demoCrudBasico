package gerardo.sn.demoCrudBasico.model;

//public class Entity {
//}
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sociosDePileta")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private boolean habilitadoParaIngresar;
    private LocalDate habilitadoHastaLaFecha;

    // Constructor vacío requerido por JPA
    public Socio() {}

    // Constructor con parámetros
    public Socio(String nombre, boolean habilitadoParaIngresar, LocalDate habilitadoHastaLaFecha) {
        this.nombre = nombre;
        this.habilitadoParaIngresar = habilitadoParaIngresar;
        this.habilitadoHastaLaFecha = habilitadoHastaLaFecha;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isHabilitadoParaIngresar() { return habilitadoParaIngresar; }
    public void setHabilitadoParaIngresar(boolean habilitadoParaIngresar) {
        this.habilitadoParaIngresar = habilitadoParaIngresar;
    }

    public LocalDate getHabilitadoHastaLaFecha() { return habilitadoHastaLaFecha; }
    public void setHabilitadoHastaLaFecha(LocalDate habilitadoHastaLaFecha) {
        this.habilitadoHastaLaFecha = habilitadoHastaLaFecha;
    }
}
