package com.example.netstuff.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class UserSecret implements UserInterface<User> {


    private User user = new User();
    private List<User> users = new ArrayList<>(4);
    private List<String> secrets = new ArrayList<>(4);

    public UserSecret() throws UnknownHostException {
        users.add(new User("foo", "bar"));
    }

    @Override
    public List<String> getAll() {
        return null;
    }

    @Override
    public InetAddress getAddress() {
        return null;
    }

    @Override
    public String getSecret() {
        return secrets.get(user.getId());
    }

    @Override
    public String randomSecret() {
        Random r = new Random(users.size());
        User randomUser = users.get(r.nextInt());
        if (randomUser.getSecretWord() != user.getSecretWord()) {
            user.setSecretWord(randomUser.getSecretWord());
        }
        return null;
    }

    @Override
    public void addSecret() {
        secrets.add(user.getSecretWord());
    }

    @Override
    public void clearSecrets() {
        secrets.clear();
    }


}

