package me.astero.asterohealth.xmlparser;


import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import me.astero.asterohealth.R;
import me.astero.asterohealth.database.HealthDatabase;
import me.astero.asterohealth.database.objects.NotesData;

public class XMLParser {


    public static void createFirstTime(InputStream in, String category) throws IOException {


        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        FileOutputStream fileOutputStream = new FileOutputStream(new File("/sdcard/Download/notes.xml"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;







        while ((line = reader.readLine()) != null) {



            bw.write(line + "\n");

        }

        bw.close();
        reader.close();

    }

    public static void edit(String location, Context context, String category) throws IOException {



        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(location));

        File file = new File(location);
        file.delete();



        FileOutputStream fileOutputStream = new FileOutputStream(new File(location));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;






        while ((line = reader.readLine()) != null) {

            System.out.println(line + " XMLTEST");

            if (line.contains("</" + category + ">")) {

                bw.write("  <entry>\n" +
                        "   <title> test123 </title>\n" +
                        "   <description> testing </description>\n" +
                        "   <date>23131</date>\n"

                + "</entry>\n");

            }

            builder.append(line);
            bw.write(line + "\n");

        }
        reader.close();
        bw.close();


    }

    public static void parse(String location, String category, Context context) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(location));

        String line;

        NotesData notesData = null;

        boolean containsCategory = false, readEntry = false;

        while ((line = reader.readLine()) != null) {




            if(line.contains("<" + category + ">"))
            {
               containsCategory = true;
            }
            else if(line.contains("</" + category + ">"))
            {
                break;
            }

            else if(line.contains("<entry>"))
            {
                readEntry = true;
                notesData = new NotesData();

            }
            else if(line.contains("</entry>"))
            {
                readEntry = false;


                NotesData finalNotesData = notesData;

                HealthDatabase.getInstance(context).healthDao().insert(finalNotesData);




                notesData = null;
                // save into dao
            }



            if(readEntry && containsCategory) {


                String refinedLine = line.trim().replace("<entry>", "")
                        .replace("</entry>", "")
                        .replace("</notes>", "");

                if(refinedLine.length() > 0) {

                    String identifier = refinedLine.substring(refinedLine.indexOf('<') + 1,
                            refinedLine.indexOf('>'));

                    String value = refinedLine.replace("<" + identifier + ">", "")
                            .replace("</" + identifier + ">", "");


                    if(identifier.equals("title"))
                    {
                        notesData.title = value;
                    }
                    else if(identifier.equals("description"))
                    {
                        notesData.description = value;
                    }
                    else if(identifier.equals("date"))
                    {
                        notesData.date = value;
                    }

                    System.out.println(value + "XML");
                }

            }
        }
        reader.close();
    }


}
