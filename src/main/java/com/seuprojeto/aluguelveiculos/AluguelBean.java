package com.seuprojeto.aluguelveiculos;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class AluguelBean implements Serializable {

    private Aluguel aluguel = new Aluguel();
    private List<Aluguel> listaAlugueis;

    // Guardam apenas o ID escolhido no dropdown da tela
    private Long veiculoId;
    private Long clienteId;

    private List<Veiculo> listaVeiculos;
    private List<Cliente> listaClientes;

    private AluguelDAO aluguelDAO = new AluguelDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void salvar() {
        // Busca os objetos completos no banco a partir dos IDs escolhidos na tela
        Veiculo veiculo = veiculoDAO.buscarPorId(veiculoId);
        Cliente cliente = clienteDAO.buscarPorId(clienteId);

        // Regra do PDF: verifica se o veículo escolhido já está alugado e não entregue
        if (veiculoId != null && aluguelDAO.veiculoJaAlugado(veiculoId)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro", "Este veículo já está alugado e não foi entregue."));
            return;
        }

        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);

       if (aluguel.getId() == null) {
          aluguel.setEntregue('N'); // todo aluguel novo começa como "não entregue"
          aluguelDAO.cadastrar(aluguel);
        }        else {
                    aluguelDAO.alterar(aluguel);
                    }

        aluguel = new Aluguel();
        veiculoId = null;
        clienteId = null;
        listaAlugueis = null;
    }

    public void excluir(Aluguel a) {
        aluguelDAO.excluir(a.getId());
        listaAlugueis = null;
    }
    public void editar(Aluguel a) {
    this.aluguel = a;
    this.veiculoId = a.getVeiculo() != null ? a.getVeiculo().getId() : null;
    this.clienteId = a.getCliente() != null ? a.getCliente().getId() : null;
    }

    public List<Aluguel> getListaAlugueis() {
        if (listaAlugueis == null) {
            listaAlugueis = aluguelDAO.listarTodos();
        }
        return listaAlugueis;
    }

    public List<Veiculo> getListaVeiculos() {
        if (listaVeiculos == null) {
            listaVeiculos = veiculoDAO.listarTodos();
        }
        return listaVeiculos;
    }

    public List<Cliente> getListaClientes() {
        if (listaClientes == null) {
            listaClientes = clienteDAO.listarTodos();
        }
        return listaClientes;
    }

    public Aluguel getAluguel() {
        return aluguel;
    }

    public void setAluguel(Aluguel aluguel) {
        this.aluguel = aluguel;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    private List<Aluguel> listaNaoEntregues;

public List<Aluguel> getListaNaoEntregues() {
    if (listaNaoEntregues == null) {
        listaNaoEntregues = aluguelDAO.listarNaoEntregues();
    }
    return listaNaoEntregues;
}
}