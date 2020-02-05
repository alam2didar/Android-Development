/**
 * ListItem.java
 * Author: MIDN Alam
 * Date: 30 APR 2014
 * This activity is designed to create a custom List Item.*/

package edu.usna.cs.alam_project;

public class ListItem {
	
	private int imageId;
    private String title;
    private String desc;
     
    public ListItem(int imageId, String title, String desc) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + desc;
    }   
	
	

}
