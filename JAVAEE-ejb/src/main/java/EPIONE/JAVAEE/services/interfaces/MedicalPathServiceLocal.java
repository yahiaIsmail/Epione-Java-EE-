package EPIONE.JAVAEE.services.interfaces;

import EPIONE.JAVAEE.entities.*;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MedicalPathServiceLocal {
    String test();

    /***Create a new Path for patient ***/
    int createPathForPatient(int rdvId,MedicalPath path);
    /**** Define Doctors for Patient Path****/
    String addDoctorsToPath(int idPath, int idDoctor, PathDoctors pathDoctors,String description);
    /***Remove Doctor from Patient path **********/
    String removeDoctorFromPath(int pathId,int doctorId);
    /*******getPathById**************/
    MedicalPath getPathById(int pathId);
    /**** update path component**/
    void updatePathComponent(int id,MedicalPath path);
    /*******update MedicalVisit status**********/
    void updateMedicalVisitStatus(int pathDoc,MedicalVisit medicalVisit);
    /*************get ALll RDV list for a signle user **************/
    List<RDV> getAllRDVPatient(int idPatient);
    /**********Get pathDoctor by path id********************/
    List<PathDoctors> getDoctorsInPathById(int pathid);
    /******************************* get all paths for patient ***********************************************/
    List<MedicalPath> allPathsForConnectedPatient(int idpatient);
    /******************************* get path with multiple search option ******************************/
    List<MedicalPath> searchPathByOption(int idPatient,MedicalPath medicalPath);
    /************************* get doctor's visits*****************************************/
    List<MedicalVisit> getDoctorAllvisits(int idDoctor);
    /*************************** Doctor update Medical Path *********************************/
    void updateDoctorInPath(String justification,int idpath,PathDoctors pathDoctors);
    /********************************* get all paths for doctor ****************************/
    List<PathDoctors> getAllPathForDoctor();
    /******************************** find nearby doctors *************************************/
    List<User> nearbyDoctors(double el1,double el2,int idPatient);
    // gettAllPathsForDoctor*****////
    List<MedicalPath> pathsforOneDoctor(int id);
    /************getAllRdvFor Doc**************************/
    List<RDV> getDocRDVS (int id);
    /*********************getAllIncludedMedicalPath*****************************************/
    List<MedicalPath> getAllIncludedMedicalPath(int id);
    
    MedicalVisit getVisitByPathId(int pathid,int iduser);
    
    void updateMedicalvisit(MedicalVisit medicalVisit);
    
    List<User>getDoctors(int id);
    List<MedicalPath>getCompletedPath(int id);
    List<MedicalPath>getunCompletedPath(int id);
    	
    

}
