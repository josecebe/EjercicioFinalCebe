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

        // Cargamos el navigation view y el toolbar
        setupNavigationView();
        setupToolbar();

        // Cuando un item del navigation view es seleccionado...
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tablaViaje:
                        // Cerramos el navigation view
                        drawerLayout.closeDrawers();
                        // Cargamos el fragment 0
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

        // Por defecto cargamos el fragment 0 (Lista de viajes)
        setFragment(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // Método para cargar el toolbar
    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // Método para cargar el navigation view
    private void setupNavigationView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    // Cuando se pulsa el botón menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Se abre el navigation view
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Método para cargar los fragmentos
    public void setFragment(int position) {
        FragmentTransaction fragmentTransaction;
        switch (position) {
            // Cargando fragmento Lista de viajes
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListaViajesFragment listaViajesFragment = new ListaViajesFragment();
                fragmentTransaction.replace(R.id.fragment, listaViajesFragment, "ListaViajesFragment");
                fragmentTransaction.addToBackStack("ListaViajesFragment");
                fragmentTransaction.commit();
                break;
            // Cargando fragmento Lista clases de viaje
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListaClasesviajeFragment clasesviajeFragment = new ListaClasesviajeFragment();
                fragmentTransaction.replace(R.id.fragment, clasesviajeFragment, "ListaClasesviajeFragment");
                fragmentTransaction.addToBackStack("ListaClasesviajeFragment");
                fragmentTransaction.commit();
                break;
            // Cargando fragmento Lista de usuarios
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                ListaUsuariosFragment listaUsuariosFragment = new ListaUsuariosFragment();
                fragmentTransaction.replace(R.id.fragment, listaUsuariosFragment, "ListaUsuariosFragment");
                fragmentTransaction.addToBackStack("ListaUsuariosFragment");
                fragmentTransaction.commit();
                break;
            // Cargando fragmento Lista de ciudades
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

    // Cuando se pulsa el botón atrás...
    @Override
    public void onBackPressed(){
        // Y hay fragmentos "cargados"...
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Se cierra el último fragment
            fragmentManager.popBackStack();
        } else {
            // Si sólo hay un fragmento... se cierra la actividad actual (se cierra MenuAtivity, y por lo tanto también se cierra la aplicación)
            super.onBackPressed();
        }
    }
}
