package com.dexafree.materialList.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dexafree.materialList.cards.model.Card;
import com.dexafree.materialList.cards.model.SnapNoteMainCard;
import com.dexafree.materialList.cards.view.BaseCardItemView;
import com.dexafree.materialList.cards.view.SnapNoteMainCardItemView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class MaterialListViewAdapter extends ArrayAdapter<Card> {
	private ArrayList<Class> mClassList = new ArrayList<Class>();
    private ArrayList<Class> mDeletedList = new ArrayList<Class>();

    public MaterialListViewAdapter(Context context){
        super(context, android.R.layout.simple_list_item_1);
	}

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        if(position == -1){
            return null;
        }
        Log.d("getView",position+"");
        Card card = getItem(position);
        Log.d("getView",card.getClass().toString());
        if(convertView == null || !convertView.getTag().getClass().isInstance(card)) {
            for(Class<?> classType : mClassList) {
                if(classType.isInstance(card)){
                    Log.d("getView","layout " + card.getLayout()+"");
                    convertView = View.inflate(getContext(), card.getLayout(), null);
                    convertView.setTag(card);
                    break;
                }
            }
        }

        if (((SnapNoteMainCardItemView)convertView) != null) {
            Log.d("getView","layout2 " + card.getLayout()+"");
            Log.d("getView",((SnapNoteMainCard)card).getTitle());
            Log.d("getView",((SnapNoteMainCard)card).getNoteTagFirst());
            Log.d("getView",((SnapNoteMainCard)card).getNoteOCRContent());
            ((SnapNoteMainCardItemView)convertView).build((SnapNoteMainCard) card);
        }

        return convertView;
    }

    @Override
    public void add(Card card) {
        super.add(card);
        Class cl = card.getClass();
        if(!mClassList.contains(cl)){
            mClassList.add(cl);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void addAll(Collection<? extends Card> collection) {
        super.addAll(collection);
        for (Card card : collection) {
			Class cl = card.getClass();
            if(!mClassList.contains(cl)){
                mClassList.add(cl);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void addAll(Card... items) {
        super.addAll(items);
        List<Card> cards = Arrays.asList(items);
        for (Card card : cards) {
            Class cl = card.getClass();
            if(!mClassList.contains(cl)){
                mClassList.add(cl);
            }
        }
    }

    @Override
    public void insert(Card card, int index) {
        super.insert(card, index);
        Class cl = card.getClass();
        if(!mClassList.contains(cl)){
            mClassList.add(cl);
        }
    }

    public void remove(Card card) {
        Log.d("remove",card.getClass().toString());
        super.remove(card);

        for(int i=0;i<super.getCount();i++){
          Log.d("After Remove",super.getItem(i).getClass().toString());
          Log.d("After Remove", ((SnapNoteMainCard) super.getItem(i)).getTitle());
        }
        if(!mDeletedList.contains(card.getClass())){
            mDeletedList.add(card.getClass());
        }
    }

    @Override
    public int getItemViewType (int position){
        Log.d("getItemViewType","Item position " + position);
        Log.d("getItemViewType","MyCLassListSize " + mClassList.size());
		if(position > -1 && position < getCount()) {
			for (int i = 0; i < mClassList.size(); i++) {
				Class cl = mClassList.get(i);
                Log.d("getItemViewType","Item Type " + cl);
				if (cl.isInstance(getItem(position))) return i;
			}
		}
        Log.d("getItemViewType","Item Type " + -1);
        return -1;
    }

    @Override
    public int getViewTypeCount (){
        // BugFix: Can't have a viewTypCount < 1 (Exception)
        return mClassList.isEmpty() ? 1 : mClassList.size();
    }
}
