import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object contadorCambiosMonedaCheck extends Properties("Contador de posibles cambios de moneda"){

  private val MAXIMO = 20

  // Generacion de vectores de longitud n
  val genVectores : Int => Gen[List[Int]] = (n : Int) => {
    Gen.listOfN(n, Gen.choose(1,10))
  }

  // Generacion de los datos necesarios para ejecutar la funcion listarCambiosPosibles
  val genSituacion : Int => Tuple2[Int, List[Int]] = (n : Int) => {
    val vector : List[Int] = (genVectores(Gen.choose(1, 5).sample.get).sample.get).distinct
    (n, vector)
  }

  property("La suma de cada una de las soluciones devueltas me da la cantidad que se queria obtener") = {
    forAll(genSituacion(Gen.choose(5, MAXIMO).sample.get)) { (valoresIniciales) => {
      val soluciones = contadorCambiosMoneda.listarCambiosPosibles(valoresIniciales._1, valoresIniciales._2)
      println("Para: "+valoresIniciales)
      println("Soluciones: "+soluciones)
      if (!soluciones.isEmpty) {
        val resultado = for (solucion <- soluciones) yield {
          solucion.reduce(_ + _) == valoresIniciales._1
        }
        resultado.distinct.size == 1 && resultado.distinct(0) == true
      } else {
        true
      }
    }
    }
  }
}


