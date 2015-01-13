package com.shanghai.works.ate.snapnote;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.shanghai.works.ate.snapnote.ui.NavigationDrawerFragment;
import com.shanghai.works.ate.snapnote.ui.NoteCardsFragment;
import com.shanghai.works.ate.snapnote.ui.NoteDetailFragment;
import com.shanghai.works.ate.snapnote.ui.NoteEditFragment;
import com.shanghai.works.ate.snapnote.ui.NotesListFragment;
import com.shanghai.works.ate.snapnote.ui.PhotoFrameResizeFragment;
import com.shanghai.works.ate.snapnote.ui.PhotoFrameResizeFragmentOld;
import com.shanghai.works.ate.snapnote.ui.PhotoFrameResizeResultFragment;
import com.shanghai.works.ate.snapnote.ui.PhotoTakingFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        NotesListFragment.OnFragmentInteractionListener,
        NoteDetailFragment.OnFragmentInteractionListener,
        PhotoTakingFragment.OnFragmentInteractionListener,
        NoteEditFragment.OnFragmentInteractionListener,
        PhotoFrameResizeFragment.OnFragmentInteractionListener,
        NoteCardsFragment.OnFragmentInteractionListener,
        PhotoFrameResizeResultFragment.OnFragmentInteractionListener {

    PhotoFrameResizeFragment resizeFragment;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, NoteCardsFragment.newInstance("",""))
                .commit();*/
        displayPhotoFrameResizeFragment(null);
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void onNoteListFragmentItemClickInteraction(String noteId){
            Log.d("SNAPNOTE","Note " + noteId + " is clicked");
            displayNoteDetailFragment(noteId);
    }

    public void onNoteDetailEditFabClickInteraction(String noteId){
        displayNoteEditFragment(noteId);
    }

    public void onNoteEditFragmentSaveFabClickInteraction(String noteId){
        displayNoteDetailFragment(noteId);
    }


    public void onNoteListFragmentCameraFabClickInteraction(){
        displayPhotoTakingFragment();
    }

    public void onPhotoTakingFragmentOKButtonClickInteraction(Uri newPhotoUri){
        displayPhotoFrameResizeFragment(newPhotoUri);
    }

    public void onPhotoFrameResizeFragmentOKButtonClickInteraction(Uri uri){
        //displayNoteEditFragment("You need to edit this photo");
        displayPhotoFrameResizeResultFragment();
    }

    public void onNoteCardsFragmentCameraFabClickInteraction(){
//        imageTempPath = PhotoService.getPhotoTempFile();
//        startActivityForResult(PhotoService.createCameraIntent(imageTempPath), CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public void onPhotoFrameResizeResultFragmentOKButtonClickInteraction() {
        displayPhotoFrameResizeFragment(null);
    }

    private void displayPhotoFrameResizeResultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PhotoFrameResizeResultFragment.newInstance(resizeFragment.getTrimmedImage()))
                .commit();
    }


    private void displayPhotoFrameResizeFragment(Uri uri){
        resizeFragment = PhotoFrameResizeFragment.newInstance("to be determined", "to be determined");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, resizeFragment)
                .commit();
    }

    private void displayNoteDetailFragment(String noteId){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, NoteDetailFragment.newInstance(noteId, "to be determined"))
//                .commit();
    }

    private void displayPhotoTakingFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PhotoTakingFragment.newInstance("to be determined", "to be determined"))
                .commit();
    }

    private void displayNoteEditFragment(String noteId){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, NoteEditFragment.newInstance("to be determined", "to be determined"))
                .commit();
    }

    private void displayNoteListFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, NotesListFragment.newInstance("to be determined", "to be determined"))
                .commit();
    }
}
