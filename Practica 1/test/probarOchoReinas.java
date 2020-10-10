import ochoReinas.Buscador;
import ochoReinas.Celda;
import ochoReinas.Tablero;

import java.util.ArrayList;

/**
 * Clase para comprobar la solucion al problema de las ocho reinas
 * @author Manuel Ros Rodriguez
 */
public class probarOchoReinas {

    public static void main(String[] args){
        int dimension = 4;
        Buscador buscador = new Buscador(dimension);
        ArrayList<Tablero> soluciones = buscador.resolver();

        System.out.println("Hay "+soluciones.size()+" soluciones para tableros de dimension "+dimension);
        System.out.println("");
        soluciones.forEach(tablero -> {
            System.out.println(tablero.toString());
        });
    }
}
