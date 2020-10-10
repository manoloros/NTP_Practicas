import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object trianguloPascalCheck extends Properties("Triangulo Pascal"){

  private val MAXIMO=30

  // Se generan los valores de fila y columna para los bordes
  val coordenadasExtremos = for {
    // se genera numero de fila: valor comprendido entre 0 y MAXIMO
    // (MAXIMO no esta incluido)
    fila <- Gen.choose(0, MAXIMO)

    // se genera numero de columna: o 0 o el valor de fila. Esto
    // asegura que se trata de un valor de los extremos (X,0) o
    // (X,X)
    columna <- Gen.oneOf(0, fila)
  } yield (fila, columna)

  property("Elementos en lados del triangulo valen 1") = {
    forAll(coordenadasExtremos) { (i) => {
      val resultado = trianguloPascal.calcularValorTrianguloPascal(i._1, i._2)
      println("Fila = "+i._1 +" Columna = "+ i._2+ " Resultado = "+resultado)
      resultado == 1
    }
    }
  }

  // Se generan los valores de fila y columna para valores internos
  val coordenadasInternas = for {
    fila <- Gen.choose(2, MAXIMO)
    columna <- Gen.choose(1, fila-1)
  } yield (fila, columna)

  property("Elementos internos del triangulo valen la suma de los dos elementos que tiene arriba") = {
    forAll(coordenadasInternas) { (i) => {
      val resultado = trianguloPascal.calcularValorTrianguloPascal(i._1, i._2)
      val solucion = factorialTRSimple(i._1)/(factorialTRSimple(i._2)*factorialTRSimple(i._1-i._2))
      println("Fila = "+i._1 +" Columna = "+ i._2+ " Resultado = "+resultado+ " Solucion = "+solucion)
      resultado == solucion
    }
    }
  }

  /**
    * Funcion que calcula el factorial del parametro pasado
    *
    * @param x Numero cuyo factorial queremos calcular
    * @return
    */
  def factorialTRSimple(x: Int) : BigInt = {
    @annotation.tailrec
    def go(x : Int, acum : BigInt) : BigInt = {
      if (x == 0) acum
      else go(x-1, x*acum)
    }

    go(x, 1)
  }
}
