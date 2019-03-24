package sample;

import backend.tcp.TcpClient;
import backend.utilidades.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ObservableList list;

    @FXML
    private ListView<?> listViewDirectory;

    @FXML
    private TextField textViewPath;


    public void downloadFile(ActionEvent e) {
        UtilFun.createFolder("HeyThere");
    }

    public void selectFolderToUpload(ActionEvent e) {
        DirectoryChooser chooser = new DirectoryChooser();
        File chosenFile = chooser.showDialog(null);
        List<File> files = new ArrayList<>();
        files.add(chosenFile);
    }

    public void selectFilesToUpload(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        List<File> chosenFiles = chooser.showOpenMultipleDialog(null);
        //chosenFiles.forEach(file -> list.add(file));
        //listViewDirectory.getItems().addAll(list);

        TcpClient client = new TcpClient();
        client.uploadFiles(chosenFiles);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();

        // set initial directory in GUI
        TcpClient client = new TcpClient();
        Pack p = new Pack(MyState.UPDATE_DIR);
        File[] subfiles = client.getSubFiles("");
        for (File subfile : subfiles) list.add(subfile);
        listViewDirectory.getItems().addAll(list);
        textViewPath.setText(Const.SERVER_FOLDER + File.separatorChar);
        //textViewPath.setDisable(true);

    }
}
