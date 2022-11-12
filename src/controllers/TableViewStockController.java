package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import models.Categoria;
import models.Produto;
import models.SubCategoria;
import repositories.ProdutoRepository;
import utils.MeuAlert;
import utils.Util;

public class TableViewStockController {

    public ObservableList<Produto> produtosObs = FXCollections.observableArrayList();

    @FXML
    private Button btnApagar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnNovo;

    @FXML
    private ChoiceBox<String> cbCategoria;

    @FXML
    private ChoiceBox<String> cbPesquisaTipo;

    @FXML
    private ChoiceBox<String> cbSubcategoria;

    @FXML
    private TableColumn<Categoria, String> colCategoria;

    @FXML
    private TableColumn<Produto, String> colDataCadastro;

    @FXML
    private TableColumn<String, Integer> colId;

    @FXML
    private TableColumn<Produto, String> colNomeProduto;

    @FXML
    private TableColumn<Produto, Integer> colQtdStock;

    @FXML
    private TableColumn<SubCategoria, String> colSubCategoria;

    @FXML
    private Label resultados;

    @FXML
    private Tab tabEntrada;

    @FXML
    private TabPane tpProdutos;

    @FXML
    private Tab tabSaida;

    @FXML
    private TextField tfPesquisar;

    @FXML
    private TextField tfProduto;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TextField tfID;

    @FXML
    private DatePicker dpCadastradoEm;

    @FXML
    private TableView<Produto> tvProdutos;

    private final String porNome = "Por Nome";
    private final String porID = "Por ID";
    private final String porCategoria = "Por Categoria";
    private final String porSubCategoria = "Por Sub-categoria";

    private void _limparCampos() {
        tfProduto.setText("");
        tfQuantidade.setText("");
        tfID.setText("");
        _setCategoriasCb();
    }

    private void _desabilitarCampos() {
        tfID.setDisable(true);
        tfProduto.setDisable(true);
        tfQuantidade.setDisable(true);
        cbCategoria.setDisable(true);
        cbSubcategoria.setDisable(true);
        dpCadastradoEm.setDisable(true);
    }

    private void _habilitarCampos() {
        tfProduto.setDisable(false);
        tfQuantidade.setDisable(false);
        cbCategoria.setDisable(false);
        cbSubcategoria.setDisable(false);
    }

    private void _configurarTableView() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQtdStock.setCellValueFactory(new PropertyValueFactory<>("qtdStockItem"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colSubCategoria.setCellValueFactory(new PropertyValueFactory<>("subCategoria"));
        colDataCadastro.setCellValueFactory(new PropertyValueFactory<>("cadastradoEm"));
        tvProdutos.setItems(produtosObs);
    }

    private void _atualizarTableView() {
        produtosObs.clear();
        Produto produto = new Produto();

        ObservableList<Produto> produtos = produto.listarProdutos();
        produtosObs.setAll(produtos);

        int length = produtos.size();
        resultados.setText("Resultados: " + length);
    }

    @FXML
    void onApagar(ActionEvent event) {
        new Produto().removerProduto(Integer.parseInt(tfID.getText()));
        MeuAlert.mostrar("Aviso", "Produto removido com sucesso", AlertType.INFORMATION);

        _limparCampos();
        _desabilitarCampos();

        btnNovo.setDisable(false);
        btnGuardar.setDisable(true);
        btnApagar.setDisable(true);
        btnCancelar.setDisable(true);

        _atualizarTableView();
    }

    @FXML
    void onAtualizar(ActionEvent event) {
        _atualizarTableView();
    }

    @FXML
    void onCancelar(ActionEvent event) {
        _limparCampos();
        _habilitarCampos();

        btnNovo.setDisable(false);
        btnGuardar.setDisable(true);
        btnApagar.setDisable(true);
        btnCancelar.setDisable(true);
    }

    @FXML
    void onContextMenuRequested(ContextMenuEvent event) {
        ContextMenu menu = new ContextMenu();
        MenuItem modificarItem = new MenuItem("Alterar");
        modificarItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Produto produto = tvProdutos.getSelectionModel().getSelectedItem();

                if (produto == null) {
                    MeuAlert.mostrar("Aviso", "Selecione um produto para modificar", AlertType.WARNING);
                    return;
                }

                tfID.setText(String.valueOf(produto.getId()));
                tfProduto.setText(produto.getNome());
                tfQuantidade.setText(String.valueOf(produto.getQtdStockItem()));
                cbCategoria.setValue(produto.getCategoria().getNome());
                cbSubcategoria.setValue(produto.getSubCategoria().getNome());
                dpCadastradoEm.setPromptText(produto.getCadastradoEm());

                _habilitarCampos();

                btnNovo.setDisable(true);
                btnGuardar.setDisable(false);
                btnApagar.setDisable(false);
                btnCancelar.setDisable(false);

                // Mudar as telas
                SingleSelectionModel<Tab> selectionModel = tpProdutos.getSelectionModel();
                selectionModel.select(0);
            }
        });

        menu.getItems().add(modificarItem);
        tvProdutos.setContextMenu(menu);
    }

    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onGuardar(ActionEvent event) {
        Produto prdt = new Produto();
        Categoria cat = new Categoria();
        SubCategoria subCat = new SubCategoria();

        int qtdStock;

        try {
            qtdStock = Integer.parseInt(tfQuantidade.getText());
        } catch (Exception e) {
            MeuAlert.mostrar("Erro de Validação", "Preencha apenas números", AlertType.WARNING);
            return;
        }

        prdt.setNome(tfProduto.getText());
        prdt.setQtdStockItem(qtdStock);

        cat.setId((new ProdutoRepository().listarCategoriasPorNome(cbCategoria.getValue()).getId()));
        cat.setNome(cbCategoria.getValue());

        subCat.setId((new ProdutoRepository().listarSubCategoriasPorNome(cbSubcategoria.getValue()).getId()));
        subCat.setNome(cbSubcategoria.getValue());

        prdt.setCategoria(cat);
        prdt.setSubCategoria(subCat);

        if (cbCategoria.getValue() == null || cbSubcategoria.getValue() == null) {
            MeuAlert.mostrar("Erro de Validação", "Os campos Categoria e Sub-categoria devem ser selecionados.",
                    AlertType.WARNING);
        } else {
            if (tfID.getText() == null || tfID.getText().isEmpty()) {
                if (prdt.cadastrarProduto(prdt)) {
                    _depoisDeGuardar();

                    MeuAlert.mostrar("Aviso", "Produto cadastrado com sucesso", AlertType.INFORMATION);

                    _atualizarTableView();
                } else {
                    MeuAlert.mostrar("Erro", "Ocorreu um erro ao cadastrar o produto.", AlertType.ERROR);
                }
            } else {
                prdt.setId(Integer.parseInt(tfID.getText()));
                prdt.setCadastradoEm(dpCadastradoEm.getPromptText());
                if (prdt.atualizarProduto(prdt)) {
                    _depoisDeGuardar();

                    MeuAlert.mostrar("Aviso", "Produto Atualizado com sucesso", AlertType.INFORMATION);

                    _atualizarTableView();
                } else {
                    MeuAlert.mostrar("Erro", "Ocorreu um erro ao Atualizar o produto.", AlertType.ERROR);
                }
            }
        }
    }

    private void _depoisDeGuardar() {
        _limparCampos();
        _desabilitarCampos();
        btnNovo.setDisable(false);
        btnGuardar.setDisable(true);
        btnApagar.setDisable(true);
        btnCancelar.setDisable(true);
    }

    @FXML
    void onNovo(ActionEvent event) {
        _limparCampos();
        _habilitarCampos();

        btnNovo.setDisable(true);
        btnGuardar.setDisable(false);
        btnApagar.setDisable(true);
        btnCancelar.setDisable(false);
    }

    @FXML
    void onPesquisar(ActionEvent event) {
        _pesquisar(tfPesquisar.getText());
    }

    @FXML
    void initialize() {

        _setCategoriasCb();
        cbCategoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                _obterSubcategoriaPorcategoria(arg2);
            }

        });

        _configurarTableView();
        _atualizarTableView();
        _limparCampos();
        _desabilitarCampos();

        btnNovo.setDisable(false);
        btnGuardar.setDisable(true);
        btnApagar.setDisable(true);
        btnCancelar.setDisable(true);

        cbPesquisaTipo.getItems().addAll(
                porNome,
                porID,
                porCategoria,
                porSubCategoria);

        // Default
        cbPesquisaTipo.setValue(porNome);

        tfPesquisar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String valorAntigo, String novoValor) {
                _pesquisar(novoValor);
            }
        });
    }

    private void _setCategoriasCb() {
        cbCategoria.getItems().clear();

        var repo = new ProdutoRepository();

        var cats = repo.listarCategorias();
        ObservableList<String> catsStr = FXCollections.observableArrayList();

        cats.forEach((c) -> {
            catsStr.add(c.getNome());
        });

        cbCategoria.getItems().addAll(catsStr);
        cbCategoria.setValue(catsStr.get(0));
    }

    private void _obterSubcategoriaPorcategoria(String cat) {
        var repo = new ProdutoRepository();
        var c = repo.listarCategoriasPorNome(cat != null ? cat : cbCategoria.getValue());
        var subCats = repo.listarSubCategoriasPorCatId(c.getId());

        ObservableList<String> subcatsStr = FXCollections.observableArrayList();

        for (SubCategoria i : subCats) {
            subcatsStr.add(i.getNome());
        }

        cbSubcategoria.getItems().clear();
        cbSubcategoria.getItems().addAll(subcatsStr);
        cbSubcategoria.setValue(subcatsStr.get(0));
    }

    private void _pesquisar(String valor) {
        produtosObs.clear();
        resultados.setText("Resultados: 0");
        var prdtRepo = new Produto();
        String tipoBusca = cbPesquisaTipo.getValue();

        if (tipoBusca.equals(porID)) {
            var id = 0;
            try {
                id = Integer.parseInt(valor);
            } catch (Exception e) {
                MeuAlert.mostrar("Erro", "Informe um Id válido", AlertType.WARNING);
                return;
            }

            var prdt = prdtRepo.listarPorId(id);
            if (prdt != null) {
                produtosObs.add(prdt);
                resultados.setText("Resultados: 1");
            } else {
                resultados.setText("Resultados: 0");
            }

            return;
        }

        ObservableList<Produto> prdts = FXCollections.observableArrayList();

        switch (tipoBusca) {
            case porNome:
                prdts = valor == null || valor.isEmpty()
                        ? prdtRepo.listarProdutos()
                        : prdtRepo.listarPorNome(valor);
                break;
            case porCategoria:
                prdts = valor == null || valor.isEmpty()
                        ? prdtRepo.listarProdutos()
                        : prdtRepo.listarPorCategoria(valor);
                break;
            case porSubCategoria:
                prdts = valor == null || valor.isEmpty()
                        ? prdtRepo.listarProdutos()
                        : prdtRepo.listarPorSubCategoria(valor);
                break;
            default:
                MeuAlert.mostrar("Erro", "Tipo de pesquisa incorreto.", AlertType.WARNING);
                return;
        }
        produtosObs.setAll(prdts);
        resultados.setText("Resultados: " + prdts.size());
    }

}
