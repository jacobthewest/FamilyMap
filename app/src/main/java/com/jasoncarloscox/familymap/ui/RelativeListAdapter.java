package com.jasoncarloscox.familymap.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasoncarloscox.familymap.R;
import com.jasoncarloscox.familymap.model.Relative;
import com.jasoncarloscox.familymap.model.Relative.Relationship;
import com.jasoncarloscox.familymap.util.ResourceGenerator;

import java.util.List;

public class RelativeListAdapter extends RecyclerView.Adapter<RelativeListAdapter.ViewHolder> {

    private List<Relative> relatives;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final int ICON_SIZE_DP = 20;

        private View itemView;
        private ImageView imgIcon;
        private TextView txtTitle;
        private TextView txtSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;

            imgIcon = itemView.findViewById(R.id.detail_item_icon);
            txtTitle = itemView.findViewById(R.id.detail_item_title);
            txtSubtitle = itemView.findViewById(R.id.detail_item_subtitle);
        }

        public void bindData(final Relative relative) {
            Drawable icon = ResourceGenerator.genderIcon(itemView.getContext(),
                                                       relative.getGender(),
                                                       ICON_SIZE_DP);
            imgIcon.setImageDrawable(icon);

            txtTitle.setText(relative.getFullName());
            txtSubtitle.setText(relationshipString(relative.getRelationship()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),
                                               PersonActivity.class);
                    intent.putExtra(PersonActivity.KEY_PERSON_ID, relative.getPersonId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private String relationshipString(Relationship relationship) {
            Resources res = itemView.getResources();

            switch (relationship) {
                case FATHER:
                    return res.getString(R.string.father);
                case MOTHER:
                    return res.getString(R.string.mother);
                case HUSBAND:
                    return res.getString(R.string.husband);
                case WIFE:
                    return res.getString(R.string.wife);
                case SON:
                    return res.getString(R.string.son);
                case DAUGHTER:
                    return res.getString(R.string.daughter);
                default:
                    return res.getString(R.string.unknown);
            }
        }
    }

    public RelativeListAdapter(List<Relative> relatives) {
        this.relatives = relatives;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(relatives.get(i));
    }

    @Override
    public int getItemCount() {
        return relatives.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.itemview_details;
    }
}
