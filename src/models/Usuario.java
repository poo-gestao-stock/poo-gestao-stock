package models;

import java.util.List;

import interfaces.IUsuario;
import interfaces.IUsuarioRepository;
import repositories.UsuarioRepository;

public class Usuario implements IUsuario {

    // Atributos
    private int id;
    private String nomeUsuario;
    private String senha;

    private final IUsuarioRepository _repository = new UsuarioRepository();

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nome) {
        this.nomeUsuario = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /// Regras de negócio
    @Override
    public boolean cadastrarUsuario(Usuario Usuario) {
        try {
            return _repository.inserirUsuario(Usuario);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        try {
            return _repository.listarUsuarios();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", Nome do Usuario: " + this.nomeUsuario;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
