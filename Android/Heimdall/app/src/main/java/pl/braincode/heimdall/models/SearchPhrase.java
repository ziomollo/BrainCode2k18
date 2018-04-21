package pl.braincode.heimdall.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchPhrase implements Parcelable {
    public String phrase;

    private SearchPhrase(Parcel in) {
        phrase = in.readString();
    }

    public SearchPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.phrase);
    }

    public static final Parcelable.Creator<SearchPhrase> CREATOR
            = new Parcelable.Creator<SearchPhrase>() {
        public SearchPhrase createFromParcel(Parcel in) {
            return new SearchPhrase(in);
        }

        public SearchPhrase[] newArray(int size) {
            return new SearchPhrase[size];
        }
    };

}
