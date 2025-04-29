package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity5 extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private CarFragment carFragment;
    private OrderFragment orderFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //默认首页选中
        selcetedFragment(0);

        //点击切换事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("Navigation", "Item clicked: " + item.getItemId());
                if(item.getItemId()==R.id.home){
                    selcetedFragment(0);
                }
                else if(item.getItemId()==R.id.car){
                    selcetedFragment(1);
                }
                else if(item.getItemId()==R.id.order){
                    selcetedFragment(2);
                }
                else {
                    selcetedFragment(3);
                }
                return true;
            }
        });
    }

    private void selcetedFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if (position == 0) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.content, homeFragment);
            } else {
                fragmentTransaction.show(homeFragment);
            }
        }
        if (position == 1) {
            if (carFragment == null) {
                carFragment = new CarFragment();
                fragmentTransaction.add(R.id.content, carFragment);
            } else {
                fragmentTransaction.show(carFragment);
            }
        }

        if (position == 2) {
            if (orderFragment == null) {
                orderFragment = new OrderFragment();
                fragmentTransaction.add(R.id.content, orderFragment);
            } else {
                fragmentTransaction.show(orderFragment);
            }
        }
        if (position == 3) {
            if (mineFragment == null) {
                mineFragment = new MineFragment();
                fragmentTransaction.add(R.id.content, mineFragment);
            } else {
                fragmentTransaction.show(mineFragment);
            }

        }
        //一定要提交
        fragmentTransaction.commit();
    }


    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (carFragment != null) {
            fragmentTransaction.hide(carFragment);
        }
        if (orderFragment != null) {
            fragmentTransaction.hide(orderFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }
}

