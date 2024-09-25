package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMarche);
	}
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche(int nbEtalsMarche) {
			etals = new Etal[nbEtalsMarche];
			for (int i = 0; i < nbEtalsMarche; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
	
		private int trouverEtalLibre() {
			for(int i = 0; i<etals.length; i++) {
				if(!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		
		private Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit) && !etals[i].isEtalOccupe()) {
					nbEtals++ ;
				}
			}
			
			Etal[] tabEtals = new Etal[nbEtals];
			int compteur = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					tabEtals[compteur] = etals[i];
					compteur += 1;
				}
			}
			return tabEtals;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalsVides = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}
				else nbEtalsVides += 1;
			}
			if (nbEtalsVides > 0) {
				chaine.append("Il reste " + nbEtalsVides + " étals non utilisés dans le marché.");
			}
			return chaine.toString();
		}
		
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int indiceEtal = marche.trouverEtalLibre();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + produit+"\n");
		
		if(indiceEtal != -1) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal numéro" + (indiceEtal+1)  );
		}
		else {
			chaine.append("Il n'y a plus de place pour " + vendeur.getNom());
		}
		return chaine.toString();
	}
	
	public int trouverEtalLibre() {
		return marche.trouverEtalLibre();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] tabEtals = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		
		if(tabEtals.length > 0) {
			chaine.append("Les vendeurs qui proposent des fleurs sont :");
			for (int i = 0; i < tabEtals.length; i++) {
				chaine.append(" - " + tabEtals[i].afficherEtal() + "\n");
			}
		}
		else {
			chaine.append("Il n'y a aucun vendeur qui porpose "+produit+" au marché");
		}
		
		return chaine.toString();
		
	}
	
	public Etal trouverVendeur(Gaulois gaulois) {
		return marche.trouverVendeur(gaulois);
	}
	
	public void afficherMarche() {
		System.out.println(marche.afficherMarche());
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}