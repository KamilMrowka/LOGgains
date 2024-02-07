package com.log.gains.user;

public class UserDao {
    private final UserRepository repository;

    public UserDao(UserRepository repository) {
        this.repository = repository;
    }

    public Long getUserIdByUsername (String username)  {
        return repository.findByUsername(username).get().getId();
    }


}
