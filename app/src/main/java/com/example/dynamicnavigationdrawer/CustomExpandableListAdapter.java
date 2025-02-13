package com.example.dynamicnavigationdrawer;

import android.content.Context;
import android.util.Pair; // <-- Import Pair class
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Pair<String, Integer>> listDataHeader; // <-- Updated to Pair
    private HashMap<Pair<String, Integer>, List<Pair<String, Integer>>> listDataChild; // <-- Updated to Pair

    public CustomExpandableListAdapter(Context context, List<Pair<String, Integer>> listDataHeader, // <-- Updated to Pair
                                       HashMap<Pair<String, Integer>, List<Pair<String, Integer>>> listChildData) { // <-- Updated to Pair
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // Get the Pair object for the parent item
        Pair<String, Integer> headerPair = (Pair<String, Integer>) getGroup(groupPosition);
        String headerTitle = headerPair.first;
        Integer headerIconResId = headerPair.second;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }

        TextView parentItemTextView = convertView
                .findViewById(R.id.parent_item_title);
        ImageView parentItemIconImageView = convertView.findViewById(R.id.parent_item_icon);
        ImageView expandIndicatorImageView = convertView.findViewById(R.id.parent_item_expand_indicator);

        parentItemTextView.setText(headerTitle);
        parentItemIconImageView.setImageResource(headerIconResId);

        // --- Conditional Expand Icon Visibility ---
        if (getChildrenCount(groupPosition) > 0) {
            // Show expand indicator if there are children
            expandIndicatorImageView.setVisibility(View.VISIBLE);
            expandIndicatorImageView.setImageResource(isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);
        } else {
            // Hide expand indicator if no children
            expandIndicatorImageView.setVisibility(View.INVISIBLE); // Or View.GONE to remove space
        }

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // Get the Pair object for the child item
        Pair<String, Integer> childPair = (Pair<String, Integer>) getChild(groupPosition, childPosition);
        final String childText = childPair.first; // Extract title from Pair
        Integer childIconResId = childPair.second; // Extract icon resource ID from Pair


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView childItemTextView = convertView
                .findViewById(R.id.child_item_title);
        ImageView childItemIconImageView = convertView.findViewById(R.id.child_item_icon);

        childItemTextView.setText(childText);

        // Set the icon from the Pair object
        childItemIconImageView.setImageResource(childIconResId); // Set dynamic icon


        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition); // Return the Pair object
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition); // Return the Pair object
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true; // Make child items selectable
    }
}
