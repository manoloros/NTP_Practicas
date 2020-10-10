import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object busquedaBinariaGenericaCheck extends Properties("Busqueda binaria generica"){

  private val MAXIMO = 30

  // Generacion de vectores de longitud n
  val genVectores : Int => Gen[List[Int]] = (n : Int) => {
    Gen.listOfN(n, Gen.choose(1,100))
  }

  // Para cada vector probaremos a buscar todos los valores del 1 al 100
  property("La busqueda binaria implementada da el mismo resultado que el metodo binarySearch") = {
    forAll(genVectores(Gen.choose(1, MAXIMO).sample.get)) { (vector) => {
      def func(x : Int, y : Int) = if (x < y) true else false
      println("Vector: " + vector.sorted)

      val resultadoTest = for (numero <- 1 to 100) yield {
        var resultadoEsperado = java.util.Arrays.binarySearch(vector.sorted.toArray, numero)
        if (resultadoEsperado < 0) resultadoEsperado = -1
        val resultado = busquedaBinariaGenerica.busquedaBinaria(vector.sorted.toArray, numero, func)
        println("Numero: "+numero+" Busqueda binaria: "+resultado+ " Binary search: "+resultadoEsperado)
        resultado == resultadoEsperado
      }

      resultadoTest.distinct.size == 1 && resultadoTest.distinct(0) == true
    }
    }
  }
}
