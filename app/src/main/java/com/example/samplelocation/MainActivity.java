package com.example.samplelocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationService();
            }
        });
    }

    // 위치관리자 객체 참조
    private void startLocationService() {

        // 위치관리자는 시스템 서비스이므로 getSystemService()메소드로 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 최근 위치 정보를 확인하기 위해 getLastKnownLocation() 사용
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String msg = "Last Known Location -> Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude();
        Log.i("SampleLocation", msg);

        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;  // 메소드를 호출하기위한 최소 시간
        float minDistance = 0;  // 메소드를 호출하기위한 최소 거리

        // 일정 시간과 일정 거리 이상 이동시 위치 정보 전달
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

        textView.setText("내 위치 : " + location.getLatitude() + ", " + location.getLongitude());
    }

    // 위치 관리자가 위치 정보를 전달할 때 호출
    // 위치 정보를 받아 처리하기 위해서는 이 리스너의 onLocationChanged() 메소드 구현
    private class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : " + latitude + "\nLongitude : " + longitude;
            Log.i("GPSListener", msg);

            textView.setText("내 위치 : " + latitude + ", " + longitude);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
