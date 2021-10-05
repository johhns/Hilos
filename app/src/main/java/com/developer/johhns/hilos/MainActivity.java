package com.developer.johhns.hilos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developer.johhns.hilos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    Button boton;
    EditText entrada;
    TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrada = findViewById(R.id.entrada);
        salida = findViewById(R.id.salida);
        boton = findViewById(R.id.btnCalcular);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcular();
            }
        });

    }

    public void calcular() {
        Long n = Long.parseLong(entrada.getText().toString());
        salida.append(n + "! == ");

        /* VERSION 1 */
        //int resultado = factorial(n);
        //salida.append(resultado + "\n");

        /* VERSION 2 */
        //MiThread hilo = new MiThread(n);
        //hilo.start();

        /* VERSION 3 */
        MiTarea tarea = new MiTarea();
        tarea.execute( n ) ;

    }

    public Long factorial(Long n) {
        Long resultado = Long.parseLong( "1" );
        for (int i = 1; i <= n; i++) {
            resultado *= i;
            //SystemClock.sleep(1000);
        }
        return resultado;
    }

    class MiThread extends Thread {
        private Long n, resultado;

        public MiThread(Long n) {
            this.n = n;
        }

        @Override
        public void run() {
            resultado = factorial(n);
            ///salida.append( resultado + " ** \n" );
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    salida.append(resultado + " \n");
                }
            });
        }
    }

    /// ESTA CLASE ESTA OBSOLETA
    class MiTarea extends AsyncTask<Long,Long,Long> {

        @Override
        protected Long doInBackground(Long[] n) {
            Log.d("BACKGROUND","EJECUTANDO PROCESO PRINCIPAL") ;
            Long f = factorial(n[0]) ;
            Log.d("BACKGROUND","el factoria calculado es : " + f) ;
            return f ;
        }

        //@Override
       protected void onProgressUpdate(Long x) {
            //super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(Long res){
            Log.d("POST-EXECUTE","ACTUALIZANDO EL TEXT VIEW") ;
           salida.append( res + "\n");
        }

    }


}
























