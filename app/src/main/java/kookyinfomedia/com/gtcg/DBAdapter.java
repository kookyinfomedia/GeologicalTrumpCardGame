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



public class DBAdapter extends SQLiteOpenHelper {
    static String name = "gtcg.sqlite";
    static String path = "";
    static String country="africa";
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

    public ArrayList<ModelClass> getData()
    {

        Cursor c1 = sdb.rawQuery("select * from "+country, null);
        a = new ArrayList<ModelClass>();


        while (c1.moveToNext())
        {
            ModelClass modelClass = new ModelClass();
            String area=c1.getString(1);
            String population=c1.getString(2);
            String coastline=c1.getString(3);
            String aUnits=c1.getString(4);
            String bCountries=c1.getString(5);
            String hPoint=c1.getString(6);
            byte map[]=c1.getBlob(7);
            byte flag[]=c1.getBlob(8);


            /**************************** Splitting strings using the delimiter "space" ***********************************/

            StringTokenizer tokens = new StringTokenizer(area, " ");
            area=tokens.nextToken();

            tokens = new StringTokenizer(population, " ");
            population=tokens.nextToken();

            // if(coastline!="LANDLOCKED") {
            tokens = new StringTokenizer(coastline, " ");
            coastline = tokens.nextToken();
            //}

            tokens=new StringTokenizer(hPoint," ");
            hPoint=tokens.nextToken();

            modelClass.setCountry(country);
            modelClass.setArea(area);
            modelClass.setPopulation(population);
            modelClass.setCoastline(coastline);
            modelClass.setaUnits(aUnits);
            modelClass.setbCountries(bCountries);
            modelClass.sethPoint(hPoint);
            modelClass.setMap(map);
            modelClass.setFlag(flag);
            a.add(modelClass);
        }
        return a;
    }

}
