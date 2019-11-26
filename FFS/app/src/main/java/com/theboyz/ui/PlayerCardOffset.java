package com.theboyz.ui;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PlayerCardOffset extends RecyclerView.ItemDecoration
{

    private int ItemOffset;

    public PlayerCardOffset(int itemOffset)
    {
        this.ItemOffset = itemOffset;
    }

    public PlayerCardOffset(@NonNull Context context, @DimenRes int itemOffsetId)
    {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(this.ItemOffset, this.ItemOffset, this.ItemOffset, this.ItemOffset);
    }
}