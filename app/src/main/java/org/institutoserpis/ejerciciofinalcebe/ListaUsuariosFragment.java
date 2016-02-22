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

public class ListaUsuariosFragment extends Fragment {
    static public final String packagename = ListaUsuariosFragment.class.getPackage().getName();
    DrawerLayout drawerLayout;
    View view;

    SQLiteHelper helper;
    int page = 1;

    ArrayList<ListaUsuarios> listaUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_lista_usuarios_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        loadUsuarios();
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
                newUsuario();
            }
        });

        return view;
    }

    private void loadUsuarios() {
        Map<Object, Object> data = helper.getPage("usuario", 5, page);
        ListView listViewUsuarios = (ListView) view.findViewById(R.id.listViewUsuarios);

        if (data.size() == 0 && page > 1) {
            page--;
            loadUsuarios();
            loadBotonera();
            return;
        }

        TextView textViewNoUsuarios = (TextView) view.findViewById(R.id.textViewNoUsuarios);

        if (data.size() == 0 && page <= 1) {
            textViewNoUsuarios.setVisibility(View.VISIBLE);
            listViewUsuarios.setAdapter(null);
            return;
        }

        textViewNoUsuarios.setVisibility(View.GONE);

        listaUsuario = new ArrayList<ListaUsuarios>();
        int i=0;
        for (i=0; i< data.size(); i++) {
            Map<Object, Object> usuario = (Map<Object, Object>) data.get(i);

            int id = Integer.parseInt(usuario.get("id").toString());
            String nombreUsuario = usuario.get("usuario").toString();
            String password = usuario.get("password").toString();

            ListaUsuarios listaUsuarioItem = new ListaUsuarios(nombreUsuario, password, id);
            listaUsuario.add(listaUsuarioItem);
        }

        ListaUsuariosAdapter adapter;
        adapter = new ListaUsuariosAdapter(this.getActivity(), listaUsuario);

        listViewUsuarios.setAdapter(adapter);

        int altura = Integer.parseInt(getTotalHeightofListView(listViewUsuarios));
        listViewUsuarios.getLayoutParams().height = altura+20;

        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewId = (TextView) view.findViewById(R.id.idUsuario);
                viewUsuario(Integer.parseInt(textViewId.getText().toString()));
            }
        });

        registerForContextMenu(listViewUsuarios);

    }

    public void nextPage() {
        page++;
        loadUsuarios();
        loadBotonera();
    }

    public void previousPage() {
        page--;
        loadUsuarios();
        loadBotonera();
    }

    private void loadBotonera() {
        int pages = helper.getPages("usuario", 5);
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

    private void viewUsuario(int idUsuario) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        UsuarioViewFragment usuarioViewFragment = new UsuarioViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", idUsuario);
        usuarioViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, usuarioViewFragment, "UsuarioViewFragment");
        fragmentTransaction.addToBackStack("UsuarioViewFragment");
        fragmentTransaction.commit();
    }

    private void deleteUsuario(int idUsuario) {
        helper.removeId("usuario", idUsuario);
        loadUsuarios();
        loadBotonera();
    }

    private void newUsuario() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        UsuarioFragment usuarioFragment = new UsuarioFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "new");
        usuarioFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, usuarioFragment, "UsuarioFragment");
        fragmentTransaction.addToBackStack("UsuarioFragment");
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
        int idUsuario = listaUsuario.get(index).getId();

        switch (item.getItemId()) {
            case R.id.view:
                viewUsuario(idUsuario);
                return true;
            case R.id.delete:
                deleteUsuario(idUsuario);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void showToast(String text) {
        /*Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }
}
