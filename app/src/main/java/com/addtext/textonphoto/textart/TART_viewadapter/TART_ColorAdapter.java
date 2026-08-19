package com.addtext.textonphoto.textart.TART_viewadapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_imagechanger.TART_RoundFrameLayout;

import java.util.ArrayList;
import java.util.List;


public class TART_ColorAdapter extends RecyclerView.Adapter<TART_ColorAdapter.ColorViewHolder> {
    ColorAdapterListener colorAdapterListener;
    Context context;
    int index = -1;
    List<Integer> colorList = genColorList();


    public interface ColorAdapterListener {
        void onColorItemSelected(int i);
    }

    public TART_ColorAdapter(Context context, ColorAdapterListener colorAdapterListener) {
        this.context = context;
        this.colorAdapterListener = colorAdapterListener;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ColorViewHolder(LayoutInflater.from(this.context).inflate(R.layout.knack_item_color, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ColorViewHolder colorViewHolder, int i) {
        if (this.index == i) {
            colorViewHolder.colorSection.getDelegate().setStrokeColor(ContextCompat.getColor(this.context, R.color.icChecked));
        } else {
            colorViewHolder.colorSection.getDelegate().setStrokeColor(17170445);
        }
        colorViewHolder.colorSection.getDelegate().setBackgroundColor(this.colorList.get(i).intValue());
    }

    @Override
    public int getItemCount() {
        return this.colorList.size();
    }


    public class ColorViewHolder extends RecyclerView.ViewHolder {
        TART_RoundFrameLayout colorSection;

        public ColorViewHolder(View view) {
            super(view);
            this.colorSection = (TART_RoundFrameLayout) view.findViewById(R.id.color_section);
            view.setOnClickListener(new View.OnClickListener() { // from class: quotes.photo.textonphoto.viewadapter.-$$Lambda$ColorAdapter$ColorViewHolder$zG4et5WbrCBcID6W3_A9I8OnsF4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ColorViewHolder.this.lambda$new$0$ColorAdapter$ColorViewHolder(view2);
                }
            });
        }

        public  void lambda$new$0$ColorAdapter$ColorViewHolder(View view) {
            if (getAdapterPosition() < TART_ColorAdapter.this.colorList.size()) {
                TART_ColorAdapter.this.colorAdapterListener.onColorItemSelected(TART_ColorAdapter.this.colorList.get(getAdapterPosition()).intValue());
                TART_ColorAdapter.this.index = getAdapterPosition();
                TART_ColorAdapter.this.notifyDataSetChanged();
            }
        }
    }

    private List<Integer> genColorList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(Color.parseColor("#FFFFFF")));
        arrayList.add(Integer.valueOf(Color.parseColor("#000000")));
        arrayList.add(Integer.valueOf(Color.parseColor("#0098F1")));
        arrayList.add(Integer.valueOf(Color.parseColor("#4CC259")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FFC859")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FF8523")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FF3A4A")));
        arrayList.add(Integer.valueOf(Color.parseColor("#E90060")));
        arrayList.add(Integer.valueOf(Color.parseColor("#B300B6")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FF0000")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FF7E88")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FFD0D1")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FFDAB2")));
        arrayList.add(Integer.valueOf(Color.parseColor("#FFC07E")));
        arrayList.add(Integer.valueOf(Color.parseColor("#E18B42")));
        arrayList.add(Integer.valueOf(Color.parseColor("#a36138")));
        arrayList.add(Integer.valueOf(Color.parseColor("#4A2829")));
        arrayList.add(Integer.valueOf(Color.parseColor("#004C30")));
        arrayList.add(Integer.valueOf(Color.parseColor("#2C2C2C")));
        arrayList.add(Integer.valueOf(Color.parseColor("#393939")));
        arrayList.add(Integer.valueOf(Color.parseColor("#555555")));
        arrayList.add(Integer.valueOf(Color.parseColor("#727272")));
        arrayList.add(Integer.valueOf(Color.parseColor("#989898")));
        arrayList.add(Integer.valueOf(Color.parseColor("#B1B1B1")));
        arrayList.add(Integer.valueOf(Color.parseColor("#C7C7C7")));
        arrayList.add(Integer.valueOf(Color.parseColor("#DBDBDB")));
        arrayList.add(Integer.valueOf(Color.parseColor("#F0F0F0")));
        return arrayList;
    }
}
