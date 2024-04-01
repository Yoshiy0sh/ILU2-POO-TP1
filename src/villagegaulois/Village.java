package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import exceptions.VillageSansChefException;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private class Marche{
		private Etal[] etals;
		private int nbEtals;
		
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for(int i=0;i<nbEtals;i++) {
				etals[i] = new Etal();
			}
			this.nbEtals = nbEtals;
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i] != null) {
					if(etals[i].isEtalOccupe() == false) {
						return i;
					}
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsTrouves = new Etal[nbEtals];
			int indiceTableau = 0;
			for(int i=0;i<nbEtals;i++) {
				if(etals[i] != null) {
					if(etals[i].contientProduit(produit)) {
						etalsTrouves[indiceTableau] = etals[i];
						indiceTableau++;
					}
				}
			}
			return etalsTrouves;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private void afficherMarche() {
			int nbNonOccupes = 0;
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()) {
					System.out.println(etals[i].afficherEtal());
				}
				else{
					nbNonOccupes++;
				}
			}
			if(nbNonOccupes>0) {
				System.out.println("Il reste " + nbNonOccupes + " étals non utilisés dans le marché.\n");
			}
		}
	}

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}	

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public String rechercherVendeursProduit(String produit) {
		int nombreGaulois = 0;
		Etal[] tabEtals = marche.trouverEtals(produit);
		StringBuilder gaulois = new StringBuilder();
		for(int i=0;i<tabEtals.length;i++) {
			if(tabEtals[i] != null){
				if(nombreGaulois == 0) {
					gaulois.append("Les gaulois disponibles sont : ");
				}
				gaulois.append(tabEtals[i].getVendeur().getNom() + ' ');
				nombreGaulois++;
			}
		}
		if(nombreGaulois == 0) {
			gaulois.append("Il n'y a aucun vendeur pour ce produit");
		}
		return gaulois.toString();
	}
	
	public String installerVendeur(Gaulois vendeur,String produit,int nbProduits) {
		StringBuilder trace = new StringBuilder();
		if(marche.trouverEtalLibre() != -1) {
			trace.append("Le vendeur " + vendeur.getNom() + " cherche un endroit pour vendre " + nbProduits + ' ' + produit);
			marche.utiliserEtal(marche.trouverEtalLibre(), vendeur, produit, nbProduits);
		}
		else {
			trace.append("Il n'y a plus d'étal libre");
		}
		return trace.toString();
		
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String afficherMarche() {
		marche.afficherMarche();
		return "Le marché a été affiché";
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder retour = new StringBuilder();
		Etal aLiberer = marche.trouverVendeur(vendeur);
		if(aLiberer == null) {
			retour.append("Ce vendeur n'a pas d'étal");
		}
		else {
			retour.append(vendeur.getNom() + " libère son étal");
			aLiberer.libererEtal();
		}
		return retour.toString();
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

	public String afficherVillageois() throws VillageSansChefException {
		if(this.chef == null) {
			throw new VillageSansChefException("Il n'y a pas de chef dans le village gaulois");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}