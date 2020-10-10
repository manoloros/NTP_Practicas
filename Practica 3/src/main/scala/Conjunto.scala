
/**
  * Clase para definir conjuntos, especificando la propiedad que define al conjunto
  *
  * @param propiedad Dato miembro, propiedad que define al conjunto
  */
class Conjunto(val propiedad : (Int => Boolean)) {

  /**
    * Metodo que comprueba si un entero pertenece al conjunto
    *
    * @param x Elemento que queremos ver si pertenece al conjunto
    * @return
    */
  def apply(x : Int) = {
    propiedad(x)
  }

  /**
    * Metodo toString, devuelve todos los elementos que pertenecen
    * al conjunto y estan dentro del rango -LIMITE LIMITE
    *
    * @return
    */
  override def toString: String = {
    (-Conjunto.LIMITE to Conjunto.LIMITE).filter(propiedad).toString().substring(7).replace(")","")
  }

  /**
    * Metodo que devuelve un conjunto que es la union de este conjunto y
    * el conjunto pasado por parametro
    *
    * @param unConjunto
    * @return
    */
  def union(unConjunto : Conjunto) = {
    new Conjunto(x => propiedad(x) || unConjunto(x))
  }

  /**
    * Metodo que devuelve un conjunto que es la interseccion de este conjunto y
    * el conjunto pasado por parametro
    *
    * @param unConjunto
    * @return
    */
  def interseccion(unConjunto : Conjunto) = {
    new Conjunto(x => propiedad(x) && unConjunto(x))
  }

  /**
    * Metodo que devuelve un conjunto que es la diferencia de este conjunto y
    * el conjunto pasado por parametro
    *
    * @param unConjunto
    * @return
    */
  def diferencia(unConjunto : Conjunto) = {
    new Conjunto(x => propiedad(x) && !unConjunto(x))
  }

  /**
    * Metodo que devuelve un conjunto formado por los elementos de este conjunto
    * que cumplen la condicion pasada por parametro
    *
    * @param condicion Condicion que deben cumplir los elementos (Funcion tipo Int => Boolean)
    * @return
    */
  def filtrar(condicion : (Int => Boolean)) = {
    new Conjunto(x => propiedad(x) && condicion(x))
  }

  /**
    * Metodo que comprueba si un predicado pasado por parametro se cumple
    * para todos los elementos del conjunto (Se comprueba solo de -LIMITE a LIMITE)
    *
    * @param predicado Funcion tipo Int => Boolean
    * @return
    */
  def paraTodo(predicado : (Int => Boolean)) = {
    @annotation.tailrec
    def go(elemento : Int): Boolean = {
      if (elemento == Conjunto.LIMITE+1) true
      else if (propiedad(elemento))
        if (!predicado(elemento)) false
        else go(elemento+1)
      else go(elemento+1)
    }

    go(-Conjunto.LIMITE)
  }

  /**
    * Metodo que comprueba si un conjunto tiene al menos un elemento para el
    * que se cumple un cierto predicado (Se comprueba solo de -LIMITE a LIMITE)
    *
    * @param predicado Funcion tipo Int => Boolean
    * @return
    */
  def existe(predicado : (Int => Boolean)) = {
    @annotation.tailrec
    def go(elemento : Int): Boolean = {
      if (elemento == Conjunto.LIMITE+1) false
      else if (propiedad(elemento))
        if (predicado(elemento)) true
        else go(elemento+1)
      else go(elemento+1)
    }

    go(-Conjunto.LIMITE)
  }

  /**
    * Metodo que transforma un conjunto en otro aplicando una cierta funcion
    *
    * @param funcion Funcion tipo Int => Int
    * @return
    */
  def map(funcion : (Int => Int)) = {
    new Conjunto(y => existe((x : Int) => funcion(x) == y))
  }

}

// Companion object de la clase Conjunto
object Conjunto {
  val LIMITE = 20

  /**
    * Metodo que devuelve un conjunto formado por un unico elemento,
    * el indicado por parametro
    *
    * @param x Elemento que formara el conjunto
    * @return
    */
  def conjuntoUnElemento(x : Int) = {
    new Conjunto((y : Int) => y == x)
  }
}
