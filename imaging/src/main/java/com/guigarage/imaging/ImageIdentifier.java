package com.guigarage.imaging;

public class ImageIdentifier {

    private String url;

    private int width;

    private int height;

    public ImageIdentifier(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageIdentifier)) return false;

        ImageIdentifier that = (ImageIdentifier) o;

        if (height != that.height) return false;
        if (width != that.width) return false;
        if (!url.equals(that.url)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
