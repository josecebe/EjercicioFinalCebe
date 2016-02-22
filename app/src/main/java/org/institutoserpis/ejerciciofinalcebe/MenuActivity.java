package org.institutoserpis.ejerciciofinalcebe;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });

        setupNavigationView();
        setupToolbar();

        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tablaViaje:
                        drawerLayout.closeDrawers();
                        setFragment(0);
                        return true;
                    case R.id.tablaClaseViaje:
                        drawerLayout.closeDrawers();
                        setFragment(1);
                        return true;
                    case R.id.tablaUsuario:
                        drawerLayout.closeDrawers();
                        setFragment(2);
                        return true;
                    case R.id.tablaCiudad:
                        drawerLayout.closeDrawers();
                        setFragment(3);
                        return true;
                }
                return true;
            }
        });

        setFragment(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setFragment(int position) {
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListaViajesFragment listaViajesFragment = new ListaViajesFragment();
                fragmentTransaction.replace(R.id.fragment, listaViajesFragment, "ListaViajesFragment");
                fragmentTransaction.addToBackStack("ListaViajesFragment");
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListaClasesviajeFragment clasesviajeFragment = new ListaClasesviajeFragment();
                fragmentTransaction.replace(R.id.fragment, clasesviajeFragment, "ListaClasesviajeFragment");
                fragmentTransaction.addToBackStack("ListaClasesviajeFragment");
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListaUsuariosFragment listaUsuariosFragment = new ListaUsuariosFragment();
                fragmentTransaction.replace(R.id.fragment, listaUsuariosFragment, "ListaUsuariosFragment");
                fragmentTransaction.addToBackStack("ListaUsuariosFragment");
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListaCiudadesFragment listaCiudadesFragment = new ListaCiudadesFragment();
                fragmentTransaction.replace(R.id.fragment, listaCiudadesFragment, "ListaCiudadesFragment");
                fragmentTransaction.addToBackStack("ListaCiudadesFragment");
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
