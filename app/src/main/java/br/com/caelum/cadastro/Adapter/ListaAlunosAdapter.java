package br.com.caelum.cadastro.Adapter;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.caelum.cadastro.Model.Aluno;
import br.com.caelum.cadastro.R;

public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos) {
        this.activity = activity;
        this.alunos = alunos;
    }


    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int pos) {
        return alunos.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return alunos.get(pos).getId();
    }


    @Override
    public View getView(int pos, View view,ViewGroup parent) {

        View item;

        if (view == null){
            LayoutInflater inflate = activity.getLayoutInflater();
            item = inflate.inflate(R.layout.listacomfoto,parent,false);
        } else {
            item = view;
        }

        Aluno aluno = (Aluno) getItem(pos);
        TextView nome = (TextView) item.findViewById(R.id.item_nome);

        nome.setText(aluno.getNome());

        Bitmap bm;

        if (aluno.getCaminhoFoto() != null) {
            bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.person);
        }

        bm = Bitmap.createScaledBitmap(bm, 100, 100, true);

        ImageView imageView = (ImageView) item.findViewById(R.id.item_foto);
        imageView.setImageBitmap(bm);
        imageView.setTag(aluno.getCaminhoFoto());


        return item;
    }
}
