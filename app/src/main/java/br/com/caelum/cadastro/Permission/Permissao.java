package br.com.caelum.cadastro.Permission;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;


public class Permissao {
    private static final int CODE = 123;
    private static ArrayList<String> listaPermissoes = new ArrayList<>();

    public static void fazPermissao(Activity activity) {

        String[] permissoes = {Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS, Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            for (String permissao: permissoes) {
                if (ActivityCompat.checkSelfPermission(activity, permissao) != PackageManager.PERMISSION_GRANTED){
                    listaPermissoes.add(permissao);
                }
            }
            request(activity);
        }
    }

    private static void request(Activity activity) {
        String[] array = listaPermissoes.toArray(new String[]{});

        if (listaPermissoes.size() > 0) {
            ActivityCompat.requestPermissions(activity, array, CODE);
        }
    }


}
