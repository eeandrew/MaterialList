package com.shanghai.works.ate.snapnote.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.shanghai.works.ate.snapnote.R;
import com.shanghai.works.ate.snapnote.ui.view.ImageTrimView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.shanghai.works.ate.snapnote.ui.PhotoFrameResizeResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.shanghai.works.ate.snapnote.ui.PhotoFrameResizeResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFrameResizeResultFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private ImageView photoResizeResultImage;
    private ImageButton photoResizeResultOK;

    private byte[] mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static PhotoFrameResizeResultFragment newInstance(byte[] image) {
        PhotoFrameResizeResultFragment fragment = new PhotoFrameResizeResultFragment();
        Bundle args = new Bundle();
        args.putByteArray(ARG_PARAM1, image);
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoFrameResizeResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getByteArray(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View photoFrameResizeResultLayout = inflater.inflate(R.layout.fragment_photo_frame_resize_result, container, false);
        photoResizeResultOK = (ImageButton) photoFrameResizeResultLayout.findViewById(R.id.photo_resize_result_ok);
        photoResizeResultImage = (ImageView) photoFrameResizeResultLayout.findViewById(R.id.photo_resize_result_image);

        Bitmap bmp = BitmapFactory.decodeByteArray(mParam1, 0, mParam1.length);
        Log.d("", "byte array length: " + mParam1.length);
        photoResizeResultImage.setImageBitmap(bmp);
        setUpClickListenerForOKBtn(null);
        return photoFrameResizeResultLayout;
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
        public void onPhotoFrameResizeResultFragmentOKButtonClickInteraction();
    }



    private void setUpClickListenerForOKBtn(final Uri uri){
        photoResizeResultOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPhotoFrameResizeResultFragmentOKButtonClickInteraction();
            }
        });
    }
}
