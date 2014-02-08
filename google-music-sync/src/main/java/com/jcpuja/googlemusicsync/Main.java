package com.jcpuja.googlemusicsync;

import gmusic.api.comm.HttpUrlConnector;
import gmusic.api.comm.JSON;
import gmusic.api.impl.GoogleMusicAPI;
import gmusic.api.impl.InvalidCredentialsException;
import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.model.Song;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException, InvalidCredentialsException,
			InvalidAttributesException {

		// Instantiate dependencies
		Config conf = new Config();
		IGoogleMusicAPI api = new GoogleMusicAPI(new HttpUrlConnector(), new JSON(), new File(conf.getMusicDirectory()));
		SongNamer naming = new SongNamer(conf);
		SongDownloader dl = new SongDownloader(api);

		// Login
		String username = conf.getUserEmail();
		String password = conf.getUserPassword();
		api.login(username, password);
		System.out.println("Authenticated with user " + username);

		// Retrieve list of "free and purchased" songs
		List<Song> purchasedSongs = dl.listPurchasedSongs();

		int songCount = purchasedSongs.size();
		for (int i = 0; i < songCount; i++) {

			Song song = purchasedSongs.get(i);

			System.out.println("Checking song " + (i + 1) + " of " + songCount);

			String songFolder = naming.getSongFolder(song);
			String songFullPath = songFolder + File.separator + naming.getSongFileName(song);
			File songFile = new File(songFullPath);

			if (!songFile.exists()) {
				System.out.println("Song not found on filesystem, downloading");
				dl.downloadSong(song, songFile);
			} else {
				System.out.println("Song found on filesystem, skipping this download");
			}
		}
		System.out.println("Done.");
		// TODO Save song metadata in tags
	}
}
