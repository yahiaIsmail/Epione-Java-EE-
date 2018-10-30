package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.*;
import EPIONE.JAVAEE.services.interfaces.UserServiceLocal;


import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import EPIONE.JAVAEE.services.interfaces.UserServiceRemote;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

@Stateless
public class UserService implements UserServiceLocal, UserServiceRemote {

    @PersistenceContext(unitName = "JAVAEE-ejb")
    EntityManager em;

    @Override
    public List<User> scrapingAllDoctors(String speciality) {
        List<String> list = new ArrayList<>();
        User d = new User();
        List<User> listDoc = new ArrayList<User>();

        int compteur = 0;
        String url = "";
        Document document;
        Elements tmp = null;
        String str = "";


        String fullName[];
        String name = "";
        String lastName = "";


        //scrapping the first 2 pages
        for (compteur = 1; compteur < 3; compteur++) {
            url = "https://www.doctolib.fr/" + speciality + "?page=" + compteur;
            try {
                document = Jsoup.connect(url).userAgent("Mozilla").get();
                tmp = document.select(".dl-search-result-presentation");

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Element p : tmp) {

                fullName = p.select(".dl-search-result-name").text().split("\\s+");


                //condition if the selected element is Doctor
                if (fullName[0].equals("Dr") || fullName[0].equals("M.") || fullName[0].equals("Mme")) {

                    //initialize the name and last name
                    name = "";
                    lastName = "";

                    //put the name and last name in string :

                    //if the doctor has more than a word in his first name or more than a world in his last name
                    if (fullName.length > 2) {


                        for (int x = 1; x < fullName.length - 1; x++) {
                            name += fullName[x] + " ";

                        }
                    }
                    //if the doctor has one word in his first name
                    else {
                        name = fullName[1];
                    }

                    lastName = fullName[fullName.length - 1];


                    //fill the doctor's list
                    listDoc.add(new User(name, lastName,
                            p.select(".dl-search-result-subtitle").text(),//speciality scraped
                            new Address(p.select(".dl-text").text()),
                            "https:" + p.select(".dl-search-result-avatar").select("a").select("img").attr("src")
                    ));


                } else if (fullName[0].equals("Centre")) {
                    //  System.out.println("centre !");
                }

            }
        }
        return listDoc;
    }

    @Override
    public int addDoctors(String firstName, String lastName, String speciality, String state, String email, String password) {
        String urlDocteur = "https://www.doctolib.fr/" + speciality + "/" + state + "/" + firstName.toLowerCase() + "-" + lastName.toLowerCase();
        String url = "";
        String tariff = "";
        String moyenPaiement = "";
        String langues = "";
        String imgAddress = "";

        String address;
        Address addressSplited = new Address();
        String fullAddress = "";
        String longitude = "";
        String latitude = "";
        List<Expertise> expertises = new ArrayList<Expertise>();
        List<Transport> moyenTrasnsport = new ArrayList<Transport>();
        User doctor = new User();
        int ok = -1;
        String exist = "";
        try {
            Document documentDocteur = Jsoup.connect(urlDocteur).userAgent("Mozilla").get();

            //if user does not exist
            exist = documentDocteur.select(".dl-profile").text();
            if (exist.isEmpty()) {
                return -1;
            }
            System.out.println("exist: " + exist);

            //Scrapping doctor's photo url
            String photoUrl = documentDocteur.select(".dl-profile-header-photo").select("img").attr("src");

            System.out.println("******* display url photo *******");
            System.out.println("https:" + photoUrl);
            url = "https:" + photoUrl;
            System.out.println("");


            //Scraping 'Tarifs et remboursements' and 'Moyens de paiement'
            Elements tarif_moyenPaimentElements = documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-row-content")
                    .select(".dl-profile-card-content")
                    .select(".dl-profile-text");
            List<String> tarif_moyenPaiment = new ArrayList<String>();

            for (Element tarif_moyenPaimentElement : tarif_moyenPaimentElements) {
                tarif_moyenPaiment.add(tarif_moyenPaimentElement.text());
            }

            tariff = tarif_moyenPaiment.get(0);
            moyenPaiement = tarif_moyenPaiment.get(1);

            System.out.println("******* display tarif *******");
            System.out.println("tarif: " + tariff);
            System.out.println("");
            System.out.println("******* display 'moyen de paiment' *******");
            System.out.println("moyen de paiement: " + moyenPaiement);
            System.out.println("");


            //Scraping 'Expertises, actes et symptômes'
            Elements expertiseElements = documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-skills")
                    .select(".dl-profile-skill-chip");

            for (Element expertiseElement : expertiseElements) {
                expertises.add(new Expertise(expertiseElement.text()));
            }
            System.out.println("******* display expertise *******");
            System.out.println(expertises);
            System.out.println("");


            //Sraping 'adresse'
            Elements addressElements = documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-doctor-place-map")
                    .select("img");
            System.out.println("******* display address *******");
            // System.out.println(addressElements.attr("data-map-modal"));
            address = addressElements.attr("data-map-modal");

            fullAddress = address.substring(address.indexOf("title") + 8, address.indexOf("lat") - 3);
            latitude = address.substring(address.indexOf("lat") + 5, address.indexOf("lng") - 2);
            longitude = address.substring(address.indexOf("lng") + 5, address.length() - 1);
//            System.out.println("fulladdress:  "+ fullAddress);
//            System.out.println("lat: "+ latitude);
//            System.out.println("lng: "+ longitude);

            addressSplited = new Address(fullAddress, latitude, longitude);
            System.out.println(addressSplited);
            System.out.println("");


            System.out.println("******* display image of the address *******");
            System.out.println(addressElements.attr("src"));
            // imgAddress=address.attr("src");
            System.out.println("");


            //Scraping 'moyen de trasnsport'

            Elements moyenTrasnsportElements = documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-card-content")
                    .select(".dl-profile-text")
                    .select("span");
            for (Element moyenTrasnsportElement : moyenTrasnsportElements) {
                if (moyenTrasnsportElement.text().substring(0, 3).equals("Bus") ||

                        moyenTrasnsportElement.text().substring(0, 5).equals("Métro")
                ) {
                    //  System.out.println(moyenTrasnsportElement.text().substring(0,3));
                    moyenTrasnsport.add(new Transport(moyenTrasnsportElement.text()));
                }

            }
            System.out.println("******* display 'moyen de transport' *******");
            System.out.println(moyenTrasnsport);
            System.out.println("");

            //Scraping 'Langues parlées'

            Elements langueParleesElements = documentDocteur.select(".dl-profile-card")
                    .select(".dl-profile-card-section")
                    .select(".dl-profile-card-content")
                    .select(".dl-profile-row-content")
                    .select(".dl-profile-row-section")
                    .select(".dl-profile-row-section");

            langues = langueParleesElements.first().text();
            System.out.println("******* display 'Langues parlées' *******");
            System.out.println(langues);
            System.out.println("");

        } catch (IOException e) {
            e.printStackTrace();

        }

        em.persist(addressSplited);


        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setPassword(password);
        doctor.setConfirmation(password);
        doctor.setEmail(email);
        doctor.setState(state);
        doctor.setSpeciality(speciality);
        doctor.setUrlPhoto(url);
        doctor.setTariff(tariff.replaceAll("Voir les tarifs", ""));
        doctor.setPaimentMethode(moyenPaiement);
        doctor.setLanguage(langues.replaceAll("Langues parlées", ""));
        doctor.setUsername(firstName + "-" + lastName);
        doctor.setRole(Roles.Doctor);
        doctor.setAddress(addressSplited);
        em.persist(doctor);


        for (int i = 0; i < expertises.size(); i++) {
            expertises.get(i).setDoctor(doctor);
            em.persist(expertises.get(i));
        }

        for (int i = 0; i < moyenTrasnsport.size(); i++) {
            moyenTrasnsport.get(i).setDoctor(doctor);
            em.persist(moyenTrasnsport.get(i));
        }

        return doctor.getId();
    }

    @Override
    public List<User> getDoctor(User user) {
        return (List<User>) em.createQuery("select u from User u where u.email=:email", User.class)
                .setParameter("email", user.getEmail())
                .getResultList();

    }

    @Override
    public List<User> getAllDoctors() {

        return em.createQuery("select u from User u" +
                " where u.role=:role", User.class)
                .setParameter("role", Roles.Doctor)
                .getResultList();
    }

    @Override
    public int addPatient(User user) {
        user.setConfirmation("0");
        user.setAddress(null);
        user.setEnabled(true);
        user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
        String token = Base64.getEncoder().encodeToString(user.getUsername().getBytes()) + Base64.getEncoder().encodeToString(user.getPassword().getBytes()) + Base64.getEncoder().encodeToString(user.getEmail().getBytes());
        user.setConfirmationToken(token);
        em.persist(user);
        return user.getId();
    }


    @Override
    public boolean activatePatient(String activationToken) {
        try {
            User usr = (User) em.createQuery(
                    "SELECT u FROM User u WHERE u.confirmationToken = :token")
                    .setParameter("token", activationToken)
                    .getSingleResult();
            usr.setConfirmation("1");
            em.merge(usr);
            return true;
        } catch (javax.persistence.NoResultException exp) {
            return false;
        }
    }

    @Override
    public boolean login(User u) throws Exception {
        // System.out.println(u.getPassword() + "password ");
        //  System.out.println(new String(Base64.getDecoder().decode(u.getPassword()),"UTF-8") + " password dencrypted");
        //byte[] decode= Base64.getDecoder().decode(u.getPassword());
        String encode = Base64.getEncoder().encodeToString(u.getPassword().getBytes());
        System.out.println("encode: " + encode);
        System.out.println("Login from service : " + u);
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username "
                + "AND u.password = :password");
        query.setParameter("username", u.getUsername());
        query.setParameter("password", encode);
        // query.setParameter("password", decode);
        int resultCount = query.getResultList().size();
        System.out.println("Found " + resultCount + " Result(s) ");
        if (resultCount != 1) {
            return false;
        }
        return true;

    }
}



