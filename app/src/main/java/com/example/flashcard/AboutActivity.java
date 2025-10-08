package com.example.flashcard;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView versionTextView = findViewById(R.id.versionTextView);

        try {
            // Get the PackageManager instance — it provides information about all installed apps
            PackageManager pm = this.getPackageManager();
            // '0' means no special flags are used
            PackageInfo pInfo = pm.getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;

            versionTextView.setText("Version : " + version);
        } catch (PackageManager.NameNotFoundException e) {
            versionTextView.setText("Version : inconnue");
            e.printStackTrace();
        }
    }
}