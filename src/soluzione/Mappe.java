package soluzione;


import java.io.*;
import java.util.*;
/** 
Questa classe contiene il main e gestisce i file in ingresso.
@author Massimiliano Smillovich
*/

public class Mappe {

	public static void main(String[] args) {		//main

	
		
		try{
		FileReader file = new FileReader(args[0]);          //legge il file in input da linea di comando
	    BufferedReader buffer = new BufferedReader(file);   //legge riga per riga
		LinkedList<Mappa> lista;
		Piano piano = null;					//il piano  considerato
		String stringa;						 
		boolean booleano = false;
		
		while((stringa = buffer.readLine()) != null && booleano == false){  //finchè ci sono linee e booleano è false
		
		StringTokenizer stringatk = new StringTokenizer(stringa);
			if(stringatk.hasMoreTokens()){							//se ci sono ancora token nella riga
				switch(stringatk.nextToken().charAt(0)){			//considero il primo carattere del token
			
				case 'c':											// crea un nuovo piano puntando a quello ed eliminando le mappe
					Integer num = Integer.parseInt(stringatk.nextToken()); //considero i numeri della stringa
					piano = new Piano(num);							    	//creo il piano con quel numero
					break;
				
				case 'r':								
					String map = stringatk.nextToken();	//salvo il nome
					piano.riduci(map);			//invoco il metodo riduci
					break;
				
					
				case 'v':										
					Integer x = Integer.parseInt(stringatk.nextToken()); //salvo il primo numero
					Integer y = Integer.parseInt(stringatk.nextToken()); //salvo il secondo
					Integer valore = Integer.parseInt(stringatk.nextToken()); //salvo il terzo
					piano.valore_punto(x, y, valore);		//associo il valore del punto al piano
					break;
				
					
				case 'd':								
					lista = new LinkedList<Mappa>();
					String dir = stringatk.nextToken();		//salvo la stringa con la specifica
					lista = piano.direzione(dir);		//invoco il metodo direzione su quella specifica
					for(Mappa m : lista)				//stampo tutte le mappe
						System.out.println(m.nome_mappa);
					
					break;
					
				case 'm':										
					String nome_mappa = stringatk.nextToken();				//salvo il nome
					Integer xmappa = Integer.parseInt(stringatk.nextToken()); //salvo il primo numero
					Integer ymappa = Integer.parseInt(stringatk.nextToken());  //salvo il secondo numero
					String specifica = stringatk.nextToken();				//salvo la specifica
					piano.crea_mappa(nome_mappa, xmappa, ymappa, specifica);	//creo mappa
					break;
	
				
				case 's':								
					String stamp = stringatk.nextToken();		//salvo la stringa con il nome
					System.out.println(piano.stampa(stamp));	//stampo
					break;
				
				
				case 'l':								
				
					if(piano.albero_delle_mappe.size()>=1){		//se esiste almeno una mappa
					Iterator<Mappa> iter = piano.albero_delle_mappe.iterator();	//creo un iteratore con l'albero delle mappe
					while(iter.hasNext()){				//finchè ci sono mappe
					Mappa list=iter.next();				//salvo in list la successiva
					System.out.println(list);}			//stampo
					break;
					}
					
					case 'u':								
						String map2 = stringatk.nextToken(); //salvo il nome
						piano.usa(map2);					//invoco usa
						break;
					
				case 'f':								// fine del programma
					booleano = true;					//esce dal while
					break;
		
			}//switch
		

			
			
		}//if 
		
		
		}// while
		
		
		
		buffer.close(); //chiude il buffer
		file.close(); //chiude il file
		
		
		
		}//try
		
		
		catch (FileNotFoundException e){
			System.out.println("Errore: il file non esiste o non è stato trovato.");	//eccezione se non si inserisce il file
			}//catch
		
		
		
		catch (IOException e){
			System.out.println("errore");	//eccezione input output
			}//catch

	}//main

}//classe