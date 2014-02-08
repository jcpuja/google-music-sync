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

		Song firstSong = purchasedSongs.get(0);

		String songFolder = naming.getSongFolder(firstSong);
		String songFullPath = songFolder + File.separator + naming.getSongFileName(firstSong);
		File songFile = new File(songFullPath);

		if (!songFile.exists()) {
			// Download first song
			dl.downloadSong(firstSong, songFile);
		}
		System.out.println("Done.");
		// TODO Iterate on all files
		// TODO Save song metadata in tags
	}
}
