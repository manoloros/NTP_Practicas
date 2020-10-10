object trianguloPascal {

  /**
    * Funcion que calcula el valor del triangulo de pascal que corresponde a la posicion indicada
    * por los parametros
    *
    * @param fila
    * @param columna
    * @return
    */
  def calcularValorTrianguloPascal(fila: Int, columna: Int): Int = {
    if (columna == 0 || columna == fila) 1
    else calcularValorTrianguloPascal(fila-1, columna) + calcularValorTrianguloPascal(fila-1, columna-1)
  }

}
