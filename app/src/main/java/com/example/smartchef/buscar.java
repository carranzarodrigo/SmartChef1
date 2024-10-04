package com.example.smartchef;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class buscar extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Thread(() -> {
            conectarABaseDeDatos();
        }).start();
    }

    // Método para realizar la conexión a la base de datos
    private void conectarABaseDeDatos() {
        DatabaseHelper databaseHelper = new DatabaseHelper();
        try (Connection connection = databaseHelper.getConnection()) {
            // Mensaje de conexión exitosa
            runOnUiThread(() -> {
                Toast.makeText(buscar.this, "Conexión exitosa a la base de datos.", Toast.LENGTH_SHORT).show();
            });

            String query = "SELECT * FROM comidas";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                StringBuilder resultado = new StringBuilder();

                while (resultSet.next()) {
                    // Procesar y obtener los valores de las columnas de la tabla
                    String receta = resultSet.getString("recetas");
                    String ingredientes = resultSet.getString("ingredientes");
                    String preparacion = resultSet.getString("preparacion");

                    resultado.append("Receta: ").append(receta).append("\n")
                            .append("Ingredientes: ").append(ingredientes).append("\n")
                            .append("Preparación: ").append(preparacion).append("\n")
                            .append("-------------------------\n");
                }

                // Actualizar la UI en el hilo principal (UI Thread)
                runOnUiThread(() -> {
                    // Mostrar los datos en un Toast
                    Toast.makeText(buscar.this, resultado.toString(), Toast.LENGTH_LONG).show();
                });

            } catch (SQLException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(buscar.this, "Error al ejecutar la consulta.", Toast.LENGTH_LONG).show();
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                Toast.makeText(buscar.this, "Error al conectar a la base de datos.", Toast.LENGTH_LONG).show();
            });
        }
    }
}

