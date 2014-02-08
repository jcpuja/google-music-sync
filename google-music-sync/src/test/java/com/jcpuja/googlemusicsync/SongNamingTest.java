package com.jcpuja.googlemusicsync;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import gmusic.api.model.Song;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SongNamingTest {

	private SongNamer sut;

	@Mock
	private Config conf;

	@Before
	public void setUp() throws IOException {
		when(conf.getMusicDirectory()).thenReturn("F:" + File.separator + "google-music-sync-test");

		sut = new SongNamer(conf);
	}

	@Test
	public void test_song_naming_path() throws IOException {
		Song testSong = new Song();
		testSong.setAlbumArtist("The Hives");
		testSong.setAlbum("The Black And White Album");
		testSong.setName("Giddy Up");
		testSong.setTrack(10);
		testSong.setDisc(1);

		String path = sut.getSongFolder(testSong);

		assertEquals("F:" + File.separator + "google-music-sync-test" + File.separator + "The Hives" + File.separator
				+ "The Black And White Album", path);
	}

	@Test
	public void test_song_naming_track() throws IOException {
		Song testSong = new Song();
		testSong.setAlbumArtist("The Hives");
		testSong.setAlbum("The Black And White Album");
		testSong.setName("Giddy Up");
		testSong.setTrack(10);
		testSong.setDisc(1);

		String fileName = sut.getSongFileName(testSong);

		assertEquals("1.10 - Giddy Up.mp3", fileName);
	}

	@Test
	public void test_song_naming_track_leading_zero() throws IOException {
		Song testSong = new Song();
		testSong.setAlbumArtist("The Hives");
		testSong.setAlbum("The Black And White Album");
		testSong.setName("Try It Again");
		testSong.setTrack(2);
		testSong.setDisc(1);

		String fileName = sut.getSongFileName(testSong);

		assertEquals("1.02 - Try It Again.mp3", fileName);
	}
}
