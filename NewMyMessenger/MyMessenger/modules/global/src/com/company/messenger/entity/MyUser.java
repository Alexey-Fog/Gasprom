package com.company.messenger.entity;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity(name = "MyMessenger_MyUser")
@Extends(User.class)
public class MyUser extends User {
    @Column(name = "LAST_TIME_ONLINE")
    private LocalDateTime lastTimeOnline;


    public LocalDateTime getLastTimeOnline() {
        return lastTimeOnline;
    }

    public void setLastTimeOnline(LocalDateTime lastTimeOnline) {
        this.lastTimeOnline = lastTimeOnline;
    }
}