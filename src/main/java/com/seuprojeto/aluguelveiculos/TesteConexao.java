package com.seuprojeto.aluguelveiculos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteConexao {

    public static void main(String[] args) {

        // Cria a "fábrica" lendo as configurações do persistence.xml
        // "AluguelVeiculosPU" precisa ser exatamente o nome que demos na persistence-unit
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AluguelVeiculosPU");

        // Cria o "atendente" que vai executar as operações no banco
        EntityManager em = emf.createEntityManager();

        // Cria um objeto Veiculo em memória (ainda não está no banco)
        Veiculo v = new Veiculo();
        v.setNumero("001");
        v.setPlaca("ABC1234");
        v.setFabricante("Fiat");
        v.setModelo("Uno");
        v.setAnoModelo(2020);
        v.setQtdPortas(4);
        v.setAcessorios("Ar condicionado, direção hidráulica");

        // getTransaction().begin() = "começa uma operação no banco"
        // Toda escrita (salvar, alterar, excluir) precisa estar dentro de uma transação
        em.getTransaction().begin();
        em.persist(v); // persist = "salva esse objeto no banco"
        em.getTransaction().commit(); // commit = "confirma, pode gravar de vez"

        System.out.println("Veículo salvo com sucesso! ID gerado: " + v.getId());

        // Fecha a conexão
        em.close();
        emf.close();
    }
}