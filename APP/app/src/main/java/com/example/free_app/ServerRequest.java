package com.example.free_app;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ServerRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://20.194.103.81/Recommend.php";
    private Map<String, String> map;


    public ServerRequest(Double Carbon, Double Score, Double Price, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("Carbon", Carbon.toString());
        map.put("Score", Score.toString());
        map.put("Price", Price.toString());
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

