package uio.androidbootcamp.storagesharedpreferences.handlers;


import java.util.ArrayList;
import java.util.List;

import uio.androidbootcamp.storagesharedpreferences.constants.Constants;
import uio.androidbootcamp.storagesharedpreferences.models.User;
import uio.androidbootcamp.storagesharedpreferences.services.SharedPreferencesService;

/**
 * Created by jrodri on 6/7/17.
 */

public class RegisterUserHandler {

    private SharedPreferencesService sharedPreferencesService;
    private List<User> users;
    List<User> retrievedUsers;

    public RegisterUserHandler(SharedPreferencesService sharedPreferencesService) {
        this.sharedPreferencesService = sharedPreferencesService;
        users = getUsers();
        retrievedUsers = sharedPreferencesService.getUsersFromSharedPreferences(Constants.USERS_KEY);
        if (retrievedUsers == null) {
            retrievedUsers = new ArrayList<>();
            sharedPreferencesService.saveUsersInSharedPreferences(Constants.USERS_KEY, retrievedUsers);
        }
    }

    private boolean saveInSharedPreferences(User user) {
        if (retrievedUsers.contains(user)) {
            return false;
        } else {
            retrievedUsers.add(user);
            sharedPreferencesService.saveUsersInSharedPreferences(Constants.USERS_KEY, retrievedUsers);
            retrievedUsers = sharedPreferencesService.getUsersFromSharedPreferences(Constants.USERS_KEY);
            return true;
        }
    }

    private boolean removeFromSharedPreferences(User user) {
        if (retrievedUsers.contains(user)) {
            retrievedUsers.remove(user);
            sharedPreferencesService.saveUsersInSharedPreferences(Constants.USERS_KEY, retrievedUsers);
            retrievedUsers = sharedPreferencesService.getUsersFromSharedPreferences(Constants.USERS_KEY);
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAllUsers() {
        return retrievedUsers;
    }

    private List<User> getUsers() {
        User user1 = new User("Jorge", "jorgito", "password1", 18);
        User user2 = new User("Luz", "luzma", "password2", 21);
        User user3 = new User("Diego", "dieguito", "password3", 24);
        User user4 = new User("Carlos", "carlitos", "password4", 48);
        User user5 = new User("Sebastian", "sebitas", "password8", 14);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        return users;
    }

    private User getUserToSave() {
        int minimumValue = 0;
        int maximumValue = users.size();
        int randomNum = minimumValue + (int) (Math.random() * maximumValue);
        return users.get(randomNum);
    }

    public boolean saveRandomUser() {
        return saveInSharedPreferences(getUserToSave());
    }

    public boolean removeRandomUser() {
        return removeFromSharedPreferences(getUserToSave());
    }
}
