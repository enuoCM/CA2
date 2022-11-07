package com.xixicm.ca.presentation.view.component;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class ColorFreezeTextView extends AppCompatTextView {
    Integer mTextColor = null;

    public ColorFreezeTextView(Context context) {
        super(context);
    }

    public ColorFreezeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorFreezeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTextColor(int color) {
        mTextColor = color;
        super.setTextColor(color);
    }


    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (mTextColor != null) {
            SavedState ss = new SavedState(superState);
            ss.mTextColor = mTextColor;
            return ss;
        }
        return superState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        mTextColor = ss.mTextColor;
        super.setTextColor(mTextColor);
    }

    private static class SavedState extends BaseSavedState {
        int mTextColor;

        SavedState(Parcelable superState) {
            super(superState);
        }

        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            mTextColor = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mTextColor);
        }

        @Override
        public String toString() {
            return "CustomTextView.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " mTextColor=" + mTextColor + "}";
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
