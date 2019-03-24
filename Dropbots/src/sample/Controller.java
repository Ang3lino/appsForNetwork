package sample;

import backend.tcp.TcpClient;
import backend.utilidades.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ObservableList list;

    @FXML
    private ListView<?> listViewDirectory;

    public void downloadFile(ActionEvent e) {
        UtilFun.createFolder("HeyThere");
    }

    public void selectFolderToUpload(ActionEvent e) {
        DirectoryChooser chooser = new DirectoryChooser();
        File chosenFile = chooser.showDialog(null);
        System.out.println(chosenFile);
    }

    public void selectFilesToUpload(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        List<File> chosenFiles = chooser.showOpenMultipleDialog(null);
        chosenFiles.forEach(file -> list.add(file));
        listViewDirectory.getItems().addAll(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();

        TcpClient client = new TcpClient();
        Pack p = new Pack(MyState.UPDATE_DIR);
        File[] subfiles = client.getSubFiles("");

        for (File subfile : subfiles) list.add(subfile);
        listViewDirectory.getItems().addAll(list);
    }
}
