/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foro.networking;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Sigma
 */
public class Pack implements Serializable {
    // establecer el valor en caso de implementar la clase Serializable
    public static final long serialVersionUID = 1L;

    private String mNick, mTopic, mTitle, mDescription, mFileUrl, mDate, mKeyword,mComment;
    private File mImg; // guardara la imagen
   
    private int mPostId; // id de la relacion post de la base de datos
    private MyState mState;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (mTitle != null) builder.append(String.format(" %s\n", mTitle));
        if (mNick != null) builder.append(String.format(" Publicado por: %s\n", mNick));
        if (mTopic != null) builder.append(String.format("Topic: %s\n", mTopic));
        if (mDate != null) builder.append(String.format("Date: %s\n", mDate));
       
        
        
        if (mDescription != null) builder.append(String.format("Description: %s\n", 
                mDescription));
        if (mFileUrl != null) builder.append(String.format("File Url: %s\n", mFileUrl));
        
        if (mKeyword != null) builder.append(String.format("Keyword: %s\n", mKeyword));

        if (mImg != null) builder.append(String.format("img as file: %s\n", mImg));
        if (mPostId != 0) builder.append(String.format("Post id: %d\n", mPostId));
        if (mState != null) builder.append(String.format("State: %s\n", mState));

        return builder.toString();
    }
    
    public String listaPost(){
    StringBuilder builder = new StringBuilder();
    if (mTitle != null) builder.append(String.format(" %s\n", mTitle));
    if (mTopic != null) builder.append(String.format("  Categoria: %s\n", mTopic));
    if (mDate != null) builder.append(String.format("  Fecha: %s\n", mDate));
    if (mNick != null) builder.append(String.format("  Publicado por: %s\n", mNick));
    return builder.toString();
    }

    public Pack() { mComment="";}

    public Pack(MyState state) { mState = state;  mComment="";}

    public void addPost(String nick, String topic, String title, 
                String description) {
        mNick = nick;
        mTopic = topic;
        mTitle = title;
        mDescription = description;
    }

    public void addPost(String nick, String topic, String title, 
                        String description, File img) {
        addPost(nick, topic, title, description);
        mImg = img;
    }

    public void addPost(String nick, String topic, String title, 
                        String description, String imageUrl) {
        addPost(nick, topic, title, description);
        mFileUrl = imageUrl;
    }

    // setters =================================================================
    public void setPostId(int id) { mPostId = id; }
    public void setKeyword(String keyword) { mKeyword = keyword; }
    public void setTitle(String title) { mTitle = title; }
    public void setDate(String date) { mDate = date; }
    public void setFile(File file) { mImg = file; }
    public void setNick(String string) {  mNick=string; }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public void setmComment(String mComment) {
        this.mComment +="\n\n\n"+ mComment;
    }

    public void setmDescription(String mDescription) {     this.mDescription = mDescription; }
    
    
    
    public void setTopic(String topic){ mTopic=topic;}
    // getters =================================================================
    public MyState getState() { return mState; }

    public String getKeyword() { return mKeyword; }
    public String getNick() { return mNick; }
    public String getTopic() { return mTopic; }
    public String getTitle() { return mTitle; }
    public String getDescription() { return mDescription; }
    public String getFileUrl() { return mFileUrl; }
    public String getmComment() {
        return mComment;
    }

    public String getmDate() {
        return mDate;
    }
   
    public File getImage () { return mImg; }

    public int getPostId() { return mPostId; }

 

}
