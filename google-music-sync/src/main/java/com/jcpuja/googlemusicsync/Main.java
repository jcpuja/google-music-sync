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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException, InvalidCredentialsException,
			InvalidAttributesException {

		// Retrieve username + password from config file and login
		Config conf = new Config();
		String username = conf.getUserEmail();
		String password = conf.getUserPassword();

		IGoogleMusicAPI api = new GoogleMusicAPI(new HttpUrlConnector(), new JSON(), new File(conf.getMusicDirectory()));
		api.login(username, password);

		System.out.println("Authenticated with user " + username);

		// Retrieve list of "free and purchased" songs
		System.out.println("Fetching data for all songs...");
		long startTS = new Date().getTime();
		Collection<Song> allSongs = api.getAllSongs();
		long endTS = new Date().getTime();
		System.out.println("Fetched data for all songs in " + ((endTS - startTS) / 1000) + "s");
		List<Song> purchasedSongs = new ArrayList<>();
		for (Song song : allSongs) {
			if (song.getType() == SongType.FREE_AND_PURCHASED.type) {
				purchasedSongs.add(song);
			}
		}
		System.out.println("Free and purchased songs count: " + purchasedSongs.size());

		System.out.println("Downloading first song...");
		startTS = new Date().getTime();
		api.downloadSong(purchasedSongs.get(0));
		endTS = new Date().getTime();
		System.out.println("Downloaded first song in " + ((endTS - startTS) / 1000) + "s");

	}
}
