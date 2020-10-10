object balanceoCadenasParentesis {

  /**
    * Funcion que comprueba si la cadena pasada por parametros
    * tiene los parentesis balanceados
    *
    * @param cadena
    * @return
    */
  def chequearBalance(cadena: List[Char]): Boolean = {
    @annotation.tailrec
    def go(contador1 : Int, contador2 : Int, cadena : List[Char]) : Boolean = {
      if (contador1 - contador2 < 0) false
      else if (cadena.isEmpty && contador1 - contador2 != 0) false
      else if (cadena.isEmpty && contador1 - contador2 == 0) true
      else {
        if (cadena.head == '(') go(contador1+1, contador2, cadena.tail)
        else if (cadena.head == ')') go(contador1, contador2+1, cadena.tail)
        else go(contador1, contador2, cadena.tail)
      }
    }

    go(0,0, cadena)
  }

}
