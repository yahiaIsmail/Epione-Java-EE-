package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.MedicalPath;
import EPIONE.JAVAEE.entities.PathDoctors;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MedicalPathServiceLocal {
    String test();

    /***Create a new Path for patient ***/
    int createPathForPatient(int rdvId,MedicalPath path);
    /**** Define Doctors for Patient Path****/
    String addDoctorsToPath(int idPath, int idDoctor, PathDoctors pathDoctors);
    /***Remove Doctor from Patient path **********/
    void removeDoctorFromPath(int pathId,int doctorId);
    /*******getPathById**************/
    MedicalPath getPathById(int pathId);
    /**** update path component**/
    void updatePathComponent(int id,MedicalPath path);


}
