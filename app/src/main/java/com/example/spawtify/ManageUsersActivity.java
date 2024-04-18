package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.databinding.ActivityManageUsersBinding;
import com.example.spawtify.viewHolders.UserAdapter;
import com.example.spawtify.viewHolders.UserViewModel;

/** ManageUsersActivity:
 * Display for admin to view the current user list (non-admins)
 * Admin can delete users or upgrade a user to admin
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */

public class ManageUsersActivity extends AppCompatActivity {

    private ActivityManageUsersBinding binding;
    private SpawtifyRepository repository;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Reference to the the songViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Adds recycler view
        RecyclerView recyclerView = binding.UserlistRecyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = SpawtifyRepository.getRepository(getApplication());

        //adding an observer
        userViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
        });

        wireUpDisplay();
    }

    private void wireUpDisplay() {
        binding.MakeAdminButton.setOnClickListener(v -> makeAdmin());
        binding.DeleteUserButton.setOnClickListener(v -> deleteUser());
        binding.FinishManageUsersButton.setOnClickListener(v -> finishManaging());
    }

    private void makeAdmin() {
        binding.ManageUsersDescriptionTextview.setText(R.string.click_on_a_user_to_make_admin);
        binding.ManageUsersDescriptionTextview.setVisibility(View.VISIBLE);
        binding.MakeAdminButton.setVisibility(View.INVISIBLE);
        binding.DeleteUserButton.setVisibility(View.INVISIBLE);
        binding.FinishManageUsersButton.setVisibility(View.VISIBLE);
    }

    private void deleteUser() {
        binding.ManageUsersDescriptionTextview.setText(R.string.click_on_a_user_to_delete);
        binding.ManageUsersDescriptionTextview.setVisibility(View.VISIBLE);
        binding.MakeAdminButton.setVisibility(View.INVISIBLE);
        binding.DeleteUserButton.setVisibility(View.INVISIBLE);
        binding.FinishManageUsersButton.setVisibility(View.VISIBLE);
    }

    private void finishManaging() {
        binding.ManageUsersDescriptionTextview.setVisibility(View.INVISIBLE);
        binding.MakeAdminButton.setVisibility(View.VISIBLE);
        binding.DeleteUserButton.setVisibility(View.VISIBLE);
        binding.FinishManageUsersButton.setVisibility(View.INVISIBLE);
    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, ManageUsersActivity.class);
        return intent;
    }
}