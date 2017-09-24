package com.robl2e.thistimes.ui.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;


import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.robl2e.thistimes.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by robl2e on 8/15/2017.
 */

public class FilterSettingsBottomDialog extends BottomDialog {
    private static final String TAG = FilterSettingsBottomDialog.class.getSimpleName();
    private static final String TAG_DATE_PICKER = TAG + "_TAG_DATE_PICKER";
    private final View customView;
    private final FilterSettings filterSettings;
    private final Listener listener;
    private Button datePickerButton;
    private Spinner sortPicker;
    private DateTime dateTime;

    private CheckBox artsCheckBox;
    private CheckBox fashionAndStyleCheckBox;
    private CheckBox sportsCheckBox;

    public interface Listener {
        void onCancel();
        void onFinishSave(FilterSettings filterSettings);
    }

    public static FilterSettingsBottomDialog newInstance(Context context, FilterSettings filterSettings, Listener listener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.dialog_filter_settings, null);

        BottomDialog.Builder builder = new BottomDialog.Builder(context)
                .setTitle(R.string.filter)
                .setNegativeText(R.string.cancel)
                .setPositiveText(R.string.save)
                .setCancelable(false)
                .setCustomView(customView);

        return new FilterSettingsBottomDialog(builder, filterSettings, customView, listener);
    }

    protected FilterSettingsBottomDialog(Builder builder, FilterSettings filterSettings, final View customView, final Listener listener) {
        super(builder);
        this.filterSettings = filterSettings;
        this.customView = customView;
        this.listener = listener;

        datePickerButton = (Button) customView.findViewById(R.id.button_date_picker);
        sortPicker = (AppCompatSpinner) customView.findViewById(R.id.picker_sort);
        artsCheckBox = (CheckBox) customView.findViewById(R.id.checkbox_arts);
        fashionAndStyleCheckBox = (CheckBox) customView.findViewById(R.id.checkbox_fashion_and_style);
        sportsCheckBox = (CheckBox) customView.findViewById(R.id.checkbox_sports);

        setDatePickerView();
        setSortPickerView();
        setNewsDeskCheckBoxes();

        builder.onPositive(new ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog bottomDialog) {
                save();
                dismiss();
            }
        });
        builder.onNegative(new ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog bottomDialog) {
                dismiss();
                if (listener != null) listener.onCancel();
            }
        });
    }


    private void setDatePickerView() {
        if (filterSettings != null && filterSettings.getBeginDate() != null) {
            dateTime = DateTime.forInstant(filterSettings.getBeginDate(), TimeZone.getDefault());
        } else {
            dateTime = DateTime.now(TimeZone.getDefault());
        }
        datePickerButton.setText(getFormattedDateTime(dateTime));
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) customView.getContext();
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setPreselectedDate(dateTime.getYear(), getCompatibleMonth(), dateTime.getDay())
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int compatibleMonth = monthOfYear + 1;
                                dateTime = DateTime.forDateOnly(year, compatibleMonth, dayOfMonth);
                                datePickerButton.setText(getFormattedDateTime(dateTime));
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDoneText(v.getResources().getString(R.string.done))
                        .setCancelText(v.getResources().getString(R.string.cancel));
                cdp.show(activity.getSupportFragmentManager(), TAG_DATE_PICKER);
            }
        });
    }

    private void setSortPickerView() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(sortPicker.getContext()
                , R.array.array_sort, R.layout.item_sort_spinner);
        sortPicker.setAdapter(adapter);

        if (filterSettings != null && filterSettings.getSortOrder() != null) {
            Sort sort = filterSettings.getSortOrder();
            int position = sort.ordinal();
            sortPicker.setSelection(position);
        } else {
            sortPicker.setSelection(0); // default to normal
        }
    }

    private void setNewsDeskCheckBoxes() {
        if (filterSettings != null && filterSettings.getNewsDesk() != null) {
            List<String> newsDeskValues = filterSettings.getNewsDesk();
            for (String name : newsDeskValues) {
                NewsDesk newsDesk = NewsDesk.fromValue(name);
                switch (newsDesk) {
                    case ARTS:
                        artsCheckBox.setChecked(true);
                        break;
                    case FASHION_AND_STYLE:
                        fashionAndStyleCheckBox.setChecked(true);
                        break;
                    case SPORTS:
                        sportsCheckBox.setChecked(true);
                        break;
                }
            }
        }
    }

    private int getCompatibleMonth() {
        if (dateTime == null) return 1;
        return dateTime.getMonth() - 1;
    }

    private Long getInputDateTime() {
        TimeZone timeZone = TimeZone.getDefault();
        if (dateTime == null) {
            dateTime = DateTime.now(timeZone);
        }
        return dateTime.getMilliseconds(timeZone);
    }
    private String getFormattedDateTime(DateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format("MM-DD-YYYY");
    }

    private String getSortOrder() {
        String sortStr = (String) sortPicker.getSelectedItem();
        return Sort.fromValue(sortStr).getValue();
    }

    private List<String> getSelectedNewsDeskValues() {
        List<String> newsDeskValues = new ArrayList<>();
        if (artsCheckBox.isChecked()) {
            newsDeskValues.add(NewsDesk.ARTS.getValue());
        }

        if (fashionAndStyleCheckBox.isChecked()) {
            newsDeskValues.add(NewsDesk.FASHION_AND_STYLE.getValue());
        }

        if (sportsCheckBox.isChecked()) {
            newsDeskValues.add(NewsDesk.SPORTS.getValue());
        }
        return newsDeskValues;
    }

    private boolean save() {
        Sort sort = Sort.fromValue(getSortOrder());
        Long beginDate = getInputDateTime();
        FilterSettings filterSettings = new FilterSettings(beginDate
                , sort, getSelectedNewsDeskValues());

        notifyListener(filterSettings);
        return true;
    }

    private void notifyListener(FilterSettings filterSettings) {
        if (listener != null) {
            if (filterSettings != null) {
                listener.onFinishSave(filterSettings);
            }
        }
    }
}