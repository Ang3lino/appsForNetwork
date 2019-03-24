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

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
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

		private void handleMove(Pack p) {
		}

		private void handleDelete(Pack p) {
			p.removeNames.forEach(name -> {
				String filename = Paths.get(p.currentPath, name).toString();
				File f = new File(filename);
				f.delete();
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
			ArrayList<File> subDirFiles = new ArrayList<>( Arrays.asList(currDir) );
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
			zippedFile.delete();
		}

	}

	public static void main(String args[]) {
		new Thread(new TcpServer()).start();
	}

}
