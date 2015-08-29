package com.khoubyari.example.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ann on 5/13/15.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payload {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;

	
	@Column(name = "receivedTimestamp", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date receivedTimestamp;

	@Column
	private String ref;

	@Column
	private String before;

	@Column
	private String after;

	// TODO: write a test to populate the objects from database to test the for
	// referencial correctness
	@OneToMany(mappedBy = "payload")
	@Cascade(CascadeType.ALL)
	private List<Commit> commits;

	public Payload() {
		commits = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public List<Commit> getCommits() {
		return commits;
	}

	public void setCommits(List<Commit> commits) {
		this.commits = commits;
	}

	public Date getReceivedTimestamp() {
		return receivedTimestamp;
		//return receivedTimestamp.toDate();
	}

	public void setReceivedTimestamp(Date receivedTimestamp) {
		this.receivedTimestamp = receivedTimestamp;
		//this.receivedTimestamp = new LocalDateTime(receivedTimestamp);
	}

	public List<String> getAllFilesModified() {
		List<String> filesModified = new ArrayList<String>();

		for (Commit commit : commits) {
			filesModified.addAll(commit.getAdded());
			filesModified.addAll(commit.getModified());
			filesModified.addAll(commit.getRemoved());
		}

		return filesModified;
	}

	public void prepareCommitsForSave() {
		for (Commit commit : commits) {
			commit.setPayload(this);
		}
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((after == null) ? 0 : after.hashCode());
		result = prime * result + ((before == null) ? 0 : before.hashCode());
		result = prime * result + ((receivedTimestamp == null) ? 0 : receivedTimestamp.hashCode());
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payload other = (Payload) obj;
		if (after == null) {
			if (other.after != null)
				return false;
		} else if (!after.equals(other.after))
			return false;
		if (before == null) {
			if (other.before != null)
				return false;
		} else if (!before.equals(other.before))
			return false;
		if (receivedTimestamp == null) {
			if (other.receivedTimestamp != null)
				return false;
		} else if (!receivedTimestamp.equals(other.receivedTimestamp))
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payload{" + "id=" + id + ", receivedTimestamp=" + receivedTimestamp + ", ref='" + ref + '\''
				+ ", before='" + before + '\'' + ", after='" + after + '\'' + ", commits=" + commits + '}';
	}
}
