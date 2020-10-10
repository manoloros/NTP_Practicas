package listado;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clase para procesar la informacion de los empleados, reparar errores y obtener informacion sobre los empleados
 * @author Manuel Ros Rodriguez
 */
public class ListadoEmpleados {
    /**
     * Dato miembro para almacenar a los empleados tal y como se encuentran
     * en el archivo de datos.txt
     */
    private List<Empleado> listadoArchivo;

    /**
     * Dato miembro para almacenar a los empleados como mapa con pares* (una vez reparados los datos leidos del archivo)
     * <dni - empleado>
     */
    private Map<String, Empleado> listado;

    /**
     * Patron que utilizaremos a la hora de procesar los archivos de asignacion
     */
    private static Pattern patronEspacios = Pattern.compile("\\s+");

    /**
     * Constructor de la clase
     * @param ruta Ruta del archivo datos.txt
     * @throws IOException
     */
    public ListadoEmpleados(String ruta) throws IOException {
        listadoArchivo = Files.lines(Paths.get(ruta)).map(linea -> new Empleado(linea)).collect(Collectors.toList());
    }

    /**
     * Metodo para obtener el numero de empleados que hay en listadoArchivo
     *
     * @return
     */
    public int obtenerNumeroEmpleadosArchivo(){
        return (listadoArchivo.size());
    }

    /**
     * Metodo que comprueba si hay DNI repetidos en los empleados
     *
     * @return
     */
    public boolean hayDnisRepetidosArchivo(){
        // Creamos un Map<DNI, veces que se repite DNI> y contamos cuantos DNI aparecen mas de una vez
        return (listadoArchivo.stream().collect(Collectors.groupingBy(Empleado::obtenerDni, Collectors.counting())).
                entrySet().stream().filter(entrada -> entrada.getValue() > 1).
                count() != 0);
    }

    /**
     * Metodo que devuelve los empleados con DNI repetido,
     * en un Map cuya clave es DNI y valor es una lista de empleados
     *
     * @return
     */
    public Map<String, List<Empleado>> obtenerDnisRepetidosArchivo(){
        // Obtenemos los DNI que se repiten
        List<String> dniRepetidos = listadoArchivo.stream().
                collect(Collectors.groupingBy(Empleado::obtenerDni, Collectors.counting())).
                entrySet().stream().filter(entrada -> entrada.getValue() > 1).
                map(entrada -> entrada.getKey()).collect(Collectors.toList());

        // Nos quedamos con los empleados cuyo DNI se repite y creamos el map que se devuelve
        return (listadoArchivo.stream().filter(empleado -> dniRepetidos.contains(empleado.obtenerDni())).
                collect(Collectors.groupingBy(Empleado::obtenerDni)));
    }

    /**
     * Metodo que cuenta cuantos empleados tienen un DNI repetido
     *
     * @return
     */
    public int contarEmpleadosDnisRepetidos(){
        return (Math.toIntExact(obtenerDnisRepetidosArchivo().entrySet().
                stream().flatMap((entrada) -> entrada.getValue().stream()).count()));
    }

    /**
     * Metodo que repara los DNI repetidos, a cada empleado con un DNI
     * repetido le asigna un nuevo DNI aleatorio
     *
     * @param listaRepeticion Map donde tenemos los empleados con DNI repetido, cuya clave es DNI y valor
     *                       es una lista de empleados
     */
    public void repararDnisRepetidos(Map<String, List<Empleado>> listaRepeticion){
        listaRepeticion.forEach((dni,empleados) -> {
            empleados.forEach((empleado) -> {
                empleado.asignarDniAleatorio();
            });
        });

        if (contarEmpleadosDnisRepetidos() != 0)
            repararDnisRepetidos(obtenerDnisRepetidosArchivo());
    }

    /**
     * Metodo que comprueba si hay correos repetidos en los empleados
     *
     * @return
     */
    public boolean hayCorreosRepetidosArchivo(){
        // Creamos un Map<correo, veces que se repite correo> y contamos cuantos correos aparecen mas de una vez
        return (listadoArchivo.stream().collect(Collectors.groupingBy(Empleado::obtenerCorreo, Collectors.counting())).
                entrySet().stream().filter(entrada -> entrada.getValue() > 1).
                count() != 0);
    }

    /**
     * Metodo que devuelve los empleados con correo repetido,
     * en un Map cuya clave es correo y valor es una lista de empleados
     *
     * @return
     */
    public Map<String, List<Empleado>> obtenerCorreosRepetidosArchivo(){
        // Obtenemos los correos que se repiten
        List<String> correosRepetidos = listadoArchivo.stream().
                collect(Collectors.groupingBy(Empleado::obtenerCorreo, Collectors.counting())).
                entrySet().stream().filter(entrada -> entrada.getValue() > 1).
                map(entrada -> entrada.getKey()).collect(Collectors.toList());

        // Nos quedamos con los empleados cuyo correo se repite y creamos el map que se devuelve
        return (listadoArchivo.stream().filter(empleado -> correosRepetidos.contains(empleado.obtenerCorreo())).
                collect(Collectors.groupingBy(Empleado::obtenerCorreo)));
    }

    /**
     * Metodo que cuenta cuantos empleados tienen un correo repetido
     *
     * @return
     */
    public int contarCorreosRepetidos(){
        return (Math.toIntExact(obtenerCorreosRepetidosArchivo().entrySet().
                stream().flatMap((entrada) -> entrada.getValue().stream()).count()));
    }

    /**
     * Metodo que repara los correos repetidos, a cada empleado con un correo
     * repetido le asigna un nuevo correo, utilizando su nombre y apellidos completos
     *
     * @param listaRepeticiones Map donde tenemos los empleados con correo repetido, cuya clave es correo y valor
     *                         es una lista de empleados
     */
    public void repararCorreosRepetidos(Map<String, List<Empleado>> listaRepeticiones){
        listaRepeticiones.forEach((correo,empleados) -> {
            empleados.forEach((empleado) -> {
                empleado.generarCorreoCompleto();
            });
        });

        if (contarCorreosRepetidos() != 0)
            repararCorreosRepetidos(obtenerCorreosRepetidosArchivo());
    }

    /**
     * Metodo que almacena la informacion de listadoArchivo en la variable listado
     *
     */
    public void validarListaArchivo(){
        listado = new TreeMap<String, Empleado>();

        listadoArchivo.forEach(empleado -> {
            listado.put(empleado.obtenerDni(),empleado);
        });
    }

    /**
     * Metodo para obtener el numero de empleados que hay en listado
     *
     * @return 
     */
    public int obtenerNumeroEmpleadosListado(){
        return (listado.size());
    }

    /**
     * Metodo que asigna a cada empleado el sector que le corresponde,
     * obtiene esa informacion del archivo de asignacion de sector que le indicamos
     *
     * Devuelve el numero de errores que han ocurrido en la asignacion
     *
     * @param rutaArchivo Ruta del archivo de asignacion de sector
     * @throws IOException
     * @return
     */
    public long cargarArchivoAsignacionSector(String rutaArchivo) throws IOException {
        List<String> lineas = Files.lines(Paths.get(rutaArchivo)).collect(Collectors.toList());

        Sector sector = procesarNombreSector(lineas.get(0));

        // Realizamos la asignacion del sector y contamos los errores que ha dado
        long errores = lineas.stream().skip(2).
        map(linea -> procesarAsignacionSector(sector, linea)).
        filter(flag -> flag == false).count();

        return (errores);
    }

    /**
     * Metodo que convierte la linea del archivo de asignacion que contiene el sector
     * en el valor Sector correspondiente
     *
     * @param cadena Linea del archivo de asignacion que contiene el sector
     * @return
     */
    public Sector procesarNombreSector(String cadena){
        List<String> infos = patronEspacios.splitAsStream(cadena).
        collect(Collectors.toList());

        Predicate<Sector> condicion = sector -> (sector.name().equals(infos.get(0)));

        return (Arrays.stream(Sector.values()).
        filter(condicion).
        findFirst().get());
    }

    /**
     * Metodo que comprueba si el empleado existe y en caso de existir
     * le asigna el sector que le corresponde
     *
     * Devuelve true si el empleado existe y se le ha asignado el sector, false si no
     *
     * @param sector Sector que tiene que asignar al empleado
     * @param linea Linea del archivo de asignacion, que contiene el DNI del empleado
     * @return
     */
    public boolean procesarAsignacionSector(Sector sector, String linea){
        List<String> infos = patronEspacios.splitAsStream(linea).
        collect(Collectors.toList());

        Empleado empleado = listado.get(infos.get(0));

        if (empleado != null) {
            empleado.asignarSector(sector);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo que asigna a cada empleado la ruta que le corresponde,
     * obtiene esa informacion del archivo de asignacion de ruta que le indicamos
     *
     * Devuelve el numero de errores que han ocurrido en la asignacion
     *
     * @param rutaArchivo Ruta del archivo de asignacion de ruta
     * @throws IOException
     * @return
     */
    public long cargarArchivoAsignacionRuta(String rutaArchivo) throws IOException {
        List<String> lineas = Files.lines(Paths.get(rutaArchivo)).collect(Collectors.toList());

        Ruta ruta = procesarNombreRuta(lineas.get(0));

        // Realizamos la asignacion de la ruta y contamos los errores que ha dado
        long errores = lineas.stream().skip(2).
                map(linea -> procesarAsignacionRuta(ruta, linea)).
                filter(flag -> flag == false).count();

        return (errores);
    }

    /**
     * Metodo que convierte la linea del archivo de asignacion que contiene la ruta
     * en el valor Ruta correspondiente
     *
     * @param cadena Linea del archivo de asignacion que contiene la ruta
     * @return
     */
    public Ruta procesarNombreRuta(String cadena){
        List<String> infos = patronEspacios.splitAsStream(cadena).
                collect(Collectors.toList());

        Predicate<Ruta> condicion = ruta -> (ruta.name().equals(infos.get(0)));

        return (Arrays.stream(Ruta.values()).
                filter(condicion).
                findFirst().get());
    }

    /**
     * Metodo que comprueba si el empleado existe y en caso de existir
     * le asigna la ruta que le corresponde
     *
     * Devuelve true si el empleado existe y se le ha asignado la ruta, false si no
     *
     * @param ruta Ruta que tiene que asignar al empleado
     * @param linea Linea del archivo de asignacion, que contiene el DNI del empleado
     * @return
     */
    public boolean procesarAsignacionRuta(Ruta ruta, String linea){
        List<String> infos = patronEspacios.splitAsStream(linea).
                collect(Collectors.toList());

        Empleado empleado = listado.get(infos.get(0));

        if (empleado != null) {
            empleado.asignarRuta(ruta);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo que devuelve los empleados cuyo sector asignado es el indicado en los
     * parametros y que no tienen ruta asignada
     *
     * @param sector Sector al que deben pertenecer los empleados
     * @return
     */
    public List<Empleado> buscarEmpleadosSinRuta(Sector sector){
        // Nos quedamos con los empleados que cumplan ambas condiciones y se devuelven
        return (listado.entrySet().stream().filter(entrada -> (entrada.getValue().perteneceSector(sector)
                && entrada.getValue().obtenerRuta() == Ruta.NORUTA)).
                map(entrada -> entrada.getValue()).collect(Collectors.toList()));
    }

    /**
     * Metodo que devuelve el numero de empleados asignados a cada ruta
     * del sector especificado, en un Map<Ruta, numero de empleados>
     *
     * @param sector Sector al que deben pertenecer los empleados
     * @return
     */
    public Map<Ruta, Long> obtenerContadoresRuta(Sector sector){
        // Nos quedamos con los empleados que pertenezcan al sector y se crea el Map
        return (listado.entrySet().stream().filter(entrada -> (entrada.getValue().perteneceSector(sector))).
                map(entrada -> entrada.getValue()).
                collect(Collectors.groupingBy(Empleado::obtenerRuta, TreeMap::new, Collectors.counting())));
    }

    /**
     * Metodo que devuelve el numero de empleados asignados a cada sector
     *
     * @return
     */
    public List<Long> obtenerContadoresSectores(){
        List<Long> empleadosSector = new ArrayList<>();

        // Para cada sector, sumamos el numero de empleados que tiene en cada ruta
        obtenerContadoresSectorRuta().entrySet().stream().forEach(entrada -> {
            empleadosSector.add(entrada.getValue().entrySet().stream().
                    map(entradaRuta -> entradaRuta.getValue()).
                    reduce(0L, (x, y) -> {
                        return (x + y);
                    }));
        });

        return (empleadosSector);
    }

    /**
     * Metodo que devuelve el numero de empleados asignados a cada ruta
     * de los sectores que hay, en un Map<Sector, Map<Ruta, numero de empleados>
     *
     * @return
     */
    public Map<Sector, Map<Ruta, Long>> obtenerContadoresSectorRuta(){
        Map<Sector, Map<Ruta, Long>> resultado = new TreeMap<>();

        Arrays.stream(Sector.values()).forEach(sector -> {
            resultado.put(sector, obtenerContadoresRuta(sector));
        });

        return (resultado);
    }

    /**
     * Metodo que devuelve los empleados sin ruta asignada y sin sector asignado
     *
     * @return
     */
    public List<Empleado> buscarEmpleadosSinSectorSinRuta(){
        return (buscarEmpleadosSinRuta(Sector.NOSECTOR));
    }

    /**
     * Metodo que devuelve los empleados con sector asignado pero que no tienen ruta asignada
     *
     * @return
     */
    public List<Empleado> buscarEmpleadosConSectorSinRuta(){
        List<Empleado> empleados = buscarEmpleadosSinRuta(Sector.SECTOR1);
        empleados.addAll(buscarEmpleadosSinRuta(Sector.SECTOR2));

        return (empleados);
    }

    /**
     * Metodo que devuelve los empleados cuya ruta asignada es la indicada en los
     * parametros y que no tienen sector asignado
     *
     * @param ruta Ruta a la que deben pertenecer los empleados
     * @return
     */
    public List<Empleado> buscarEmpleadosSinSector(Ruta ruta){
        // Nos quedamos con los empleados que cumplan ambas condiciones y se devuelven
        return (listado.entrySet().stream().filter(entrada -> (entrada.getValue().perteneceSector(Sector.NOSECTOR)
                && entrada.getValue().obtenerRuta() == ruta)).
                map(entrada -> entrada.getValue()).collect(Collectors.toList()));
    }

    /**
     * Metodo que devuelve los empleados con ruta asignada pero que no tienen sector asignado
     *
     * @return
     */
    public List<Empleado> buscarEmpleadosSinSectorConRuta(){
        List<Empleado> empleados = buscarEmpleadosSinSector(Ruta.RUTA1);
        empleados.addAll(buscarEmpleadosSinSector(Ruta.RUTA2));
        empleados.addAll(buscarEmpleadosSinSector(Ruta.RUTA3));

        return (empleados);
    }

}
