package com.jcpuja.googlemusicsync;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static final String GOOGLECREDENDTIALS_EMAIL = "googlecredentials.email";
	private static final String GOOGLECREDENDTIALS_PASSWORD = "googlecredentials.password";
	private static final String LOCAL_MUSICDIR = "local.musicdirectory";

	// TODO: Move credentials file out of the jar
	private static final String CONFIG_FILE = "src/main/resources/config.properties";

	public String getUserEmail() throws IOException {
		return getPropertyFromConfigFile(GOOGLECREDENDTIALS_EMAIL);
	}

	public String getUserPassword() throws IOException {
		return getPropertyFromConfigFile(GOOGLECREDENDTIALS_PASSWORD);
	}

	public String getMusicDirectory() throws IOException {
		return getPropertyFromConfigFile(LOCAL_MUSICDIR);
	}

	private String getPropertyFromConfigFile(String property) throws IOException {
		Properties props = new Properties();
		InputStream is = new FileInputStream(CONFIG_FILE);
		props.load(is);
		return props.getProperty(property);
	}
}
