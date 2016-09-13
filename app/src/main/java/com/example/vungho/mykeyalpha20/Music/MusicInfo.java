package com.example.vungho.mykeyalpha20.Music;

/**
 * Created by vungho on 22/03/2016.
 */
public class MusicInfo {
    private String name;
    private String path;
    private String newPath;
    private boolean statut;

    public MusicInfo(String name, String path) {
        this.name = name;
        this.path = path;
        this.newPath = null;
        statut = false;
    }

    public String getNewPath() {
        return newPath;
    }

    public void setNewPath(String newPath) {
        this.newPath = newPath;
    }

    public MusicInfo() {
        this(null, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        if (!(o instanceof MusicInfo)) return false;

        MusicInfo musicInfo = (MusicInfo) o;

        return path.equalsIgnoreCase(musicInfo.path);

    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
