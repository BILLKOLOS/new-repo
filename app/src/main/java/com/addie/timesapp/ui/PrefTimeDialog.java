/*
 * MIT License
 *
 * Copyright (c) 2018 aSoft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.addie.shutt.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.addie.shutt.R;
import com.triggertrap.seekarc.SeekArc;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A variation of TimeDialog to be displayed from Preferences to be able to set a default time
 */
public class PrefTimeDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Context mContext;
    private SharedPreferences preferences;

    private int minutes;


    @BindView(R.id.btn_pref_dialog_cancel)
    Button mCancelButton;
    @BindView(R.id.btn_pref_dialog_start)
    Button mStartButton;
    @BindView(R.id.seekArc_dialog_pref)
    SeekArc mSeekArc;
    @BindView(R.id.tv_seekarc_progress_pref)
    TextView mSeekArcProgressTextView;
    @BindView(R.id.tv_pref_dialog_title)
    TextView mDialogTitleTextView;

    public PrefTimeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Timber.d("Created pref dialog");

        setContentView(R.layout.layout_pref_time_dialog);

        ButterKnife.bind(this);

        mStartButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        String progressText = " " + String.valueOf(mSeekArc.getProgress());
        mSeekArcProgressTextView.setText(progressText);

        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int progress = Integer.parseInt(preferences.getString(mContext.getString(R.string.pref_app_time_key), "10"));

        mSeekArc.setProgress(progress);
        String progressTxt = " " + String.valueOf(progress);
        mSeekArcProgressTextView.setText(progressTxt);
        minutes = progress;


        mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int i, boolean b) {
                if (i == 0)
                    i = 1;
                String progressText = " " + String.valueOf(i);
                mSeekArcProgressTextView.setText(progressText);
                minutes = i;
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pref_dialog_start:
                dismiss();
                saveDefaultAppDuration();
                break;
            case R.id.btn_pref_dialog_cancel:
                dismiss();
                ((Activity)mContext).finish();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void saveDefaultAppDuration() {
        String minutesString = String.valueOf(minutes);
        preferences.edit().putString(mContext.getString(R.string.pref_app_time_key),minutesString).apply();
        ((Activity)mContext).finish();
    }


    //Finish dialog and activity on pressing back
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.dismiss();
        ((Activity) mContext).finish();

    }

}
