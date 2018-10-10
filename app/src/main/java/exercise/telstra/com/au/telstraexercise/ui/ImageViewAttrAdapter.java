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

    /**
     * Defines extra ImageView attributes which can be used in the layout xml files to customize ImageViews.
     *
     * @param imageView The ImageView will be customized.
     * @param url The target image will be displayed on the imageView.
     * @param holderDrawable A placehold image will be displayed on the ImageView when Glide is downloading target image from internet.
     * @param errorDrawable A error image to be displayed on the ImageView when Glide fail to download the target image.
     */
    @BindingAdapter({"imageUrl", "placeHolder", "errorImage"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable){
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(holderDrawable))
                .apply(new RequestOptions().error(errorDrawable))
                .into(imageView);
    }
}
