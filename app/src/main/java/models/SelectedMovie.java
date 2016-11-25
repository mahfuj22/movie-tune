package models;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectedMovie implements Serializable{

    private boolean adult;
    private String backdropPath;
    private String belongs_oCollection;
    private String title;

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    private String budget;

    private ArrayList<Genre> genres;
    private ArrayList<SpokenLanguage> spokenLanguageList;

    public ArrayList<ProductionCompany> getProductionCompanyList() {
        return productionCompanyList;
    }

    public void setProductionCompanyList(ArrayList<ProductionCompany> productionCompanyList) {
        this.productionCompanyList = productionCompanyList;
    }

    public ArrayList<ProductionCountries> getProductionCountriesList() {
        return productionCountriesList;
    }

    public void setProductionCountriesList(ArrayList<ProductionCountries> productionCountriesList) {
        this.productionCountriesList = productionCountriesList;
    }

    private ArrayList<ProductionCompany> productionCompanyList;
    private ArrayList<ProductionCountries> productionCountriesList;

    private String homepage;
    private int id;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    private String imdbId;
    private String originalLanguage;
    private String originalTitle;
    private String overview;

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    private double popularity;
    private String posterPath;
    private String prdCmpName;
    private int prdCmpId;
    private String prdCountryName;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private String releaseDate;
    private int revenue;
    private int runtime;
    private String status;
    private String tagline;
    private boolean video;

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    private double voteAverage;
    private int voteCount;

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBelongs_oCollection() {
        return belongs_oCollection;
    }

    public void setBelongs_oCollection(String belongs_oCollection) {
        this.belongs_oCollection = belongs_oCollection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPrdCmpName() {
        return prdCmpName;
    }

    public void setPrdCmpName(String prdCmpName) {
        this.prdCmpName = prdCmpName;
    }

    public int getPrdCmpId() {
        return prdCmpId;
    }

    public void setPrdCmpId(int prdCmpId) {
        this.prdCmpId = prdCmpId;
    }

    public String getPrdCountryName() {
        return prdCountryName;
    }

    public void setPrdCountryName(String prdCountryName) {
        this.prdCountryName = prdCountryName;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }



    public ArrayList<SpokenLanguage> getSpokenLanguageList() {
        return spokenLanguageList;
    }

    public void setSpokenLanguageList(ArrayList<SpokenLanguage> spokenLanguageList) {
        this.spokenLanguageList = spokenLanguageList;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }


}