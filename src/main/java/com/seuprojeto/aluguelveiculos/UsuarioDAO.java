package com.seuprojeto.aluguelveiculos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UsuarioDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AluguelVeiculosPU");

    public void cadastrar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public List<Usuario> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> lista = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        em.close();
        return lista;
    }

    public Usuario buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = em.find(Usuario.class, id);
        em.close();
        return usuario;
    }

    public void alterar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
        em.getTransaction().commit();
        em.close();
    }

    // Usado para validar login: busca um usuário pelo login e senha informados
    public Usuario validarLogin(String login, String senha) {
        EntityManager em = emf.createEntityManager();
        List<Usuario> lista = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha",
            Usuario.class)
            .setParameter("login", login)
            .setParameter("senha", senha)
            .getResultList();
        em.close();
        return lista.isEmpty() ? null : lista.get(0);
    }
}
