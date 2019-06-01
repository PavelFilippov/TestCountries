package ru.com.testcountries.ui.adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

import ru.com.testcountries.R;
import ru.com.testcountries.core.data.model.Country;
import ru.com.testcountries.core.utils.svg.SvgDecoder;
import ru.com.testcountries.core.utils.svg.SvgDrawableTranscoder;
import ru.com.testcountries.core.utils.svg.SvgSoftwareLayerSetter;
import ru.com.testcountries.ui.adapters.base.BaseAdapter;
import ru.com.testcountries.ui.adapters.base.BaseViewHolder;

public class CountriesAdapter extends BaseAdapter<Country, CountriesAdapter.Holder> {

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public CountriesAdapter(Context context) {
        super(context);
        generateRequestBuilder();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateView(R.layout.item_country, parent);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolderInternal(Holder holder, int position) {
        Country country = getItemById(position);

        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(Uri.parse(country.getFlag()))
                .into(holder.imgFlag);

        holder.txtCountryName.setText(country.getName());

        setupListener(holder.itemView, country, position);

    }

    class Holder extends BaseViewHolder {

        private AppCompatImageView imgFlag;
        private TextView txtCountryName;


        public Holder(View itemView) {
            super(itemView);
            imgFlag = getView(R.id.imgFlag);
            txtCountryName = getView(R.id.txtCountryName);

        }
    }

    private void generateRequestBuilder() {
        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.rounded_corners_empty_image)
                .error(R.drawable.rounded_corners_empty_image)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }
}
