package org.institutoserpis.ejerciciofinalcebe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class ListaViajesFragment extends Fragment {
    static public final String packagename = ListaViajesFragment.class.getPackage().getName();
    DrawerLayout drawerLayout;
    View view;

    SQLiteHelper helper;
    int page = 1;

    ArrayList<ListaViajes> listaViaje;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_lista_viajes_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        loadViajes();
        loadBotonera();

        Button buttonPreviousPage = (Button) view.findViewById(R.id.previousPage);

        buttonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        Button buttonNextPage = (Button) view.findViewById(R.id.nextPage);

        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        ImageButton imageButtonEdit = (ImageButton) view.findViewById(R.id.imageButton);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newViaje();
            }
        });

        return view;
    }

    private void loadViajes() {
        Map<Object, Object> data = helper.getPage("viaje", 5, page);
        ListView listViewViajes = (ListView) view.findViewById(R.id.listViewViajes);

        if (data.size() == 0 && page > 1) {
            page--;
            loadViajes();
            loadBotonera();
            return;
        }

        TextView textViewNoViajes = (TextView) view.findViewById(R.id.textViewNoViajes);

        if (data.size() == 0 && page <= 1) {
            textViewNoViajes.setVisibility(View.VISIBLE);
            listViewViajes.setAdapter(null);
            return;
        }

        textViewNoViajes.setVisibility(View.GONE);

        listaViaje = new ArrayList<ListaViajes>();
        int i=0;
        for (i=0; i< data.size(); i++) {
            Map<Object, Object> viaje = (Map<Object, Object>) data.get(i);
            Map<Object, Object> origen = (Map<Object, Object>) viaje.get("id_ciudad_1");
            Map<Object, Object> destino = (Map<Object, Object>) viaje.get("id_ciudad_2");
            Map<Object, Object> clase = (Map<Object, Object>) viaje.get("id_claseviaje");
            Map<Object, Object> usuario = (Map<Object, Object>) viaje.get("id_usuario");

            Map<Object, Object> ciudadOrigen = (Map<Object, Object>) origen.get(0);
            Map<Object, Object> ciudadDestino = (Map<Object, Object>) destino.get(0);
            Map<Object, Object> claseViaje = (Map<Object, Object>) clase.get(0);


            String nombreOrigen = ciudadOrigen.get("diminutivo").toString();
            String nombreDestino = ciudadDestino.get("diminutivo").toString();
            String fotoDestino = "";
            try {
                fotoDestino = ciudadDestino.get("s_foto").toString();
            } catch (Exception e) {}
            String nombreClase = claseViaje.get("descripcion").toString();
            String pasajeros = viaje.get("pasajeros").toString();
            String vuelta = viaje.get("vuelta").toString();
            String id = viaje.get("id").toString();

            ListaViajes listaViajeItem = new ListaViajes(nombreOrigen, nombreDestino, fotoDestino, nombreClase, Integer.parseInt(pasajeros), Integer.parseInt(vuelta), Integer.parseInt(id));
            listaViaje.add(listaViajeItem);
        }

        ListaViajesAdapter adapter;
        adapter = new ListaViajesAdapter(this.getActivity(), listaViaje);

        listViewViajes.setAdapter(adapter);

        int altura = Integer.parseInt(getTotalHeightofListView(listViewViajes));
        listViewViajes.getLayoutParams().height = altura+20;

        listViewViajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewId = (TextView) view.findViewById(R.id.idViaje);
                viewViaje(Integer.parseInt(textViewId.getText().toString()));
            }
        });

        registerForContextMenu(listViewViajes);

    }

    public void nextPage() {
        page++;
        loadViajes();
        loadBotonera();
    }

    public void previousPage() {
        page--;
        loadViajes();
        loadBotonera();
    }

    private void loadBotonera() {
        int pages = helper.getPages("viaje", 5);
        LinearLayout botonera = (LinearLayout) view.findViewById(R.id.botonera);
        if (pages > 1) {
            botonera.setVisibility(View.VISIBLE);
            Button previousPage = (Button) view.findViewById(R.id.previousPage);
            Button nextPage = (Button) view.findViewById(R.id.nextPage);

            if (page > 1 && page != pages) {
                previousPage.setEnabled(true);
                nextPage.setEnabled(true);
            } else if (page > 1 && page == pages) {
                previousPage.setEnabled(true);
                nextPage.setEnabled(false);
            } else {
                previousPage.setEnabled(false);
                nextPage.setEnabled(true);
            }
        } else {
            botonera.setVisibility(View.GONE);
        }
    }

    private void viewViaje(int idViaje) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ViajeViewFragment viajeViewFragment = new ViajeViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", idViaje);
        viajeViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, viajeViewFragment, "ViajeViewFragment");
        fragmentTransaction.addToBackStack("ViajeViewFragment");
        fragmentTransaction.commit();
    }

    private void deleteViaje(int idViaje) {
        helper.removeId("viaje", idViaje);
        loadViajes();
        loadBotonera();
    }

    private void newViaje() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ViajeFragment viajeFragment = new ViajeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "new");
        viajeFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, viajeFragment, "ViajeFragment");
        fragmentTransaction.addToBackStack("ViajeFragment");
        fragmentTransaction.commit();
    }

    public static String getTotalHeightofListView(ListView listView) {
        String altura = "";
        ListAdapter mAdapter = listView.getAdapter();

        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            altura = String.valueOf(totalHeight);

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

        return altura;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        int idViaje = listaViaje.get(index).getId();

        switch (item.getItemId()) {
            case R.id.view:
                viewViaje(idViaje);
                return true;
            case R.id.delete:
                deleteViaje(idViaje);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void showToast(String text) {
        /*Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }
}
