package com.addtext.textonphoto.textart.TART_tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.addtext.textonphoto.textart.R;
import java.util.ArrayList;
import java.util.List;

public class TART_EditingToolsAdapter extends RecyclerView.Adapter<TART_EditingToolsAdapter.ViewHolder> {

    public OnItemSelected mOnItemSelected;

    public List<ToolModel> mToolList = new ArrayList<>();

    public interface OnItemSelected {
        void onToolSelected(TART_ToolType toolType);
    }

    public TART_EditingToolsAdapter(OnItemSelected onItemSelected) {
        this.mOnItemSelected = onItemSelected;
        this.mToolList.add(new ToolModel("Crop", R.drawable.knack_ic_crop, TART_ToolType.CROP));//
        this.mToolList.add(new ToolModel("Adjust", R.drawable.knack_ic_adjust, TART_ToolType.ADJUST));//
        this.mToolList.add(new ToolModel("Filter", R.drawable.knack_ic_filter, TART_ToolType.FILTER));//
        this.mToolList.add(new ToolModel("Overlay", R.drawable.knack_ic_overlay, TART_ToolType.OVERLAY));
        this.mToolList.add(new ToolModel("Sticker", R.drawable.knack_ic_sticker, TART_ToolType.STICKER));
        this.mToolList.add(new ToolModel("Text", R.drawable.knack_ic_text, TART_ToolType.TEXT));
        this.mToolList.add(new ToolModel("Fit", R.drawable.knack_ic_fit, TART_ToolType.INSTA));
        this.mToolList.add(new ToolModel("Blur", R.drawable.knack_ic_blur, TART_ToolType.BLUR));
        this.mToolList.add(new ToolModel("Splash", R.drawable.knack_ic_splash_two, TART_ToolType.SPLASH));
        this.mToolList.add(new ToolModel("Brush", R.drawable.knack_ic_paint_two, TART_ToolType.BRUSH));
        this.mToolList.add(new ToolModel("Mosaic", R.drawable.knack_ic_mosaic, TART_ToolType.MOSAIC));
        this.mToolList.add(new ToolModel("Beauty", R.drawable.knack_ic_beauty, TART_ToolType.BEAUTY));
    }

    public TART_EditingToolsAdapter(OnItemSelected onItemSelected, boolean z) {
        this.mOnItemSelected = onItemSelected;
        this.mToolList.add(new ToolModel("Layout", R.drawable.knack_ic_collage, TART_ToolType.LAYOUT));
        this.mToolList.add(new ToolModel("Border", R.drawable.knack_ic_border, TART_ToolType.BORDER));
        this.mToolList.add(new ToolModel("Ratio", R.drawable.knack_ic_ratio, TART_ToolType.RATIO));
        this.mToolList.add(new ToolModel("Filter", R.drawable.knack_ic_filter_two, TART_ToolType.FILTER));
        this.mToolList.add(new ToolModel("Sticker", R.drawable.knack_ic_sticker_two, TART_ToolType.STICKER));
        this.mToolList.add(new ToolModel("Text", R.drawable.knack_ic_text_two, TART_ToolType.TEXT));
        this.mToolList.add(new ToolModel("Bg", R.drawable.knack_background_icon, TART_ToolType.BACKGROUND));
    }



    class ToolModel {

        public int mToolIcon;

        public String mToolName;

        public TART_ToolType mToolType;

        ToolModel(String str, int i, TART_ToolType toolType) {
            this.mToolName = str;
            this.mToolIcon = i;
            this.mToolType = toolType;
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_row_editing_tools, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ToolModel toolModel = this.mToolList.get(i);
        viewHolder.txtTool.setText(toolModel.mToolName);
        viewHolder.imgToolIcon.setImageResource(toolModel.mToolIcon);
    }

    public int getItemCount() {
        return this.mToolList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgToolIcon;
        TextView txtTool;
        ConstraintLayout wrapTool;

        ViewHolder(View view) {
            super(view);
            this.imgToolIcon = view.findViewById(R.id.imgToolIcon);
            this.txtTool = view.findViewById(R.id.txtTool);
            this.wrapTool = view.findViewById(R.id.wrapTool);
            this.wrapTool.setOnClickListener(view1 -> TART_EditingToolsAdapter.this.mOnItemSelected.onToolSelected((TART_EditingToolsAdapter.this.mToolList.get(ViewHolder.this.getLayoutPosition())).mToolType));
        }
    }
}
