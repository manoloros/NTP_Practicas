import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object busquedaSaltosGenericaCheck extends Properties("Busqueda a saltos generica"){

  private val MAXIMO = 30

  // Generacion de vectores de longitud n
  val genVectores : Int => Gen[List[Int]] = (n : Int) => {
    Gen.listOfN(n, Gen.choose(1,100))
  }

  // Para cada vector probaremos a buscar todos los valores del 1 al 100
  property("El indice devuelto por la busqueda a saltos implementada corresponde al elemento que se buscaba") = {
    forAll(genVectores(Gen.choose(1, MAXIMO).sample.get)) { (vector) => {
      def func(x : Int, y : Int) = if (x < y) true else false

      val vectorOrdenado = vector.sorted
      println("Vector: " + vectorOrdenado)

      val resultadoTest = for (numero <- 1 to 100) yield {
        val resultado = busquedaSaltosGenerica.busquedaSaltos(vectorOrdenado.toArray, numero, func)
        println("Numero: "+numero+" Busqueda a saltos: "+resultado)
        // Si no se encuentra el numero, comprobamos si existe en el vector y si no existe es que ha funcionado correctamente
        if (resultado != -1)
          numero == vectorOrdenado(resultado)
        else
          !vectorOrdenado.contains(numero)
      }
      resultadoTest.distinct.size == 1 && resultadoTest.distinct(0) == true
    }
    }
  }
}
