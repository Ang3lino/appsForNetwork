/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.tcp;

import backend.utilidades.Const;
import backend.utilidades.Pack;
import backend.utilidades.UtilFun;
import backend.zip.Unzipper;
import backend.zip.Zipper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author Angel Lopez Manriquez
 */
public class TcpServer implements Runnable {

    private ServerSocket serverSocket;

    public TcpServer() {
        try {
            serverSocket = new ServerSocket(Const.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting a connection");
                Socket socket = serverSocket.accept();
                new Clone(socket).start();
                //  socket.close();
            } catch (IOException e) {
                System.out.println("Something went wrong with TCP connection.");
                e.printStackTrace();
            }
        }
    }

    private class Clone extends Thread {

        private Socket socket;
        private ObjectOutputStream oos;
        private ObjectInputStream ois;
        private Pack pack;

        public Clone(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            // Prepare to receive objects
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());

                pack = (Pack) ois.readObject();

                switch (pack.getState()) {
                    case DELETE:
                        handleDelete(pack);
                        break;
                    case DOWNLOAD:
                        handleDownload(pack);
                        break;
                    case MOVE:
                        handleMove(pack);
                        break;
                    case UPDATE_DIR:
                        handleUpdateDir(pack);
                        break;
                    case UPLOAD:
                        handleUpload(pack);
                        break;
                }
                //socket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void handleUpdateDir(Pack pack) {
            String serverFolder = Const.SERVER_FOLDER;
            UtilFun.createFolder(serverFolder);
            File file = new File(pack.currentPath);
            File[] subfiles = file.listFiles();

            for (File subfile : subfiles) {
                System.out.println(subfile);
                System.out.println(subfile.getName());
                System.out.println(subfile.getPath());
            }

            try {
                oos.writeObject(subfiles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleMove(Pack req) {
            File root = new File(Const.SERVER_FOLDER);
            try {
                oos.writeObject(root);
                File dst = (File) ois.readObject();

                req.fileNames.forEach(file -> {
                    try {
                        Path srcPath = Paths.get(req.currentPath, file),
                                dstPath = Paths.get(dst.getPath(), file);
                        System.out.println(srcPath);
                        System.out.println(dstPath);
                        Files.move(srcPath, dstPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void deleteDirectory(File file) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    deleteDirectory(f);
                }
            }
            file.delete();
        }

        private void handleDelete(Pack p) {
            p.fileNames.forEach(name -> {
                String filename = Paths.get(p.currentPath, name).toString();
                File f = new File(filename);
                if (f.isDirectory()) deleteDirectory(f);
                else f.delete();
            });
        }

        private void handleUpload(Pack p) {
            UtilFun.storeFile(p.file, socket, p.currentPath);

            String zipPath = Paths.get(p.currentPath, p.file.getName()).toString();
            File zipFile = new File(zipPath), outputFile = new File(p.currentPath);
            Unzipper.extract(zipFile, outputFile);
            zipFile.delete();

        }

        private void handleDownload(Pack p) {
            // zip the selected folder in the current path
            File currDir = new File(p.currentPath);
            ArrayList<File> subDirFiles = new ArrayList<>(Arrays.asList(currDir));
            String zipName = currDir.getName() + ".zip";
            Zipper.zipFiles(subDirFiles, zipName);

            // pass the file to the client, delete the zipped file after it
            File zippedFile = new File(zipName);
            Pack res = new Pack();
            res.file = zippedFile;
            try {
                oos.writeObject(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UtilFun.uploadFile(zippedFile, socket);

            //zippedFile.delete(); // WARNING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }

    }

    public static void main(String args[]) {
        new Thread(new TcpServer()).start();
    }

}
