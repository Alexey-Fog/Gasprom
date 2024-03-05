package com.company.messenger.web.screens.message;

import com.company.messenger.entity.Message;
import com.company.messenger.entity.MyUser;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.time.LocalDateTime;

@UiController("MyMessenger_MessengerScreen")
@UiDescriptor("messenger-screen.xml")
@LoadDataBeforeShow
public class MessengerScreen extends Screen {
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private CollectionLoader<Message> messagesDl;
    @Inject
    private CollectionLoader<MyUser> usersDl;


    @Inject
    private GroupTable<User> userTable;
    @Inject
    private TextArea messageText;
    @Inject
    private Button saveBtn;
    @Inject
    private Label login;

    private User userSender;
    private String recipientLogin;

    @Subscribe
    public void onInit(InitEvent event) {
        usersDl.setParameter("anonymous", "anonymous");
        userSender = userSessionSource.getUserSession().getUser();
        messagesDl.addPreLoadListener(e -> {
            if(userTable.getSelected().isEmpty()) {
                e.preventLoad();
            }
        });
        messagesDl.setParameter("user", userSender);
        userTable.addSelectionListener(userSelectionEvent -> {
            userTable.getSelected().stream()
                    .findFirst()
                    .ifPresent(user -> {
                        recipientLogin = user.getLogin();
                        login.setValue(recipientLogin);
                        messagesDl.setParameter("login", recipientLogin);
                        messagesDl.load();
                    });
            messagesDl.load();
        });
        saveBtn.addClickListener(clickEvent -> {
            String newMessage = messageText.getValue().toString();
            if (newMessage != null && !newMessage.isEmpty()) {
                Message mess = dataManager.create(Message.class);
                mess.setSender(userSender);
                MyUser userRecipient = dataManager.load(
                        LoadContext.create(MyUser.class).setQuery(
                                LoadContext
                                        .createQuery("select u from MyMessenger_MyUser u where u.login like :ulogin")
                                        .setParameter("ulogin", recipientLogin)
                        )
                );
                mess.setRecipient(userRecipient);
                mess.setDateTime(LocalDateTime.now());
                mess.setMessage(newMessage);
                dataManager.commit(mess);
                messageText.clear();
                messagesDl.load();
            }
        });

        //table   .addSelectionListener()
    }

//    @Install(to = "usersDl", target = Target.DATA_LOADER)
//    private List<UserSessionEntity> usersDlLoadDelegate(LoadContext<UserSessionEntity> loadContext) {
//        return List.copyOf(userSessionService.loadUserSessionEntities(UserSessionService.Filter.ALL));
//    }

    @Inject
    private DataManager dataManager;
}