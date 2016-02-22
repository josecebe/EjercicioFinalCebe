package org.institutoserpis.ejerciciofinalcebe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class ListaCiudadesFragment extends Fragment {
    View view;

    SQLiteHelper helper;
    int page = 1;

    ArrayList<ListaCiudades> listaCiudades;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_lista_ciudades_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        loadCiudades();
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
                newCiudad();
            }
        });

        return view;
    }

    private void loadCiudades() {
        Map<Object, Object> data = helper.getPage("ciudad", 5, page);
        ListView listViewCiudades= (ListView) view.findViewById(R.id.listViewCiudades);

        if (data.size() == 0 && page > 1) {
            page--;
            loadCiudades();
            loadBotonera();
            return;
        }

        TextView textViewNoCiudades = (TextView) view.findViewById(R.id.textViewNoCiudades);

        if (data.size() == 0 && page <= 1) {
            textViewNoCiudades.setVisibility(View.VISIBLE);
            listViewCiudades.setAdapter(null);
            return;
        }

        textViewNoCiudades.setVisibility(View.GONE);

        listaCiudades = new ArrayList<ListaCiudades>();
        int i=0;
        for (i=0; i< data.size(); i++) {
            Map<Object, Object> ciudad = (Map<Object, Object>) data.get(i);

            int id = Integer.parseInt(ciudad.get("id").toString());
            String nombre = ciudad.get("nombre").toString();
            String diminutivo = ciudad.get("diminutivo").toString();
            String foto = "", s_foto = "";

            try {
                foto = ciudad.get("foto").toString();
                s_foto = ciudad.get("s_foto").toString();
            } catch (Exception e) {}

            ListaCiudades listaCiudadesItem = new ListaCiudades(nombre, diminutivo, foto, s_foto, id);
            listaCiudades.add(listaCiudadesItem);
        }

        ListaCiudadesAdapter adapter;
        adapter = new ListaCiudadesAdapter(this.getActivity(), listaCiudades);

        listViewCiudades.setAdapter(adapter);

        int altura = Integer.parseInt(getTotalHeightofListView(listViewCiudades));
        listViewCiudades.getLayoutParams().height = altura+20;

        listViewCiudades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewId = (TextView) view.findViewById(R.id.idCiudad);
                viewCiudad(Integer.parseInt(textViewId.getText().toString()));
            }
        });

        registerForContextMenu(listViewCiudades);

    }

    public void nextPage() {
        page++;
        loadCiudades();
        loadBotonera();
    }

    public void previousPage() {
        page--;
        loadCiudades();
        loadBotonera();
    }

    private void viewCiudad(int idCiudad) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        CiudadViewFragment ciudadViewFragment = new CiudadViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", idCiudad);
        ciudadViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, ciudadViewFragment, "CiudadViewFragment");
        fragmentTransaction.addToBackStack("CiudadViewFragment");
        fragmentTransaction.commit();
    }

    private void loadBotonera() {
        int pages = helper.getPages("ciudad", 5);
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

    private void deleteCiudad(int idCiudad) {
        helper.removeId("ciudad", idCiudad);
        loadCiudades();
        loadBotonera();
    }

    private void newCiudad() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        CiudadFragment ciudadFragment = new CiudadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "new");
        ciudadFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, ciudadFragment, "CiudadFragment");
        fragmentTransaction.addToBackStack("CiudadFragment");
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
        int idCiudad = listaCiudades.get(index).getId();

        switch (item.getItemId()) {
            case R.id.view:
                viewCiudad(idCiudad);
                return true;
            case R.id.delete:
                deleteCiudad(idCiudad);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void showToast(String text) {
        /*Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }
}
