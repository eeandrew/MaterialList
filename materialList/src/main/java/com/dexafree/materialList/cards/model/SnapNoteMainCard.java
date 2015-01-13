package com.dexafree.materialList.cards.model;

import android.content.Context;

import com.dexafree.materialList.R;
import com.dexafree.materialList.controller.OnButtonPressListener;
import com.dexafree.materialList.controller.OnImageViewPressListener;

/**
 * Created by zhoulin on 2015/1/8.
 */
public class SnapNoteMainCard extends ExtendedCard{
    private String editButtonText;
    private String deleteButtonText;
    private OnButtonPressListener onEditButtonPressedListener;
    private OnButtonPressListener onDeleteButtonPressedListener;
    private OnButtonPressListener onCalendarButtonPressedListener;
    private OnImageViewPressListener onFavoriateButtonPressedListener;
    private OnButtonPressListener onShareButtonPressedListener;

    private boolean showDivider = true;
    private boolean fullDividerLength = true;

    //By default, no calender is shown
    private boolean showCalender = true;
    //By default, this note is not favorite
    private boolean isMyFavorite = true;
    //By default, show share button
    private boolean showShare = true;

    private String noteTakenTime;

    //Tags
    private String noteTagFirst;
    private String noteTagSecond;
    private String noteTagThird;

    //OCR Main Content
    private String noteOCRContent;

    public SnapNoteMainCard(final Context context){
        super(context);
    }

    public String getEditButtonText() {
        return editButtonText;
    }

    public void setEditButtonText(String editButtonText) {
        this.editButtonText = editButtonText;
    }

    public String getDeleteButtonText() {
        return deleteButtonText;
    }

    public void setDeleteButtonText(String deleteButtonText) {
        this.deleteButtonText = deleteButtonText;
    }

    public boolean getShowDivider() {
        return showDivider;
    }

    public boolean getFullDividerLength() {
        return fullDividerLength;
    }

    public void setFullDividerLength(boolean fullDividerLength) {
        this.fullDividerLength = fullDividerLength;
    }

    public void setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
    }

    public OnButtonPressListener getOnEditButtonPressedListener() {
        return onEditButtonPressedListener;
    }

    public void setOnEditButtonPressedListener(OnButtonPressListener onEditButtonPressedListener) {
        this.onEditButtonPressedListener = onEditButtonPressedListener;
    }

    public OnButtonPressListener getOnDeleteButtonPressedListener() {
        return onDeleteButtonPressedListener;
    }

    public void setOnDeleteButtonPressedListener(OnButtonPressListener onDeleteButtonPressedListener) {
        this.onDeleteButtonPressedListener = onDeleteButtonPressedListener;
    }

    public OnButtonPressListener getOnCalendarButtonPressedListener() {
        return onCalendarButtonPressedListener;
    }

    public void setOnCalendarButtonPressedListener(OnButtonPressListener onCalendarButtonPressedListener) {
        this.onCalendarButtonPressedListener = onCalendarButtonPressedListener;
    }

    public OnImageViewPressListener getOnFavoriateButtonPressedListener() {
        return onFavoriateButtonPressedListener;
    }

    public void setOnFavoriateButtonPressedListener(OnImageViewPressListener onFavoriateButtonPressedListener) {
        this.onFavoriateButtonPressedListener = onFavoriateButtonPressedListener;
    }

    public OnButtonPressListener getOnShareButtonPressedListener() {
        return onShareButtonPressedListener;
    }

    public void setOnShareButtonPressedListener(OnButtonPressListener onShareButtonPressedListener) {
        this.onShareButtonPressedListener = onShareButtonPressedListener;
    }

    public String getNoteTakenTime() {
        return noteTakenTime;
    }

    public void setNoteTakenTime(String noteTakenTime) {
        this.noteTakenTime = noteTakenTime;
    }

    public String getNoteTagFirst() {
        return noteTagFirst;
    }

    public void setNoteTagFirst(String noteTagFirst) {
        this.noteTagFirst = noteTagFirst;
    }

    public String getNoteTagSecond() {
        return noteTagSecond;
    }

    public void setNoteTagSecond(String noteTagSecond) {
        this.noteTagSecond = noteTagSecond;
    }

    public String getNoteTagThird() {
        return noteTagThird;
    }

    public void setNoteTagThird(String noteTagThird) {
        this.noteTagThird = noteTagThird;
    }

    public boolean isShowDivider() {
        return showDivider;
    }

    public boolean isFullDividerLength() {
        return fullDividerLength;
    }

    public boolean isShowCalender() {
        return showCalender;
    }

    public void setShowCalender(boolean showCalender) {
        this.showCalender = showCalender;
    }

    public boolean isMyFavorite() {
        return isMyFavorite;
    }

    public void setMyFavorite(boolean isMyFavorite) {
        this.isMyFavorite = isMyFavorite;
    }

    public boolean isShowShare() {
        return showShare;
    }

    public void setShowShare(boolean showShare) {
        this.showShare = showShare;
    }

    public String getNoteOCRContent() {
        return noteOCRContent;
    }

    public void setNoteOCRContent(String noteOCRContent) {
        this.noteOCRContent = noteOCRContent;
    }

    @Override
    public int getLayout() {
        return R.layout.snapnote_main_card;
    }
}
