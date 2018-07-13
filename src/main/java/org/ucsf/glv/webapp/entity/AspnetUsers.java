package org.ucsf.glv.webapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "aspnet_Users")
public class AspnetUsers {

    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "ApplicationId", columnDefinition = "uniqueidentifier")
    private String applicationId;

    @Id
    @GenericGenerator(name = "generator", strategy = "guid", parameters = {})
    @GeneratedValue(generator = "generator")
    @Column(name = "UserId", columnDefinition = "uniqueidentifier")
    private String userId;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "LoweredUserName")
    private String loweredUserName;

    @Column(name = "MobileAlias")
    private String mobileAlias;

    @Column(name = "IsAnonymous")
    private boolean isAnonymous;

    @Column(name = "LastActivityDate")
    private Date lastActivityDate;

    public AspnetUsers() {

    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoweredUserName() {
        return loweredUserName;
    }

    public void setLoweredUserName(String loweredUserName) {
        this.loweredUserName = loweredUserName;
    }

    public String getMobileAlias() {
        return mobileAlias;
    }

    public void setMobileAlias(String mobileAlias) {
        this.mobileAlias = mobileAlias;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

}

// [ApplicationId] [uniqueidentifier] NOT NULL,
// [UserId] [uniqueidentifier] NOT NULL DEFAULT (newid()),
// [UserName] [nvarchar](256) NOT NULL,
// [LoweredUserName] [nvarchar](256) NOT NULL,
// [MobileAlias] [nvarchar](16) NULL DEFAULT (NULL),
// [IsAnonymous] [bit] NOT NULL DEFAULT ((0)),
// [LastActivityDate] [datetime]