package controlador;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Estudiante;
import modelo.EstudianteDaoJDBC;

@WebServlet(name = "ServletControlador", urlPatterns = "/ServletControlador")
public class ServletControlador extends HttpServlet {

    protected void editarEstudiante(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));

        Estudiante estudiante = new EstudianteDaoJDBC().buscar(new Estudiante(idEstudiante));
        request.setAttribute("estudiante", estudiante);

        String jspEditar = "/WEB-INF/vista/estudiante/editarEstudiante.jsp";

        request.getRequestDispatcher(jspEditar).forward(request, response);

    }

    protected void accionDefault(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Estudiante> estudiantes = new EstudianteDaoJDBC().listar();
        System.out.println("estudiantes: " + estudiantes);

        // creo una sesion para mantener la informacion
        HttpSession sesion = request.getSession();
        sesion.setAttribute("estudiantes", estudiantes);
        sesion.setAttribute("totalEstudiantes", estudiantes.size());
        response.sendRedirect("estudiantes.jsp");
    }

    protected void modificarEstudiante(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double edad = 0;

        // parseo la edad a Double
        String edadString = request.getParameter("edad");
        if (edadString != null && !"".equals(edadString)) {
            edad = Double.parseDouble(edadString);
        }
        Estudiante estudiante = new Estudiante(idEstudiante, nombre, apellido, email, telefono, edad);
        // Actualizo el registro
        int registrosModificados = new EstudianteDaoJDBC().actualizar(estudiante);
        this.accionDefault(request, response);
    }
    
    protected void eliminarEstudiante(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int idEstudiante = Integer.parseInt(request.getParameter("idEstudiante"));
        Estudiante estudiante = new Estudiante(idEstudiante);
        
        
        int registrosModificados = new EstudianteDaoJDBC().eliminar(estudiante);
        this.accionDefault(request, response);
    }
    
    
    protected void insertarEstudiante(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        double edad = 0;
        
        String edadString = request.getParameter("edad");
        
        if(edadString != null && !"".equals(edadString)){
            edad = Double.parseDouble(edadString);
        }
        
        Estudiante estudiante = new Estudiante(nombre, apellido, email, telefono, edad);
        
        int registroModificados = new EstudianteDaoJDBC().insertar(estudiante);
        this.accionDefault(request, response);
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion != null){
            switch (accion) {
                case "editar":
                    this.editarEstudiante(request, response);
                    break;
                case "eliminar":
                    this.eliminarEstudiante(request, response);
                    break;
                case "insertar":
                    this.insertarEstudiante(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        }else{
            this.accionDefault(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion != null){
            switch (accion) {
                case "modificar":
                    this.modificarEstudiante(request, response);
                    break;
                case "eliminar":
                    this.eliminarEstudiante(request, response);
                    break;
                case "insertar":
                    this.insertarEstudiante(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
            }
        }else{
            this.accionDefault(request, response);
        }
    }
    
    

}
