
import listado.Empleado;
import listado.Ruta;
import listado.Sector;
import org.junit.BeforeClass;
import org.junit.Test;

import listado.ListadoEmpleados;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Práctica 1 NTP
 */
public class ListadoTestP2 {
   private static ListadoEmpleados listado;

   /**
    * Codigo a ejecutar antes de realizar las llamadas a los métodos
    * de la clase; incluso antes de la propia instanciación de la
    * clase. Por eso el método debe ser estatico
    */
   @BeforeClass
   public static void inicializacion() {
      System.out.println("Metodo inicializacion conjunto pruebas");
      // Se genera el listado de empleados
      try {
         listado = new ListadoEmpleados("./data/datos.txt");
      } catch (IOException e) {
         System.out.println("Error en lectura de archivo de datos");
      }

      // Se reparan los problemas y se pasan los datos al datos miembro
      // listado
      Map<String, List<Empleado>> dnisRepetidos=listado.obtenerDnisRepetidosArchivo();
      listado.repararDnisRepetidos(dnisRepetidos);
      Map<String, List<Empleado>> correosRepetidos = listado.obtenerCorreosRepetidosArchivo();
      listado.repararCorreosRepetidos(correosRepetidos);
      listado.validarListaArchivo();

      // Se leen ahora los archivos de asignaciones de sectores y rutas
      try{
         long errores;
         errores = listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR1.txt");
         System.out.println("Errores archivo asignacion SECTOR1: " + errores);
         errores = listado.cargarArchivoAsignacionSector("./data/asignacionSECTOR2.txt");
         System.out.println("Errores archivo asignacion SECTOR2: "+errores);
         errores = listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA1.txt");
         System.out.println("Errores archivo asignacion RUTA1: "+errores);
         errores = listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA2.txt");
         System.out.println("Errores archivo asignacion RUTA2: "+errores);
         errores = listado.cargarArchivoAsignacionRuta("./data/asignacionRUTA3.txt");
         System.out.println("Errores archivo asignacion RUTA3: "+errores);
      } catch(IOException e){
         System.out.println("Problema lectura datos asignacion");
         System.exit(0);
      }
   }

   /**
    * Test que comprueba la busqueda de empleados sin rutas asignadas pero
    * con un sector especifico asignado
    *
    */
   @Test
   public void testBusquedaEmpleadosSinRuta() {
      // Se obtienen los empleados sin ruta de cada sector
      // y se comprueba su valor
      int res1, res2, res3;
      res1=listado.buscarEmpleadosSinRuta(Sector.NOSECTOR).size();
      res2=listado.buscarEmpleadosSinRuta(Sector.SECTOR1).size();
      res3=listado.buscarEmpleadosSinRuta(Sector.SECTOR2).size();
      System.out.println("res1: "+res1+" res2: "+res2+ " res3: "+res3);
      assertTrue (res1 == 418);
      assertTrue (res2 == 432);
      assertTrue (res3 == 399);
   }

   /**
    * Test que comprueba el numero de empleados asignados a cada ruta
    * de un sector especifico
    */
   @Test
   public void testObtenerContadoresSector1() {
      // Se obtienen los contadores para el sector 1
      Map<Ruta, Long> contadores = listado.obtenerContadoresRuta(Sector.SECTOR1);
      contadores.keySet().stream().forEach(key -> System.out.println(
         key.toString() + "- " + contadores.get(key)));
      // Se comprueba que los valores son RUTA1 = 401, RUTA2 = 437, RUTA3 = 403, NORUTA = 432
      Long contadoresReferencia[] = {401L, 437L, 403L, 432L};
      Long contadoresCalculados[] = new Long[4];
      assertArrayEquals(contadores.values().toArray(contadoresCalculados),
         contadoresReferencia);
   }

   /**
    * Test que comprueba el numero de empleados asignados a cada ruta
    * de todos los sectores
    *
    * @throws Exception
    */
   @Test
   public void testObtenerContadoresSector() throws Exception {
      // Se obtienen los contadores para todos los grupos
      Map<Sector, Map<Ruta, Long>> contadores =
         listado.obtenerContadoresSectorRuta();

      Long contadoresReferenciaSector1[] = {401L, 437L, 403L, 432L};
      Long contadoresReferenciaSector2[] = {428L, 425L, 388L, 399L};
      Long contadoresReferenciaNoSector[] = {446L, 414L, 409L, 418L};

      // Se comprueban los resultados del metodo con los de referencia
      Long contadoresCalculados[] = new Long[4];
      assertArrayEquals(contadores.get(Sector.NOSECTOR).values().
         toArray(contadoresCalculados), contadoresReferenciaNoSector);
      assertArrayEquals(contadores.get(Sector.SECTOR1).values().
         toArray(contadoresCalculados), contadoresReferenciaSector1);
      assertArrayEquals(contadores.get(Sector.SECTOR2).values().
         toArray(contadoresCalculados), contadoresReferenciaSector2);
   }

   /**
    * Test para comprobar la reparacion de DNI
    */
   @Test
   public void testComprobarReparacionDni() {
      assertTrue(listado.contarEmpleadosDnisRepetidos() == 0);
   }

   /**
    * Test para comprobar la reparacion de correos
    */
   @Test
   public void testComprobarReparacionCorreos() {
      assertTrue(listado.contarCorreosRepetidos() == 0);
   }

   /**
    * Test para comprobar que la variable listado tenga el mismo numero de empleados que
    * la variable listadoArchivo
    */
   @Test
   public void testComprobarValidarListaArchivo() {
      assertTrue(listado.obtenerNumeroEmpleadosArchivo() == listado.obtenerNumeroEmpleadosListado());
   }

   /**
    * Test para comprobar que se devuelve el numero correcto de empleados asignados a cada sector
    */
   @Test
   public void testComprobarObtenerContadoresSectores() {
      Long contadoresReferenciaSectores[] = {1673L, 1640L, 1687L};
      List<Long> contadoresSectores = listado.obtenerContadoresSectores();

      Long contadoresCalculados[] = new Long[3];
      contadoresSectores.toArray(contadoresCalculados);
      assertArrayEquals(contadoresCalculados, contadoresReferenciaSectores);
   }

   /**
    * Test para comprobar que se devuelve el numero correcto de empleados sin ruta asignada y
    * sin sector asignado
    */
   @Test
   public void testComprobarBuscarEmpleadosSinSectorSinRuta() {
      assertTrue(listado.buscarEmpleadosSinSectorSinRuta().size() == 418);
   }

   /**
    * Test para comprobar que se devuelve el numero correcto de empleados con sector asignado pero que
    * no tienen ruta asignada
    */
   @Test
   public void testComprobarBuscarEmpleadosConSectorSinRuta() {
      assertTrue(listado.buscarEmpleadosConSectorSinRuta().size() == 831);
   }

   /**
    * Test para comprobar que se devuelve el numero correcto de empleados que no tienen sector asignado y
    * que pertenecen a la ruta especificada
    */
   @Test
   public void testComprobarBuscarEmpleadosSinSector() {
      int numEmpleados[] = new int[4];
      numEmpleados[0] = listado.buscarEmpleadosSinSector(Ruta.RUTA1).size();
      numEmpleados[1] = listado.buscarEmpleadosSinSector(Ruta.RUTA2).size();
      numEmpleados[2] = listado.buscarEmpleadosSinSector(Ruta.RUTA3).size();
      numEmpleados[3] = listado.buscarEmpleadosSinSector(Ruta.NORUTA).size();
      int numEmpleadosReferencia[] = {446, 414, 409, 418};
      assertArrayEquals(numEmpleados, numEmpleadosReferencia);
   }

   /**
    * Test para comprobar que se devuelve el numero correcto de empleados con ruta asignada pero que
    * no tienen sector asignado
    */
   @Test
   public void testComprobarBuscarEmpleadosSinSectorConRuta() {
      assertTrue(listado.buscarEmpleadosSinSectorConRuta().size() == 1269);
   }
}