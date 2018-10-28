package EPIONE.JAVAEE.services.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import javax.ws.rs.Path;


import EPIONE.JAVAEE.entities.DoctorData;
import EPIONE.JAVAEE.entities.Motif;
import EPIONE.JAVAEE.services.interfaces.MotifServiceLocal;


@Stateless
public class MotifService implements MotifServiceLocal {


	@PersistenceContext
	EntityManager em;

	@Override
	public int ajouterMotif(Motif motif) {
		em.persist(motif);
		return 1;
	}

	@Override
	public void affecterMotifDoctor(int motifId, int docId) {
		Motif m = em.find(Motif.class, motifId);
		DoctorData d = em.find(DoctorData.class, docId);
		List<Motif> lstM = new ArrayList<Motif>() ;
		lstM.add(m);
		m.setDoctor(d);

	}

	@Override
	public int modifierMotif(int motifId,String desc) {
		Motif motif = em.find(Motif.class, motifId);
		motif.setDescription(desc);
		return motif.getId();
	}

	@Override
	public int supprimerMotif(int motifId) {
		Motif motif = em.find(Motif.class, motifId);
		motif.setDoctor(null);
		em.remove(motif);
		return 0;
	}


	@Override
	public List<String> listerMotifByDoc(DoctorData doc) {
		TypedQuery<String> query = em.createQuery("select m.description from Motif m where doctor="+doc,String.class);
		List<String> lst = new ArrayList<String>() ;
		lst = query.getResultList();

		for(int i=0;i<lst.size();i++)
		{
			System.out.println(lst.get(i));
		}
		return lst;
	}

}
