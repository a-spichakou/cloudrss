package app.engine.rss.shared.dto;

import java.io.Serializable;

public class ItemDTO extends HasDummyEmpty<ItemDTO>  implements Serializable{
	private static final long serialVersionUID = 2381291980613172812L;
	public static ItemDTO EMPTY = new ItemDTO();

	private Long id;

	private String title;
	private String guid;
	private String link;
	private String description;
	private String pubDate;
	private String author;
	private Boolean read;
	private Long feedId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public Long getFeedId() {
		return feedId;
	}
	public void setFeedId(Long feedId) {
		this.feedId = feedId;
	}
	public ItemDTO getEmpty() {		
		return EMPTY;
	}
}
