package soluzione;
import java.util.*;

/**	
	creo una classe dove si crea un oggetto di tipo piano che è formato da mappe 
	e punti
	@author Massimiliano Smillovich
 */
 
public class Piano {
	
//campi

TreeSet<Punto_sul_piano> albero_dei_punti; //albero in cui vengono inseriti i punti che hanno cambiato valore
                                           //rispetto al valore iniziale del piano
LinkedList<Mappa> lista;	                       		  
LinkedList<Mappa> l1;
TreeSet<Mappa> albero_delle_mappe;          //albero in cui vengono inserite le mappe
int valore_piano;                    //valore dato a tutti i punti del piano.



//costruttori
/** 
   Crea un piano ponendo come valore del piano,il valore val
   e creo gli alberi per mappe e punti.
   @param val l'intero che rappresenta il valore per ogni punto del piano.
 */

public Piano(int val){						//creo un piano
	albero_delle_mappe = new TreeSet<Mappa>();		//creo albero delle mappe
	albero_dei_punti = new TreeSet<Punto_sul_piano>();	//creo albero dei punti
	valore_piano = val;						//associo il valore
}//piano
	
//metodi
/** 
   Crea un punto con coordinate e valore,se esiste già,lo modifico e infine lo aggiungo all'albero.
   @param x ascissa del punto.
   @param y ordinata del punto.
   @param valore valore da dare al punto.
 */
public void valore_punto(int x, int y, int valore){
	Punto_sul_piano a = new Punto_sul_piano(x,y,valore);		//creo un punto a con i parametri
	Punto_sul_piano b = a;						//creo un punto b identico ad a
	Boolean esiste = albero_dei_punti.contains(a);		//creo un punto esiste a cui verrà 
	if(esiste == true)		{							//associato un booleano								
		albero_dei_punti.remove(a);		//se esiste già il punto lo cancello
		albero_dei_punti.add(b);	}//if  	//e lo aggiorno con il nuovo punto
	else	
		albero_dei_punti.add(a);		//altrimenti lo inserisco	
		this.aggiorna_valori();			//invoco il metodo per aggiornare i valori
}//valore punto

/** 
   Assegna il valore ad un punto inserito tramite parametro in modo da non dover
   utilizzare punto_del_piano.x e punto_del_piano.y. 
   @param a il punto.
   @param valore valore da assegnare al punto.
 */

public void valore_punto(Punto_sul_piano a, int valore){
	this.valore_punto(a.x, a.y, valore);
}//valore punto


/** 
   cerco le mappe che hanno la stessa direzione e le salvo in una lista
   @param direzione la direzione delle mappe cercate
   @return la lista contenente le mappe trovate o la lista vuota nel caso non ci siano mappe con tale direzione
 */
	
   LinkedList<Mappa> direzione(String direzione ){
	   lista = new LinkedList<Mappa>();
	   Iterator<Mappa> elenco = albero_delle_mappe.iterator();   //crea un elenco di tutte le mappe
	   Mappa m = elenco.next(); //assegno a m il primo valore dell'elenco (iterator)
	   if( albero_delle_mappe.size()>=1){   //se ci sono mappe 
	   do{
	        if(m.controlla_direzione(direzione)){ //invoco controlla direzione
	            lista.add(m);  //aggiungo le mappe consone alla lista
	        }//if
	   m = elenco.next();//incremento in modo da non avere un ciclo infinito
	   if(elenco.hasNext() == false) //se non c'è il successivo
           if(m.controlla_direzione(direzione)){ //ma c'è ancora l'ultima mappa
                lista.add(m);  //aggiungo la mappa
           }//if
       }//do
	   while(elenco.hasNext()); 					//finchè ci sono mappe
	   //uso il do-while perchè anche se l'iterator contenesse un solo elemento 
	   //devo inserire quell'elemento nella lista se ha la direzione voluta

   	
}//if
	return lista;
	}//direzione

/** 
   Cerca tra le mappe con direzione specificata una di quelle col valore massimo e 
   la elimina dall'albero dopo aver assegnato al punto della sua destinazione il valore 0.
   Non fa nulla se non trova alcuna mappa con tale direzione.
   @param direzione la direzione della mappa da cancellare
 */
   
 public void usa(String direzione){
      lista = new LinkedList<Mappa>();
	  lista = this.direzione(direzione);     //lista contiene mappe con direzione corretta
	if(lista.get(0) == null)				//se non ci sono mappe	
		return;
	else{
	int max = lista.get(0).valore_dest();  //inizializza il max col primo valore della lista		
	Mappa map = lista.get(0);         	   //inizializza con la prima mappa della lista
	for(int i = 1; i<lista.size() ; i++){  //trova il massimo
		int a = lista.get(i).valore_dest(); 
		if(a >= max){
			max = a;
			map = lista.get(i);
		}//if
	}//for
	map.dest().valore_punto = 0;          //assegna 0 alla destinazione della mappa
	this.valore_punto(map.dest(), 0);     //aggiorna o inserisce il valore del nodo cambiato
	this.aggiorna_valori();               // aggiorna tutte le mappe con il nuovo nodo messo a 0
	albero_delle_mappe.remove(map);	      //cancella la mappa
	}//else
}//usa
 
 /** 
 Crea una nuova mappa e se è già presente,viene sostituita.
 @param nome_mappa nome della mappa
 @param x coordinata x dell'origine
 @param y coordinata y dell'origine
 @param specifica la specifica della mappa {N,O,S,E}
 @return restituisce la mappa
*/
 
public Mappa crea_mappa(String nome_mappa, int x, int y, String specifica){
	Punto_sul_piano a = new Punto_sul_piano(x,y,valore_piano);		//creo un punto con il valore del piano
	Mappa map = new Mappa(nome_mappa,a,specifica, this);			//creo una mappa che parte dal punto
	Mappa map1=map;
	Boolean esiste = albero_delle_mappe.contains(map);			//controllo che non esista già
	if(esiste == true)	{										//se esiste la sostituisco
		albero_delle_mappe.remove(map);
		albero_delle_mappe.add(map1);
		return map1;
		}//if
	else
		albero_delle_mappe.add(map);							//altrimenti la creo
	return map;	
}//crea mappa

/** 
Elimina i cicli della mappa il cui nome è specificato nel parametro.
@param nome_mappa nome della mappa da ridurre
*/

public void riduci(String nome_mappa){
	Punto_sul_piano c = new Punto_sul_piano(0,0,0);
	Mappa mappa = new Mappa(nome_mappa, c, "" , this); //creo mappa
	if(albero_delle_mappe.contains(mappa)== true){ //se la mappa di nome nome_mappa è presente
		mappa = albero_delle_mappe.ceiling(mappa); //associo la mappa esistente a quella creata
		mappa.riduzioneIterata();                  //chiamo il metodo della classe mappa per ridurla
	}//if
}//riduci

/** 
  restituisce la mappa che ha lo stesso nome di quella passata per parametro
  Se non esiste restituisce la stringa vuota.
  @param nome_mappa il nome della mappa
 */

public String stampa(String nome_mappa){
	Punto_sul_piano c = new Punto_sul_piano(0,0,0);   //crea una finta origine
	Mappa mappa = new Mappa(nome_mappa, c, "", this); //crea una finta mappa
	Boolean esiste = albero_delle_mappe.contains(mappa); //se questa mappa esiste
	if(esiste == true)
		return  albero_delle_mappe.ceiling(mappa).toString(); 			//stampa
	else
		return "";
}//stampa



/**
   aggiorna i valori delle mappe
 */

private void aggiorna_valori(){
	l1 = new LinkedList<Mappa>();
	if(!albero_delle_mappe.isEmpty()){     //se sono gia presenti mappe
		Iterator<Mappa> elenco = albero_delle_mappe.iterator(); //crea un elenco 
		 Mappa map = elenco.next(); //assegna alla variabile map il primo oggetto dell'elenco
		 do{
		l1.add(map);   //aggiungo la mappa alla lista finchè ne esistono
		map = elenco.next();  //incremento
		 
         }//do
		 while(elenco.hasNext()); 
		l1.add(map);			//aggiungo l'ultima
	for(Mappa m1: l1)			//aggiorna i valori di tutte le mappe
		m1.aggiorna_valori(); //metodo classe mappa
	}//if
}//aggiorna valori
 

}//classe