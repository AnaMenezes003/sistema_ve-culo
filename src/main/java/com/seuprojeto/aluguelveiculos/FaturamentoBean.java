package com.seuprojeto.aluguelveiculos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class FaturamentoBean implements Serializable {

    private Date dataInicio;
    private Date dataFim;
    private BigDecimal resultado;

    private AluguelDAO aluguelDAO = new AluguelDAO();

    public void consultar() {
        resultado = aluguelDAO.calcularFaturamento(dataInicio, dataFim);
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getResultado() {
        return resultado;
    }
}