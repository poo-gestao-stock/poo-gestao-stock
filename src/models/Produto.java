package models;

import interfaces.IProduto;
import interfaces.IProdutoRepository;
import javafx.collections.ObservableList;
import repositories.ProdutoRepository;

public class Produto implements IProduto {

    // Atributos
    private int id;
    private String nome;
    private int qtdStockItem;
    private Categoria categoria;
    private SubCategoria subCategoria;
    private String cadastradoEm;

    private final IProdutoRepository _repository = new ProdutoRepository();

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdStockItem() {
        return qtdStockItem;
    }

    public void setQtdStockItem(int qtdStockItem) {
        this.qtdStockItem = qtdStockItem;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getCadastradoEm() {
        return cadastradoEm;
    }

    public void setCadastradoEm(String data) {
        this.cadastradoEm = data;
    }

    /// Regras de negócio
    @Override
    public boolean cadastrarProduto(Produto produto) {
        try {
            return _repository.inserirProduto(produto);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean atualizarProduto(Produto produto) {
        try {
            return _repository.atualizarProduto(produto);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removerProduto(int id) {
        try {
            return _repository.removerProduto(id);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ObservableList<Produto> listarProdutos() {
        try {
            return _repository.listarProdutos();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public Produto listarPorId(int id) {
        try {
            return _repository.listarProdutoPorId(id);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public ObservableList<Produto> listarPorNome(String nome) {
        try {
            return _repository.listarProdutosPorNome(nome);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public ObservableList<Produto> listarPorCategoria(String categoria) {
        try {
            return _repository.listarProdutosPorCategoria(categoria);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public ObservableList<Produto> listarPorSubCategoria(String subCategoria) {
        try {
            return _repository.listarProdutosPorSubCategoria(subCategoria);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", Nome do Produto: " + this.nome + ", Qtd. Stock: "
                + this.qtdStockItem
                + ", Categoria: " + this.getCategoria().getNome() + ", Sub-categoria: " +
                this.getSubCategoria().getNome() + "Data Cadastro: " + this.cadastradoEm;
    }

}
