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
	@Temporal(TemporalType.TIME)
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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Payload payload = (Payload) o;

		if (ref != null ? !ref.equals(payload.ref) : payload.ref != null)
			return false;
		if (before != null ? !before.equals(payload.before) : payload.before != null)
			return false;
		if (after != null ? !after.equals(payload.after) : payload.after != null)
			return false;
		if (commits != null ? !commits.equals(payload.commits) : payload.commits != null)
			return false;
		return !(receivedTimestamp != null ? !receivedTimestamp.equals(payload.receivedTimestamp)
				: payload.receivedTimestamp != null);

	}

	@Override
	public int hashCode() {
		int result = ref != null ? ref.hashCode() : 0;
		result = 31 * result + (before != null ? before.hashCode() : 0);
		result = 31 * result + (after != null ? after.hashCode() : 0);
		result = 31 * result + (commits != null ? commits.hashCode() : 0);
		result = 31 * result + (receivedTimestamp != null ? receivedTimestamp.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Payload{" + "id=" + id + ", receivedTimestamp=" + receivedTimestamp + ", ref='" + ref + '\''
				+ ", before='" + before + '\'' + ", after='" + after + '\'' + ", commits=" + commits + '}';
	}
}
