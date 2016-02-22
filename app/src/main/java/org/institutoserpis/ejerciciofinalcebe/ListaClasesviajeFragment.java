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

public class ListaClasesviajeFragment extends Fragment {
    View view;

    SQLiteHelper helper;
    int page = 1;

    ArrayList<ListaClasesviaje> listaClaseviaje;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_lista_clasesviaje_fragment, container, false);
        helper = new SQLiteHelper(getActivity(), "DBCebe", null, 1);

        loadClasesviaje();
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
                newClaseviaje();
            }
        });

        return view;
    }

    private void loadClasesviaje() {
        Map<Object, Object> data = helper.getPage("claseviaje", 5, page);
        ListView listViewClasesviaje = (ListView) view.findViewById(R.id.listViewClasesviaje);

        if (data.size() == 0 && page > 1) {
            page--;
            loadClasesviaje();
            loadBotonera();
            return;
        }

        TextView textViewNoClases = (TextView) view.findViewById(R.id.textViewNoClases);

        if (data.size() == 0 && page <= 1) {
            textViewNoClases.setVisibility(View.VISIBLE);
            listViewClasesviaje.setAdapter(null);
            return;
        }

        textViewNoClases.setVisibility(View.GONE);

        listaClaseviaje = new ArrayList<ListaClasesviaje>();
        int i=0;
        for (i=0; i< data.size(); i++) {
            Map<Object, Object> claseviaje = (Map<Object, Object>) data.get(i);

            int id = Integer.parseInt(claseviaje.get("id").toString());
            String descripcion = claseviaje.get("descripcion").toString();

            ListaClasesviaje listaClaseviajeItem = new ListaClasesviaje(descripcion, id);
            listaClaseviaje.add(listaClaseviajeItem);
        }

        ListaClasesviajeAdapter adapter;
        adapter = new ListaClasesviajeAdapter(this.getActivity(), listaClaseviaje);

        listViewClasesviaje.setAdapter(adapter);

        int altura = Integer.parseInt(getTotalHeightofListView(listViewClasesviaje));
        listViewClasesviaje.getLayoutParams().height = altura+20;

        listViewClasesviaje.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewId = (TextView) view.findViewById(R.id.idClaseviaje);
                viewClaseviaje(Integer.parseInt(textViewId.getText().toString()));
            }
        });

        registerForContextMenu(listViewClasesviaje);

    }

    public void nextPage() {
        page++;
        loadClasesviaje();
        loadBotonera();
    }

    public void previousPage() {
        page--;
        loadClasesviaje();
        loadBotonera();
    }

    private void loadBotonera() {
        int pages = helper.getPages("claseviaje", 5);
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

    private void viewClaseviaje(int idClaseviaje) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ClaseviajeViewFragment claseviajeViewFragment = new ClaseviajeViewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", idClaseviaje);
        claseviajeViewFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, claseviajeViewFragment, "ClaseviajeViewFragment");
        fragmentTransaction.addToBackStack("ClaseviajeViewFragment");
        fragmentTransaction.commit();
    }

    private void deleteClaseviaje(int idClaseviaje) {
        helper.removeId("claseviaje", idClaseviaje);
        loadClasesviaje();
        loadBotonera();
    }

    private void newClaseviaje() {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ClaseviajeFragment claseviajeFragment = new ClaseviajeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("modo", "new");
        claseviajeFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment, claseviajeFragment, "ClaseviajeFragment");
        fragmentTransaction.addToBackStack("ClaseviajeFragment");
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
        int idClaseviaje = listaClaseviaje.get(index).getId();

        switch (item.getItemId()) {
            case R.id.view:
                viewClaseviaje(idClaseviaje);
                return true;
            case R.id.delete:
                deleteClaseviaje(idClaseviaje);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void showToast(String text) {
        /*Toast.makeText(this, text, Toast.LENGTH_SHORT).show();*/
    }
}
