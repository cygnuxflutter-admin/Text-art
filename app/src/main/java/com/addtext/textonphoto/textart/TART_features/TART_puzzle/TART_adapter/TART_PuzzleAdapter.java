package com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_PuzzleLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_SquarePuzzleView;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_slant.TART_NumberSlantLayout;
import com.addtext.textonphoto.textart.TART_features.TART_puzzle.TART_layout.TART_straight.TART_NumberStraightLayout;
import com.addtext.textonphoto.textart.R;

import java.util.ArrayList;
import java.util.List;

public class TART_PuzzleAdapter extends RecyclerView.Adapter<TART_PuzzleAdapter.PuzzleViewHolder> {
    private List<Bitmap> bitmapData = new ArrayList();
    private List<TART_PuzzleLayout> layoutData = new ArrayList();

    public OnItemClickListener onItemClickListener;

    public int selectedIndex = 0;

    public interface OnItemClickListener {
        void onItemClick(TART_PuzzleLayout puzzleLayout, int i);
    }

    public PuzzleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PuzzleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack_item_puzzle, viewGroup, false));
    }

    public void setSelectedIndex(int i) {
        this.selectedIndex = i;
    }

    public void onBindViewHolder(PuzzleViewHolder puzzleViewHolder, final int i) {
        final TART_PuzzleLayout puzzleLayout = this.layoutData.get(i);
        puzzleViewHolder.puzzleView.setNeedDrawLine(true);
        puzzleViewHolder.puzzleView.setNeedDrawOuterLine(true);
        puzzleViewHolder.puzzleView.setTouchEnable(false);
        puzzleViewHolder.puzzleView.setLineSize(6);
        puzzleViewHolder.puzzleView.setPuzzleLayout(puzzleLayout);
        if (this.selectedIndex == i) {
            puzzleViewHolder.puzzleView.setBackgroundColor(Color.parseColor("#FF4081"));
        } else {
            puzzleViewHolder.puzzleView.setBackgroundColor(0);
        }
        puzzleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TART_PuzzleAdapter.this.onItemClickListener != null) {
                    int i = 0;
                    if (puzzleLayout instanceof TART_NumberSlantLayout) {
                        i = ((TART_NumberSlantLayout) puzzleLayout).getTheme();
                    } else if (puzzleLayout instanceof TART_NumberStraightLayout) {
                        i = ((TART_NumberStraightLayout) puzzleLayout).getTheme();
                    }
                    TART_PuzzleAdapter.this.onItemClickListener.onItemClick(puzzleLayout, i);
                }
                TART_PuzzleAdapter.this.selectedIndex = i;
                TART_PuzzleAdapter.this.notifyDataSetChanged();
            }
        });
        if (this.bitmapData != null) {
            int size = this.bitmapData.size();
            if (puzzleLayout.getAreaCount() > size) {
                for (int i2 = 0; i2 < puzzleLayout.getAreaCount(); i2++) {
                    puzzleViewHolder.puzzleView.addPiece(this.bitmapData.get(i2 % size));
                }
                return;
            }
            puzzleViewHolder.puzzleView.addPieces(this.bitmapData);
        }
    }

    public int getItemCount() {
        if (this.layoutData == null) {
            return 0;
        }
        return this.layoutData.size();
    }

    public void refreshData(List<TART_PuzzleLayout> list, List<Bitmap> list2) {
        this.layoutData = list;
        this.bitmapData = list2;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public static class PuzzleViewHolder extends RecyclerView.ViewHolder {
        TART_SquarePuzzleView puzzleView;

        public PuzzleViewHolder(View view) {
            super(view);
            this.puzzleView = view.findViewById(R.id.puzzle);
        }
    }
}
