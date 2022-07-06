package ar.edu.unlp.info.bd2.promocionbd2.beans;

import com.opencsv.bean.CsvBindByName;

public class PruebaBean extends CsvBean {
    @CsvBindByName(column = "id")
    private Long id;

    @CsvBindByName(column = "nombre")
    private String nombre;

    @CsvBindByName(column = "apellido")
    private String apellido;

    @CsvBindByName(column = "edad")
    private Integer edad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}
