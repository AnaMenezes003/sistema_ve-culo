package com.seuprojeto.aluguelveiculos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class VeiculoDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AluguelVeiculosPU");

    public void cadastrar(Veiculo veiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(veiculo);
        em.getTransaction().commit();
        em.close();
    }

    public List<Veiculo> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Veiculo> lista = em.createQuery("SELECT v FROM Veiculo v", Veiculo.class).getResultList();
        em.close();
        return lista;
    }

    public Veiculo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Veiculo veiculo = em.find(Veiculo.class, id);
        em.close();
        return veiculo;
    }

    public void alterar(Veiculo veiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(veiculo);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Veiculo veiculo = em.find(Veiculo.class, id);
        if (veiculo != null) {
            em.remove(veiculo);
        }
        em.getTransaction().commit();
        em.close();
    }
}