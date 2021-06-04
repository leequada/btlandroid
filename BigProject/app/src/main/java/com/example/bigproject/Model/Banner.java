package com.example.bigproject.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Banner {

@SerializedName("id")
@Expose
private String id;
@SerializedName("image")
@Expose
private String image;
@SerializedName("link")
@Expose
private String link;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getLink() {
return link;
}

public void setLink(String link) {
this.link = link;
}

}