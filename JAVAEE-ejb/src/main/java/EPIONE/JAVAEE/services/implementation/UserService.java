package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;


import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

@Stateless
@LocalBean
public class UserService implements UserServiceLocal {

    @PersistenceContext(unitName = "JAVAEE-ejb")
    EntityManager em;

    @Override
    public List<User> scrapingAllDoctors(String speciality)  {
        List<String> list= new ArrayList<>();
        User d= new User();
        List<User> listDoc= new ArrayList<User>();

        int compteur=0;
        String url = "";
        Document document ;
        Elements tmp = null;
        String str="";


        String fullName[];
        String name="";
        String lastName="";

        //scrapping the first 2 pages
        for (compteur=1; compteur<3; compteur++){
                    url = "https://www.doctolib.fr/"+speciality+"?page="+compteur;
                    try {
                        document = Jsoup.connect(url).userAgent("Mozilla").get();
                        tmp= document.select(".dl-search-result-presentation");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//        url = "https://www.doctolib.fr/dentiste";
//        try {
//            document = Jsoup.connect(url).userAgent("Mozilla").get();
//            tmp= document.select(".dl-search-result-presentation");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        for(Element p : tmp)
        {

            fullName=p.select(".dl-search-result-name").text().split("\\s+");


            //condition if the selected element is Doctor
            if(fullName[0].equals("Dr")){

                //initialize the name and last name
                name=""; lastName="";

                //put the name and last name in string :

                //if the doctor has more than a word in his first name or more than a world in his last name
                if(fullName.length>2){



                    for(int x=1; x<fullName.length-1;x++){
                        name+= fullName[x]+" ";

                    }
                }
                //if the doctor has one word in his first name
                else {
                    name=fullName[1];
                }

                lastName=fullName[fullName.length-1];

                //fill the doctor's list
                listDoc.add(new User(name,lastName,
                        p.select(".dl-search-result-subtitle").text(),
                        p.select(".dl-text").text(),
                        "https:"+p.select(".dl-search-result-avatar").select("a").select("img").attr("src")
                )) ;


            }else if(fullName[0].equals("Centre")){
                //  System.out.println("centre !");
            }

        }
        }
        return listDoc;
    }

    @Override
    public void addDoctors(String fullName,String speciality, String state) {
        String urlDocteur = "https://www.doctolib.fr/"+speciality+"/"+state+"/"+fullName;
        try {
            Document documentDocteur = Jsoup.connect(urlDocteur).userAgent("Mozilla").get();

            //Scrapping doctor's photo url
            String photoUrl = documentDocteur.select(".dl-profile-header-photo").select("img").attr("src");

            System.out.println("********* display url photo *********");
            System.out.println("https:"+photoUrl);
            System.out.println("");


            //Scraping 'Tarifs et remboursements' and 'Moyens de paiement'
            Elements tarif_moyenPaimentElements= documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-row-content")
                    .select(".dl-profile-card-content")
                    .select(".dl-profile-text");
            List<String> tarif_moyenPaiment= new ArrayList<String>();

            for (Element tarif_moyenPaimentElement: tarif_moyenPaimentElements){
                tarif_moyenPaiment.add(tarif_moyenPaimentElement.text());
            }

            String tarif=tarif_moyenPaiment.get(0);
            String moyenPaiement=tarif_moyenPaiment.get(1);

            System.out.println("********* display tarif *********");
            System.out.println("tarif: " + tarif);
            System.out.println("");
            System.out.println("********* display 'moyen de paiment' *********");
            System.out.println("moyen de paiement: " + moyenPaiement);
            System.out.println("");


            //Scraping 'Expertises, actes et symptômes'
            Elements expertiseElements= documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-skills")
                    .select(".dl-profile-skill-chip")
                    ;

            List<String> expertises= new ArrayList<String>();
            for (Element expertiseElement: expertiseElements){
                expertises.add(expertiseElement.text());
            }
            System.out.println("********* display expertise *********");
            System.out.println(expertises);
            System.out.println("");


            //Sraping 'adresse'
            Elements address= documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-doctor-place-map")
                    .select("img")
                    ;
            System.out.println("********* display address *********");
            System.out.println(address.attr("data-map-modal"));
            System.out.println("");


            System.out.println("********* display image of the address *********");
            System.out.println(address.attr("src"));
            System.out.println("");


            //Scraping 'moyen de trasnsport'

            List<String> moyenTrasnsport= new ArrayList<String>();
            Elements moyenTrasnsportElements= documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-card-content")
                    .select(".dl-profile-text")
                    .select("span")
                    ;
            for (Element moyenTrasnsportElement: moyenTrasnsportElements){

                moyenTrasnsport.add(moyenTrasnsportElement.text());
            }
            System.out.println("********* display 'moyen de transport' *********");
            System.out.println(moyenTrasnsport);
            System.out.println("");

            //Scraping 'Langues parlées'
            String langues="";
            Elements langueParleesElements= documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-card-content")
                    .select(".dl-profile-row-content")
                    .select(".dl-profile-row-section")
                    .select(".dl-profile-row-section")
                    ;

            langues=langueParleesElements.first().text();
            System.out.println("********* display 'Langues parlées' *********");
            System.out.println(langues);
            System.out.println("");



        } catch (IOException e) {
            e.printStackTrace();
        }

       // User moez= new User("moez");
        em.persist(new User(fullName));

    }


}
