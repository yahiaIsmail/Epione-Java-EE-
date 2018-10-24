package EPIONE.JAVAEE.services.implementation;

import EPIONE.JAVAEE.entities.User;
import EPIONE.JAVAEE.services.interfaces.UserServiceRemote;


import javax.ejb.Stateless;
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
        User d= new User("xx","xx","xx","xx","xx");
        List<User> listDoc= new ArrayList<User>();
        listDoc.add(d);
        return listDoc;
    }



}
