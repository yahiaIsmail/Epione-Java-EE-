package EPIONE.JAVAEE.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import EPIONE.JAVAEE.entities.Motif;
import EPIONE.JAVAEE.entities.User;

@Local
public interface MotifServiceLocal {

	public int ajouterMotif(Motif motif);
	public void affecterMotifDoctor(int motifId, int docId);
	public int modifierMotif(int motifId, String desc);
	public int supprimerMotif(int motifId);
	public List<String> listerMotifByDoc(User user);

}
