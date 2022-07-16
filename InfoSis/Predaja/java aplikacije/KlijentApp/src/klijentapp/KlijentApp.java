/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.font.CreatedFontTracker;

/**
 *
 * @author Bogdan
 */
public class KlijentApp {

    /**
     * @param args the command line arguments
     */
    //fali poslednja dva
    public enum JOB {
        CreateMesto, CreateFilijala, CreateKomitent, PromeniSediste,
        OtvoriRacun, ZatvoriRacun, TransakcijaSaRacunaNaRacun, TransakcijaUplata, TransakcijaIsplata,
        DohvatiSvaMesta, DohvatiSveFilijale, DohvatiSveKomitente,
        DohvatiSveRacune, DohvatiSveTransakcije, DohvatiBackup, GetRazlike
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int in = 0;
        while (true) {
            System.out.println("Pick operation");
            System.out.println("1. Kreiranje mesta");
            System.out.println("2. Kreiranje filijale u mestu");
            System.out.println("3. Kreiranje komitenta");
            System.out.println("4. Promena sedista za zadatkog komitenta");
            System.out.println("5. Otvaranje racuna");
            System.out.println("6. Zatvaranje racuna");
            System.out.println("7. Kreiranje transakcije koja je prenos sume sa jednog racuna na drugi");
            System.out.println("8. Kreiranje transakcije koja je uplata novca na racun");
            System.out.println("9. Kreiranje transakcije koja je isplatna novca sa raunca");
            System.out.println("10. Dohvatanje svih mesta");
            System.out.println("11. Dohvatanje svih filijala");
            System.out.println("12. Dohvatanje svih komitenta");
            System.out.println("13. Dohvatanje svih racuna za komitenta");
            System.out.println("14. Dohvatanje svih transakcija za racun");
            System.out.println("15. Dohvatanje svih podataka iz rezervne kopije");
            System.out.println("16. Dohvatanje razlike u podacima u originalnim podacima i u rezernoj kopiji");
            System.out.println("17. Test");

            try {
                in = scan.nextInt();
            } catch (Exception e) {
                System.out.println("Pogresan unos");
                scan.next();
                continue;
            }

            if (in < 1 || in > 17) {
                System.out.println("Pogresno unet broj");
                continue;
            }

            String base_uri = "http://localhost:8080/centralniserver/resources/javaee8/";
            String ret;

            if (in == 17) {
                ret = Test(base_uri);
                printString(ret);
                continue;
            }

            switch (JOB.values()[in - 1]) {
                case CreateMesto:
                    ret = CreateMesto(base_uri);
                    printString(ret);
                    break;
                case CreateFilijala:
                    //Proveri da li su dobri brojevi kod klijentske Strane           
                    ret = CreateFilijala(base_uri);
                    printString(ret);
                    break;
                case CreateKomitent:
                    //Proveri da li su dobri brojevi kod klijentske Strane             
                    ret = CreateKomitent(base_uri);
                    printString(ret);
                    break;
                case PromeniSediste:
                    //Proveri da li su dobri brojevi kod klijentske Strane               
                    ret = PromeniSediste(base_uri);
                    printString(ret);
                    break;
                case DohvatiSvaMesta:
                    ret = DohvatiSvaMesta(base_uri);
                    printString(ret);

                    break;
                case DohvatiSveFilijale:
                    ret = DohvatiSveFilijale(base_uri);
                    printString(ret);
                    break;
                case DohvatiSveKomitente:
                    ret = DohvatiSveKomitente(base_uri);
                    printString(ret);
                    break;
                case OtvoriRacun:
                    ret = OtvoriRacun(base_uri);
                    printString(ret);
                    break;
                case ZatvoriRacun:
                    //Proveri da li su dobri brojevi kod klijentske Strane
                    ret = ZatvoriRacun(base_uri);
                    printString(ret);
                    break;
                case TransakcijaSaRacunaNaRacun:
                    //Proveri da li su dobri brojevi kod klijentske Strane
                    ret = TransakcijaSaRacunaNaRacun(base_uri);
                    printString(ret);
                    break;
                case TransakcijaUplata:
                    //Proveri da li su dobri brojevi kod klijentske Strane
                    ret = TransakcijaUplata(base_uri);
                    printString(ret);
                    break;
                case TransakcijaIsplata:
                    ret = TransakcijaIsplata(base_uri);
                    printString(ret);
                    break;
                case DohvatiSveRacune:
                    //Proveri da li su dobri brojevi kod klijentske Strane
                    ret = DohvatiSveRacune(base_uri);
                    printString(ret);
                    break;
                case DohvatiSveTransakcije:
                    //Proveri da li su dobri brojevi kod klijentske Strane
                    ret = DohvatiSveTransakcije(base_uri);
                    printString(ret);
                    break;
                case DohvatiBackup:
                    ret = DohvatiBackup(base_uri);
                    printString(ret);
                    break;
                case GetRazlike:
                    ret = GetRazlike(base_uri);
                    printString(ret);
                    break;
            }

            String nextLine = scan.nextLine();
            nextLine = scan.nextLine();
        }

    }

    private static String Test(String base) {
        String[] paramNames = {
            "TempString",
            "TempInt"
        };

        return requestHelper(base, "TestPost",
                paramNames, "GET");

    }

    private static String CreateMesto(String base) {

        String[] paramNames = {
            "Naziv",
            "PostanskiBroj"
        };

        return requestHelper(base, "KreirajMesto",
                paramNames, "GET");

    }

    private static String CreateFilijala(String base) {

        String[] paramNames = {
            "Naziv",
            "Adresa",
            "IdMesto"
        };

        return requestHelper(base, "KreirajFilijalu",
                paramNames, "GET");

    }

    private static String CreateKomitent(String base) {

        String[] paramNames = {
            "Naziv",
            "Adresa",
            "Sediste"
        };

        return requestHelper(base, "kreirajKomitenta",
                paramNames, "GET");

    }

    private static String PromeniSediste(String base) {

        String[] paramNames = {
            "Sediste",
            "Komitent"
        };

        return requestHelper(base, "PromeniSediste",
                paramNames, "GET");

    }

    private static String DohvatiSvaMesta(String base) {

        String[] paramNames = {};

        return requestHelper(base, "DohvatiSvaMesta",
                paramNames, "GET");

    }

    private static String DohvatiSveFilijale(String base) {

        String[] paramNames = {};

        return requestHelper(base, "DohvatiSveFilijale",
                paramNames, "GET");

    }

    private static String DohvatiSveKomitente(String base) {

        String[] paramNames = {};

        return requestHelper(base, "DohvatiSveKomitente",
                paramNames, "GET");

    }

    private static String OtvoriRacun(String base) {

        String[] paramNames = {
            "Komitent"
        };

        return requestHelper(base, "OtvoriRacun",
                paramNames, "GET");

    }

    private static String ZatvoriRacun(String base) {

        String[] paramNames = {
            "Racun"
        };

        return requestHelper(base, "ZatvoriRacun",
                paramNames, "GET");

    }

    private static String TransakcijaSaRacunaNaRacun(String base) {

        String[] paramNames = {
            "RacunOD",
            "RacunDO",
            "SUM",
            "Svrha"
        };

        return requestHelper(base, "TransakcijaSaRacunaNaRacun",
                paramNames, "GET");

    }

    private static String TransakcijaUplata(String base) {

        String[] paramNames = {
            "Racun",
            "SUM",
            "Svrha",
            "Filijala"
        };

        return requestHelper(base, "TransakcijaUplata",
                paramNames, "GET");

    }

    private static String TransakcijaIsplata(String base) {

        String[] paramNames = {
            "Racun",
            "SUM",
            "Svrha",
            "Filijala"
        };

        return requestHelper(base, "TransakcijaIsplata",
                paramNames, "GET");

    }

    private static String DohvatiSveRacune(String base) {

        String[] paramNames = {
            "Komitent"
        };

        return requestHelper(base, "DohvatiSveRacune",
                paramNames, "GET");

    }

    private static String DohvatiSveTransakcije(String base) {

        String[] paramNames = {
            "Racun"
        };

        return requestHelper(base, "DohvatiSveTransakcije",
                paramNames, "GET");

    }

    private static String DohvatiBackup(String base) {
        String[] paramNames = {};

        return requestHelper(base, "DohvatiBackup",
                paramNames, "GET");
    }

    private static String GetRazlike(String base) {
        String[] paramNames = {};

        return requestHelper(base, "GetRazlike",
                paramNames, "GET");
    }

    private static String requestHelper(String base, String endpoint, String[] paramNames, String TypeRequest) {
        Scanner scan = new Scanner(System.in);
        Map<String, String> params = new HashMap<>();
        String temp_string = null;
        for (String paramName : paramNames) {
            System.out.println("Unesite " + paramName);
            temp_string = scan.nextLine();
            if (temp_string.equals("")) {
                return "Morate uneti sve podatke";
            }
            params.put(paramName, temp_string);
        }

        /*
        System.out.println("Unesite Naziv");
        String naziv = scan.nextLine();

        System.out.println("Unesite Postanski Broj");
        String PB = scan.nextLine();

        
        params.put("Naziv", naziv);
        params.put("PostanskiBroj", PB);
         */
        String paramString = SetParams(params);
        byte[] requestBytes = null;
        URL uri = null;
        HttpURLConnection connection = null;
        try {
            requestBytes = paramString.getBytes("UTF-8");
            uri = new URL(base + endpoint + "?" + paramString);
            connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod(TypeRequest);
            if (TypeRequest.equals("POST")) {
                connection.setDoOutput(true);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(KlijentApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(KlijentApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KlijentApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        String res = sendRequest(requestBytes, connection);

        if (connection != null) {
            connection.disconnect();
        }

        return res;

    }

    private static String SetParams(Map<String, String> paramMap) {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (sb.length() != 0) {
                sb.append('&');
            }
            try {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(KlijentApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sb.toString();
    }

    private static String sendRequest(byte[] requestBytes, HttpURLConnection connection) {
        DataOutputStream wr = null;
        try {
            if (false) {
                connection.setDoOutput(true);
                connection.setDoInput(true);
                wr = new DataOutputStream(connection.getOutputStream());

                wr.write(requestBytes);
            }

            StringBuilder responseBuilder;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {

                String line = null;

                responseBuilder = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    responseBuilder.append(line);
                    //responseBuilder.append(System.lineSeparator());
                }

            }
            if (wr != null) {
                wr.close();
            }
            return responseBuilder.toString();
        } catch (IOException ex) {
            //Logger.getLogger(KlijentApp.class.getName()).log(Level.SEVERE, null, ex);
            
        }

        return "Pogresno uneti podaci/InterniError";
    }

    private static void printString(String s) {
        String[] temp = s.split(";-");

        for (String string : temp) {
            System.out.println(string);
        }

    }

}
