package com.example.bigproject.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Notificationss {

@SerializedName("id")
@Expose
private String id;
@SerializedName("idUser")
@Expose
private String idUser;
@SerializedName("idClient")
@Expose
private String idClient;
@SerializedName("Content")
@Expose
private String content;
@SerializedName("isChecked")
@Expose
private String isChecked;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getIdUser() {
return idUser;
}

public void setIdUser(String idUser) {
this.idUser = idUser;
}

public String getIdClient() {
return idClient;
}

public void setIdClient(String idClient) {
this.idClient = idClient;
}

public String getContent() {
return content;
}

public void setContent(String content) {
this.content = content;
}

public String getIsChecked() {
return isChecked;
}

public void setIsChecked(String isChecked) {
this.isChecked = isChecked;
}

}