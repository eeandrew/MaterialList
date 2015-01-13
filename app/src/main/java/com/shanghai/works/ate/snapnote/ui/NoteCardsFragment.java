package com.shanghai.works.ate.snapnote.ui;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dexafree.materialList.cards.model.BigImageButtonsCard;
import com.dexafree.materialList.cards.model.Card;
import com.dexafree.materialList.cards.model.SnapNoteMainCard;
import com.dexafree.materialList.controller.OnButtonPressListener;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.OnImageViewPressListener;
import com.dexafree.materialList.events.DismissEvent;
import com.dexafree.materialList.view.IMaterialView;
import com.dexafree.materialList.view.MaterialListView;
import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.model.SnapNote;
import com.shanghai.works.ate.snapnote.service.PhotoService;
import com.shanghai.works.ate.snapnote.ui.adapter.CardListAdapter;
import com.shanghai.works.ate.snapnote.ui.anim.AnimateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteCardsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteCardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteCardsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView cardsContainer;
    private OnFragmentInteractionListener mListener;
    private CardListAdapter cardListAdapter;
    private List<SnapNote> snapNotes;
    private ImageButton cameraFab;

    private MaterialListView materialListView;

    private Uri imageTempPath;

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteCardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteCardsFragment newInstance(String param1, String param2) {
        NoteCardsFragment fragment = new NoteCardsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteCardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private void setUpCardListAdapter(){
        cardListAdapter = new CardListAdapter(getActivity(),snapNotes);
        cardsContainer.setAdapter(cardListAdapter);
    }

    private void setUpListenerForCameraFab(){
        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mListener.onNoteCardsFragmentCameraFabClickInteraction();
                imageTempPath = PhotoService.getPhotoTempFile();
                startActivityForResult(PhotoService.createCameraIntent(imageTempPath), CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = BitmapFactory.decodeFile(imageTempPath.toString());
                SnapNote freshNote = new SnapNote();
                freshNote.setDate(System.currentTimeMillis());
                freshNote.setTitle("Test");
                freshNote.setContent("Test2");
                freshNote.setImagePath(imageTempPath.toString());
                freshNote.save();
//                Bitmap photo = (Bitmap)data.getExtras().get("data");
//                displayPhotoFrameResizeFragment(imageTempPath);
            } else {
                Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View noteCardsFragmentLayout = inflater.inflate(R.layout.fragment_note_cards, container, false);
        materialListView = (MaterialListView)noteCardsFragmentLayout.findViewById(R.id.material_card_list);
        materialListView.setCardAnimation(IMaterialView.CardAnimation.SCALE_IN);
        materialListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int position) {

            }
        });
        //Load SnapNote Here
        loadSnapNoteFromLocalStorage(materialListView);
        cameraFab = (ImageButton)noteCardsFragmentLayout.findViewById(R.id.card_camera_fab);
        setUpListenerForCameraFab();
        return noteCardsFragmentLayout;
    }

    private void loadSnapNoteFromLocalStorage(MaterialListView materialListView){
        for(int i=0;i<30;i++){
            SnapNote snapNote = new SnapNote();
            snapNote.setContent("Sample Content");
            snapNote.setTitle("Sample Title");
            materialListView.add(generateSnapNote(snapNote));
        }
    }


    private SnapNoteMainCard generateSnapNote(SnapNote snapNote){
        SnapNoteMainCard card = new SnapNoteMainCard(getActivity());
        card.setDrawable(R.drawable.card_sample);
        card.setTitle(snapNote.getTitle());
        card.setNoteTagFirst("Tag1");
        card.setNoteTagSecond("Tag2");
        card.setNoteTagThird("Tag3");
        card.setNoteOCRContent(snapNote.getContent());
        card.setNoteTakenTime("April 2014");
        card.setEditButtonText("EDIT");
        card.setDeleteButtonText("DELETE");
        card.setOnDeleteButtonPressedListener(new OnButtonPressListener() {
            @Override
            public void onButtonPressedListener(View view,Card card) {
                Toast.makeText(getActivity(), "On Delete", Toast.LENGTH_SHORT).show();
                card.dismiss();
            }
        });
        card.setOnFavoriateButtonPressedListener(new OnImageViewPressListener() {
            private boolean isMyFavorite = false;
            @Override
            public void onButtonPressedListener(ImageView imageView) {
                new AnimateUtil().addOrRemoveMyFavorite(getActivity(),imageView,isMyFavorite);
                isMyFavorite = !isMyFavorite;
            }
        });
        return card;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onNoteCardsFragmentCameraFabClickInteraction();
    }

}
