package com.example.week7_assignment2;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.week7_assignment2.databinding.ActivityMainBinding;
import com.example.week7_assignment2.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    MenuItem menuLogin;
    static public boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Menu menu = navigationView.getMenu();
        MenuItem menuLogin = menu.findItem(R.id.nav_slideshow);

        menuLogin.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if(isLogin) {
                    isLogin = false;
                    updateNavHeaderLogout();

                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.login){
            if(!isLogin) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);

                View dialogView;
                dialogView = (View) View.inflate(MainActivity.this, R.layout.alertdialog, null);

                dlg.setTitle("사용자 입력");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setView(dialogView);

                //ok 눌렀을 때
                dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isLogin = true;
                        EditText name = dialogView.findViewById(R.id.name);
                        EditText email = dialogView.findViewById(R.id.email);
                        RadioGroup group = dialogView.findViewById(R.id.radioGroup);

                        int selectedImg = 0;

                        if (group.getCheckedRadioButtonId() == R.id.radioButton)
                            selectedImg = 1;
                        else if (group.getCheckedRadioButtonId() == R.id.radioButton2)
                            selectedImg = 2;
                        else if (group.getCheckedRadioButtonId() == R.id.radioButton3)
                            selectedImg = 3;

                        updateNavHeaderLogin(name.getText().toString(), email.getText().toString(), selectedImg);

                    }
                });

                //취소 눌렀을 때
                dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "cancel action", Toast.LENGTH_SHORT).show();
                    }
                });


                dlg.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void updateNavHeaderLogin(String name, String email, int img) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewName = headerView.findViewById(R.id.name);
        TextView textViewEmail = headerView.findViewById(R.id.email);
        ImageView imageView = headerView.findViewById(R.id.imageView);

        //이름, 이메일, 이미지 설정
        textViewName.setText(name);
        textViewEmail.setText(email);
        switch (img) {
            case 1:
                imageView.setImageResource(R.mipmap.dog);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.cat);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.horse);
                break;
            default:
                break;
        }

        Menu menu = navigationView.getMenu();
        MenuItem menuLogin = menu.findItem(R.id.nav_slideshow);

        menuLogin.setTitle("Logout");
    }

    public void updateNavHeaderLogout() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewName = headerView.findViewById(R.id.name);
        TextView textViewEmail = headerView.findViewById(R.id.email);
        ImageView imageView = headerView.findViewById(R.id.imageView);

        Menu menu = navigationView.getMenu();
        MenuItem menuLogin = menu.findItem(R.id.nav_slideshow);

        menuLogin.setTitle("Login 해야함");

        //이름, 이메일, 이미지 기본값 설정
        textViewName.setText("Android Studio");
        textViewEmail.setText("Android.studio@android.com");
        imageView.setImageResource(R.mipmap.ic_launcher_round);
    }
}