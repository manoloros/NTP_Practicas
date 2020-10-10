object busquedaSaltosGenerica {
  
  /**
    * Funcion que realiza una busqueda a saltos y busca el elemento indicado por parametro
    * en el vector indicado por parametro utilizando el criterio indicado por parametro
    *
    * Devuelve el indice del elemento si lo ha encontrado, -1 si no lo ha encontrado
    *
    * @param coleccion Array sobre el que buscaremos
    * @param aBuscar Elemento que queremos buscar
    * @param criterio Criterio a utilizar a la hora de realizar la busqueda
    * @return
    */
  def busquedaSaltos[A](coleccion : Array[A], aBuscar: A, criterio : (A,A) => Boolean) : Int = {
    val tamBloque = math.ceil(math.sqrt(coleccion.length)).toInt

    @annotation.tailrec
    def go(indiceInicio : Int, indiceFin : Int) : Int = {

      var nuevoIndiceFin = indiceFin
      if (nuevoIndiceFin > coleccion.length-1) nuevoIndiceFin = coleccion.length-1

      if (coleccion(nuevoIndiceFin) == aBuscar) nuevoIndiceFin
      else if (criterio(aBuscar, coleccion(nuevoIndiceFin))) {
        var indice = -1
        for (i <- indiceInicio until nuevoIndiceFin)
          if (aBuscar == coleccion(i) && indice == -1)
            indice = i
        indice
      }
      else if (indiceFin > coleccion.length-1) -1
      else go(indiceInicio+tamBloque, nuevoIndiceFin+tamBloque)
    }

    go(0, tamBloque-1)
  }

}
