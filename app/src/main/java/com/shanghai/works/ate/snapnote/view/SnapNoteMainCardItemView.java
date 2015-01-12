package com.shanghai.works.ate.snapnote.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dexafree.materialList.cards.view.BaseButtonsCardItemView;
import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.model.customcard.SnapNoteMainCard;

/**
 * Created by zhoulin on 2015/1/8.
 */
public class SnapNoteMainCardItemView extends BaseButtonsCardItemView<SnapNoteMainCard> {
    private final static int DIVIDER_MARGIN_DP = 16;
    TextView title;
    TextView noteTakenTime;
    TextView noteTagFirst;
    TextView noteTagSecond;
    TextView noteTagThird;
    TextView noteOCRContent;
    TextView editText;
    TextView deleteText;
    private ImageView mImage;
    ImageView shareBtn;
    ImageView addTOFavoriteBtn;
    ImageView calendarBtn;

    public SnapNoteMainCardItemView(Context context) {
        super(context);
    }

    public SnapNoteMainCardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnapNoteMainCardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(final SnapNoteMainCard card) {
        Log.d("SnapNoteMainCardItemView",card.getTitle());
        setTitle(card.getTitle());
        setImage(card.getDrawable());
        setNoteTakenTime(card.getNoteTakenTime());
        setNoteTagFirst(card.getNoteTagFirst());
        setNoteTagSecond(card.getNoteTagSecond());
        setNoteTagThird(card.getNoteTagThird());
        setNoteOCRContent(card.getNoteOCRContent());
        setEditText(card);
        setDeleteText(card);
        setShareBtn(card);
        setAddTOFavoriteBtn(card);
        setCalendarBtn(card);
        setDivider(card);
    }

    public void setImage(Drawable bm){
        mImage = (ImageView)findViewById(R.id.imageView);
        mImage.setImageDrawable(bm);
    }

    public void setTitle(String titleString){
        Log.d("SNAPNOTE SETTITLE",titleString);
        title = (TextView) findViewById(R.id.titleTextView);
        title.setText(titleString);
    }

    public void setNoteTakenTime(String noteTakenTimeString){
        noteTakenTime = (TextView) findViewById(R.id.noteTakenTimeTextView);
        noteTakenTime.setText(noteTakenTimeString);
    }

    public void setNoteTagFirst (String noteTagFirstString){
        noteTagFirst = (TextView) findViewById(R.id.noteTagFirstTextView);
        noteTagFirst.setText(noteTagFirstString);
    }

    public void setNoteTagSecond (String noteTagSecondString){
        noteTagSecond = (TextView) findViewById(R.id.noteTagSecondTextView);
        noteTagSecond.setText(noteTagSecondString);
    }

    public void setNoteTagThird (String noteTagThirdString){
        noteTagThird = (TextView) findViewById(R.id.noteTagThirdTextView);
        noteTagThird.setText(noteTagThirdString);
    }

    public void setNoteOCRContent(String noteOCRContentString){
        noteOCRContent = (TextView) findViewById(R.id.noteOCRContentTextView);
        noteOCRContent.setText(noteOCRContentString);
    }

    public void setEditText(final SnapNoteMainCard card){
        editText = (TextView) findViewById(R.id.left_edit_button);
        editText.setText(card.getEditButtonText());
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                card.getOnEditButtonPressedListener().onButtonPressedListener(editText,card);
            }
        });
    }

    public void setDeleteText(final SnapNoteMainCard card){
        deleteText = (TextView) findViewById(R.id.right_delete_button);
        deleteText.setText(card.getDeleteButtonText());
        deleteText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SNAPNOTE","Delete Button Clicked");
                if(card.getOnDeleteButtonPressedListener() != null) {
                    card.getOnDeleteButtonPressedListener().onButtonPressedListener(deleteText,card);
                }
            }
        });
    }

    public void setShareBtn(final SnapNoteMainCard card){
        shareBtn = (ImageView)findViewById(R.id.note_share_btn);
        int visibility = card.isShowShare()? VISIBLE : INVISIBLE;
        shareBtn.setVisibility(visibility);
    }

    public void setAddTOFavoriteBtn(final SnapNoteMainCard card) {
        addTOFavoriteBtn = (ImageView)findViewById(R.id.add_to_favorite_btn);
        int visibility = card.isMyFavorite()? VISIBLE : INVISIBLE;
        addTOFavoriteBtn.setVisibility(visibility);
        addTOFavoriteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SNAPNOTE","Add to favorite button clicked");
                if(card.getOnFavoriateButtonPressedListener() != null){
                    card.getOnFavoriateButtonPressedListener().onButtonPressedListener(addTOFavoriteBtn);
                }
            }
        });
    }

    public void setCalendarBtn(final SnapNoteMainCard card) {
        calendarBtn = (ImageView) findViewById(R.id.note_calender_btn);
        int visibility = card.isShowCalender()? VISIBLE : INVISIBLE;
        calendarBtn.setVisibility(visibility);
    }

    public void setDivider(final SnapNoteMainCard card){
        int visibility = card.getShowDivider()? VISIBLE : INVISIBLE;

        View divider = findViewById(R.id.cardDivider);

        divider.setVisibility(visibility);

        // After setting the visibility, we prepare the divider params according to the preferences
        if(card.getShowDivider()){

            // If the divider has to be from side to side, the margin will be 0
            if(card.getFullDividerLength()) {
                ((RelativeLayout.LayoutParams) divider.getLayoutParams()).setMargins(0, 0, 0, 0);
            } else {

                // Convert DP to PX
                int dividerMarginPx = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        DIVIDER_MARGIN_DP,
                        getContext().getResources().getDisplayMetrics()
                );

                // Set the margin
                ((RelativeLayout.LayoutParams) divider.getLayoutParams()).setMargins(
                        dividerMarginPx,
                        0,
                        dividerMarginPx,
                        0
                );
            }

        }
    }


}
