package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.UserServiceRemote;


import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
public class UserService implements UserServiceRemote {


    @Override
    public List<User> scrapingAllDoctors()  {
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
        //for (compteur=1; compteur<3; compteur++){
        //            url = "https://www.doctolib.fr/"+speciality+"?page="+compteur;
        //            try {
        //                document = Jsoup.connect(url).userAgent("Mozilla").get();
        //                tmp= document.select(".dl-search-result-presentation");
        //
        //            } catch (IOException e) {
        //                e.printStackTrace();
        //            }

        url = "https://www.doctolib.fr/dentiste";
        try {
            document = Jsoup.connect(url).userAgent("Mozilla").get();
            tmp= document.select(".dl-search-result-presentation");

        } catch (IOException e) {
            e.printStackTrace();
        }



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
        return listDoc;
    }



}
