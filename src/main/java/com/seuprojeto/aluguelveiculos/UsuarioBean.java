package com.seuprojeto.aluguelveiculos;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

    private Usuario usuario = new Usuario();
    private List<Usuario> listaUsuarios;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void salvar() {
        if (usuario.getId() == null) {
            usuarioDAO.cadastrar(usuario);
        } else {
            usuarioDAO.alterar(usuario);
        }
        usuario = new Usuario();
        listaUsuarios = null;
    }

    public void editar(Usuario u) {
        this.usuario = u;
    }

    public void excluir(Usuario u) {
        usuarioDAO.excluir(u.getId());
        listaUsuarios = null;
    }

    public List<Usuario> getListaUsuarios() {
        if (listaUsuarios == null) {
            listaUsuarios = usuarioDAO.listarTodos();
        }
        return listaUsuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}