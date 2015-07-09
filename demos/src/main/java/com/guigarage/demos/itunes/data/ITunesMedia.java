package com.guigarage.demos.itunes.data;

import java.io.Serializable;import java.lang.String;

public class ITunesMedia implements Serializable {

	private static final long serialVersionUID = 1L;

	private String artworkUrl100;

    private String coverUrl;

	private String trackName;

    private String artistName;

    private String collectionName;

	private double trackPrice;

    private String previewUrl;

    private int trackNumber;

    private String primaryGenreName;

    private String artistId;

    private String collectionId;

    private String trackId;

    private int trackCount;

	public ITunesMedia() {
	}

	public String getArtworkUrl100() {
		return artworkUrl100;
	}

	public void setArtworkUrl100(String artworkUrl100) {
		this.artworkUrl100 = artworkUrl100;
        coverUrl = artworkUrl100.replace("100x100", "600x600");
	}

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public double getTrackPrice() {
		return trackPrice;
	}

	public void setTrackPrice(double trackPrice) {
		this.trackPrice = trackPrice;
	}
}
