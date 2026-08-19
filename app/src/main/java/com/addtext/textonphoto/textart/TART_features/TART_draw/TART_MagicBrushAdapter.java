package com.addtext.textonphoto.textart.TART_features.TART_draw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.TART_photoeditor.TART_DrawBitmapModel;
import com.addtext.textonphoto.textart.TART_utils.TART_SystemUtil;
import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TART_MagicBrushAdapter extends RecyclerView.Adapter<TART_MagicBrushAdapter.ViewHolder> {
    public static List<TART_DrawBitmapModel> drawBitmapModels = new ArrayList();
    private int borderSize = 0;

    public TART_BrushMagicListener brushMagicListener;
    private Context context;

    public int selectedColorIndex;

    public TART_MagicBrushAdapter(Context context2, TART_BrushMagicListener brushMagicListener2) {
        this.context = context2;
        this.borderSize = TART_SystemUtil.dpToPx(context2, 2);
        this.brushMagicListener = brushMagicListener2;
        drawBitmapModels = lstDrawBitmapModel(context2);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_magic_brush_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.magicBrush.setImageResource(drawBitmapModels.get(i).getMainIcon());
        if (this.selectedColorIndex == i) {
            viewHolder.magicBrush.setBorderWidth(this.borderSize);
        } else {
            viewHolder.magicBrush.setBorderWidth(0);
        }
    }

    public void setSelectedColorIndex(int i) {
        this.selectedColorIndex = i;
    }

    public int getItemCount() {
        return drawBitmapModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView magicBrush;

        ViewHolder(View view) {
            super(view);
            this.magicBrush = view.findViewById(R.id.magicBrush);
            this.magicBrush.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TART_MagicBrushAdapter.this.selectedColorIndex = ViewHolder.this.getLayoutPosition();
                    TART_MagicBrushAdapter.this.brushMagicListener.onMagicChanged(TART_MagicBrushAdapter.drawBitmapModels.get(TART_MagicBrushAdapter.this.selectedColorIndex));
                    TART_MagicBrushAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    public static List<TART_DrawBitmapModel> lstDrawBitmapModel(Context context2) {
        if (drawBitmapModels != null && !drawBitmapModels.isEmpty()) {
            return drawBitmapModels;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(R.drawable.knack_b4));
        arrayList.add(Integer.valueOf(R.drawable.knack_b5));
        arrayList.add(Integer.valueOf(R.drawable.knack_b6));
        arrayList.add(Integer.valueOf(R.drawable.knack_b7));
        arrayList.add(Integer.valueOf(R.drawable.knack_b8));
        arrayList.add(Integer.valueOf(R.drawable.knack_b9));
        arrayList.add(Integer.valueOf(R.drawable.knack_b10));
        arrayList.add(Integer.valueOf(R.drawable.knack_b11));
        arrayList.add(Integer.valueOf(R.drawable.knack_b12));
        arrayList.add(Integer.valueOf(R.drawable.knack_b13));
        arrayList.add(Integer.valueOf(R.drawable.knack_b14));
        arrayList.add(Integer.valueOf(R.drawable.knack_b15));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_butterfly, arrayList, true, context2));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.valueOf(R.drawable.knack_magic21));
        arrayList2.add(Integer.valueOf(R.drawable.knack_magic22));
        arrayList2.add(Integer.valueOf(R.drawable.knack_magic23));
        arrayList2.add(Integer.valueOf(R.drawable.knack_magic24));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_heart_1, arrayList2, true, context2));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(Integer.valueOf(R.drawable.knack_f1));
        arrayList3.add(Integer.valueOf(R.drawable.knack_f1));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_f1_icon, arrayList3, true, context2));
        ArrayList arrayList4 = new ArrayList();
        arrayList4.add(Integer.valueOf(R.drawable.knack_s1));
        arrayList4.add(Integer.valueOf(R.drawable.knack_s1));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_s1_icon, arrayList4, true, context2));
        ArrayList arrayList5 = new ArrayList();
        arrayList5.add(Integer.valueOf(R.drawable.knack_b1));
        arrayList5.add(Integer.valueOf(R.drawable.knack_b2));
        arrayList5.add(Integer.valueOf(R.drawable.knack_b3));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_b1_icon, arrayList5, true, context2));
        ArrayList arrayList6 = new ArrayList();
        arrayList6.add(Integer.valueOf(R.drawable.knack_f3));
        arrayList6.add(Integer.valueOf(R.drawable.knack_f7));
        arrayList6.add(Integer.valueOf(R.drawable.knack_f5));
        arrayList6.add(Integer.valueOf(R.drawable.knack_f6));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_f2_icon, arrayList6, true, context2));
        ArrayList arrayList7 = new ArrayList();
        arrayList7.add(Integer.valueOf(R.drawable.knack_m1));
        arrayList7.add(Integer.valueOf(R.drawable.knack_m2));
        arrayList7.add(Integer.valueOf(R.drawable.knack_m3));
        arrayList7.add(Integer.valueOf(R.drawable.knack_m4));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_bb2_icon, arrayList7, true, context2));
        ArrayList arrayList8 = new ArrayList();
        arrayList8.add(Integer.valueOf(R.drawable.knack_ss1));
        arrayList8.add(Integer.valueOf(R.drawable.knack_ss2));
        arrayList8.add(Integer.valueOf(R.drawable.knack_ss3));
        arrayList8.add(Integer.valueOf(R.drawable.knack_ss5));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_f3_icon, arrayList8, true, context2));
        ArrayList arrayList9 = new ArrayList();
        arrayList9.add(Integer.valueOf(R.drawable.knack_s17));
        arrayList9.add(Integer.valueOf(R.drawable.knack_s17));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_smile_icon1, arrayList9, true, context2));
        ArrayList arrayList10 = new ArrayList();
        arrayList10.add(Integer.valueOf(R.drawable.knack_s21));
        arrayList10.add(Integer.valueOf(R.drawable.knack_s21));
        drawBitmapModels.add(new TART_DrawBitmapModel(R.drawable.knack_smile_icon2, arrayList10, true, context2));
        return drawBitmapModels;
    }
}
