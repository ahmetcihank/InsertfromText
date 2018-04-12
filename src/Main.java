import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args)
    {
        String everything ="";
       try
       {
           StringBuilder sb = new StringBuilder("jdbc:sqlserver");
           sb.append("://");
           sb.append("localhost:1433;");
           sb.append("databaseName=******");
           String url = sb.toString();
           //String url = 'jdbc:sqlserver://localhost:1433;databaseName=EDS';

           Connection myConn = DriverManager.getConnection(url,"****", "*****");
           Statement mStatement = myConn.createStatement();

        /*   StringBuilder msB = new StringBuilder("INSERT INTO PLACES");
           msB.append(" VALUES('tR', '123W', 'aDANA', 'adminname1',");
           msB.append(" 'admincode1', 'adminname2', 'admincode2', ");
           msB.append(" 'adminname3', 'admincode3', 23.2342, 23.2323, 3)");

           mStatement.execute(msB.toString());  */



          try(BufferedReader br = new BufferedReader(new InputStreamReader(
                  new FileInputStream("C:\\Users\\SOFTWARE02\\Desktop\\allCountries.txt"),"UTF8")))
          {

               String line = br.readLine();

               while (line != null) {
                   StringBuilder stringBuilder = new StringBuilder();
                   stringBuilder.append(line);
                   stringBuilder.append(System.lineSeparator());
                   line = br.readLine();
                   System.out.println(stringBuilder.toString());

                  // String[] insertStArray = new String[10];
                   //stringBuilder.toString().replace("\\r\\n","");
                    String[] mArray = new String[2];
                    mArray =stringBuilder.toString().split("\r\n");

                   String[] insertStArray=mArray[0].split("\t");
                   //insertStArray = stringBuilder.toString().split("\t");
                   System.out.println(insertStArray);

                   String sqlStr="";
                   if(insertStArray.length==12)
                   {
                    sqlStr = "INSERT INTO PLACES VALUES( ";
                   }

                   else if(insertStArray.length ==11)
                   {
                        sqlStr = "INSERT INTO PLACES(COUNTRYCODE, POSTALCODE, PLACENAME, ADMINNAME1, ADMINCODE1,ADMINNAME2, ADMINCODE2, ADMINNAME3, ADMINCODE3, LATITUDE, LONGITUDE) VALUES( ";
                   }

                   for(int i=0; i<9; i++)
                   {

                           sqlStr += "N"+"'"+insertStArray[i] +"'"+ ", "; //N for unicode Characters in database

                   }

                   sqlStr +=  insertStArray[9]+ ", ";

                   if(insertStArray.length==12) {
                       sqlStr += insertStArray[10] + ", ";

                       sqlStr += insertStArray[11] + ")";
                   }

                   else if(insertStArray.length==11){
                       sqlStr += insertStArray[10] + ")";
                   }


                   mStatement.execute(sqlStr);

               }

           }

           myConn.close();
         //  System.out.println(everything);

       }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
    }
}
