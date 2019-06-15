package com.jasoncarloscox.familymap.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jasoncarloscox.familymap.R;
import com.jasoncarloscox.familymap.model.FilterItem;
import com.jasoncarloscox.familymap.model.Model;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    // views
    private RecyclerView recyclerFilters;

    // member variables
    private Model model = Model.instance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.filter_activity_title);

        initComponents();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    /**
     * Initializes all of the components that need to be accessed by grabbing
     * them from the view and adding necessary listeners.
     */
    private void initComponents() {
        recyclerFilters = findViewById(R.id.filter_recycler);

        recyclerFilters.setLayoutManager(new LinearLayoutManager(this));
        recyclerFilters.setAdapter(new FilterListAdapter(getFilterItems()));
    }

    private List<FilterItem> getFilterItems() {
        List<FilterItem> items = new ArrayList<>();

        FilterItem male = new FilterItem(getString(R.string.filter_title_male),
                                         getString(R.string.filter_description_male),
                                         model.shouldShowMale());
        male.setListener(new FilterItem.Listener() {
            @Override
            public void onFilterStatusChange(boolean filtered) {
                model.setShouldShowMale(filtered);
            }
        });
        items.add(male);

        FilterItem female = new FilterItem(getString(R.string.filter_title_female),
                                           getString(R.string.filter_description_female),
                                           model.shouldShowFemale());
        female.setListener(new FilterItem.Listener() {
            @Override
            public void onFilterStatusChange(boolean filtered) {
                model.setShouldShowFemale(filtered);
            }
        });
        items.add(female);

        FilterItem paternal = new FilterItem(getString(R.string.filter_title_paternal),
                getString(R.string.filter_description_paternal),
                model.shouldShowPaternalSide());
        paternal.setListener(new FilterItem.Listener() {
            @Override
            public void onFilterStatusChange(boolean filtered) {
                model.setShouldShowPaternalSide(filtered);
            }
        });
        items.add(paternal);

        FilterItem maternal = new FilterItem(getString(R.string.filter_title_maternal),
                getString(R.string.filter_description_maternal),
                model.shouldShowMaternalSide());
        maternal.setListener(new FilterItem.Listener() {
            @Override
            public void onFilterStatusChange(boolean filtered) {
                model.setShouldShowMaternalSide(filtered);
            }
        });
        items.add(maternal);

        for (String type : model.getEventTypes()) {
            items.add(getEventTypeFilterItem(type));
        }

        return items;
    }

    private FilterItem getEventTypeFilterItem(final String type) {
        FilterItem item = new FilterItem(getString(R.string.filter_title_type, type),
                                         getString(R.string.filter_description_type, type),
                                         model.shouldShow(type));
        item.setListener(new FilterItem.Listener() {
            @Override
            public void onFilterStatusChange(boolean filtered) {
                model.setShouldShow(type, filtered);
            }
        });

        return item;
    }
}
