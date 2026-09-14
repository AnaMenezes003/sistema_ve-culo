package com.seuprojeto.aluguelveiculos;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ClienteBean implements Serializable {

    private Cliente cliente = new Cliente();
    private List<Cliente> listaClientes;

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void salvar() {
        if (cliente.getId() == null) {
            clienteDAO.cadastrar(cliente);
        } else {
            clienteDAO.alterar(cliente);
        }
        cliente = new Cliente();
        listaClientes = null;
    }

    public void editar(Cliente c) {
        this.cliente = c;
    }

    public void excluir(Cliente c) {
    try {
        clienteDAO.excluir(c.getId());
        listaClientes = null;
    } catch (Exception e) {
        javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
            new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR,
                "Erro", "Não é possível excluir: este cliente possui aluguéis vinculados."));
    }
}

    public List<Cliente> getListaClientes() {
        if (listaClientes == null) {
            listaClientes = clienteDAO.listarTodos();
        }
        return listaClientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}