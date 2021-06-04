package com.example.bigproject.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

@SerializedName("IdUser")
@Expose
private String idUser;
@SerializedName("NameUser")
@Expose
private String nameUser;
@SerializedName("AccountUser")
@Expose
private String accountUser;
@SerializedName("PasswordUser")
@Expose
private String passwordUser;
@SerializedName("ImageUser")
@Expose
private String imageUser;

public String getIdUser() {
return idUser;
}

public void setIdUser(String idUser) {
this.idUser = idUser;
}

public String getNameUser() {
return nameUser;
}

public void setNameUser(String nameUser) {
this.nameUser = nameUser;
}

public String getAccountUser() {
return accountUser;
}

public void setAccountUser(String accountUser) {
this.accountUser = accountUser;
}

public String getPasswordUser() {
return passwordUser;
}

public void setPasswordUser(String passwordUser) {
this.passwordUser = passwordUser;
}

public String getImageUser() {
return imageUser;
}

public void setImageUser(String imageUser) {
this.imageUser = imageUser;
}

}