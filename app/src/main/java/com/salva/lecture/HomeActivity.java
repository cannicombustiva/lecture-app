package com.salva.lecture;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.salva.lecture.Fragments.BookableFragment;
import com.salva.lecture.Fragments.HistoryFragment;
import com.salva.lecture.Fragments.ReservedFragment;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {
    private BookableFragment bookableFragment;
    private ReservedFragment reservedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Prenotazioni");
        setContentView(R.layout.activity_home);

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    reservedFragment.load(reservedFragment.getView());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        bookableFragment = new BookableFragment();
        reservedFragment = new ReservedFragment();
        adapter.addFragment(bookableFragment, "Prenotabili");
        adapter.addFragment(reservedFragment, "Prenotati");
        adapter.addFragment(new HistoryFragment(), "Cronologia");

        viewPager.setAdapter(adapter);


    }


   /* public void book(View view){
        String token = sharedData.getAccessToken();
        view.
        Call<UserAuth> res = restClient.getReservationService().book(token,)
        res.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (response.errorBody() != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<UserAuth>() {
                    }.getType();
                    UserAuth errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                    onLoginFailed(errorResponse.getExMessage());
                    return;
                }
                //SETTO IL TOKEN IN LOCALE
                sharedData.setAccessToken(response.body().token);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                _this.finish();
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Log.e("ERROR", "exception", t);
                onLoginFailed("Login fallito");
            }
        });
    }*/
}

class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

