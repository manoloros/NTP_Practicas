object contadorCambiosMoneda {

  /**
    * Funcion que comprueba si nos encontramos en una situacion donde no
    * hay solucion, es decir, donde no existe ninguna moneda que sumandosela al
    * valor sumado nos de un valor menor o igual que cantidad
    *
    * @param cantidad Cantidad que se quiere sumar
    * @param suma Cantidad sumada hasta ahora
    * @param monedas Monedas disponibles
    * @return
    */
  def noHaySolucion(cantidad : Int, suma : Int, monedas : List[Int]) : Boolean = {
    var noHaySolucion = true
    for (moneda <- monedas) {
      if (suma + moneda <= cantidad)
        noHaySolucion = false
    }
    noHaySolucion
  }

  /**
    * Funcion que comprueba si existe una solucion equivalente a la pasada por parametro
    * en la variable soluciones, a solucion equivalente nos referimos a una solucion con los mismos
    * elementos sin importar el orden
    *
    * @param unaSolucion
    * @param soluciones Conjunto de soluciones donde buscamos una solucion equivalente
    * @return
    */
  def haySolucionEquivalente(unaSolucion : List[Int], soluciones : List[List[Int]]) : Boolean = {
    var haySolucionEquivalente = false

    for (solucion <- soluciones)
      if (!haySolucionEquivalente)
        haySolucionEquivalente = (unaSolucion.sorted == solucion.sorted)

    haySolucionEquivalente
  }

  /**
    * Funcion que calcula todas las posibles combinaciones de monedas que suman
    * la cantidad pasada por parametros usando las monedas pasadas por parametro
    *
    * @param cantidad Cantidad que se quiere sumar
    * @param monedas Monedas disponibles
    * @return
    */
  def listarCambiosPosibles(cantidad: Int, monedas: List[Int]): List[List[Int]] = {
    def go(suma : Int, solucion : List[Int]) : List[List[Int]] = {
      if (suma == cantidad) List(solucion)
      else if (noHaySolucion(cantidad, suma, monedas)) List()
      else {
        var soluciones : List[List[Int]] = List()
        for (moneda <- monedas)
          soluciones = soluciones ::: go(suma + moneda, moneda :: solucion)
        soluciones
      }
    }

    // Eliminamos soluciones equivalentes
    var solucionesProvisionales = go(0, List())
    var soluciones : List[List[Int]] = List()

    for (solucion <- solucionesProvisionales)
      if (!haySolucionEquivalente(solucion, soluciones))
        soluciones = solucion :: soluciones

    soluciones
  }

}
