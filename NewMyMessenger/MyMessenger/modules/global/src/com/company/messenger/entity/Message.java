package com.company.messenger.entity;

import com.esotericsoftware.kryo.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "MY_MESSENGER_MESSAGE")
@Entity(name = "MyMessenger_Message")
public class Message extends StandardEntity {
    private static final long serialVersionUID = -6398934763283047873L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SENDER_ID", nullable = false)
    @OnDeleteInverse(DeletePolicy.DENY)
    protected User sender;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_RECIPIENT_ID", nullable = false)
    @OnDeleteInverse(DeletePolicy.DENY)
    protected MyUser recipient;

    @NotNull
    @Column(name = "MESSAGE", length = 255, nullable = false)
    protected String message;

    @NotNull
    @Column(name = "DATE_TIME", nullable = false)
    protected LocalDateTime dateTime;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public MyUser getRecipient() {
        return recipient;
    }

    public void setRecipient(MyUser recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}