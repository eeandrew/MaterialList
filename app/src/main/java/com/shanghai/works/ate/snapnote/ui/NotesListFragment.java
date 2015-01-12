package com.shanghai.works.ate.snapnote.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.model.NoteListItem;
import com.shanghai.works.ate.snapnote.service.PhotoService;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class NotesListFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // camera intent code
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView noteListView;

    private OnFragmentInteractionListener mListener;

    private List<NoteListItem> noteListItems;
    private ImageButton cameraFab;

    private Uri imageTempPath;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static NotesListFragment newInstance(String param1, String param2) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // TODO: Change Adapter to display your content
        setUpNoteListAdapter();
        fetchNotesFromLocalStorage();
    }

    private void setUpNoteListAdapter(){
        if(noteListItems != null){
            NoteAdapter adapter = new NoteAdapter(getActivity());
            setListAdapter(adapter);
        }else{
            setListAdapter(null);
        }
    }

    private void fetchNotesFromLocalStorage(){
        noteListItems = new ArrayList<>();
        NoteListItem item = new NoteListItem();
        item.setSummary("This is dummy data for note summary");
        noteListItems.add(item);
        noteListItems.add(item);
        setUpNoteListAdapter();
    }

    private void setUpCameraFabListener(){
        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SnapNote","Camera Fab Clicked");
                imageTempPath = PhotoService.getPhotoTempFile();
                startActivityForResult(PhotoService.createCameraIntent(imageTempPath), CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap)data.getExtras().get("data");
                mListener.onNoteListFragmentCameraFabClickInteraction();
            } else {
                Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View notesListsFragment = inflater.inflate(R.layout.fragment_notes_list, container, false);
        noteListView =(ListView) notesListsFragment.findViewById(android.R.id.list);
        cameraFab = (ImageButton)notesListsFragment.findViewById(R.id.camera_fab);
        setUpCameraFabListener();
        return notesListsFragment;
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

    public void onListItemClick(ListView l,View v,int position, long id){
        Log.d("SNAPNOTE", "List Item Click" + noteListItems.get(position).getSummary());
        mListener.onNoteListFragmentItemClickInteraction(noteListItems.get(position).getSummary());
    }



    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onNoteListFragmentItemClickInteraction(String id);

        public void onNoteListFragmentCameraFabClickInteraction();
    }

    private final class NoteHolder {
        public TextView summary;
    }

    private class NoteAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context context;

        public NoteAdapter(Context context){
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        public int getCount() {return noteListItems.size();};

        public Object getItem(int argo){
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position,View convertView,ViewGroup container){
            NoteHolder noteHolder = null;
            if(convertView == null){
                noteHolder = new NoteHolder();
                convertView = inflater.inflate(R.layout.list_item_note,null);
                noteHolder.summary = (TextView) convertView.findViewById(R.id.note_summary);
                convertView.setTag(noteHolder);
            }else{
                noteHolder = (NoteHolder)convertView.getTag();
            }

            noteHolder.summary.setText(((NoteListItem)noteListItems.get(position)).getSummary());
            return convertView;
        }
    }

}
