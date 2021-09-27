package ru.SuperCoon666;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Main
{
    public static PrintStream out = System.out;


    public static boolean query_date(String u)
    {
        boolean flag = false; // переменная для отслеживания состояния трансляции

        // обработчик ошибок
        try
        {
            // парсим страничку с трансляцией
            URL url = new URL(u);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
            connection.setRequestProperty("Accept-language", "en-US,en;q=0.5");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            // проверяем состояние трансляции
            int i = 0;
            while ((inputLine = in.readLine()) != null)
            {
                if ((inputLine.contains("isLiveNow\":true,")))
                {
                    flag = true;
                    break;
                }
                i++;
                if (i == 700) {break;}
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return flag; // возвращаем состояние переменной
    }

    public static void main(String[] args)
    {
        String preset = "https://www.youtube.com/watch?v="; // заготовка для ссылок
        ArrayList<String> id_streams = new ArrayList<String>(); // список ссылок

        // обработчик ошибок
        try
        {
            String way = "C:\\Users\\Vlad\\Desktop\\Data_Streams.txt"; // тут нужно указать путь к файлу txt
            File file = new File(way); // создаем файл для чтения
            FileReader fr = new FileReader(file);

            BufferedReader reader = new BufferedReader(fr); // создаем BufferedReader для считывания

            String line = reader.readLine(); // считаем первую строку

            while (line != null) // поочередно считываем каждую строку в список
            {
                id_streams.add(line);
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < id_streams.size(); i++) // поочерёдно проверяем каждую ссылку
        {
            boolean res = query_date(preset+id_streams.get(i)) ; // вызываем функцию-обработчик
            out.println(i+1 + ". " + id_streams.get(i) + " : " + res); // выводим результат проверки ссылки с индексом i
        }
    }
}