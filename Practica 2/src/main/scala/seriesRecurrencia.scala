object seriesRecurrencia {

  /**
    * Funcion que calcula el valor en el indice indicado por indice de la serie especificada
    * por el primer valor, el segundo, y la relacion entre dos valores para obtener un tercero
    *
    * @param indice Elemento de la serie que queremos calcular
    * @param primerValor Primer valor de la serie
    * @param segundoValor Segundo valor de la serie
    * @param relacion La relacion entre dos valores que nos da un tercero
    * @return
    */
  def calcularValorSerieGenerica(indice : Int, primerValor : Long, segundoValor : Long, relacion : (Long, Long) => Long) : Long = {
    @annotation.tailrec
    def go(n : Int, prev : Long, act : Long) : Long = {
      if (n == 0) prev
      else go(n-1, act, relacion(act, prev))
    }

    go(indice, primerValor, segundoValor)
  }

}
