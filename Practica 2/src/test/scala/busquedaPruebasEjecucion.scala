import org.scalameter.api._

object busquedaPruebasEjecucion extends Bench.LocalTime {

  private val MAXIMO = 120000

  // Genera los tamaños que se utilizaran para crear los vectores
  val sizes = Gen.range("size")(10000, MAXIMO, 20000)

  // Genera vectores con datos de tipo Int
  val vectoresInt = for {
    size <- sizes
  } yield {
    var vector : List[Int] = List()
    for (i <- 1 to size)
      vector = i :: vector
    vector.sorted.toArray
  }

  // Genera vectores con datos de tipo Double
  val vectoresDouble = for {
    size <- sizes
  } yield {
    var vector : List[Double] = List()
    for (i <- 1 to size)
      vector = i*0.632423824 :: vector
    vector.sorted.toArray
  }

  // Genera vectores con datos de tipo Char
  val vectoresChar = for {
    size <- sizes
  } yield {
    var vector : List[Char] = List()
    for (i <- 1 to size)
      vector = i.toChar :: vector
    vector.sorted.toArray
  }

  performance of "Busqueda con enteros" in {
    measure method "Busqueda binaria" in {
      using(vectoresInt) in { (vector) => {
        busquedaBinariaGenerica.busquedaBinaria(vector, MAXIMO, (x : Int, y : Int) => if (x < y) true else false)
      }
      }
    }

    measure method "Busqueda a saltos" in {
      using(vectoresInt) in { (vector) => {
        busquedaSaltosGenerica.busquedaSaltos(vector, MAXIMO, (x : Int, y : Int) => if (x < y) true else false)
      }
      }
    }
  }

  performance of "Busqueda con double" in {
    measure method "Busqueda binaria" in {
      using(vectoresDouble) in { (vector) => {
        busquedaBinariaGenerica.busquedaBinaria(vector, MAXIMO.toDouble, (x : Double, y : Double) => if (x < y) true else false)
      }
      }
    }

    measure method "Busqueda a saltos" in {
      using(vectoresDouble) in { (vector) => {
        busquedaSaltosGenerica.busquedaSaltos(vector, MAXIMO.toDouble, (x : Double, y : Double) => if (x < y) true else false)
      }
      }
    }
  }

  performance of "Busqueda con char" in {
    measure method "Busqueda binaria" in {
      using(vectoresChar) in { (vector) => {
        busquedaBinariaGenerica.busquedaBinaria(vector, MAXIMO.toChar, (x : Char, y : Char) => if (x < y) true else false)
      }
      }
    }

    measure method "Busqueda a saltos" in {
      using(vectoresChar) in { (vector) => {
        busquedaSaltosGenerica.busquedaSaltos(vector, MAXIMO.toChar, (x : Char, y : Char) => if (x < y) true else false)
      }
      }
    }
  }
}
