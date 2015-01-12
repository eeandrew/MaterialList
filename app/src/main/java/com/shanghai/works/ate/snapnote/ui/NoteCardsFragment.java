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

import com.dexafree.materialList.cards.model.Card;
import com.dexafree.materialList.controller.OnButtonPressListener;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.OnImageViewPressListener;
import com.dexafree.materialList.view.MaterialListView;
import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.model.SnapNote;
import com.shanghai.works.ate.snapnote.model.customcard.SnapNoteMainCard;
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

    private void loadSnapNoteFromLocalStorage(){
        snapNotes = new ArrayList<>();
        SnapNote note1 = new SnapNote();
        note1.setImagePath(String.valueOf(R.drawable.card_sample));
        note1.setTitle("Irland Travel");
        note1.setContent("Spring here is beutiful");
        note1.setDate(System.currentTimeMillis());
        snapNotes.add(note1);
        SnapNote note2 = new SnapNote();
        note2.setImagePath(String.valueOf(R.drawable.card_sample_2));
        note2.setTitle("NewYear Firework");
        note2.setContent("What a wonderful night");
        note2.setDate(System.currentTimeMillis());
        snapNotes.add(note2);
        SnapNote note3 = new SnapNote();
        note3.setImagePath(String.valueOf(R.drawable.card_sample_3));
        note3.setTitle("The Lightning");
        note3.setContent("First night alone");
        note3.setDate(System.currentTimeMillis());
        snapNotes.add(note3);
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
//        cardsContainer = (RecyclerView) noteCardsFragmentLayout.findViewById(R.id.cardListContainer);
//        cardsContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
//        loadSnapNoteFromLocalStorage();
//        setUpCardListAdapter();


        materialListView = (MaterialListView)noteCardsFragmentLayout.findViewById(R.id.material_card_list);

        final SnapNoteMainCard card1 = new SnapNoteMainCard(getActivity());
        card1.setTitle("Irland Travel");
        card1.setDescription("Spring here is beutiful");
        card1.setDrawable(R.drawable.card_sample);
        card1.setNoteTagFirst("Tag First");
        card1.setNoteTagSecond("Tag Second");
        card1.setNoteTagThird("Tag Third");
        card1.setNoteOCRContent("Spring here is beutiful");
        card1.setNoteTakenTime("April 10");
        card1.setEditButtonText("EDIT");
        card1.setDeleteButtonText("DELETE");
        card1.setShowDivider(true);
        card1.setOnDeleteButtonPressedListener(new OnButtonPressListener() {
            @Override
            public void onButtonPressedListener(View view,Card card) {
                Toast.makeText(getActivity(), "On Delete", Toast.LENGTH_SHORT).show();
                card.dismiss();
            }
        });
        card1.setOnFavoriateButtonPressedListener(new OnImageViewPressListener() {
            private boolean isMyFavorite = false;
            @Override
            public void onButtonPressedListener(ImageView imageView) {
                new AnimateUtil().addOrRemoveMyFavorite(getActivity(),imageView,isMyFavorite);
                isMyFavorite = !isMyFavorite;
            }
        });
        materialListView.add(card1);

        SnapNoteMainCard card2 = new SnapNoteMainCard(getActivity());
        card2.setTitle("NewYear Firework");
        card2.setDescription("What a wonderful night");
        card2.setDrawable(R.drawable.card_sample_2);
        card2.setNoteTagFirst("Tag First");
        card2.setNoteTagSecond("Tag Second");
        card2.setNoteTagThird("Tag Third");
        card2.setNoteOCRContent("What a wonderful night");
        card2.setNoteTakenTime("April 10");
        card2.setEditButtonText("EDIT");
        card2.setDeleteButtonText("DELETE");
        card2.setShowDivider(true);
        materialListView.add(card2);

        SnapNoteMainCard card3 = new SnapNoteMainCard(getActivity());
        card3.setTitle("The Lightning");
        card3.setDescription("First night alone");
        card3.setDrawable(R.drawable.card_sample_3);
        card3.setNoteTagFirst("Tag First");
        card3.setNoteTagSecond("Tag Second");
        card3.setNoteTagThird("Tag Third");
        card3.setNoteOCRContent("First night alone");
        card3.setNoteTakenTime("April 10");
        card3.setEditButtonText("EDIT");
        card3.setDeleteButtonText("DELETE");
        card3.setShowDivider(true);
        materialListView.add(card3);

        materialListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int position) {

            }
        });

//        BigImageButtonsCard card2 = new BigImageButtonsCard();
//        card2.setTitle("NewYear Firework");
//        card2.setDescription("What a wonderful night");
//        card2.setBitmap(getResources().getDrawable(R.drawable.card_sample_2));
//        card2.setLeftButtonText("EDIT");
//        card2.setRightButtonText("DELETE");
//        materialListView.add(card2);
//
//        BigImageButtonsCard card3 = new BigImageButtonsCard();
//        card3.setTitle("The Lightning");
//        card3.setDescription("First night alone");
//        card3.setBitmap(getResources().getDrawable(R.drawable.card_sample_3));
//        card3.setLeftButtonText("EDIT");
//        card3.setRightButtonText("DELETE");
//        materialListView.add(card3);

        cameraFab = (ImageButton)noteCardsFragmentLayout.findViewById(R.id.card_camera_fab);
        setUpListenerForCameraFab();
        return noteCardsFragmentLayout;
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
