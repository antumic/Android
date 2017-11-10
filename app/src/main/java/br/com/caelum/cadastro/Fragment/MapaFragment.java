package br.com.caelum.cadastro.Fragment;


import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import br.com.caelum.cadastro.DAO.AlunoDAO;
import br.com.caelum.cadastro.Model.Aluno;
import br.com.caelum.cadastro.Util.Localizador;


public class MapaFragment extends SupportMapFragment{

    @Override
            public void onResume () {
                super.onResume();

                AlunoDAO dao = new AlunoDAO(getContext());
                final List<Aluno> alunos = dao.getLista();
                dao.close();

                getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        Localizador localizador = new Localizador(getContext());

                        for(Aluno aluno : alunos) {


                            MarkerOptions marker = new MarkerOptions();
                            LatLng coordenada = localizador.getCoordenada(aluno.getEndereco());
                            marker.position(coordenada);
                            googleMap.addMarker(marker);



                        }



                    }
                });

            }






}
