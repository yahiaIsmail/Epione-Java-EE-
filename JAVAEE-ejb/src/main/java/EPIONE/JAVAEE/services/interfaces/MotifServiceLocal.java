package EPIONE.JAVAEE.services.interfaces;

import java.util.List;

import javax.ejb.Local;
<<<<<<< HEAD
import javax.ejb.Remote;

import EPIONE.JAVAEE.entities.DoctorData;
import EPIONE.JAVAEE.entities.Motif;
import EPIONE.JAVAEE.entities.User;
=======


import EPIONE.JAVAEE.entities.Motif;
>>>>>>> 76e4cd93b61adaace56b5625328272e4172cdac2

@Local
public interface MotifServiceLocal {

	public int ajouterMotif(Motif motif);
	public void affecterMotifDoctor(int motifId, int docId);
	public int modifierMotif(int motifId, String desc);
	public int supprimerMotif(int motifId);
<<<<<<< HEAD
	public List<String> listerMotifByDoc(User user);
=======
>>>>>>> 76e4cd93b61adaace56b5625328272e4172cdac2
}
