package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.MedicalPath;

import javax.ejb.Local;

@Local
public interface MedicalPathServiceLocal {
    String test();

    /***Create a new Path for patient ***/
    int createPathForPatient(int rdvId,MedicalPath path);
    /**** Define Doctors for Patient Path****/
    String addDoctorsToPath(int idPath,int idDoctor);
    /***Remove Doctor from Patient path **********/
    void removeDoctorFromPath(int pathId,int doctorId);


}
