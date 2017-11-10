package br.com.caelum.cadastro.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.Activity.FormularioActivity;
import br.com.caelum.cadastro.Model.Aluno;


public class AlunoDAO extends SQLiteOpenHelper{
    private static final int VERSAO = 1 ;
    private static final String TABELA = "Alunos";
    private static final String DATABASE = "CadastroCaelum";

    public AlunoDAO(Context context){
        super (context, DATABASE, null, VERSAO);
    }

    public void onCreate(SQLiteDatabase database) {
        String ddl = "CREATE TABLE " + TABELA
                + " (id INTEGER PRIMARY KEY, "
                + " nome TEXT NOT NULL, "
                + " telefone TEXT, "
                + " endereco TEXT, "
                + " email TEXT, "
                + " nota REAL, "
                + "caminhoFoto TEXT)";
        database.execSQL(ddl);
    }



    public void altera(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("endereco", aluno.getEndereco());
        values.put("telefone", aluno.getTelefone());
        values.put("email", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());
        String[] idParaSerAlterado = {aluno.getId().toString() };
        getWritableDatabase().update(TABELA, values, "id=?", idParaSerAlterado);
    }



    public void onUpgrade(SQLiteDatabase database, int versaoAntiga, int versaoNova) {
        String sql = "ALTER TABLE " + TABELA + " ADD COLUMN caminhoFoto TEXT;";
        database.execSQL(sql);

    }



    public void insere(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("email", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());
        getWritableDatabase().insert(TABELA, null, values);
    }



    public void deletar(Aluno aluno){
        String[] id= {aluno.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?",id);
    }



    public List<Aluno> getLista(){
        List<Aluno> alunos = new ArrayList<Aluno>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABELA + " ORDER BY nome ASC;", null);
        while (c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setSite(c.getString(c.getColumnIndex("email")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        c.close();
        return alunos;
    }


}
