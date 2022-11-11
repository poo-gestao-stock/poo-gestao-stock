package utils;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Util {

    public final static Util instance = new Util();

    public void colocarImagensBotoes(
            Button btnNovo,
            Button btnGuardar,
            Button btnApagar,
            Button btnCancelar,
            Button btnAtualizar

    ) {
        var linkNovo = getClass().getResource("/img/novo.png");
        var linkGuardar = getClass().getResource("/img/guardar.png");
        var linkApagar = getClass().getResource("/img/apagar.png");
        var linkCancelar = getClass().getResource("/img/cancelar.png");
        var linkRefresh = getClass().getResource("/img/atualizar.png");

        Image novaImg = new Image(linkNovo.toString(), 24, 24, false, true);
        Image guardarImg = new Image(linkGuardar.toString(), 24, 24, false, true);
        Image apagarImg = new Image(linkApagar.toString(), 24, 24, false, true);
        Image cancelarImg = new Image(linkCancelar.toString(), 24, 24, false, true);
        Image refreshImg = new Image(linkRefresh.toString(), 16, 16, false, true);

        btnNovo.setGraphic(new ImageView(novaImg));
        btnGuardar.setGraphic(new ImageView(guardarImg));
        btnApagar.setGraphic(new ImageView(apagarImg));
        btnCancelar.setGraphic(new ImageView(cancelarImg));
        btnAtualizar.setGraphic(new ImageView(refreshImg));
    }
}
