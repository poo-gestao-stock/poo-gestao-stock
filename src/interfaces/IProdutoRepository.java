package interfaces;

import javafx.collections.ObservableList;
import models.Categoria;
import models.Produto;
import models.SubCategoria;

public interface IProdutoRepository {
    public boolean inserirProduto(Produto produto);

    public boolean atualizarProduto(Produto produto);

    public boolean removerProduto(int id);

    public ObservableList<Produto> listarProdutos();

    public ObservableList<Produto> listarProdutosPorCategoria(String categoria);

    public ObservableList<Produto> listarProdutosPorSubCategoria(String subCategoria);

    public Produto listarProdutoPorId(int id);

    public ObservableList<Produto> listarProdutosPorNome(String nome);

    public ObservableList<SubCategoria> listarSubCategorias();

    public ObservableList<Categoria> listarCategorias();

    public SubCategoria listarSubCategoriasPorNome(String kNome);

    public Categoria listarCategoriasPorNome(String kNome);
}
