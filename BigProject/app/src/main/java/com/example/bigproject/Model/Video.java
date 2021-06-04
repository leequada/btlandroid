package com.example.bigproject.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Video implements Serializable {

@SerializedName("IdVideo")
@Expose
private String idVideo;
@SerializedName("TitleVideo")
@Expose
private String titleVideo;
@SerializedName("LinkVideo")
@Expose
private String linkVideo;
@SerializedName("TagVideo")
@Expose
private String tagVideo;
@SerializedName("LikesVideo")
@Expose
private String likesVideo;
@SerializedName("IdUser")
@Expose
private String idUser;

public String getIdVideo() {
return idVideo;
}

public void setIdVideo(String idVideo) {
this.idVideo = idVideo;
}

public String getTitleVideo() {
return titleVideo;
}

public void setTitleVideo(String titleVideo) {
this.titleVideo = titleVideo;
}

public String getLinkVideo() {
return linkVideo;
}

public void setLinkVideo(String linkVideo) {
this.linkVideo = linkVideo;
}

public String getTagVideo() {
return tagVideo;
}

public void setTagVideo(String tagVideo) {
this.tagVideo = tagVideo;
}

public String getLikesVideo() {
return likesVideo;
}

public void setLikesVideo(String likesVideo) {
this.likesVideo = likesVideo;
}

public String getIdUser() {
return idUser;
}

public void setIdUser(String idUser) {
this.idUser = idUser;
}

}