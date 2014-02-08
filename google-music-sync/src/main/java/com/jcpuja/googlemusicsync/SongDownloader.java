package com.jcpuja.googlemusicsync;

import gmusic.api.interfaces.IGoogleMusicAPI;
import gmusic.api.model.Song;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.directory.InvalidAttributesException;

public class SongDownloader {

	private IGoogleMusicAPI api;

	public SongDownloader(IGoogleMusicAPI api) {
		this.api = api;
	}

	public List<Song> listPurchasedSongs() throws IOException, URISyntaxException {
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

		return purchasedSongs;
	}

	public void downloadSong(Song song, File destinationFile) throws MalformedURLException, InvalidAttributesException,
			IOException, URISyntaxException {
		if (song == null || destinationFile == null) {
			throw new IllegalArgumentException("Song and Destination File should not be null");
		}

		if (song.getId() == null) {
			throw new IllegalArgumentException("Song should not have a null ID");
		}

		if (destinationFile.isDirectory()) {
			throw new IllegalArgumentException("Destination File should not be a directory");
		}

		// Create needed directories
		new File(destinationFile.getParent()).mkdirs();

		System.out.println("Downloading song " + song.getId() + " (" + song.getAlbum() + " - " + song.getName()
				+ ")...");
		long startTS = new Date().getTime();

		File tempFile = api.downloadSong(song);

		long endTS = new Date().getTime();
		System.out.println("Downloaded in " + ((endTS - startTS) / 1000) + "s");

		boolean renameSucceeded = tempFile.renameTo(destinationFile);

		System.out.println("Saving to " + destinationFile.getAbsolutePath()
				+ (renameSucceeded ? " succeeded." : " failed."));
	}
}
