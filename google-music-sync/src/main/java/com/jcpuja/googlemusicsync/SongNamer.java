package com.jcpuja.googlemusicsync;

import gmusic.api.model.Song;

import java.io.File;
import java.io.IOException;

public class SongNamer {
	private Config conf;

	public SongNamer(Config conf) {
		this.conf = conf;
	}

	private static final String MP3_EXTENSION = ".mp3";

	/**
	 * Return the path of a song based on its metadata
	 * 
	 * @param song
	 * @return
	 * @throws IOException
	 */
	public String getSongFileName(Song song) throws IOException {

		String discNumberPrefix = song.getDisc() + ".";
		String trackNumberPrefix = String.format("%02d", song.getTrack()) + " - ";
		String trackName = song.getName();

		StringBuilder songLocation = new StringBuilder().append(discNumberPrefix)//
				.append(trackNumberPrefix)//
				.append(trackName).append(MP3_EXTENSION);

		return songLocation.toString();
	}

	public String getSongFolder(Song song) throws IOException {
		if (song == null) {
			return null;
		}

		String artistFolder = song.getAlbumArtist();
		String albumFolder = song.getAlbum();

		StringBuilder folderName = new StringBuilder(conf.getMusicDirectory()).append(File.separator)//
				.append(artistFolder).append(File.separator)//
				.append(albumFolder);

		return folderName.toString();
	}
}
