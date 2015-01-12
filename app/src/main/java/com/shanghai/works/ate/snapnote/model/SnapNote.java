package com.shanghai.works.ate.snapnote.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.orm.SugarRecord;
import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.ui.NoteDetailFragment;

import org.parceler.Parcel;

/**
 * Created by fc on 15-1-6.
 */
@Parcel
public class SnapNote extends SugarRecord<SnapNote>{
    String title;
    String imagePath;
    String content;
    long date;

    public SnapNote(){

    }

    public Bitmap getNoteImage(Context context){
        try{
            int photoId = Integer.parseInt(imagePath);
            return BitmapFactory.decodeResource(context.getResources(), photoId);
        }catch(Exception e){
            return BitmapFactory.decodeFile(imagePath);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
