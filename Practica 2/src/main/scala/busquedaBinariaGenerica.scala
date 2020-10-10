object busquedaBinariaGenerica {

  /**
    * Funcion que realiza una busqueda binaria y busca el elemento indicado por parametro
    * en el vector indicado por parametro utilizando el criterio indicado por parametro
    *
    * Devuelve el indice del elemento si lo ha encontrado, -1 si no lo ha encontrado
    *
    * @param coleccion Array sobre el que buscaremos
    * @param aBuscar Elemento que queremos buscar
    * @param criterio Criterio a utilizar a la hora de realizar la busqueda
    * @return
    */
  def busquedaBinaria[A](coleccion : Array[A], aBuscar: A, criterio : (A,A) => Boolean) : Int = {
    @annotation.tailrec
    def go(indiceInicio : Int, indiceFin : Int) : Int = {
      val tam = indiceFin - indiceInicio + 1

      val indice = indiceInicio + math.ceil(tam/2.0).toInt-1

      if (tam == 0) -1
      else if (coleccion(indice) == aBuscar) indice
      else if (tam == 1) -1
      else if (criterio(aBuscar, coleccion(indice))) go(indiceInicio, indice-1)
      else go(indice+1, indiceFin)
    }

    go(0, coleccion.length-1)
  }

}
