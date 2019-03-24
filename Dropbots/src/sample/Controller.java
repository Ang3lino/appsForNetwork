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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ObservableList list;

    @FXML
    private ListView<String> listViewDirectory;

    @FXML
    private TextField textViewPath;


    public void downloadFile(ActionEvent e) {
        File zipFile = new File("test.zip"), output = new File("extracted");
        Unzipper.extract(zipFile, output);
    }

    public void selectFolderToUpload(ActionEvent e) {
        final DirectoryChooser chooser = new DirectoryChooser();
        final File chosenFile = chooser.showDialog(null);

        // The user accepted a Folder
        if (chosenFile != null) {
            ArrayList<File> files = new ArrayList<>();
            files.add(chosenFile);
            final String zipName = chosenFile.getName() + ".zip";
            Zipper.zipFiles(files, zipName);

            TcpClient client = new TcpClient();
            File zipped = new File(zipName);
            client.uploadFile(zipped, Const.SERVER_FOLDER);
            zipped.delete();

            list.clear();
            list.add(chosenFile.getName());
            listViewDirectory.getItems().addAll(list);

            textViewPath.setText(Const.SERVER_FOLDER + File.separatorChar);
        }
    }

    public void selectFilesToUpload(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        List<File> chosenFiles = chooser.showOpenMultipleDialog(null);
        //chosenFiles.forEach(file -> list.add(file));
        //listViewDirectory.getItems().addAll(list);

        if (chosenFiles != null) {
            TcpClient client = new TcpClient();
            ArrayList<File> files = new ArrayList<>(chosenFiles.size());
            files.addAll(chosenFiles);
            Zipper.zipFiles(files, "tmp.zip");
            File zipped = new File("tmp.zip");
            client.uploadFile(zipped, textViewPath.getText());

            listViewDirectory.getItems().clear();
            files.forEach(file -> list.add(file.getName()));
            listViewDirectory.getItems().addAll(list);

            client.closeSocket();
            zipped.delete();
        }
    }


    private void onDoubleClickItemSelected(MouseEvent click) {
        if (click.getClickCount() == 2) {
            String itemSelected = listViewDirectory.getSelectionModel().getSelectedItem();
            //Use ListView's getSelected Item
            System.out.println(itemSelected);
            String filepath = Paths.get(textViewPath.getText(), itemSelected).toString();
            TcpClient client = new TcpClient();
            File[] subFiles = client.getSubFiles(filepath);

            if (subFiles.length > 0) {

                listViewDirectory.getItems().clear();
                list.clear();
                for (File subFile : subFiles) list.add(subFile.getName());
                listViewDirectory.getItems().addAll(list);

                textViewPath.setText(filepath);
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList();

        // set initial directory in GUI
        TcpClient client = new TcpClient();
        File[] subfiles = client.getSubFiles(Const.SERVER_FOLDER);
        for (File subfile : subfiles) list.add(subfile.getName());
        listViewDirectory.getItems().addAll(list);
        listViewDirectory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        textViewPath.setText(Const.SERVER_FOLDER + File.separatorChar);
        //textViewPath.setDisable(true);

        listViewDirectory.setOnMouseClicked(this::onDoubleClickItemSelected);

    }
}
