package EPIONE.JAVAEE.services.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import javax.ws.rs.Path;


import EPIONE.JAVAEE.entities.Motif;
import EPIONE.JAVAEE.services.interfaces.MotifServiceLocal;


@Stateless
public class MotifService implements MotifServiceLocal {


	@Override
	public int ajouterMotif(Motif motif) {
		return 0;
	}

	@Override
	public void affecterMotifDoctor(int motifId, int docId) {

	}

	@Override
	public int modifierMotif(int motifId, String desc) {
		return 0;
	}

	@Override
	public int supprimerMotif(int motifId) {
		return 0;
	}
}
