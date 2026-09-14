package com.seuprojeto.aluguelveiculos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AluguelDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AluguelVeiculosPU");

    public void cadastrar(Aluguel aluguel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(aluguel);
        em.getTransaction().commit();
        em.close();
    }

    public List<Aluguel> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Aluguel> lista = em.createQuery("SELECT a FROM Aluguel a", Aluguel.class).getResultList();
        em.close();
        return lista;
    }

    public Aluguel buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Aluguel aluguel = em.find(Aluguel.class, id);
        em.close();
        return aluguel;
    }

    public void alterar(Aluguel aluguel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(aluguel);
        em.getTransaction().commit();
        em.close();
    }

    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Aluguel aluguel = em.find(Aluguel.class, id);
        if (aluguel != null) {
            em.remove(aluguel);
        }
        em.getTransaction().commit();
        em.close();
    }

    // Regra do PDF: verifica se um veículo já está alugado e não foi entregue ('N')
    public boolean veiculoJaAlugado(Long veiculoId) {
        EntityManager em = emf.createEntityManager();
        Long count = em.createQuery(
            "SELECT COUNT(a) FROM Aluguel a WHERE a.veiculo.id = :veiculoId AND a.entregue = 'N'",
            Long.class)
            .setParameter("veiculoId", veiculoId)
            .getSingleResult();
        em.close();
        return count > 0;
    }
    // Consulta o faturamento total num período específico, somando o valorPago de todos os aluguéis
    public java.math.BigDecimal calcularFaturamento(java.util.Date dataInicio, java.util.Date dataFim) {
    EntityManager em = emf.createEntityManager();

    java.math.BigDecimal total = em.createQuery(
        "SELECT SUM(a.valorPago) FROM Aluguel a WHERE a.dataAluguel BETWEEN :inicio AND :fim",
        java.math.BigDecimal.class)
        .setParameter("inicio", dataInicio)
        .setParameter("fim", dataFim)
        .getSingleResult();

    em.close();

    // Se não houver nenhum aluguel no período, o SUM retorna null - tratamos como zero
    return total != null ? total : java.math.BigDecimal.ZERO;
    }
    // Lista todos os aluguéis cujo veículo ainda não foi entregue ('N')
    public List<Aluguel> listarNaoEntregues() {
    EntityManager em = emf.createEntityManager();

    List<Aluguel> lista = em.createQuery(
        "SELECT a FROM Aluguel a WHERE a.entregue = 'N'",
        Aluguel.class)
        .getResultList();

    em.close();
    return lista;

    }
}
