package it.polito.tdp.anagrammi.model;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.anagrammi.DAO.AnagrammaDAO;

public class Model {
	
	List<Parola> soluzione; //lista di anagrammi possibili di tipo Parola(nome, boolean corretta)
	AnagrammaDAO dao = new AnagrammaDAO();

	/**
	 * Genera tutti gli anagrammi della parola specificata in ingresso
	 * @param parola parola da anagrammare
	 * @return elenco di tutti gli anagrammi della parola data
	 */
	public List<Parola> anagrammi(String parola) {
		//inizializzo la lista di anagrammi vuota
		this.soluzione = new ArrayList<>();
		//imposto sempre maiuscola la parola da anagrammare
		parola=parola.toLowerCase();
		//imposto la lista iniziale di caratteri per creare gli anagrammi = tutti i caratteri della parola da anagrammare 
		List<Character> disponibili = new ArrayList<>();
		for(int i=0; i<parola.length(); i++) {
			disponibili.add(parola.charAt(i));
		}
		//avvio la ricorsione - caso iniziale: parola vuota, livello 0, disponibili tutti i  caratteri della parola
		cerca("", 0, disponibili); 
		//ritorno tutta la lista di anagrammi con boolean corretta già impostato correttamente
		return this.soluzione;
	}

	/**
	 * Procedura ricorsiva per il calcolo dell'anagramma
	 * 
	 * @param parziale parte iniziale dell'anagramma costruito finora
	 * @param livello livello della ricorsione, sempre uguale a parziale.length()
	 * @param disponibili insieme delle lettere non ancora utilizzate
	 */
	private void cerca(String parziale, int livello, List<Character> disponibili) {
		//caso terminale: la lista di caratteri disponibili è vuota - il parziale è l'anagramma definitivo
		if(disponibili.size()==0) {
			//creo un nuovo oggetto Parola con il parziale definitivo e corretta = false
			Parola parola = new Parola(parziale);
			//controllo correttezza anagramma: if(parziale è nel dizionario) corretta = true
			if(dao.isCorrect(parziale))
				parola.setCorretta(true);
			//controllo parola già presente (caso in cui la parola iniziale contiene più lettere uguali)
			//aggiungo Parola al dizionario solo non è già presente
			if(!this.soluzione.contains(parola))
				this.soluzione.add(parola);
		}
		//caso normale
		//provo ad aggiungere a 'parziale' tutti i caratteri presenti tra i 'disponibili'
		for(Character ch: disponibili) {
			//aggiungo al parziale un carattere
			String tentativo = parziale + ch ;
			//creo la lista di caratteri rimanenti togliendo dalla precedente lista di disponibili il carattere appena utilizzato
			List<Character> rimanenti = new ArrayList<>(disponibili) ;
			rimanenti.remove(ch) ;
			//riavvio la ricorsione - caso normale: parola con tot di caratteri, incremento il livello, caratteri rimanenti
			cerca(tentativo, livello+1, rimanenti) ;
		}
	}
}
