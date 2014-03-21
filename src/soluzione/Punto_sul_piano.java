

package soluzione;

  /**In questa classe vengono creati oggetti caratterizzati da due coordinate
     e un valore.
     Questi oggetti saranno dei punti presenti sul piano.
     @author Massimiliano Smillovich
  */

public class Punto_sul_piano implements Comparable<Punto_sul_piano>{  //userò il comparatore

//campi

int x; //coordinata sull'asse delle ascisse
int y; //coordinata sull'asse delle ordinate
int valore_punto; //valore assegnato al punto rappresentato da x e y

//costruttori
 
 /**Crea un'istanza del punto ponendo i valori dei parametri assegnati.
    @param a ascissa del punto.
    @param b ordinata del punto.
    @param c Il valore del punto.
 */

public Punto_sul_piano(int a, int b, int c){
	x = a;
	y = b;
	valore_punto = c;
} //Punto_sul_piano
 
	
//metodi

  
   /**restitisce un valore negativo, nullo o positivo se l'oggetto chiamante è minore, uguale 
      o maggiore dell'oggetto chiamato.
      I campi utilizzati per l'ordinamento sono in primis la x e successivamente,se non
      si ottengono risultati utili,la y.
      @param punto oggetto chiamato al confronto.
      @return valore < 0 se è minore, = 0 se i due punti sono uguali, > 0 se è maggiore. 
   */

public int compareTo(Punto_sul_piano punto){
	if(punto.x != this.x)        //se la x chiamante è diversa da quella chiamata
		return this.x - punto.x; //restituisci un valore <,> o =0 a seconda del risultato
	else
			return this.y - punto.y;  //se la x fosse uguale,uso la y

} //compareTo


  /**Restituisce una stringa con i campi x,y del punto sul piano.
     @return la stringa con x e y.
  */
  
  
public String toString(){
	return x + " " + y;
} //toString

}//classe