package com.zfl.mapd721_p1;

public class SearchResultModel {

    private String id;
    private String alias;
    private String name;
    private String image_url;
    private Boolean is_closed;
    private String url;
    private Integer review_count;
    private Float rating;
    private Object coordinates;
    private String location;
    private String price;

    public SearchResultModel() {
    }

    public SearchResultModel(String id, String alias, String name, String image_url, Boolean is_closed, String url, Integer review_count, Float rating, Object coordinates, String location, String price) {
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.image_url = image_url;
        this.is_closed = is_closed;
        this.url = url;
        this.review_count = review_count;
        this.rating = rating;
        this.coordinates = coordinates;
        this.location = location;
        this.price = price;
    }

    @Override
    public String toString() {

        return "id='" + id + '\'' +
                ", name='" + name + '\'';

//        return "SearchResultModel{" +
//                "id='" + id + '\'' +
//                ", alias='" + alias + '\'' +
//                ", name='" + name + '\'' +
//                ", image_url='" + image_url + '\'' +
//                ", is_closed=" + is_closed +
//                ", url='" + url + '\'' +
//                ", review_count=" + review_count +
//                ", rating=" + rating +
//                ", coordinates=" + coordinates +
//                ", location='" + location + '\'' +
//                ", price='" + price + '\'' +
//                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Boolean getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(Boolean is_closed) {
        this.is_closed = is_closed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReview_count() {
        return review_count;
    }

    public void setReview_count(Integer review_count) {
        this.review_count = review_count;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
