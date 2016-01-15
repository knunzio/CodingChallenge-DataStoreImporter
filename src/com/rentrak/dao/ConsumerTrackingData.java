package com.rentrak.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.rentrak.datastore.DataStoreObject;

/**
 * STB - The set top box id on which the media asset was viewed. (Text, max size 64 char)
 * TITLE - The title of the media asset. (Text, max size 64 char)
 * PROVIDER - The distributor of the media asset. (Text, max size 64 char)
 * DATE - The local date on which the content was leased by through the STB (A date in YYYY-MM-DD format)
 * REV - The price incurred by the STB to lease the asset. (Price in US dollars and cents)
 * VIEW_TIME - The amount of time the STB played the asset.  (Time in hours:minutes)
 */
public class ConsumerTrackingData extends DataStoreObject implements  Comparable<ConsumerTrackingData>{

    private String stb = null;
    private String title = null;
    private String provider = null;
    private String  date = null;
    private String rev = null;
    private String view_time = null;

    private transient  Date date_time = null;
    private static transient List<String> compareFieldList = null;

    private ConsumerTrackingData(Builder builder){
        this.stb = builder.builder_stb;
        this.title = builder.builder_title;
        this.provider = builder.builder_provider;
        this.date = builder.builder_date;
        this.rev = builder.builder_rev;
        this.view_time = builder.builder_view_time;
        this.date_time = builder.builder_date_time;
    }

    public String getStb() {
        return stb;
    }

    public String getTitle() {
        return title;
    }

    public String getProvider() {
        return provider;
    }

    public String getDate() {
        return date;
    }

    public String getRev() {
        return rev;
    }

    public String getView_Time() {
        return view_time;
    }

    public Date getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        try {
            this.date_time = dateFormat.parse(this.date+"-"+view_time);
        }
        catch (ParseException pe) {
           System.out.println("Error: Failed to parse date and time.");
        }
        return this.date_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumerTrackingData)) {
            return false;
        }

        ConsumerTrackingData that = (ConsumerTrackingData) o;

        if (!getStb().equals(that.getStb())) {
            return false;
        }
        if (!getTitle().equals(that.getTitle())) {
            return false;
        }
        return getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        int result = getStb().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getDate().hashCode();
        return result;
    }

    public ConsumerTrackingData copy(){
        return new ConsumerTrackingData.Builder()
                .STB(stb)
                .TITLE(title)
                .PROVIDER(provider)
                .DATE(date)
                .REV(rev)
                .VIEW_TIME(view_time)
                .build();
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public String toPipedString() {
        return stb + "|" + title + "|" + provider + "|" + date + "|" + rev + "|" + view_time + "|";
    }
    public static class Builder {

        private String builder_stb = null;
        private String builder_title = null;
        private String builder_provider = null;
        private String builder_date = null;
        private String builder_rev = null;
        private String builder_view_time = null;
        private Date   builder_date_time = null;

        public Builder STB(String val){
            this.builder_stb = val;
            return this;
        }

        public Builder TITLE(String val){
            this.builder_title = val;
            return this;
        }

        public Builder PROVIDER(String val){
            this.builder_provider = val;
            return this;
        }

        public Builder DATE(String val){
            this.builder_date = val;
            return this;
        }
        public Builder REV(String val){
            this.builder_rev = val;
            return this;
        }
        public Builder VIEW_TIME(String val){
            this.builder_view_time = val;
            return this;
        }

        public ConsumerTrackingData build (){
            return new ConsumerTrackingData(this);
        }
    }

    @Override
    public int compareTo(ConsumerTrackingData data) {
        return compareToFromFieldList(data);
    }

    /**
     * Custom comparator based on the compareFieldList. The comparison
     * continues until the comparing fields aren't equal then it will
     * sort.
     *
     * @param data
     *     - ConsumerTrackingData object to compare to.
     * @return
     *     - Standard Compare result.
     */
    public int compareToFromFieldList(ConsumerTrackingData data) {
        int compareResult = 0;
        for(String compareField : compareFieldList){
            switch (compareField) {
                case "STB": {
                    compareResult = data.getStb().compareTo(this.getStb());
                    break;
                }
                case "TITLE": {
                    compareResult = data.getTitle().compareTo(this.getTitle());
                    break;
                }
                case "PROVIDER": {
                    compareResult = data.getProvider().compareTo(this.getProvider());
                    break;
                }
                case "DATE": {
                    compareResult = data.getDateTime().compareTo(this.getDateTime());
                    break;
                }
                case "REV": {
                    compareResult = data.getRev().compareTo(this.getRev());
                    break;
                }
                case "VIEW_TIME": {
                    compareResult = data.getView_Time().compareTo(this.getView_Time());
                    break;
                }
                default:{
                    break;
                }
            }
            if(0 != compareResult){
                break;
            }
        }
        return compareResult;
    }

    public static void setCompareFieldList(List<String> optionList){
        compareFieldList = optionList;
    }

}
