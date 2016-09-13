package com.example.vungho.mykeyalpha20.Image;

/**
 * Created by vungho on 17/03/2016.
 */
public class ImageInfo {

    private String name;
    private String stringPath;
    private String newPath;
    private boolean touchStatut;



    public ImageInfo(String name, String stringPath) {
        this.name = name;
        this.stringPath = stringPath;
        this.touchStatut = false;
        this.newPath = null;
    }



    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public ImageInfo() {
        this(null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStringPath() {
        return stringPath;
    }

    public void setStringPath(String stringPath) {
        this.stringPath = stringPath;
    }

    public boolean isTouchStatut() {
        return touchStatut;
    }

    public void setTouchStatut(boolean touchStatut) {
        this.touchStatut = touchStatut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageInfo)) return false;

        ImageInfo imageInfo = (ImageInfo) o;

        return stringPath.equals(imageInfo.stringPath);

    }

    @Override
    public int hashCode() {
        return stringPath.hashCode();
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "name='" + name + '\'' +
                ", stringPath='" + stringPath + '\'' +
                '}';
    }
}
