package com.beautysalon.Inteface;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuotationService
{
    public String getCotacaoDolar()
    {
    String url = "https://economia.awesomeapi.com.br/json/last/USD-BRL";

        try {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod("GET");

    int responseCode = con.getResponseCode();
    if (responseCode != 200) {
        return "Erro ao buscar cotação: " + responseCode;
    }

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();

    while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
    }
    in.close();

    JSONObject json = new JSONObject(content.toString());
    JSONObject usdbrl = json.getJSONObject("USDBRL");

    return "Cotação atual do dólar: R$ " + usdbrl.getString("bid");

} catch (Exception e) {
    e.printStackTrace();
    return "Erro ao buscar cotação";
}
}
}
