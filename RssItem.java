/**RssItem.java  
 * implements Serializable 
 * Author:      Didar Alam, m150084@usna.edu
 * Date:        12 APR 2014
 * Description:  An RssItem object to hold the data.
 *
 */

package edu.usna.cs.alam_project;
/**
 * RssItem.java
 * implements Serializable
 * Author: MIDN Alam
 * Date: 30 APR 2014
 * This activity is designed to create a custom RssItem and store the file.*/



import java.io.Serializable;


public class RssItem implements Serializable{
	
	private String link;
	private String pubDate;
	private String description;
	private String title;
	
	//constructor with no args
		public RssItem () {
			this.title = "Need to Update!";
			this.pubDate = "Click the Update botton to get started.";
			this.description = "Please Wait while downloading Your Feed Back!";
			this.link = "http://www.usna.edu/homepage.php";
		}
	
		/**Setters and getters*/
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return this.title;
	}
	
	

}

