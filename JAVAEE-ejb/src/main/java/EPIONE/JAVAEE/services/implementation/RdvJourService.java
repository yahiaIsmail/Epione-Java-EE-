package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.RDV;
import EPIONE.JAVAEE.services.interfaces.RdvJourServiceLocal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;



/**
 * Created by Ellouze Skander on 27/10/2018.
 */
@Stateless
public class RdvJourService implements RdvJourServiceLocal {

    @PersistenceContext
    EntityManager em;


    @Override
    public Collection<RDV> ProgrammeJourneJ(int docId,int year,int month,int day) {
        Query query = em.createQuery("SELECT r FROM RDV r WHERE r.doctors.id =:d and YEAR(r.dateRDV)=:year and MONTH(r.dateRDV)=:month and DAY(r.dateRDV)=:day");
        query.setParameter("day", day);
        query.setParameter("month", month);
        query.setParameter("year", year);
        query.setParameter("d",docId);
        Collection<RDV> lst = (Collection<RDV>) query.getResultList();
        if (lst.size()==0)
            return null;
        else
            return lst;
    }


    @Override
    public void generateExcel(int docId) throws FileNotFoundException, DocumentException {

        Query query = em.createQuery("SELECT r FROM RDV r WHERE r.doctors.id =:d ");
        query.setParameter("d",docId);
        Collection<RDV> lst = (Collection<RDV>) query.getResultList();

        Document document = new Document();
        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Id");
        table.addCell("Date");
        table.addCell("My confirmation");
        table.addCell("Patient confirmation");
        table.addCell("Patient confirmation");
        table.addCell("Status");
        table.addCell("Patient first name");
        table.addCell("Patient last name");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for(RDV rdv: lst) {
            table.addCell(String.valueOf(rdv.getId()));
            table.addCell(String.valueOf(rdv.isConfirmationDoc()));
            table.addCell(String.valueOf(rdv.isConfirmationPatient()));
            table.addCell(String.valueOf(rdv.getStatus()));
            table.addCell(rdv.getUsers().getFirstName());
        }
        PdfWriter.getInstance(document, new FileOutputStream("sample3.pdf"));
        document.open();
        document.add(table);
        document.close();
        System.out.println("Done");

    }
}
