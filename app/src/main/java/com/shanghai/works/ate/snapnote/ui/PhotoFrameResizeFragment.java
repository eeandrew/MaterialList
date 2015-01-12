package com.shanghai.works.ate.snapnote.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.shanghai.works.ate.snapnote.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotoFrameResizeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotoFrameResizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFrameResizeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TEST_PHOTO_01 = "";
    private static final int MINIMUM_MASK_WIDTH = 36;
    private static final int MINIMUM_MASK_HEIGHT = 36;
    // TODO: Rename and change types of parameters
    private String photoUrl;

    private ImageButton photoResizeOK;

    private SurfaceView photoResizeNorthMask;
    private SurfaceView photoResizeEastMask;
    private SurfaceView photoResizeSouthMask;
    private SurfaceView photoResizeWestMask;
    private SurfaceView photoResizeCenterMask;

    private SurfaceView photoResizeNorthBar;
    private SurfaceView photoResizeSouthBar;
    private SurfaceView photoResizeEastBar;
    private SurfaceView photoResizeWestBar;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param photoUrl Parameter 1.
     * @return A new instance of fragment PhotoFrameResizeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFrameResizeFragment newInstance(String photoUrl, String param2) {
        PhotoFrameResizeFragment fragment = new PhotoFrameResizeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, photoUrl);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoFrameResizeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            photoUrl = getArguments().getString(ARG_PARAM1);
        } else {
            photoUrl = TEST_PHOTO_01;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View photoFrameResizeLayout = inflater.inflate(R.layout.fragment_photo_frame_resize, container, false);
        photoResizeOK = (ImageButton)photoFrameResizeLayout.findViewById(R.id.photo_resize_ok);

        // Initialize the Masks Part
        photoResizeNorthMask = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_north_mask);
        photoResizeEastMask = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_east_mask);
        photoResizeWestMask = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_west_mask);
        photoResizeSouthMask = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_south_mask);

        photoResizeCenterMask = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_center_mask);

        // Initialize the Bars(Drag to Resize) Part
        photoResizeNorthBar = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_north_bar);
        photoResizeSouthBar = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_south_bar);
        photoResizeEastBar = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_east_bar);
        photoResizeWestBar = (SurfaceView) photoFrameResizeLayout.findViewById(R.id.photo_resize_west_bar);

        setUpClickListenerForOKBtn(null);

        setUpTouchListenerForCenterMask();
        setUpTouchListenerForResizeBars();
        return photoFrameResizeLayout;
    }


    private void setUpClickListenerForOKBtn(final Uri uri){
        photoResizeOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpDefaultPhotoResizeMaskPositions();
                //mListener.onPhotoFrameResizeFragmentOKButtonClickInteraction(uri);
            }
        });
    }

    private void setUpTouchListenerForResizeBars() {
        photoResizeNorthBar.setOnTouchListener(new ResizeBarOnTouchListener(ResizeBarOnTouchListener.NORTH_BAR));
        photoResizeSouthBar.setOnTouchListener(new ResizeBarOnTouchListener(ResizeBarOnTouchListener.SOUTH_BAR));
        photoResizeWestBar.setOnTouchListener(new ResizeBarOnTouchListener(ResizeBarOnTouchListener.WEST_BAR));
        photoResizeEastBar.setOnTouchListener(new ResizeBarOnTouchListener(ResizeBarOnTouchListener.EAST_BAR));
    }

    private void setUpTouchListenerForCenterMask() {
        photoResizeCenterMask.setOnTouchListener(new View.OnTouchListener() {
            private float originX;
            private float originY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        float offsetX = event.getRawX() - originX;
                        float offsetY = event.getRawY() - originY;
                        view.setX(view.getX() + offsetX);
                        view.setY(view.getY() + offsetY);
                        originX = event.getRawX();
                        originY = event.getRawY();
                        updatePhotoResizeMaskPositions((int)view.getX(), (int)view.getY(), view.getWidth(), view.getHeight());
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_DOWN:
                        originX = event.getRawX();
                        originY = event.getRawY();

                        Log.d("PhotoResizeMask", String.format("Touch Event(ACTION_DOWN): Position(%f, %f), ViewPosition(%f, %f).",
                                originX, originY, view.getX(), view.getY()));
                        break;
                }

                return true;
            }
        });
    }

    private class ResizeBarOnTouchListener implements View.OnTouchListener {
        public final static int NORTH_BAR = 0;
        public final static int SOUTH_BAR = 1;
        public final static int WEST_BAR = 2;
        public final static int EAST_BAR = 3;

        private int barType;
        private float originX;
        private float originY;

        public ResizeBarOnTouchListener(int barType) {
            this.barType = barType;
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    int centerX, centerY, offsetX, offsetY;
                    int centerWidth, centerHeight;

                    centerX = (int)photoResizeCenterMask.getX();
                    centerY = (int)photoResizeCenterMask.getY();
                    centerWidth = photoResizeCenterMask.getWidth();
                    centerHeight = photoResizeCenterMask.getHeight();
                    offsetX = (int) (event.getRawX() - originX);
                    offsetY = (int) (event.getRawY() - originY);
                    originX = event.getRawX();
                    originY = event.getRawY();


                    switch (this.barType) {
                        case NORTH_BAR:
                            if (centerHeight - offsetY >= MINIMUM_MASK_HEIGHT) {
                                centerY = centerY + offsetY;
                                centerHeight = centerHeight - offsetY;
                            }
                            break;
                        case SOUTH_BAR:
                            if (centerHeight + offsetY >= MINIMUM_MASK_HEIGHT)
                                centerHeight += offsetY;
                            break;
                        case WEST_BAR:
                            if (centerWidth - offsetX >= MINIMUM_MASK_WIDTH) {
                                centerWidth -= offsetX;
                                centerX += offsetX;
                            }
                            break;
                        case EAST_BAR:
                            if (centerWidth + offsetX >= MINIMUM_MASK_WIDTH)
                                centerWidth += offsetX;
                            break;
                        default:
                            break;

                    }

                    updatePhotoResizeMaskPositions(centerX, centerY, centerWidth, centerHeight);
                    break;

                case MotionEvent.ACTION_UP:
                    break;

                case MotionEvent.ACTION_DOWN:
                    originX = event.getRawX();
                    originY = event.getRawY();
                    switch (this.barType) {
                        case NORTH_BAR:
                            Log.d("PhotoFrameResizeFragment", "PhotoResizeNorthBar Touched." + event.getRawY());
                            break;
                        case SOUTH_BAR:
                            Log.d("PhotoFrameResizeFragment", "PhotoResizeSouthBar Touched.");
                            break;
                        case WEST_BAR:
                            Log.d("PhotoFrameResizeFragment", "PhotoResizeWestBar Touched.");
                            break;
                        case EAST_BAR:
                            Log.d("PhotoFrameResizeFragment", "PhotoResizeEastBar Touched.");
                            break;
                        default:
                            break;

                    }
                    break;
            }

            return true;

        }
    }
    private void setUpDefaultPhotoResizeMaskPositions() {
        View photoFrameResizeLayout = getActivity().findViewById(R.id.photo_resize_frame);
        int height = photoFrameResizeLayout.getHeight();
        int width = photoFrameResizeLayout.getWidth();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int centerWidth = width / 2;
        int centerHeight = height / 2;
        int centerX = width / 4;
        int centerY = height / 4;
        updatePhotoResizeMaskPositions(centerX, centerY, centerWidth, centerHeight, width, height);

    }

    private void updatePhotoResizeMaskPositions(int centerX, int centerY, int centerWidth, int centerHeight) {
        View photoFrameResizeLayout = getActivity().findViewById(R.id.photo_resize_frame);
        int height = photoFrameResizeLayout.getHeight();
        int width = photoFrameResizeLayout.getWidth();

        updatePhotoResizeMaskPositions(centerX, centerY, centerWidth, centerHeight, width, height);
    }

    private void updatePhotoResizeMaskPositions(int centerX, int centerY, int centerWidth, int centerHeight, int width, int height) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)photoResizeCenterMask.getLayoutParams();

        layoutParams.width = centerWidth;
        layoutParams.height = centerHeight;
        photoResizeCenterMask.setX(centerX);
        photoResizeCenterMask.setY(centerY);
        photoResizeCenterMask.setLayoutParams(layoutParams);

        int southHeight = height - centerY - centerHeight;
        int eastWidth = width - centerX - centerWidth;

        // Resize and Replace North Mask
        layoutParams = (FrameLayout.LayoutParams)photoResizeNorthMask.getLayoutParams();
        layoutParams.height = centerY;
        photoResizeNorthMask.setLayoutParams(layoutParams);

        // Resize and Replace South Mask
        layoutParams = (FrameLayout.LayoutParams)photoResizeSouthMask.getLayoutParams();
        layoutParams.height = southHeight;
        photoResizeSouthMask.setLayoutParams(layoutParams);

        // Resize and Replace West Mask
        layoutParams = (FrameLayout.LayoutParams)photoResizeWestMask.getLayoutParams();
        layoutParams.width = centerX;
        layoutParams.height = centerHeight;
        photoResizeWestMask.setLayoutParams(layoutParams);
        photoResizeWestMask.setY(photoResizeCenterMask.getY());

        // Resize and Replace East Mask
        layoutParams = (FrameLayout.LayoutParams)photoResizeEastMask.getLayoutParams();
        layoutParams.width = eastWidth;
        layoutParams.height = centerHeight;
        photoResizeEastMask.setLayoutParams(layoutParams);
        photoResizeEastMask.setY(photoResizeCenterMask.getY());

        // Replace the Resize Bars
        float barNorthSouthX = photoResizeCenterMask.getX() + (photoResizeCenterMask.getWidth() - photoResizeNorthBar.getWidth()) / 2;
        float barNorthY = photoResizeCenterMask.getY() - photoResizeNorthBar.getHeight() / 2;
        float barSouthY = barNorthY + photoResizeCenterMask.getHeight();
        float barWestEastY = photoResizeCenterMask.getY() + (photoResizeCenterMask.getHeight() - photoResizeNorthBar.getHeight()) / 2;
        float barWestX = photoResizeCenterMask.getX() - photoResizeNorthBar.getWidth() / 2;
        float barEastX = barWestX + photoResizeCenterMask.getWidth();

        photoResizeNorthBar.setX(barNorthSouthX);
        photoResizeNorthBar.setY(barNorthY);

        photoResizeSouthBar.setX(barNorthSouthX);
        photoResizeSouthBar.setY(barSouthY);

        photoResizeWestBar.setX(barWestX);
        photoResizeWestBar.setY(barWestEastY);

        photoResizeEastBar.setX(barEastX);
        photoResizeEastBar.setY(barWestEastY);

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
        public void onPhotoFrameResizeFragmentOKButtonClickInteraction(Uri uri);
    }

}
