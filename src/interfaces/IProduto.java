package interfaces;

import java.util.List;

import models.Produto;

//Contrato da regra de negócio
public interface IProduto {
    public boolean cadastrarProduto(Produto produto);
    public boolean atualizarProduto(Produto produto);
    public boolean removerProduto(int id);
    public List<Produto> listarProdutos();
    public Produto listarPorId(int id);
    public List<Produto> listarPorNome(String id);
    public List<Produto> listarPorCategoria(String categoria);
    public List<Produto> listarPorSubCategoria(String subCategoria);
}
