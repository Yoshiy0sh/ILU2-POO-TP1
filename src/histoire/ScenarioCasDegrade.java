package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois jean = new Gaulois("Jean",23);
		try {
			System.out.println(etal.acheterProduit(1, jean));
		}
		catch(IllegalArgumentException e){
			System.out.println(e);
		}
		catch(IllegalStateException e) {
			System.out.println(e);
		}
	}
}
