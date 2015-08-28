package com.khoubyari.example.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ann on 5/13/15.
 */
@Entity
@Table
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Script {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Id
    @Column
    private String scriptName;

    @Column(name = "scriptLastUpdated", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scriptLastUpdated;

    @Column
    private String data;

    public Script() {

    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public Date getScriptLastUpdated() {
        return scriptLastUpdated;
    }

    public void setScriptLastUpdated(Date scriptLastUpdated) {
        this.scriptLastUpdated = scriptLastUpdated;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Script script = (Script) o;

        if (scriptName != null ? !scriptName.equals(script.scriptName) : script.scriptName != null) return false;
        if (scriptLastUpdated != null ? !scriptLastUpdated.equals(script.scriptLastUpdated) : script.scriptLastUpdated != null)
            return false;
        return !(data != null ? !data.equals(script.data) : script.data != null);

    }

    @Override
    public int hashCode() {
        int result = scriptName != null ? scriptName.hashCode() : 0;
        result = 31 * result + (scriptLastUpdated != null ? scriptLastUpdated.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Script{" +
                "scriptName='" + scriptName + '\'' +
                ", scriptLastUpdated=" + scriptLastUpdated +
                ", data='" + data + '\'' +
                '}';
    }
}
