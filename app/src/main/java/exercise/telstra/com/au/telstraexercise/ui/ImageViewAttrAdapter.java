package exercise.telstra.com.au.telstraexercise.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * This class defines the properties which will be used in ImageView for Glide.
 */
public class ImageViewAttrAdapter {

    @BindingAdapter({"imageUrl", "placeHolder", "errorImage"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable){
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(holderDrawable))
                .apply(new RequestOptions().error(errorDrawable))
                .into(imageView);
    }
}
