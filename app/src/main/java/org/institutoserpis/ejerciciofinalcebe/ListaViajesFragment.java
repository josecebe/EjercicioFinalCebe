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

        // Cargamos la lista de viajes
        loadViajes();

        // Cargamos la botonera de paginación
        loadBotonera();

        // Si se pulsa el botón Anterior...
        Button buttonPreviousPage = (Button) view.findViewById(R.id.previousPage);
        buttonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cargamos la página anterior
                previousPage();
            }
        });

        // Si se pulsa el botón Siguiente...
        Button buttonNextPage = (Button) view.findViewById(R.id.nextPage);
        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cargamos la página siguiente
                nextPage();
            }
        });

        // Si se pulsa el botón Nuevo...
        ImageButton imageButtonEdit = (ImageButton) view.findViewById(R.id.imageButton);
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cargamos el fragment ViajeFragment (para crear un nuevo viaje)
                newViaje();
            }
        });

        return view;
    }

    // Método que actualiza la lista de viajes
    private void loadViajes() {
        // Realizamos la consulta de viajes, cargamos 5 registros de la página page (por defecto page es 1)
        Map<Object, Object> data = helper.getPage("viaje", 5, page);
        ListView listViewViajes = (ListView) view.findViewById(R.id.listViewViajes);

        // Si la consulta no devuelve ningún registro, pero la página actual es superior a la 1...
        if (data.size() == 0 && page > 1) {
            // Restamos la página y volvemos a actualizar la lista de viajes
            page--;
            loadViajes();
            loadBotonera();
            return;
        }

        // TextView que se muestra solamente cuando no hay ningún registro en la tabla viaje
        TextView textViewNoViajes = (TextView) view.findViewById(R.id.textViewNoViajes);

        // Si la consulta no tiene ningún registro, y además la página actual es 1...
        if (data.size() == 0 && page <= 1) {
            // Mostramos el TextView que indica que la tabla no tiene viajes
            textViewNoViajes.setVisibility(View.VISIBLE);
            // Vaciamos el listView de viajes
            listViewViajes.setAdapter(null);
            // Return para romper la ejecución
            return;
        }

        // Si la ejecución del método llega hasta aquí, quiere decir que hay viajes para mostrar
        // Ocultamos el TextView que indica que no hay viajes
        textViewNoViajes.setVisibility(View.GONE);

        // Creamos el arraylist de lista de viajes
        listaViaje = new ArrayList<ListaViajes>();
        int i=0;
        for (i=0; i< data.size(); i++) {
            // Guardamos en mapas de datos cada registro
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

            // Creamos un nuevo item de la Lista de Viajes por cada registro de la consulta
            ListaViajes listaViajeItem = new ListaViajes(nombreOrigen, nombreDestino, fotoDestino, nombreClase, Integer.parseInt(pasajeros), Integer.parseInt(vuelta), Integer.parseInt(id));
            // Añadimos el item al arraylist de lista de viajes
            listaViaje.add(listaViajeItem);
        }

        // Creamos el adapter de lista de viajes (le pasamos el arraylist de lista de viajes)
        ListaViajesAdapter adapter;
        adapter = new ListaViajesAdapter(this.getActivity(), listaViaje);

        // Ponemos el adaptador al ListView de lista de viajes
        listViewViajes.setAdapter(adapter);

        // Obtenemos la altura del listView "abierto" (sin scroll)
        int altura = Integer.parseInt(getTotalHeightofListView(listViewViajes));
        listViewViajes.getLayoutParams().height = altura+20;

        // Cuando se hace click en un item del ListView...
        listViewViajes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Cargamos la vista detallada del viaje seleccionado
                TextView textViewId = (TextView) view.findViewById(R.id.idViaje);
                viewViaje(Integer.parseInt(textViewId.getText().toString()));
            }
        });

        // Preparamos el ListView para abrir un menú contextual al hacer un click largo (long click)
        registerForContextMenu(listViewViajes);
    }

    // Método para cargar la página siguiente de la lista de viajes
    public void nextPage() {
        page++;
        loadViajes();
        loadBotonera();
    }

    // Método para cargar la página anterior de la lista de viajes
    public void previousPage() {
        page--;
        loadViajes();
        loadBotonera();
    }

    // Método para cargar la botonera de paginación
    private void loadBotonera() {
        // Consultamos el número de páginas que tiene la tabla viaje si muestra 5 viajes por página
        int pages = helper.getPages("viaje", 5);

        LinearLayout botonera = (LinearLayout) view.findViewById(R.id.botonera);
        // Si hay más de una página...
        if (pages > 1) {
            // Se muestra la botonera de paginación
            botonera.setVisibility(View.VISIBLE);
            Button previousPage = (Button) view.findViewById(R.id.previousPage);
            Button nextPage = (Button) view.findViewById(R.id.nextPage);

            // Si estamos en una página superior a la 1 y el número de páginas actuales no es el mismo que la página actual...
            if (page > 1 && page != pages) {
                // Se activan los 2 botones (Anterior y siguiente)
                previousPage.setEnabled(true);
                nextPage.setEnabled(true);
            } else if (page > 1 && page == pages) {
                // Si estamos en una página superior a la 1 y el número de páginas actuales es el mismo que la página actual...
                // Sólo se activa el botón Anterior
                previousPage.setEnabled(true);
                nextPage.setEnabled(false);
            } else {
                // En el resto de ocasinoes se activa el botón Siguiente
                previousPage.setEnabled(false);
                nextPage.setEnabled(true);
            }
        } else {
            // Si no hay más de 1 página, no se muestra la botonera de paginación
            botonera.setVisibility(View.GONE);
        }
    }

    // Método para ir a la vista de un viaje (se le pasa el id del viaje como variable)
    private void viewViaje(int idViaje) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Nuevo fragment de ViajeView
        ViajeViewFragment viajeViewFragment = new ViajeViewFragment();
        // Bundle de datos para pasar el idViaje a la vista de viaje
        Bundle bundle = new Bundle();
        bundle.putInt("id", idViaje);
        viajeViewFragment.setArguments(bundle);

        // Reemplazamos el fragment actual por el nuevo fragment de ViajeView
        fragmentTransaction.replace(R.id.fragment, viajeViewFragment, "ViajeViewFragment");
        // Lo añadimos al Back Stack
        fragmentTransaction.addToBackStack("ViajeViewFragment");
        fragmentTransaction.commit();
    }

    // Método para eliminar un viaje (se le pasa el id del viaje como variable)
    private void deleteViaje(int idViaje) {
        helper.removeId("viaje", idViaje);
        // Actualizamos la lista de viajes
        loadViajes();
        loadBotonera();
    }

    // Método para cargar un viaje detalladamente (se le pasa el id del viaje como variable)
    private void newViaje() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Nuevo fragment de Viaje
        ViajeFragment viajeFragment = new ViajeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "new");
        viajeFragment.setArguments(bundle);

        // Reemplazamos el fragment actual por el nuevo fragment de Viaje
        fragmentTransaction.replace(R.id.fragment, viajeFragment, "ViajeFragment");
        // Lo añadimos al Back Stack
        fragmentTransaction.addToBackStack("ViajeFragment");
        fragmentTransaction.commit();
    }

    // Método para saber la altura de un ListView
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

    // Se abre un menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    // Cuando un item del menú contextual es seleccionado
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        int idViaje = listaViaje.get(index).getId();

        switch (item.getItemId()) {
            // Si es seleccionado el item Ver...
            case R.id.view:
                // Se carga detalladamente el viaje con id (idViaje)
                viewViaje(idViaje);
                return true;
            case R.id.delete:
                // Se carga elimina el viaje con id (idViaje)
                deleteViaje(idViaje);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
