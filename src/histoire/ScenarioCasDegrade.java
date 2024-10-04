package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois asterix = new Gaulois("Astérix", 12);
		try {
		etal.acheterProduit(12,asterix);
		}
		catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		catch(IllegalStateException e) {
			System.out.println(e);
		}
		System.out.println("Fin du test");
	}
}
