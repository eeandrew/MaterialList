package com.shanghai.works.ate.snapnote.ui.anim;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.shanghai.works.ate.snapnote.R;

/**
 * Created by zhoulin on 2015/1/8.
 */
public class AnimateUtil {
    public void animateImageTransition(final ImageView imageView,final int images[],final int imageIndex,final boolean forever){
        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 500;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.VISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animateImageTransition(imageView, images, imageIndex + 1, forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever == true){
                        animateImageTransition(imageView, images, 0, forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void addOrRemoveMyFavorite(Context context,ImageView imageView,boolean isFavorite){
        Drawable myFavorite = context.getDrawable(R.drawable.ic_action_favorite);
        myFavorite.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);
        int cx = (imageView.getLeft() + imageView.getRight())/2;
        int cy = (imageView.getTop() + imageView.getBottom())/2;
        Animator anim = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0, 0);
        anim.start();
        if(isFavorite)
            imageView.setImageResource(R.drawable.ic_action_new);
        else
            imageView.setImageDrawable(myFavorite);
        cx = (imageView.getLeft() + imageView.getRight())/2;
        cy = (imageView.getTop() + imageView.getBottom())/2;
        int finalRadius = Math.max(imageView.getWidth(), imageView.getHeight());
        anim = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0, finalRadius);
        anim.start();
    }
}
