package com.addtext.textonphoto.textart.TART_tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;
import java.util.List;

public class TART_TextToolsAdapter extends RecyclerView.Adapter<TART_TextToolsAdapter.ViewHolder> {

    public List<ToolModel> mToolList = new ArrayList<>();

    public OnPieceFuncItemSelected onPieceFuncItemSelected;

    public interface OnPieceFuncItemSelected {
        void onPieceFuncSelected(TART_ToolType toolType);
    }

    public TART_TextToolsAdapter(OnPieceFuncItemSelected onPieceFuncItemSelected2) {
        this.onPieceFuncItemSelected = onPieceFuncItemSelected2;
        this.mToolList.add(new ToolModel("Change", R.drawable.knack_background_icon_white, TART_ToolType.REPLACE));
        this.mToolList.add(new ToolModel("Crop", R.drawable.knack_ic_crop_two_white, TART_ToolType.CROP));
        this.mToolList.add(new ToolModel("Filter", R.drawable.knack_ic_filter_two_white, TART_ToolType.FILTER));
        this.mToolList.add(new ToolModel("Rotate", R.drawable.knack_rotate_white, TART_ToolType.ROTATE));
        this.mToolList.add(new ToolModel("H Flip", R.drawable.knack_h_flip_white, TART_ToolType.H_FLIP));
        this.mToolList.add(new ToolModel("V Flip", R.drawable.knack_v_flip_white, TART_ToolType.V_FLIP));
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
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_row_piece_tools, viewGroup, false));
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

        ViewHolder(View view) {
            super(view);
            this.imgToolIcon = view.findViewById(R.id.imgToolIcon);
            this.txtTool = view.findViewById(R.id.txtTool);
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ToolModel toolModel = TART_TextToolsAdapter.this.mToolList.get(ViewHolder.this.getLayoutPosition());
                    TART_ToolType toolType = toolModel.mToolType;
                    TART_TextToolsAdapter.this.onPieceFuncItemSelected.onPieceFuncSelected(toolType);
                }
            });
        }
    }
}
