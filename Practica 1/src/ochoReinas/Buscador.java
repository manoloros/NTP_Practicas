package ochoReinas;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Clase que resuelve el problema de las ocho reinas
 * @author Manuel Ros Rodriguez
 */
public class Buscador {

    /**
     * Dato miembro para almacenar la dimension del tablero
     */
    private int dimension;

    /**
     * Constructor de la clase
     * @param dimension
     */
    public Buscador(int dimension){
        this.dimension = dimension;
    }

    /**
     * Metodo que inicia el proceso de resolucion del problema de las ocho reinas
     * y devuelve las soluciones que hay al problema
     *
     * @return
     */
    public ArrayList<Tablero> resolver(){
        return (ubicarReina(dimension-1));
    }

    /**
     * Algoritmo que resuelve el problema de las ocho reinas recursivamente,
     * cada vez que se llama se le da una fila e intentara colocar una reina en cada
     * columna, devuelve los tableros en los que ha conseguido colocar una reina
     *
     * @param fila La fila que le toca procesar
     * @return
     */
    public ArrayList<Tablero> ubicarReina(int fila){
        ArrayList<Tablero> nuevasSoluciones = new ArrayList<Tablero>();

        if (fila == -1){
            // caso base
            nuevasSoluciones.add(new Tablero(dimension));
        } else {
            // caso inductivo
            ArrayList<Tablero> soluciones = ubicarReina(fila-1);

            // Para cada tablero devuelto, intenta colocar una reina en cada columna y cada vez
            // que lo consigue guarda ese nuevo tablero en una lista
            soluciones.stream().forEach(tablero -> {
                IntStream.range(0,dimension).boxed().forEach(columna -> {
                    if (tablero.posicionSegura(new Celda(fila,columna))) {
                        Tablero nuevoTablero = new Tablero(tablero);
                        nuevoTablero.ponerReina(fila, columna);
                        nuevasSoluciones.add(nuevoTablero);
                    }
                });
            });
        }

        return (nuevasSoluciones);
    }
}
