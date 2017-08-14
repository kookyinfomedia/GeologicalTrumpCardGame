package kookyinfomedia.com.gtcg;


/*Class for fetching data from database.*/


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

import static kookyinfomedia.com.gtcg.Category.selectedContinent;


class DBAdapter extends SQLiteOpenHelper {
    private static String name = "gtcg.sqlite";
    private static String path = "";
    private static String continent;
    static SQLiteDatabase sdb;

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    private DBAdapter(Context v)
    {
        super(v, name, null, 1);
        path = "/data/data/" + v.getApplicationContext().getPackageName()
                + "/databases";
    }

    boolean checkDatabase()
    {
        SQLiteDatabase db = null;
        try
        {
            db = SQLiteDatabase.openDatabase(path + "/" + name, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception ignored)
        {

        }
        if(db==null)
        {
            return false;
        }
        else
        {
            db.close();
            return true;
        }
    }

    static synchronized DBAdapter getDBAdapter(Context v)
    {
        return(new DBAdapter(v));
    }

    void createDatabase(Context v)
    {
        this.getReadableDatabase();
        try
        {
            InputStream myInput = v.getAssets().open(name);
            // Path to the just created empty db
            String outFileName = path +"/"+ name;
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] bytes = new byte[1024];
            int length;
            while ((length = myInput.read(bytes)) > 0)
            {
                myOutput.write(bytes, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    void openDatabase()
    {
        try
        {
            sdb = SQLiteDatabase.openDatabase(path + "/" + name, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public ArrayList<ModelClass> getData() {
        continent = selectedContinent;
        Cursor c1;
        if(Objects.equals(continent, "world"))
            c1 = sdb.rawQuery("select * from " + continent, null);
        else
         c1 = sdb.rawQuery("select * from " + continent+"_view", null);
        ArrayList<ModelClass> a = new ArrayList<>();

        try {
            while (c1.moveToNext()) {
                ModelClass modelClass = new ModelClass();
                String country = c1.getString(0);
                String capital=c1.getString(1);
                String area = c1.getString(2);
                String population = c1.getString(3);
                String coastline = c1.getString(4);
                String aUnits = c1.getString(5);
                String bCountries = c1.getString(6);
                String hPoint = c1.getString(7);
                String hPointName=c1.getString(8);
                byte flag[] = c1.getBlob(9);
                byte map[] = c1.getBlob(10);



                /*Splitting strings using the delimiter "space"*/

                StringTokenizer tokens = new StringTokenizer(hPoint, " ");
                hPoint = tokens.nextToken();

                modelClass.setCountry(country);
                modelClass.setCapital(capital);
                modelClass.setArea(area);
                modelClass.setPopulation(population);
                modelClass.setCoastline(coastline);
                modelClass.setaUnits(aUnits);
                modelClass.setbCountries(bCountries);
                modelClass.sethPoint(hPoint);
                modelClass.sethPointName(hPointName);
                modelClass.setMap(map);
                modelClass.setFlag(flag);
                a.add(modelClass);
            }
        }
        catch (Exception ignored){}
        finally {
            c1.close();
            sdb.close();
        }
            return a;
    }



    ArrayList<ModelClassIndia> getDataIndia() {
        continent = selectedContinent;
        ArrayList<ModelClassIndia> i = new ArrayList<>();

        try (Cursor c1 = sdb.rawQuery("select * from india_view", null)) {
            while (c1.moveToNext()) {
                ModelClassIndia modelClassIndia = new ModelClassIndia();
                String state = c1.getString(0);
                String districts = c1.getString(1);
                String area = c1.getString(2);
                String population = c1.getString(3);
                String national_parks = c1.getString(4);
                String river = c1.getString(5);

                String crop = c1.getString(6);

                String mineral = c1.getString(7);


                //byte map[] = c1.getBlob(31);


                modelClassIndia.setState(state);
                modelClassIndia.setArea(area);
                modelClassIndia.setPopulation(population);
                modelClassIndia.setDistricts(districts);
                modelClassIndia.setNational_parks(national_parks);

                modelClassIndia.setRiver(river);

                modelClassIndia.setCrop(crop);

                modelClassIndia.setMineral(mineral);


                //modelClass.setMap(map);
                i.add(modelClassIndia);
            }
        } catch (Exception ignored) {
        } finally {
            sdb.close();
        }
        return i;
    }

}
