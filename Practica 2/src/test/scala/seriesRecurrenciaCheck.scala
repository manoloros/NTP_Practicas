import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object seriesRecurrenciaCheck extends Properties("Series definidas de forma recurrente"){

  private val MAXIMO = 20

  property("El termino de las series de Pell y Jacobsthal calculado coincide con el resultado de la version secuencial") = {
    forAll(Gen.chooseNum(0, MAXIMO)) { (indice) => {
      val resultadoPell = serieSecuencial(indice, 2, 6, (x, y) => 2*x+y)
      val resultadoRecursivoPell = seriesRecurrencia.calcularValorSerieGenerica(indice, 2, 6, (x, y) => 2*x+y)
      val resultadoJacobsthal = serieSecuencial(indice, 0, 1, (x, y) => x+2*y)
      val resultadoRecursivoJacobsthal = seriesRecurrencia.calcularValorSerieGenerica(indice, 0, 1, (x, y) => x+2*y)

      println("Indice: "+indice)
      println("Resultado Pell recursivo: "+resultadoRecursivoPell)
      println("Resultado Pell sin recursion: "+resultadoPell)
      println("Resultado Jacobsthal recursivo: "+resultadoRecursivoJacobsthal)
      println("Resultado Jacobsthal sin recursion: "+resultadoJacobsthal)

      resultadoPell == resultadoRecursivoPell && resultadoJacobsthal == resultadoRecursivoJacobsthal
    }
    }
  }

  /**
    * Funcion que calcula, de forma secuencial, el valor en el indice indicado por indice de la serie
    * especificada por el primer valor, el segundo, y la relacion entre dos valores para obtener un tercero
    *
    * @param indice Elemento de la serie que queremos calcular
    * @param primerValor Primer valor de la serie
    * @param segundoValor Segundo valor de la serie
    * @param relacion La relacion entre dos valores que nos da un tercero
    * @return
    */
  def serieSecuencial(indice: Int, primerValor : Long, segundoValor : Long, relacion : (Long, Long) => Long) : Long = {
    var resultadoAnterior = primerValor
    var resultado = segundoValor
    var tmp : Long = 0

    if (indice == 0) resultado = primerValor

    for (i <- 0 to indice-2){
      tmp = resultado
      resultado = relacion(resultado, resultadoAnterior)
      resultadoAnterior = tmp
    }

    resultado
  }
}
