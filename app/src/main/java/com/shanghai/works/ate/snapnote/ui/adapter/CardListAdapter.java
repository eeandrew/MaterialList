package com.shanghai.works.ate.snapnote.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.query.Condition;
import com.orm.query.Select;
import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.model.SnapNote;
import com.shanghai.works.ate.snapnote.ui.NoteDetailFragment;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhoulin on 2015/1/7.
 */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder>{
    private List<SnapNote> snapNotes;
    private Context context;

    private long oldFetchTime;
    private long newFetchTime;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.noteImage) ImageView noteImage;
        @InjectView(R.id.noteTitle) TextView noteTitle;
        @InjectView(R.id.noteDescription) TextView noteDescription;
        public ViewHolder (View v){
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    public CardListAdapter(Context context,List<SnapNote> snapNotes){
        this.context = context;
        this.snapNotes = snapNotes;
        this.oldFetchTime = System.currentTimeMillis();
        this.newFetchTime = oldFetchTime;

        for(SnapNote note : snapNotes){
            if(note.getDate() > newFetchTime){
                newFetchTime = note.getDate();
            }else if(note.getDate() < oldFetchTime){
                oldFetchTime = note.getDate();
            }
        }
    }
    @Override
    public CardListAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i )
    {
        // 给ViewHolder设置元素
        final SnapNote note = snapNotes.get(i);
        viewHolder.noteTitle.setText(note.getTitle());
        viewHolder.noteDescription.setText(note.getContent());
        Log.d("SNAPNOTE", "Get Drawable ID POSITION: " + i);
        Log.d("SNAPNOTE","Get Drawable ID: " + note.getImagePath());
        //TODO just for demo, we need to remove resource generation code.
        Bitmap photo = note.getNoteImage(context);
        viewHolder.noteImage.setImageBitmap(photo);
        viewHolder.noteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, NoteDetailFragment.newInstance("","", note))
                        .commit();
            }
        });
    }


    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return snapNotes == null ? 0 : snapNotes.size();
    }

    public void pullFromTop(){
        List<SnapNote> freshNotes = Select.from(SnapNote.class).where(Condition.prop("date").gt(newFetchTime)).orderBy("date desc").list();
        List<SnapNote> tempTailNotes = snapNotes;
        snapNotes = freshNotes;
        snapNotes.addAll(tempTailNotes);
        notifyDataSetChanged();
    }

    public void pullFromBottom(){
        List<SnapNote> freshNotes = Select.from(SnapNote.class).where(Condition.prop("date").lt(oldFetchTime)).orderBy("date desc").list();
        List<SnapNote> tempTailNotes = snapNotes;
        snapNotes = freshNotes;
        snapNotes.addAll(tempTailNotes);
        notifyDataSetChanged();
    }

}
