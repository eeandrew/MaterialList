package com.shanghai.works.ate.snapnote.ui;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.model.SnapNote;

import org.parceler.Parcels;
import org.parceler.javaxinject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoteDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_NOTE = "note";

    private ImageButton noteEditFab;

    private SnapNote note;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @InjectView(R.id.noteDetailImage) ImageView noteImage;
    @InjectView(R.id.note_detail_title) TextView noteTitle;
    @InjectView(R.id.note_detail_content) TextView noteContent;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteDetailFragment newInstance(String param1, String param2, SnapNote note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putParcelable(ARG_NOTE, Parcels.wrap(note));
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            note = Parcels.unwrap(getArguments().getParcelable(ARG_NOTE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View noteDetailLayout = inflater.inflate(R.layout.fragment_note_detail, container, false);
//        TextView noteDetail = (TextView)noteDetailLayout.findViewById(R.id.note_detail);
        ButterKnife.inject(this, noteDetailLayout);
//        SnapNote note = Parcels.unwrap(savedInstanceState.getParcelable(ARG_NOTE));
        noteImage.setImageBitmap(note.getNoteImage(getActivity()));
        noteTitle.setText(note.getTitle());
        noteContent.setText(note.getContent());

//        noteDetail.setText(mParam1);
        noteEditFab = (ImageButton)noteDetailLayout.findViewById(R.id.note_edit_fab);
        setUpClickListenerForNoteEditFab(mParam1);
        return noteDetailLayout;
    }

    private void setUpClickListenerForNoteEditFab(final String noteId){
        noteEditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNoteDetailEditFabClickInteraction(noteId);
            }
        });
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
        public void onNoteDetailEditFabClickInteraction(String noteId);
    }

}
