package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDaoJDBC {

    private final String GET_ALL = "SELECT * from estudiante;";
    private final String GETBYID = "SELECT * from estudiante WHERE id_estudiante = ?;";
    private final String INSERT = "INSERT INTO estudiante(nombre, apellido, email, telefono, edad) VALUE (?, ?, ?, ?, ?);";
    private final String UPDATE = "UPDATE estudiante SET nombre = ?, apellido = ?, email = ?, telefono = ?, edad = ? WHERE id_estudiante = ?;";
    private final String DELETE = "DELETE FROM estudiante WHERE id_estudiante = ?;";

    public List<Estudiante> listar() {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(GET_ALL); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idEstudiante = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                String email = rs.getString(4);
                String telefono = rs.getString(5);
                double edad = rs.getInt(6);

                Estudiante estudiante = new Estudiante(idEstudiante, nombre, apellido, email, telefono, edad);
                estudiantes.add(estudiante);
            }

        } catch (Exception e) {
            System.out.println("Erro al obtener todos: " + e.getMessage());
        }

        return estudiantes;
    }

    public Estudiante buscar(Estudiante estudiante) {
        Estudiante estudianteBusqueda = new Estudiante();
        try {
            Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(GETBYID);
            stmt.setInt(1, estudiante.getIdEstudiante());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idEstudiante = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                String email = rs.getString(4);
                String telefono = rs.getString(5);
                double edad = rs.getInt(6);

                estudianteBusqueda.setIdEstudiante(idEstudiante);
                estudianteBusqueda.setNombre(nombre);
                estudianteBusqueda.setApellido(apellido);
                estudianteBusqueda.setEmail(email);
                estudianteBusqueda.setTelefono(telefono);
                estudianteBusqueda.setEdad(edad);

            }

        } catch (SQLException e) {
            System.out.println("Error al buscar estudiante: " + e.getMessage());
        }
        return estudianteBusqueda;
    }

    public int insertar(Estudiante estudiante) {
        int rows = 0;

        try (Connection connection = Conexion.getConnection(); PreparedStatement stmt = connection.prepareStatement(INSERT)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellido());
            stmt.setString(3, estudiante.getEmail());
            stmt.setString(4, estudiante.getTelefono());
            stmt.setDouble(5, estudiante.getEdad());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro al insertar: " + e.getMessage());
        }

        return rows;
    }

    public int actualizar(Estudiante estudiante) {
        int rows = 0;
        try (Connection connection = Conexion.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE)) {

            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellido());
            stmt.setString(3, estudiante.getEmail());
            stmt.setString(4, estudiante.getTelefono());
            stmt.setDouble(5, estudiante.getEdad());
            stmt.setInt(6, estudiante.getIdEstudiante());

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro al actualizar estudiante: " + e.getMessage());
        }

        return rows;
    }

    public int eliminar(Estudiante estudiante) {
        int rows = 0;
        try (Connection connection = Conexion.getConnection(); 
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {

            preparedStatement.setInt(1, estudiante.getIdEstudiante());

            rows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        
        return rows;
    }

}
