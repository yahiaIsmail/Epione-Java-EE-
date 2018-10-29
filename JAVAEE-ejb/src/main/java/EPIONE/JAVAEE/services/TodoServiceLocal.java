package EPIONE.JAVAEE.services;

import java.util.List;

import javax.ejb.Local;

import EPIONE.JAVAEE.persistence.Todo;

@Local
public interface TodoServiceLocal {

    void create(Todo todo);

    List<Todo> findAll();

}
