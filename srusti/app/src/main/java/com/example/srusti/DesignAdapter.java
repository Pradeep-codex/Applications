package com.example.srusti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DesignAdapter extends RecyclerView.Adapter<DesignAdapter.ViewHolder> {

    private Context context;
    private int[] images;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public DesignAdapter(Context context, int[] images, OnItemClickListener listener) {
        this.context = context;
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DesignAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_design_image, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DesignAdapter.ViewHolder holder, int position) {
        holder.designImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView designImage;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            designImage = itemView.findViewById(R.id.designImage);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }
                }
            });
        }
    }
}
