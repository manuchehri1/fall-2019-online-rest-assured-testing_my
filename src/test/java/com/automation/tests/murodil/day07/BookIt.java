package com.automation.tests.murodil.day07;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookIt {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("clusters")
    @Expose
    private List<Cluster> clusters = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public BookIt() {
    }

    /**
     *
     * @param location
     * @param
     * @param clusters
     */
    public BookIt( String location, List<Cluster> clusters) {
        super();

        this.location = location;
        this.clusters = clusters;
    }

    public Integer getId() {
        return id;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    @Override
    public String toString() {
        return "BookIt{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", clusters=" + clusters +
                '}';
    }
}
