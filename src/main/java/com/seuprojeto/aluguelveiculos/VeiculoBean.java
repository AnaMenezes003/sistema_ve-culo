package com.seuprojeto.aluguelveiculos;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class VeiculoBean implements Serializable {

    private Veiculo veiculo = new Veiculo();
    private List<Veiculo> listaVeiculos;

    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    public void salvar() {
        if (veiculo.getId() == null) {
            veiculoDAO.cadastrar(veiculo); // não tem ID ainda: é um veículo novo
        } else {
            veiculoDAO.alterar(veiculo); // já tem ID: é uma edição de um existente
        }
        veiculo = new Veiculo();
        listaVeiculos = null;
    }

    // Carrega o veículo escolhido de volta no formulário, para edição
    public void editar(Veiculo v) {
        this.veiculo = v;
    }

    public void excluir(Veiculo v) {
    try {
        veiculoDAO.excluir(v.getId());
        listaVeiculos = null;
    } catch (Exception e) {
        javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
            new javax.faces.application.FacesMessage(javax.faces.application.FacesMessage.SEVERITY_ERROR,
                "Erro", "Não é possível excluir: este veículo possui aluguéis vinculados."));
    }
}

    public List<Veiculo> getListaVeiculos() {
        if (listaVeiculos == null) {
            listaVeiculos = veiculoDAO.listarTodos();
        }
        return listaVeiculos;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}