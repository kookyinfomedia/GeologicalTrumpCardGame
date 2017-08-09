package kookyinfomedia.com.gtcg;


/********************************** Class for fetching data from database. *****************************************************/


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static kookyinfomedia.com.gtcg.Category.selectedContinent;


public class DBAdapter extends SQLiteOpenHelper {
    static String name = "gtcg.sqlite";
    static String path = "";
    static String continent;
    static ArrayList<ModelClassIndia> i;
    static ArrayList<ModelClassWorld> w;
    static ArrayList<ModelClass> a;
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

    public boolean checkDatabase()
    {
        SQLiteDatabase db = null;
        try
        {
            db = SQLiteDatabase.openDatabase(path + "/" + name, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        catch (Exception e)
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

    public static synchronized DBAdapter getDBAdapter(Context v)
    {
        return(new DBAdapter(v));
    }

    public void createDatabase(Context v)
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

    public void openDatabase()
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
        Cursor c1 = sdb.rawQuery("select * from " + continent+"_view", null);
        a = new ArrayList<ModelClass>();

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



                /**************************** Splitting strings using the delimiter "space" ***********************************/

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
        catch (Exception e){}
        finally {
            c1.close();
            sdb.close();
        }
            return a;
    }



    public ArrayList<ModelClassIndia> getDataIndia() {
        continent = selectedContinent;
        Cursor c1 = sdb.rawQuery("select * from " + continent, null);
        i = new ArrayList<ModelClassIndia>();

        try {
            while (c1.moveToNext()) {
                ModelClassIndia modelClassIndia = new ModelClassIndia();
                String state = c1.getString(0);
                String districts=c1.getString(1);
                String area = c1.getString(2);
                String population = c1.getString(3);
                String national_parks = c1.getString(4);
                String river1 = c1.getString(5);
                String river2 = c1.getString(6);
                String river3 = c1.getString(7);
                String river4 = c1.getString(8);

                String crop1 = c1.getString(9);
                String crop2 = c1.getString(10);
                String crop3 = c1.getString(11);
                String crop4 = c1.getString(12);
                String crop5 = c1.getString(13);
                String crop6 = c1.getString(14);
                String crop7 = c1.getString(15);
                String crop8 = c1.getString(16);
                String crop9 = c1.getString(17);
                String crop10 = c1.getString(18);
                String crop11 = c1.getString(19);

                String mineral1 = c1.getString(20);
                String mineral2 = c1.getString(21);
                String mineral3 = c1.getString(22);
                String mineral4 = c1.getString(23);
                String mineral5 = c1.getString(24);
                String mineral6 = c1.getString(25);
                String mineral7 = c1.getString(26);
                String mineral8 = c1.getString(27);
                String mineral9 = c1.getString(28);
                String mineral10 = c1.getString(29);
                String mineral11 = c1.getString(30);


                //byte map[] = c1.getBlob(31);


                modelClassIndia.setState(state);
                modelClassIndia.setArea(area);
                modelClassIndia.setPopulation(population);
                modelClassIndia.setDistricts(districts);
                modelClassIndia.setNational_parks(national_parks);

                modelClassIndia.setRiver1(river1);
                modelClassIndia.setRiver2(river2);
                modelClassIndia.setRiver3(river3);
                modelClassIndia.setRiver4(river4);

                modelClassIndia.setCrop1(crop1);
                modelClassIndia.setCrop2(crop2);
                modelClassIndia.setCrop3(crop3);
                modelClassIndia.setCrop4(crop4);
                modelClassIndia.setCrop5(crop5);
                modelClassIndia.setCrop6(crop6);
                modelClassIndia.setCrop7(crop7);
                modelClassIndia.setCrop8(crop8);
                modelClassIndia.setCrop9(crop9);
                modelClassIndia.setCrop10(crop10);
                modelClassIndia.setCrop11(crop11);

                modelClassIndia.setMineral1(mineral1);
                modelClassIndia.setMineral2(mineral2);
                modelClassIndia.setMineral3(mineral3);
                modelClassIndia.setMineral4(mineral4);
                modelClassIndia.setMineral5(mineral5);
                modelClassIndia.setMineral6(mineral6);
                modelClassIndia.setMineral7(mineral7);
                modelClassIndia.setMineral8(mineral8);
                modelClassIndia.setMineral9(mineral9);
                modelClassIndia.setMineral10(mineral10);
                modelClassIndia.setMineral11(mineral11);


                //modelClass.setMap(map);
                i.add(modelClassIndia);
            }
        }
        catch (Exception e){}
        finally {
            c1.close();
            sdb.close();
        }
        return i;
    }


    public ArrayList<ModelClassWorld> getDataWorld() {
        continent = selectedContinent;
        Cursor c1 = sdb.rawQuery("select * from " + continent, null);
        w = new ArrayList<ModelClassWorld>();

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



                /**************************** Splitting strings using the delimiter "space" ***********************************/

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
        catch (Exception e){}
        finally {
            c1.close();
            sdb.close();
        }
        return w;
    }


}
