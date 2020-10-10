package ochoReinas;

import static java.lang.Math.abs;

/**
 * Clase que representa celdas
 * @author Manuel Ros Rodriguez
 */
public class Celda {

    /**
     * Dato miembro para almacenar la fila de la celda
     */
    private int fila;

    /**
     * Dato miembro para almacenar la columna de la celda
     */
    private int columna;

    /**
     * Constructor de la clase
     * @param fila
     * @param columna
     */
    public Celda(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Metodo que devuelve la fila de la celda
     *
     * @return
     */
    public int getFila(){
        return (fila);
    }

    /**
     * Metodo que devuelve la columna de la celda
     *
     * @return
     */
    public int getColumna(){
        return (columna);
    }

    /**
     * Metodo que comprueba si esta celda esta en la misma fila, columna
     * o diagonal que la otra celda, devuelve true si lo esta, false si no
     *
     * @return
     */
    public boolean hayConflicto(Celda otraCelda){
        boolean conflicto = false;

        if (fila == otraCelda.getFila() || columna == otraCelda.getColumna() ||
            abs(fila - otraCelda.getFila()) == abs(columna - otraCelda.getColumna()))
            conflicto = true;

        return (conflicto);
    }
}
