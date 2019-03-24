package sample;

import backend.tcp.TcpClient;
import backend.utilidades.*;

import backend.zip.Unzipper;
import backend.zip.Zipper;
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
        File zipFile = new File("test.zip"), output = new File("extracted");
        Unzipper.extract(zipFile, output);
    }

    public void selectFolderToUpload(ActionEvent e) {
        final DirectoryChooser chooser = new DirectoryChooser();
        final File chosenFile = chooser.showDialog(null);

        ArrayList<File> files = new ArrayList<>();
        files.add(chosenFile);
        final String zipName = chosenFile.getName() + ".zip";
        Zipper.zipFiles(files, zipName);

        TcpClient client = new TcpClient();
        File zipped = new File(zipName);
        client.uploadFile(zipped, Const.SERVER_FOLDER);
        zipped.delete();
    }

    public void selectFilesToUpload(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        List<File> chosenFiles = chooser.showOpenMultipleDialog(null);
        //chosenFiles.forEach(file -> list.add(file));
        //listViewDirectory.getItems().addAll(list);

        TcpClient client = new TcpClient();
        //client.uploadFiles(chosenFiles);
        client.closeSocket();

        ArrayList<File> files = new ArrayList<>(chosenFiles.size());
        files.addAll(chosenFiles);
        Zipper.zipFiles(files, "test.zip");
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
