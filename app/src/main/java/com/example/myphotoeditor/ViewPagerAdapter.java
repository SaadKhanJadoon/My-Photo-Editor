package com.example.myphotoeditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private final ArrayList<String> mFilePaths;
    private final Context mContext;
    private final Activity myActivity;

    ViewPagerAdapter(Context context) {
        mContext = context;
        mFilePaths = MainActivity.getFilePaths();
        myActivity = (Activity) mContext;
    }

    @Override
    public int getCount() {
        return mFilePaths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        MyImageView image = new MyImageView(mContext);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setPath(mFilePaths.get(position));
        image.setTag(position);
        ViewCompat.setTransitionName(image, mFilePaths.get(position));

        Glide.with(mContext)
                .load(mFilePaths.get(position))
                .centerCrop()
                .dontAnimate()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ActivityCompat.startPostponedEnterTransition(myActivity);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ActivityCompat.startPostponedEnterTransition(myActivity);
                        return false;
                    }
                })
                .into(image);

        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((MyImageView) object);
    }
}
