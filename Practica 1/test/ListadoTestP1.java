
import listado.Sector;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import listado.ListadoEmpleados;
import listado.Ruta;

import java.io.IOException;
import java.util.Map;

/**
 * Práctica 1 NTP
 */
public class ListadoTestP1 {
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
      };
   }

   /**
    * Test para comprobar que se ha leido de forma correcta la
    * informacion de los empleados
    *
    * @throws Exception
    */
   @Test
   public void testConstruccionListado() throws Exception {
      assertTrue(listado.obtenerNumeroEmpleadosArchivo() == 5000);
   }

   /**
    * Test para comprobar la deteccion de DNI repetidos
    */
   @Test
   public void testComprobarHayDnisRepetidos() {
      assertTrue(listado.hayDnisRepetidosArchivo() == true);
   }

   /**
    * Test para comprobar el numero de empleados con DNI
    * repetido
    */
   @Test
   public void testComprobarContadoresDnisRepetidosArchivo() {
      assertTrue(listado.contarEmpleadosDnisRepetidos() == 4);
   }

   /**
    * Test para comprobar la deteccion de correos repetidos
    */
   @Test
   public void testComprobarHayCorreosRepetidos() {
      assertTrue(listado.hayCorreosRepetidosArchivo() == true);
   }

   /**
    * Test para comprobar el numero de empleados con correo
    * repetido
    */
   @Test
   public void testComprobarContadoresCorreosRepetidosArchivo() {
      assertTrue(listado.contarCorreosRepetidos() == 315);
   }
}