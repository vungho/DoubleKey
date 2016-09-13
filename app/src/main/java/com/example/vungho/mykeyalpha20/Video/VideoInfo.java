package com.example.vungho.mykeyalpha20.Video;

/**
 * Created by vungho on 20/03/2016.
 */
public class VideoInfo {
    private String stringPath;
    private String name;
    private String newPath;
    private boolean statut;

    /**
     * @param stringPath
     * @param name
     */
    public VideoInfo(String stringPath, String name) {
        this.stringPath = stringPath;
        this.name = name;
        this.statut = false;
        this.newPath = null;
    }

    public VideoInfo() {
        this(null, null);
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public String getStringPath() {
        return stringPath;
    }

    public void setStringPath(String stringPath) {
        this.stringPath = stringPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoInfo)) return false;

        VideoInfo videoInfo = (VideoInfo) o;

        return stringPath.equals(videoInfo.stringPath);

    }

    @Override
    public int hashCode() {
        return stringPath.hashCode();
    }
}
