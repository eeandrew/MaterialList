package com.shanghai.works.ate.snapnote.model;

/**
 * Created by zhoulin on 2015/1/7.
 */
public class CardItem {
    private int photoID;
    private String noteTitle;
    private String noteDescription;

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
