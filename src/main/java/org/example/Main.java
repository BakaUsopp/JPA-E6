package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String puName = "pu-name";
        Map<String,String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create"); // create ,update, none

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Post p =new Post();
            p.setTitle("One Piece");
            p.setContent("Anime");

            Comment c1 = new Comment();
            c1.setContent("Action,Adventure,Thriller");


            Comment c2 = new Comment();
            c2.setContent("Comic");


            p.setComments(List.of(c1,c2));
            c1.setPost(p);
            c2.setPost(p);


//            em.persist(c1);
            em.persist(p);


            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}