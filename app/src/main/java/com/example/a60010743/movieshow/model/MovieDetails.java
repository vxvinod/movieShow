package com.example.a60010743.movieshow.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetails implements Parcelable {
    private String title;
    private String posterTv;
    private String overview;
    private float userRating;
    private String releasedDate;
    private String movieId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterTv() {
        return posterTv;
    }

    public void setPosterTv(String posterTv) {
        this.posterTv = posterTv;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getMovieId(){ return movieId; }

    public void setMovieId(String movieId) { this.movieId = movieId; }


    public MovieDetails(String title, String posterTv, String overview, float userRating, String released_date, String movieId) {
        this.title = title;
        this.posterTv = posterTv;
        this.overview = overview;
        this.userRating = userRating;
        this.releasedDate = released_date;
        this.movieId = movieId;
    }

    private MovieDetails(Parcel parcel){
        title = parcel.readString();
        posterTv = parcel.readString();
        overview = parcel.readString();
        userRating = parcel.readFloat();
        releasedDate = parcel.readString();
        movieId = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterTv);
        dest.writeString(overview);
        dest.writeFloat(userRating);
        dest.writeString(releasedDate);
        dest.writeString(movieId);
   }


   public final static Parcelable.Creator<MovieDetails> CREATOR = new Parcelable.Creator<MovieDetails>(){

       @Override
       public MovieDetails createFromParcel(Parcel source) {
           return new MovieDetails(source);
       }

       @Override
       public MovieDetails[] newArray(int size) {
           return new MovieDetails[size];
       }
   };


}
