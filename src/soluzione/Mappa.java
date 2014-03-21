
package soluzione;

import java.util.LinkedList;
import java.util.TreeSet;
/** 
   Questa classe crea un oggetto che rappresenta una mappa con nome,specifica,punto di origine.
   @author Massimiliano Smillovich
 */

public class Mappa implements Comparable<Mappa>{		//userò il comparatore

//campi

String nome_mappa; //nome della mappa

String specifica; //la specifica della mappa {N,O,S,E}

Punto_sul_piano origine; //punto di origine della mappa

LinkedList<Punto_sul_piano> spostamento; //lista contenente i punti dove la mappa si è spostata

Piano piano_mappa; //piano dove è presente la mappa

Punto_sul_piano p1;

//costruttori

/**
    crea una mappa in base ai parametri inseriti e associa alla variabile spostamento,
    il cammino che fa la mappa causato dalla specifica inserita.
    @param nome una stringa che rappresenta il nome della mappa.
    @param orig punto di origine della mappa.
    @param spec una stringa che specifica i movimenti della mappa
    @param piano il piano su cui si crea la mappa.
 */
 
public Mappa(String nome, Punto_sul_piano orig, String spec, Piano piano){
	spostamento = new LinkedList<Punto_sul_piano>();
	nome_mappa = nome;
	specifica = spec;
	piano_mappa = piano;
	boolean esiste = piano_mappa.albero_dei_punti.contains(orig);
	if(esiste == true) //se l'albero dei punti contiene già l'origine
		origine = piano_mappa.albero_dei_punti.ceiling(orig); //questo punto diventerà la nostra origine
	else
		origine = orig;
	spostamento.add(origine);		//altrimenti aggiungiamo l'origine e la inseriamo in spostamento
	spostamento = this.spostamento();	//invoco il metodo
	
}//mappa
		
		
//metodi

/**
@return Restituisce una descrizione della mappa.

*/

public String toString(){
	return nome_mappa + " " + origine.toString() + " " + specifica;
}// tostring


/** 
   Restituisce un intero positivo, nullo o negativo se l'oggetto chiamante
   è maggiore, uguale o minore dell'oggetto chiamato,in base all'ordine lessicografico.
   Il campo è nome_mappa. 
   @param mappa2 mappa di confronto.
   @return un numero negativo,nullo o positivo.
 */

public int compareTo(Mappa mappa2){
	String lunghezza = mappa2.nome_mappa; //stringa lunghezza con il nome della mappa
	
	if((lunghezza.length()-this.nome_mappa.length()) > 0) //controlla che il nome di nome_mappa è il piu corto
		lunghezza = this.nome_mappa;			//tengo in lunghezza la mappa con il nome più corto per fare meno confronti
	for(int i=0; i<lunghezza.length();i++){
		if((this.nome_mappa.charAt(i)) > (mappa2.nome_mappa.charAt(i))) //se la lettera del nome di mappa2 è precedente a quella della mappa chiamante
			return 1;						//allora nome_mappa andrà messo dopo
		else if((this.nome_mappa.charAt(i)) < (mappa2.nome_mappa.charAt(i))) // se la lettera del nome di mappa2 è seguente a quella della mappa chiamante
			return -1;							//allora nome_mappa andrà messo prima
		}//for
	if((this.nome_mappa.length()) < (mappa2.nome_mappa.length()))		//se nome_mappa è minore di mappa2 andrà inserito prima es map,mappa
		return -1;
	else if((this.nome_mappa.length()) > (mappa2.nome_mappa.length()))	//se nome_mappa è maggiore di mappa2 andrà inserito dopo
		return 1;
	else									//altrimenti hanno lo stesso nome
		return 0;
}//compareto





/** 
   aggiorna la mappa se cambiano i valori
 */
public void aggiorna_valori(){
	this.spostamento.clear(); //cancella lo spostamento
	boolean esiste=piano_mappa.albero_dei_punti.contains(origine);
	if(esiste==true) { //se l'albero dei punti del piano contiene l'origine
	Punto_sul_piano punto_origine = origine; //associa l'origine a punto_origine
	this.origine = punto_origine;			//aggiorna origine
	spostamento.add(origine);		//aggiunge l'origine
	this.spostamento();		}	//chiama il metodo per ricreare lo spostamento
	else{
        spostamento.add(origine);  //aggiungi l'origine se non è stata modificata
        this.spostamento();
	}
}//aggiornavalori



/** 
   ricerca di cicli durante lo spostamento.
   @return restituisce null se non ci sono cicli,altrimenti gli indici di inizio e fine del ciclo.
 */
private int[] Ciclo(){
	int[] indici;						//conterrà i due indici
	boolean booleano = false;
	int indice1 = 0;
	int indice2 = 0;
	LinkedList<Punto_sul_piano> percorso = new LinkedList<Punto_sul_piano>();  
	percorso = this.spostamento;			//inseriamo in percorso i dati di spostamento
	for(int i = 0; booleano==false && i<percorso.size()-1 ; i++){   //scandisce la lista per trovare un eventuale punto doppio
		Punto_sul_piano punto = percorso.get(i);		//assegna a punto,tutti i valori di percorso a turno
		for(int j = i+1; booleano==false && j<percorso.size(); j++){	//scandisce da dopo il punto assegnato
			if(punto.compareTo(percorso.get(j))== 0){     //se ha trovato un punto doppio
				booleano = true; 		//c'è un punto doppio quindi devo uscire dal for
				indice1 = i;		    //assegno i punti agli indici
				indice2 = j;
			}//if	
		}//for
	}//for
	if(booleano==true){					//se esiste il ciclo
		indici = new int[2];			//inizializzo l'array
		indici[0] = indice1;			//aggiungo i valori
		indici[1] = indice2;
	}//if
	else							//se non esiste
		indici = null;
		return indici;
}//ciclo


/** 
    Itero la riduzione su tutti i cicli
 */
public void riduzioneIterata(){
	int[] array;
	while ((array = this.Ciclo()) != null) //finche il metodo ciclo trova qualcosa
		this.riduci(array);  //ripetiamo la riduzione
}//riduzioneiterata



/** 
    @return la fine della mappa
 */
public Punto_sul_piano dest(){
	return spostamento.get(spostamento.size()-1);  //restituisco l'ultimo elemento della lista
}//dest

/** 
metodo che rimuove il ciclo e aggiorna la specifica
@param indici array contenente i due indici.
*/
private void riduci(int[] indici){
	   int primo_indice= indici[0];						//salvo il primo indice
	   int secondo_indice = indici[1];					//salvo il secondo indice
		for(int k = primo_indice; k<secondo_indice; k++){		//rimuovo tutti i dati che sono nel ciclo
			spostamento.remove(primo_indice);
		}//for
		specifica = specifica.substring(0, primo_indice).concat(specifica.substring(secondo_indice));
		//tengo la prima parte di specifica e la concateno con la seconda parte 
}//riduci

/** 
@return Restituisce il valore della dest.
*/
public int valore_dest(){
	return this.dest().valore_punto; //valore del punto dest
}//valoredest 


/** 
   controlla che la direzione della mappa sia uguale alla stringa inserita.
   @param a La stringa indicante la direzione che si vuole controllare.
   @return true se a è la direzione della mappa, false se non lo è.
 */
public  boolean controlla_direzione(String a){
	boolean differenti = false;
	if(a.length() > specifica.length())		//non avrebbe senso una stringa di controllo maggiore della specifica stessa
		return false;
	else {
		for(int i = 0; differenti == false && i < a.length(); i++){ //per tutta la stringa
			if(a.charAt(i) != specifica.charAt(i))   //se almeno un carattere è diverso
				differenti = true;	 //esco dal ciclo
		}//for
		if(differenti == true)
			return false;   //se si esce dal ciclo forzatamente,controlla direzione darà errore,quindi false
		else
			return true;
	}//else
}//controlladirezione

/** 
crea lo spostamento della mappa.
@return una lista con tutti i punti.
*/
public LinkedList<Punto_sul_piano> spostamento(){
	
	LinkedList<Punto_sul_piano> sp = this.spostamento; 		//creo la lista sp che contiene spostamento
	int valore_piano = piano_mappa.valore_piano;			//creo una variabile che contiene il valore del piano
	Punto_sul_piano p;										//creo un punto p
	TreeSet<Punto_sul_piano> albero = piano_mappa.albero_dei_punti; //creo un albero di punti con i valori dei punti dell'albero
																	//nel piano della mappa
	if(albero.contains(origine) == true){		//se l'albero contiene l'origine modificata
		Punto_sul_piano o = origine;		//la aggiorno nella lista sp
		sp.remove();
		sp.add(o);
	}//if
	
	for(int i = 0; i< this.specifica.length(); i++){	//scandisco la specifica
		char c = this.specifica.charAt(i);
		Punto_sul_piano s = sp.get(i);		//copio in s l'elemento in posizione i della lista
		
		
		switch(c){
			
		case 'S':
			
			p = new Punto_sul_piano(s.x,s.y-1, valore_piano); //modifico il punto in base alla specifica
			if(albero.contains(p) == true){
			p1 = albero.ceiling(p);	     //se esiste già lo tengo alrtimenti aggiungo quello nuovo
			sp.add(i+1, p1);}//if
				
			else
				sp.add(i+1,p);
			break; 
			
	
			
		case 'N':
			
			p = new Punto_sul_piano(s.x,s.y+1, valore_piano); //modifico il punto in base alla specifica
			if(albero.contains(p) == true){	//se esiste già lo tengo alrtimenti aggiungo quello nuovo
			 p1 = albero.ceiling(p);	
			sp.add(i+1, p1);}//if
				
			else
				sp.add(i+1,p);
			break; 
			
		case 'O':
			
			p = new Punto_sul_piano(s.x-1,s.y, valore_piano);//modifico il punto in base alla specifica
			if(albero.contains(p) == true){  //se esiste già lo tengo alrtimenti aggiungo quello nuovo
			 p1 = albero.ceiling(p);	
				sp.add(i+1, p1);
			}//if
			else
				sp.add(i+1,p);
			break; 
			
		
			
		case 'E':
		
			p = new Punto_sul_piano(s.x+1,s.y, valore_piano);//modifico il punto in base alla specifica
			if(albero.contains(p) == true){
			p1 = albero.ceiling(p);	   //se esiste già lo tengo alrtimenti aggiungo quello nuovo
				sp.add(i+1, p1);
			}//if
			else
				sp.add(i+1,p);
			break; 
			
		
		}//switch
		
	}//for
	this.spostamento = sp;		//aggiorno la lista spostamento
	return sp;
}//spostamento




}//class