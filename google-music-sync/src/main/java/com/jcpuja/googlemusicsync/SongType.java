package com.jcpuja.googlemusicsync;

public enum SongType {
	FREE_AND_PURCHASED(1), UPLOADED_AND_UNMATCHED(2), UPLOADED_AND_MATCHED(6);

	public final int type;

	private SongType(int type) {
		this.type = type;
	}
}
