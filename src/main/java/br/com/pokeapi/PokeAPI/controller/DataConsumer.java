package br.com.pokeapi.PokeAPI.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataConsumer {
    List<JSONObject> pokemonList = new ArrayList<>();

    public List<JSONObject> getData(){
        Boolean dadosNaoEstaoBaixados = !dadosBaixados();

        if(dadosNaoEstaoBaixados){
            baixarDados();
        }
        if(pokemonList.isEmpty()){
            pokemonList = getPokeAPIData();
        }
        return pokemonList;
    }
    private List<JSONObject> getPokeAPIData(){
        List<JSONObject> pokeList = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File("data.json"))){
            while(scanner.hasNextLine()){
                String linha = scanner.nextLine();

                JSONParser parse = new JSONParser();
                JSONObject json = (JSONObject) parse.parse(linha);

                pokeList.add(json);
            }
        }
        catch(IOException | ParseException ex){
        }
        return pokeList;
    }

    private Boolean dadosBaixados(){
        try(InputStream fis = new FileInputStream("data.json")){
            return true;
        }
        catch(IOException ex){
            return false;
        }
    }

    private void baixarDados(){
        try{
            OutputStream fos = new FileOutputStream("data.json");
            Writer osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            for(int index = 1; index <= 893; index++){
                URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + index);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setConnectTimeout(5000);

                Reader streamReader = null;
                if(con.getResponseCode() > 299){
                    streamReader = new InputStreamReader(con.getErrorStream());
                }
                else{
                    streamReader = new InputStreamReader(con.getInputStream());
                }
                BufferedReader bf = new BufferedReader(streamReader);
                bw.write(bf.readLine());
                bw.newLine();
                bf.close();
            }
            bw.close();
        }
        catch (IOException ex){
        }
    }
}
