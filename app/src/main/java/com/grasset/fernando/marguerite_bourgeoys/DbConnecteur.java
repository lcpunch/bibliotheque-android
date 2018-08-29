package com.grasset.fernando.marguerite_bourgeoys;

import android.os.Debug;
import android.text.format.DateUtils;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DbConnecteur {
    public void testDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM biblioteca.user;");

            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getString(1) + "\n";
            }
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
    }

    public boolean validateLogin(String IDUSER, String PASSWORD) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2", "Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT AES_DECRYPT(MD5PASSWORD,"+PASSWORD+") AS PW  FROM biblioteca.user WHERE IDUSER = '"+IDUSER+"';");
            System.out.println();
            if (( rs.next() )&& (PASSWORD.equals(rs.getString("PW")))){
                return true;
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(String SURNAME, String USERNAME,String IDUSERTYPE, String IDUSER, String MD5PASSWORD, String ADDRESS, String PHONE, String EMAIL) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            st.executeUpdate(("INSERT INTO biblioteca.user " +
                    "VALUES ('"+ SURNAME +"', '"+ USERNAME +"','"+IDUSERTYPE+"', '"+IDUSER+"', AES_ENCRYPT('"+MD5PASSWORD+"','"+MD5PASSWORD+"'), '"+ADDRESS+"','"+PHONE+"', '"+ EMAIL +"');"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
    }

    public ArrayList<Book> getBooksInformationsRechercher(String data, String researchType) {
        ArrayList<Book> myBooks = new ArrayList<Book>();
        String query = "";

        System.out.println("RESEARCH TYPE : " + researchType);

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            if (researchType.equals("Title")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.TITLE = \""+data+"\" AND RAREEDITION = 0;";
            } else if (researchType.equals("L'auteur")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.AUTHOR = \""+data+"\" AND RAREEDITION = 0;";
            } else if (researchType.equals("Année")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.EDITIONYEAR = \""+data+"\" AND RAREEDITION = 0;";
            } else if (researchType.equals("Maison d'edition")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.EDITOR = \""+data+"\" AND RAREEDITION = 0;";
            }

            ResultSet rs = st.executeQuery(query);

           ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                String title = rs.getString("TITLE");
                String auteur = rs.getString("AUTHOR");
                String year = rs.getString("EDITIONYEAR");

                Book newBook = new Book(title,auteur,year);

                myBooks.add(newBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
        return myBooks;
    }

    public ArrayList<Book> getBooksInformationsRDV(String data, String researchType) {
        ArrayList<Book> myBooks = new ArrayList<Book>();
        String query = "";

        System.out.println("RESEARCH TYPE : " + researchType);

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            if (researchType.equals("Title")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.TITLE = \""+data+"\" AND RAREEDITION = 1;";
            } else if (researchType.equals("L'auteur")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.AUTHOR = \""+data+"\" AND RAREEDITION = 1;";
            } else if (researchType.equals("Année")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.EDITIONYEAR = \""+data+"\" AND RAREEDITION = 1;";
            } else if (researchType.equals("Maison d'edition")) {
                query = "SELECT bookedition.TITLE,bookedition.AUTHOR,bookedition.EDITIONYEAR FROM biblioteca.bookedition\n" +
                        "WHERE bookedition.EDITOR = \""+data+"\" AND RAREEDITION = 1;";
            }

            ResultSet rs = st.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                String title = rs.getString("TITLE");
                String auteur = rs.getString("AUTHOR");
                String year = rs.getString("EDITIONYEAR");

                Book newBook = new Book(title,auteur,year);

                myBooks.add(newBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
        return myBooks;
    }

    public ArrayList<Book> getMyBooks (String USERNAME) {
        ArrayList<Book> myBooks = new ArrayList<Book>();

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT TITLE,AUTHOR,EDITIONYEAR,DTRETURN FROM biblioteca.loanlist " +
                    "INNER JOIN biblioteca.bookcopy ON bookcopy.IDCOPY = loanlist.IDCOPY " +
                    "INNER JOIN biblioteca.bookedition ON bookedition.ISBN = bookcopy.ISBN " +
                    "WHERE IDUSER='"+USERNAME+"' AND RAREEDITION=0;");

            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                String title = rs.getString("TITLE");
                String auteur = rs.getString("AUTHOR");
                String year = rs.getString("EDITIONYEAR");
                String dtreturn = rs.getString("DTRETURN");

                Book newBook = new Book(title,auteur,year,dtreturn);

                myBooks.add(newBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
        return myBooks;
    }

    public ArrayList<Book> getMyLivreRareRDV (String USERNAME) {
        ArrayList<Book> myBooks = new ArrayList<Book>();

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT TITLE,AUTHOR,EDITIONYEAR,DTAPPOINTMENT FROM biblioteca.appointmentlist\n" +
                    "INNER JOIN biblioteca.bookcopy ON bookcopy.IDCOPY = appointmentlist.IDCOPY\n" +
                    "INNER JOIN biblioteca.bookedition ON bookedition.ISBN = bookcopy.ISBN\n" +
                    "WHERE IDUSER = '"+USERNAME+"';");

            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                String title = rs.getString("TITLE");
                String auteur = rs.getString("AUTHOR");
                String year = rs.getString("EDITIONYEAR");
                String dtappointment = rs.getString("DTAPPOINTMENT");

                Book newBook = new Book(title,auteur,year,dtappointment);

                myBooks.add(newBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
        return myBooks;
    }

    public void addBookToUser(String USERNAME, String BookName) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT IDCOPY from bookedition\n" +
                    "INNER JOIN bookcopy ON bookcopy.ISBN = bookedition.ISBN\n" +
                    "WHERE IDCOPY NOT IN (select IDCOPY from loanlist) AND TITLE='"+BookName+"';");

            if (rs.next()) {
                st.executeUpdate("INSERT INTO loanlist values ('"+ USERNAME +"','"+rs.getString("IDCOPY")+"', DATE_ADD(now(), INTERVAL 10 DAY) , now());");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
    }

    public void RDVtoUser(String USERNAME, String BookName, String DateAppointement, Integer Periode) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * from bookedition\n" +
                    "INNER JOIN bookcopy ON bookcopy.ISBN = bookedition.ISBN\n" +
                    "WHERE IDCOPY NOT IN (SELECT IDCOPY from appointmentlist) AND TITLE = '"+BookName+"'; ");

            if (rs.next()) {
                st.executeUpdate("INSERT INTO appointmentlist values ('"+ USERNAME +"','"+rs.getString("IDCOPY")+"', '"+DateAppointement+"', '"+Periode+"');");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
    }
    public void CancelRDV(String USERNAME, String BookName, String DateAppointement) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT appointmentlist.IDCOPY from appointmentlist\n" +
                    "INNER JOIN bookcopy ON bookcopy.IDCOPY = appointmentlist.IDCOPY\n" +
                    "INNER JOIN bookedition ON bookedition.ISBN = bookcopy.ISBN\n" +
                    "WHERE IDUSER='"+USERNAME+"' AND DTAPPOINTMENT='"+DateAppointement+"' AND TITLE='"+BookName+"';");

            if (rs.next()) {
                st.executeUpdate("DELETE FROM `biblioteca`.`appointmentlist` WHERE `IDUSER`='"+USERNAME+"' and`IDCOPY`='"+rs.getString("IDCOPY")+"' and`DTAPPOINTMENT`='"+DateAppointement+"';");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
    }
    public void RenewBook(String USERNAME, String BookName, String DateReturn) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT loanlist.IDCOPY from loanlist\n" +
                    "INNER JOIN bookcopy ON bookcopy.IDCOPY = loanlist.IDCOPY\n" +
                    "INNER JOIN bookedition ON bookedition.ISBN = bookcopy.ISBN\n" +
                    "WHERE IDUSER='"+USERNAME+"' AND DTRETURN='"+DateReturn+"' AND TITLE='"+BookName+"';");


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 10);

            if (rs.next()) {
                st.executeUpdate("UPDATE biblioteca.loanlist SET DTRETURN='"+dateFormat.format(c.getTime())+"' WHERE IDUSER='"+USERNAME+"' and`IDCOPY`='"+rs.getString("IDCOPY")+"';");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
    }

    public ArrayList<String> getUserInformation (String USERNAME) {
        ArrayList myInformation = new ArrayList<String>();

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT ADDRESS,PHONE,EMAIL FROM user " +
                    "WHERE IDUSER = '"+USERNAME+"';");

            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                String address = rs.getString("ADDRESS");
                myInformation.add(address);
                String phone = rs.getString("PHONE");
                myInformation.add(phone);
                String email = rs.getString("EMAIL");
                myInformation.add(email);


            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
        return myInformation;
    }

    public void UpdateUserInfo(ArrayList<String> myInfo, String IDUSER) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Log.i("Android", " MySQL Connection ok");
            Connection con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/biblioteca", "root", "7xpw2123lol123@mySQL");
            //         System.out.println("Database connection success");
            Log.d("Android2","Line 2");
            String result = ("");
            Log.d("Android3", " Line 3");
            Statement st = con.createStatement();

            st.executeUpdate(("UPDATE user\n" +
                    "SET ADDRESS = '"+myInfo.get(0)+"', PHONE= '"+myInfo.get(1)+"', EMAIL = '"+myInfo.get(2)+"'\n" +
                    "WHERE IDUSER = '"+IDUSER+"';"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Android-system","system get connection");
        }
    }

}