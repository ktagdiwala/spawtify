package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.spawtify.databinding.ActivityHoldsFragmentsBinding;

public class HoldsFragments extends AppCompatActivity implements FragmentActionListener{

    ActivityHoldsFragmentsBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoldsFragmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();

        //  Sets up default fragment when activity is opened;
        replaceFragment(addHomeFragment(), "Home");

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.homeNavItem){
                replaceFragment(addHomeFragment(), "Home");
            }
            if(item.getItemId() == R.id.profileNavItem){
                replaceFragment(new ProfileFragment(), "Profile");
            }
            if(item.getItemId() == R.id.infoNavItem){
                replaceFragment(new InfoFragment(), "Info");
            }

            return true;
        });
    }

    private Fragment addHomeFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setFragmentActionListener(this);

        fragmentTransaction.add(R.id.frameLayout, homeFragment);
        fragmentTransaction.commit();
        return homeFragment;
    }

    private void addBrowseSongsFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();

        BrowseSongsFragment browseSongsFragment = new BrowseSongsFragment();
        browseSongsFragment.setArguments(null);

        fragmentTransaction.replace(R.id.frameLayout, browseSongsFragment);
        fragmentTransaction.addToBackStack("Home");
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment, String name){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, HoldsFragments.class);
        return intent;
    }

    @Override
    public void onButtonPress() {
        toaster("Nooice!");
        addBrowseSongsFragment();
    }

    public void toaster(String message){
        Toast.makeText(this, "Nice", Toast.LENGTH_SHORT).show();
    }
}