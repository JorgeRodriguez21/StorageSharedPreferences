package uio.androidbootcamp.storagesharedpreferences.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

import uio.androidbootcamp.storagesharedpreferences.R;
import uio.androidbootcamp.storagesharedpreferences.handlers.RegisterUserHandler;
import uio.androidbootcamp.storagesharedpreferences.models.User;
import uio.androidbootcamp.storagesharedpreferences.services.SharedPreferencesService;

public class MainActivity extends ListActivity {

    private RegisterUserHandler registerUserHandler;
    private SharedPreferencesService sharedPreferencesService;
    private List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferencesService = new SharedPreferencesService(this);
        registerUserHandler = new RegisterUserHandler(sharedPreferencesService);
        this.users = registerUserHandler.getAllUsers();
        initializeElements();
    }

    public void initializeElements(){

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, users);
        setListAdapter(adapter);

    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<User> adapter = (ArrayAdapter<User>) getListAdapter();
        switch (view.getId()) {
            case R.id.button_add:
                boolean isUserSaved = registerUserHandler.saveRandomUser();
                if(isUserSaved) {
                    this.users = registerUserHandler.getAllUsers();
                    initializeElements();
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.user_exists, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_delete:
                boolean isUserDeleted = registerUserHandler.removeRandomUser();
                if(isUserDeleted) {
                    this.users = registerUserHandler.getAllUsers();
                    initializeElements();
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(), R.string.user_does_not_exist, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

}
