import org.scalacheck.{Gen, Properties}
import org.scalacheck.Prop.forAll

object balanceoCadenasParentesisCheck extends Properties("Balanceo de cadenas con parentesis") {

  private val MAXIMALONGITUD = 10

   // Generacion de cadenas de longitud n: forma de uso strGen(10) para cadenas
   // de 10 caracteres
   val strGen = (n: Int) => Gen.listOfN(n, Gen.oneOf('(',')',Gen.alphaChar.sample.get)).
   map(_.mkString)


   property("Balanceo de cadenas") = {
     forAll(strGen(Gen.choose(1, MAXIMALONGITUD).sample.get)) {
       cadena => {
         val condicion = balanceoCadenasParentesis.chequearBalance(cadena.toList)
         var condicion2 = true
         for (i <- 1 to cadena.length){
           val substring=cadena.substring(0,i)
           val openCount=substring.filter(c => c == '(').length
           val closeCount=substring.filter(c => c == ')').length
           if (openCount - closeCount < 0) condicion2 = false
           else if (i == cadena.length && openCount - closeCount != 0 && condicion2) condicion2 = false
           else if (i == cadena.length && openCount - closeCount == 0 && condicion2) condicion2 = true
         }
         println(cadena+" chequearBalance: "+condicion+" balanceado: "+condicion2)
         condicion == condicion2
       }
     }
   }
}
