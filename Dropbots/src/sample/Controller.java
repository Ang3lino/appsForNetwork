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

    // Note: In order to reference a method from .FXML file, the method must be public or having @FXML annotation

    @FXML
    public void downloadFile(ActionEvent e) {
        String currentPath = textViewPath.getText();
        TcpClient client = new TcpClient();
        client.downloadFiles(currentPath);
        client.closeSocket();
    }

    @FXML
    private void moveFiles(ActionEvent e) {
        // set elements to move into ArrayList
        ObservableList<String> moveList = listViewDirectory.getSelectionModel().getSelectedItems();
        ArrayList<String> itemsToMove = new ArrayList<>(moveList.size());
        itemsToMove.addAll(moveList);

        TcpClient client = new TcpClient();
        client.requestMoveFiles(itemsToMove, textViewPath.getText());
        client.closeSocket();

        list.removeAll(itemsToMove);
        listViewDirectory.getItems().removeAll(itemsToMove);
    }

    @FXML
    private void deleteFiles(ActionEvent e) {
        // Insert elements selected into ArrayList
        ObservableList<String> deleteList = listViewDirectory.getSelectionModel().getSelectedItems();
        ArrayList<String> removables = new ArrayList<>(deleteList.size());
        removables.addAll(deleteList);
        removables.forEach(System.out::println);

        // request to delete the files
        TcpClient client = new TcpClient();
        client.requestRemoveFiles(removables, textViewPath.getText());
        client.closeSocket();

        list.removeAll(removables);
        listViewDirectory.getItems().removeAll(removables);
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
        textViewPath.setDisable(true);

        listViewDirectory.setOnMouseClicked(this::onDoubleClickItemSelected);
    }
}
