package ochoReinas;

import java.util.ArrayList;

/**
 * Clase que representa tableros
 * @author Manuel Ros Rodriguez
 */
public class Tablero {

    /**
     * Dato miembro para almacenar la dimension del tablero
     */
    private int dimension;

    /**
     * Dato miembro para almacenar las celdas donde hay colocada una reina
     */
    private ArrayList<Celda> contenido;

    /**
     * Constructor de la clase
     * @param dimension
     */
    public Tablero(int dimension){
        this.dimension = dimension;
        this.contenido = new ArrayList<Celda>();
    }

    /**
     * Constructor de la clase
     * @param tablero
     */
    public Tablero(Tablero tablero){
        this.dimension = tablero.getDimension();
        this.contenido = new ArrayList<Celda>();
        tablero.getContenido().forEach(celda -> {
            this.contenido.add(celda);
        });
    }

    /**
     * Metodo que devuelve el dato miembro contenido
     *
     * @return
     */
    public ArrayList<Celda> getContenido(){
        return (contenido);
    }

    /**
     * Metodo que devuelve la dimension del tablero
     *
     * @return
     */
    public int getDimension(){
        return (dimension);
    }

    /**
     * Metodo que coloca una reina en el tablero en la posicion indicada
     *
     * @param fila
     * @param columna
     */
    public void ponerReina(int fila, int columna){
        contenido.add(new Celda(fila, columna));
    }

    /**
     * Metodo que comprueba si se puede colocar una reina en una celda especifica
     *
     * @param celda
     * @return
     */
    public boolean posicionSegura(Celda celda){
        return (contenido.stream().filter(unaCelda -> (unaCelda.hayConflicto(celda))).
                count() == 0);
    }

    /**
     * Metodo toString, crea una representacion grafica del tablero
     *
     * @return
     */
    public String toString(){
        String tablero = "";

        for (int i=0; i<dimension; i++){
            for (int j=0; j<dimension; j++){
                boolean reina = false;

                for (int z=0; z<contenido.size(); z++)
                    if (contenido.get(z).getFila() == i && contenido.get(z).getColumna() == j)
                        reina = true;

                if (reina)
                    tablero = tablero + " R";
                else
                    tablero = tablero + " X";
            }
            tablero = tablero + "\n";
        }

        return (tablero);
    }
}
