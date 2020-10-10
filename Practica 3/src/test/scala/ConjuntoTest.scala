import org.scalatest.FunSuite

class ConjuntoTest extends FunSuite  {

  /**
    * Test para comprobar la pertenencia de elementos a un conjunto (Metodo apply)
    */
  test("Prueba apply") {
    val funcion = (x : Int) => x >= 0
    val conjunto1 = new Conjunto(funcion)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall(elemento => conjunto1(elemento) == funcion(elemento)))
  }

  /**
    * Test para comprobar la visualizacion del conjunto (Metodo toString)
    */
  test("Prueba toString") {
    val conjunto1 = new Conjunto((x : Int) => x >= 0)
    var solucion : String = ""

    for (elemento <- -Conjunto.LIMITE to Conjunto.LIMITE)
      if (conjunto1(elemento))
        solucion = solucion + elemento.toString + ", "
    solucion = solucion.substring(0, solucion.length-2)

    assert(conjunto1.toString.equals(solucion))
  }

  /**
    * Test para comprobar la creacion de un conjunto formado por un unico elemento (Metodo conjuntoUnElemento)
    */
  test("Prueba conjuntoUnElemento") {
    val elementoUnico = 0
    val conjunto1 = Conjunto.conjuntoUnElemento(elementoUnico)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall(elemento => (elemento == elementoUnico) == conjunto1(elemento)))
  }

  /**
    * Test para comprobar la union de dos conjuntos (Metodo union)
    */
  test("Prueba union") {
    val conjunto1 = new Conjunto((x : Int) => x > 5)
    val conjunto2 = new Conjunto((x : Int) => x < -5)
    val conjuntoUnion = conjunto1.union(conjunto2)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall(elemento => (conjunto1(elemento) || conjunto2(elemento)) == conjuntoUnion(elemento)))
  }

  /**
    * Test para comprobar la union de dos conjuntos (Metodo union)
    */
  test("Prueba union 2") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val conjunto2 = new Conjunto((x : Int) => x < 5)
    val conjuntoUnion = conjunto1.union(conjunto2)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall(elemento => (conjunto1(elemento) || conjunto2(elemento)) == conjuntoUnion(elemento)))
  }

  /**
    * Test para comprobar la interseccion de dos conjuntos (Metodo interseccion)
    */
  test("Prueba interseccion") {
    val conjunto1 = new Conjunto((x : Int) => x > 5)
    val conjunto2 = new Conjunto((x : Int) => x < -5)
    val conjuntoInterseccion = conjunto1.interseccion(conjunto2)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall((elemento : Int) => (conjunto1(elemento) && conjunto2(elemento)) == conjuntoInterseccion(elemento)))
  }

  /**
    * Test para comprobar la interseccion de dos conjuntos (Metodo interseccion)
    */
  test("Prueba interseccion 2") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val conjunto2 = new Conjunto((x : Int) => x < 5)
    val conjuntoInterseccion = conjunto1.interseccion(conjunto2)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall((elemento : Int) => (conjunto1(elemento) && conjunto2(elemento)) == conjuntoInterseccion(elemento)))
  }

  /**
    * Test para comprobar la diferencia de dos conjuntos (Metodo diferencia)
    */
  test("Prueba diferencia") {
    val conjunto1 = new Conjunto((x : Int) => x > 5)
    val conjunto2 = new Conjunto((x : Int) => x < -5)
    val conjuntoDiferencia = conjunto1.diferencia(conjunto2)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall(elemento => (conjunto1(elemento) && !conjunto2(elemento)) == conjuntoDiferencia(elemento)))
  }

  /**
    * Test para comprobar la diferencia de dos conjuntos (Metodo diferencia)
    */
  test("Prueba diferencia 2") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val conjunto2 = new Conjunto((x : Int) => x < 5)
    val conjuntoDiferencia = conjunto1.diferencia(conjunto2)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall(elemento => (conjunto1(elemento) && !conjunto2(elemento)) == conjuntoDiferencia(elemento)))
  }

  /**
    * Test para comprobar el filtrado de un conjunto (Metodo filtrar)
    */
  test("Prueba filtrar") {
    val conjunto1 = new Conjunto((x : Int) => x > -5)
    val funcion = (x : Int) => x < 5
    val conjuntoFiltrar = conjunto1.filtrar(funcion)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall((elemento : Int) => (conjunto1(elemento) && funcion(elemento)) == conjuntoFiltrar(elemento)))
  }

  /**
    * Test para comprobar el filtrado de un conjunto (Metodo filtrar)
    */
  test("Prueba filtrar 2") {
    val conjunto1 = new Conjunto((x : Int) => x < -5)
    val funcion = (x : Int) => x > 5
    val conjuntoFiltrar = conjunto1.filtrar(funcion)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall((elemento : Int) => (conjunto1(elemento) && funcion(elemento)) == conjuntoFiltrar(elemento)))
  }

  /**
    * Test para comprobar si todos los elementos de un conjunto cumplen un predicado (Metodo paraTodo)
    */
  test("Prueba paraTodo") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val predicado = (x : Int) => x % 2 == 0
    val paraTodo = conjunto1.paraTodo(predicado)

    assert(!paraTodo)
  }

  /**
    * Test para comprobar si todos los elementos de un conjunto cumplen un predicado (Metodo paraTodo)
    */
  test("Prueba paraTodo 2") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val predicado = (x : Int) => x >= 1
    val paraTodo = conjunto1.paraTodo(predicado)

    assert(paraTodo)
  }

  /**
    * Test para comprobar si algun elemento de un conjunto cumple un predicado (Metodo existe)
    */
  test("Prueba existe") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val predicado = (x : Int) => x < 0
    val existe = conjunto1.existe(predicado)

    assert(!existe)
  }

  /**
    * Test para comprobar si algun elemento de un conjunto cumple un predicado (Metodo existe)
    */
  test("Prueba existe 2") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val predicado = (x : Int) => x % 2 == 0
    val existe = conjunto1.existe(predicado)

    assert(existe)
  }

  /**
    * Test para comprobar la transformacion de un conjunto en otro aplicando una funcion (Metodo map)
    */
  test("Prueba map") {
    val conjunto1 = new Conjunto((x : Int) => x > 0)
    val funcion = (x : Int) => x*2
    val conjuntoMap = conjunto1.map(funcion)

    assert((-Conjunto.LIMITE to Conjunto.LIMITE).forall(elemento => conjunto1(elemento) == conjuntoMap(funcion(elemento))))
  }
}
